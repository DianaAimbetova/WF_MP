package thread;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Diana_Aimbetova
 * created on 12/14/2019
 *
 * Try to fix the problem with ConcurrentHashMap.
 */
public class ConcurrentHashMapExample extends Task1{

    public ConcurrentHashMapExample() {
        map = new ConcurrentHashMap<>();
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
        ConcurrentHashMapExample.ThreadA a = new ConcurrentHashMapExample.ThreadA();
        ConcurrentHashMapExample.ThreadB b = new ConcurrentHashMapExample.ThreadB();
        a.start();
        b.start();
    }

    public static void main(String[] args) {
        ConcurrentHashMapExample example = new ConcurrentHashMapExample();
        example.run();
    }

}
