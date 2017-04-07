package sample.test;

import org.junit.jupiter.api.Test;
import sample.MathUtils;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static sample.MathUtils.ERROR_UNMATCHED_BRACES;
import static sample.MathUtils.isNumber;

/**
 * Created by mypc on 3/25/2017.
 */
class MathUtilsTest {
    @Test
    void evaluate() {
        MathUtils.evaluate("(3 * 45.000000 + (8*6) + 7)");
        MathUtils.evaluate("(4 * 45.000000 + (8*6) + 7)");
        MathUtils.evaluate("(3 * 45.000000 + ((8*6)) + 7)");
        MathUtils.evaluate("((65 + (5 * 3)) - (83 * 2))");
    }
    @Test
    void evaluateBracesAreEqual() {
        //arrange
        String input = "(3*2)(";
        String expected = ERROR_UNMATCHED_BRACES;
        //act
        Throwable exception = assertThrows(InvalidParameterException.class, () -> {
            MathUtils.evaluate(input);
        });
        //assert
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void evaluateMultiplication() {
        //arrange
        String input = "(3*2)";
        double expected = 6;
        //act
        double actual = MathUtils.evaluate(input);
        //assert
        assertEquals(expected, actual);
    }

    @Test
    void evaluateDivision() {
        //arrange
        String input = "(12/2)";
        double expected = 6;
        //act
        double actual = MathUtils.evaluate(input);
        //assert
        assertEquals(expected, actual);
    }

    @Test
    void evaluateAddition() {
        //arrange
        String input = "(4+2)";
        double expected = 6;
        //act
        double actual = MathUtils.evaluate(input);
        //assert
        assertEquals(expected, actual);
    }

    @Test
    void evaluateSubstraction() {
        //arrange
        String input = "(8-2)";
        double expected = 6;
        //act
        double actual = MathUtils.evaluate(input);
        //assert
        assertEquals(expected, actual);
    }

    @Test
    void testIsNumber() {
        String text = "0.056";
        assertTrue(isNumber(text.toCharArray()));
    }

    @Test
    public void testIsNotANumber() {
        String text = "0.056A";
        assertFalse(isNumber(text.toCharArray()));

    }
}