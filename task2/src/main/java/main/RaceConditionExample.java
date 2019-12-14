package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Diana_Aimbetova
 * created on 12/14/2019
 * <p>
 * Create three threads:
 * <p>
 * •	1st thread is infinitely writing random number to the collection
 * •	2nd thread is printing sum of the numbers in the collection
 * •	3rd is printing square root of sum of squares of all numbers in the collection
 * <p>
 * Make these calculations thread-safe using synchronization block and avoid the possible race condition.
 */

public class RaceConditionExample implements Runnable {
    final Map<Integer, Integer> map = new HashMap<>();

    public void put() {
        int i = 0;
            map.put(i++, new Random().nextInt());
    }

    public void sum() {
        AtomicInteger sum = new AtomicInteger();
        map.forEach((k, v) -> sum.addAndGet(v));
        System.out.println("Sum is: " + sum.get());
    }

    public void squareRoot() {
        AtomicInteger sumOfSquares = new AtomicInteger();
        map.forEach((k, v) -> sumOfSquares.addAndGet(v * v));
        System.out.println("Square root of sum of squares is: " + Math.sqrt(sumOfSquares.get()));
    }

    @Override
    public void run() {
        synchronized (this) {
            this.put();
            this.sum();
            this.squareRoot();
        }
    }
}

class RaceConditionDemo {
    public static void main(String[] args) {
        RaceConditionExample example = new RaceConditionExample();
        Thread t1 = new Thread(example, "Thread1");
        Thread t2 = new Thread(example, "Thread2");
        Thread t3 = new Thread(example, "Thread3");
        t1.start();
        t2.start();
        t3.start();
    }
}
