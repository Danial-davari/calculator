package sample;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mypc on 3/25/2017.
 */
public class MathUtils {
    public static final char LEFT_BRACE  = '(';
    public static final char RIGHT_BRACE = ')';
    public static final String ERROR_UNMATCHED_BRACES = "left braces must match right braces";

    public static final char[] OPERATORS = new char[]{'*', '/', '+', '-'};
    public static final char[] BRACES = new char[]{LEFT_BRACE, RIGHT_BRACE};
    public static final char DOT = '.';
    public static final char SPACE = ' ';
    public static final String ZERO = "0";

    // (3 * 45.000 000 + (8*6) + 7)
    // (65 + (5 * 3)) - 83
    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static ScriptEngine javaScriptEngine = scriptEngineManager.getEngineByName("JavaScript");
    private static DecimalFormat format = new DecimalFormat("0.######");
    public static String evaluate(String text) throws ScriptException {
        Object object = javaScriptEngine.eval(text);
        System.out.println("Type "+object.getClass());
        String result = object.toString();
        System.out.println("Before Trimming   " + result);
        if(object instanceof Double){
            double doubleValue = Double.parseDouble(result);
            result = format.format(doubleValue);
        }
        System.out.println("After Trimming    " + result);
        return result;
    }
    public static double evaluateOld(String text){
        for (int i = 0; i < OPERATORS.length; i++) {
            String aChar = OPERATORS[i] + "";
            text = text.replace(aChar, (" " + aChar + " "));
        }
        text = text.trim().replaceAll("\\s+", " ");
        text = text.replace("( ", "(").replace(" )", ")");
        if (text.startsWith("(") && text.endsWith(")")) {
            text = text.substring(1, text.length() - 1);
        }
        int lBraceCount = StringUtils.countMatches(text, LEFT_BRACE);
        int rBraceCount = StringUtils.countMatches(text, RIGHT_BRACE);
        if (lBraceCount != rBraceCount)
            throw new InvalidParameterException(ERROR_UNMATCHED_BRACES);
        System.out.println(text);
        char[] chars = text.toCharArray();
        while (ArrayUtils.contains(chars, LEFT_BRACE))
        {
            System.out.println();
            int[] lBraceIndex = getIndexesOf(LEFT_BRACE, chars, lBraceCount);
            int[] rBraceIndex = getMatchingBraces(chars, lBraceIndex);
            System.out.println(Arrays.toString(lBraceIndex));
            System.out.println(Arrays.toString(rBraceIndex));
            int len = lBraceIndex.length;
            System.out.println("Braces len "+len);
            for (int i = 0; i < len; i++)
            {
                int lb = lBraceIndex[i];
                int rb = rBraceIndex[i];
                boolean hasInnerBrace = false;
                for(int l = i+1; l < len; l++)
                {
                    int ilb = lBraceIndex[l];
                    if(ilb < rb)
                    {
                        hasInnerBrace = true;
                        break;
                    }
                }
                System.out.println("hasInnerBrace "+hasInnerBrace);
                if(!hasInnerBrace)
                {
                    double innerBraceResult = evaluateNoQuote(chars,lb+1,rb-1);
                    System.out.println("innerBraceResult " +innerBraceResult);
                    chars = replaceExpressionToValue(chars,lb,rb,innerBraceResult);
                    break;
                }
            }
        }
        System.out.println();
        return 0.0;
    }

    private static char[] replaceExpressionToValue(char[] chars, int lb, int rb, double innerBraceResult)
    {
        String result =
                new String(ArrayUtils.subarray(chars,0,lb))
                +innerBraceResult+
                new String(ArrayUtils.subarray(chars,rb,chars.length-1));
        System.out.println("result "+result);
        return result.toCharArray();
    }

    private static double evaluateNoQuote(char[] chars, int lb, int rb)
    {
        String textToEvaluate = new String(ArrayUtils.subarray(chars,lb,rb));
        System.out.println(textToEvaluate);
        return 0.0;
    }

    private static boolean containsInnerBraces(int[] lBraceIndex, int[] rBraceIndex, int i)
    {
        int lb = lBraceIndex[i];
        int rb = rBraceIndex[i];
        return false;
    }

    public static int[] getMatchingBraces(char[] chars,int[] left)
    {
        int length  = left.length;
        int[] right = new int[length];
        for (int i = 0; i < length; i++) {
            right[i] = getClosingBraces(chars,left[i]);
        }
        return right;
    }
    public static int getClosingBraces(char[] chars,int left){
        int right = left;
        int count = 1;
        while (count > 0){
            char aChar = chars[++right];
            if(aChar== LEFT_BRACE){
                count++;
            }
            else if(aChar== RIGHT_BRACE){
                count--;
            }
        }
        return right;
    }

    private static int[] getIndexesOf(char aChar, char[] chars, int charCount)
    {
        int length = chars.length;
        int[] result = new int[charCount];
        int index = 0;
        for (int i = 0; i < length; i++)
        {
            if(aChar == chars[i])
            {
                result[index++] = i;
            }
        }
        return result;
    }
    public static int getNextNumber(char[] chars, int startPos, List<String> values) {
        int length = chars.length;
        int dotCount = 0;
        StringBuilder str = new StringBuilder();
        for (int i = startPos; i < length; i++) {
            char aChar = chars[i];
            if (Character.isDigit(aChar)) {
                str.append(aChar);
                continue;
            }
            if (aChar == DOT) {
                if (++dotCount > 1)
                    throw new InvalidParameterException("Invalid number found " + str.toString());
                str.append(DOT);
                continue;
            }
            if (aChar == SPACE) {
                //ignore
                continue;
            }
            if (ArrayUtils.contains(OPERATORS, aChar)) {
                values.add(str.toString());
                return i;
            }
        }
        return startPos;
    }

    public static boolean isNumber(char[] chars) {
        int dotCount = 0;
        for (char aChar : chars) {
            if (aChar == DOT) {
                dotCount++;
                continue;
            }
            if (!Character.isDigit(aChar)) {
                return false;
            }
        }
        if (dotCount > 1 || chars[0] == '.' || chars[chars.length - 1] == '.') return false;
        return true;
    }
}
