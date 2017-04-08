package sample;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * Created by mypc on 3/25/2017.
 */
public class MathUtils {
    public static final char LEFT_BRACES = '(';
    public static final char RIGHT_BRACES = ')';
    public static final String ERROR_UNMATCHED_BRACES = "left braces must match right braces";

    public static final char[] OPERATORS = new char[]{'*', '/', '+', '-'};
    public static final char[] BRACES = new char[]{LEFT_BRACES, RIGHT_BRACES};
    public static final char DOT = '.';
    public static final char SPACE = ' ';

    // (3 * 45.000 000 + (8*6) + 7)
    // (65 + (5 * 3)) - 83
    public static double evaluate(String text) {
        for (int i = 0; i < OPERATORS.length; i++) {
            String aChar = OPERATORS[i] + "";
            text = text.replace(aChar, (" " + aChar + " "));
        }
        text = text.trim().replaceAll("\\s+", " ");
        text = text.replace("( ", "(").replace(" )", ")");
        if (text.startsWith("(") && text.endsWith(")")) {
            text = text.substring(1, text.length() - 1);
        }
        int lBraceCount = StringUtils.countMatches(text, LEFT_BRACES);
        int rBraceCount = StringUtils.countMatches(text, RIGHT_BRACES);
        if (lBraceCount != rBraceCount)
            throw new InvalidParameterException(ERROR_UNMATCHED_BRACES);
        System.out.println(text);
        char[] chars = text.toCharArray();
        int[] lBraceIndex = getIndexesOf(LEFT_BRACES, chars,lBraceCount);
        int[] rBraceIndex = getMatchingBraces(chars,lBraceIndex);
        System.out.println(Arrays.toString(lBraceIndex));
        System.out.println(Arrays.toString(rBraceIndex));
        System.out.println();
        return 0.0;
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
            if(aChar==LEFT_BRACES){
                count++;
            }
            else if(aChar==RIGHT_BRACES){
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
