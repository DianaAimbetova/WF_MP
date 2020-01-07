package task1;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by Diana_Aimbetova on 1/5/2020
 **/

public class FactorialCalculatorRunner {
    private static final Integer FAC_INDEX = 6;

    public static void main(String[] args) {
        final ForkJoinPool pool = new ForkJoinPool();
        FactorialTask task = new FactorialTask(FAC_INDEX);

        BigInteger facNumber = (BigInteger) pool.invoke(task);
        System.out.println("Factorial Number: " + facNumber + " with index: " + FAC_INDEX);
    }
}
