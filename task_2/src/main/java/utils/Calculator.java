package utils;

import java.util.ArrayList;
import java.util.List;

public class Calculator {


    public  double add(double number1, double number2) {
        return number1 + number2;
    }

    public  double subtract(double number1, double number2) {
        return number1 - number2;
    }

    public  double multiply(double number1, double number2) {
        return number1 * number2;
    }

    public  double divide(double number1, double number2) {
        if(number2 == 0){
            throw new IllegalArgumentException("Can not divide by zero");
        }
        return number1 / number2;
    }

    public  double root(double base, double exponent) {
        return Math.pow(base, 1/exponent);
    }

    public  double power(double base, double exponent) {
        return Math.pow(base, exponent); // :) Math.pow()
    }

    public boolean isPrime(double number)
    {
        if (number == 2)
            return true;
        if (number < 2 || number % 2 == 0)
            return false;
        for (int i = 3; i * i <= number; i += 2)
            if (number % i == 0)
                return false;
        return true;
    }

    public List<Integer> fibonacci(int count){
        List<Integer> result = new ArrayList<>();
        int number1 = 0;
        int number2 = 1;
        for (int i = 0; i < count; i++) {
            result.add(number1);
            int sum = number1 + number2;
            number1 = number2;
            number2 = sum;
        }
        return result;
    }

}
