package task2;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by Diana_Aimbetova on 1/5/2020
 **/

public class QuickSortRunner {

    private static final Integer ARRAY_SIZE = 5;

    public static void main(String[] args) {
        Random rand = new Random();
        final ForkJoinPool pool = new ForkJoinPool();
        Integer[] array = new Integer[ARRAY_SIZE];

        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(100);
        }

        System.out.println("Array length is: " + array.length);
        Arrays.stream(array).forEach(System.out::println);
        QuickSortTask task = new QuickSortTask(array, 0, array.length - 1);
        pool.invoke(task);
        System.out.println("Quick Sorted Array: ");
        Arrays.stream(array).forEach(System.out::println);
    }
}
