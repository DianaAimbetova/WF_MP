package thread;

import java.util.HashMap;

/**
 * @author Diana_Aimbetova
 * created on 12/14/2019
 * <p>
 * Create HashMap<Integer, Integer>.
 * The first thread adds elements into the map,
 * the other go along the given map and sum the values.
 * Threads should work before catching ConcurrentModificationException.
 */
public class SimpleThreadExample extends Task1 {

    public SimpleThreadExample() {
        map = new HashMap<>();
    }

    class ThreadA extends Thread {
        @Override
        public void run() {
            put();
        }
    }

    class ThreadB extends Thread {
        @Override
        public void run() {
            sum();
        }
    }

    public void run() {
            ThreadA a = new ThreadA();
            ThreadB b = new ThreadB();
            a.start();
            b.start();
    }


    public static void main(String[] args) {
        SimpleThreadExample example = new SimpleThreadExample();
        example.run();
    }
}
