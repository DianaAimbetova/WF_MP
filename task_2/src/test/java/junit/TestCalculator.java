package junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Calculator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCalculator {
    private Calculator calculator;
    private double number1, number2;
    private int count;

    @Before
    public void prepareData() {
        calculator = new Calculator();
        number1 = 2;
        number2 = 2;
        count = 10;
    }

    @Test
    public void testAdd() {
        double result = calculator.add(number1, number2);
        assertEquals(result, 4);
    }

    @Test
    public void testSubtract() {
        assertEquals(0, calculator.subtract(number1, number2));
    }

    @Test
    public void testDivide() {
        assertEquals(1, calculator.divide(number1, number2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivideByZero() {
        calculator.divide(number1, 0);
    }

    @Test
    public void testPower(){
        assertEquals(4, calculator.power(number1, number2));;
    }

    @Test
    public void testRoot(){
        assertEquals(2, calculator.root(4, number2));;
    }

    @Test(timeout = 1)
    public void testFactorialWithTimeout1() {
        boolean result = calculator.isPrime(105655873);
        assertTrue(result);
    }

    @Test
    public void testFibonacci(){
        List<Integer> actualResult = calculator.fibonacci(count);
        List<Integer> expectedResult = Arrays.stream(new int[]{0, 1, 1, 2, 3, 5, 8, 13, 21, 34})
                .boxed()
                .collect(Collectors.toList());;
        assertThat(actualResult, hasSize(count));
        assertThat(actualResult, is(expectedResult));
    }


    @After
    public void drop(){
        calculator = null;
    }
}
