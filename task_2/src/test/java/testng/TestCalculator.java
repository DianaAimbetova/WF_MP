package testng;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Calculator;



public class TestCalculator {
    private Calculator calculator;
    private double number1, number2;

    @BeforeMethod
    public void prepareData() {
        calculator = new Calculator();
        number1 = 2;
        number2 = 2;
    }

    @Test
    public void testAdd() {
        double result = calculator.add(number1, number2);
        Assert.assertEquals(result, 4,0.0);
    }

    @Test
    public void testSubtract() {
        Assert.assertEquals(0, calculator.subtract(number1, number2),0.0);
    }

    @Test
    public void testDivide() {
        Assert.assertEquals(1, calculator.divide(number1, number2),0.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDivideByZero() {
        calculator.divide(number1, 0);
    }

    @Test
    public void testPower(){
        Assert.assertEquals(4, calculator.power(number1, number2),0.0);;
    }

    @Test
    public void testRoot(){
        Assert.assertEquals(2, calculator.root(4, number2),0.0);;
    }

    @Test(timeOut = 1)
    public void testFactorialWithTimeout1() {
        boolean result = calculator.isPrime(105655873);
        Assert.assertTrue(result);
    }

    @AfterMethod
    public void drop(){
        calculator = null;
    }
}
