package thread;

import java.util.Collections;
import java.util.HashMap;

    /**
     * @author Diana_Aimbetova
     * created on 12/14/2019
     *
     * Try to fix the problem with Collections.synchronizedMap.
     */
    public class SynchronizedMapExample extends Task1{

        public SynchronizedMapExample() {
            map = Collections.synchronizedMap(new HashMap<>());
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
            thread.SynchronizedMapExample.ThreadA a = new thread.SynchronizedMapExample.ThreadA();
            thread.SynchronizedMapExample.ThreadB b = new thread.SynchronizedMapExample.ThreadB();
            a.start();
            b.start();
        }

        public static void main(String[] args) {
            thread.SynchronizedMapExample example = new thread.SynchronizedMapExample();
            example.run();
        }

    }

