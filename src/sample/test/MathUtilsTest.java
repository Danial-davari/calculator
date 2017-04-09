package sample.test;

import org.junit.jupiter.api.Test;
import sample.MathUtils;

import javax.script.ScriptException;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static sample.MathUtils.ERROR_UNMATCHED_BRACES;

/**
 * Created by mypc on 3/25/2017.
 */
class MathUtilsTest
{
    @Test
    void evaluate()
    {
        try {
            MathUtils.evaluate("((65 + (5 * 3)) - (83 * 2))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void evaluateBracesAreNotEqual() {
        //arrange
        String input = "(3*2)(";
        //act
        assertThrows(ScriptException.class, () -> {
            MathUtils.evaluate(input);
        });
    }
}