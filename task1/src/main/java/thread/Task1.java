package thread;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Diana_Aimbetova
 * created on 12/14/2019
 * <p>
 * Parent Class for Thread Examples
 * Create HashMap<Integer, Integer>.
 * The first thread adds elements into the map,
 * the other go along the given map and sum the values.
 * Threads should work before catching ConcurrentModificationException.
 */
public class Task1 {
    static final int THREAD_SIZE = 500000000;
    Map<Integer, Integer> map;

    public void put() {
        for (int i = 1; i <= THREAD_SIZE; i++) {
            map.put(i, 1);
        }
    }

    public void sum() {
        try {
            AtomicInteger sum = new AtomicInteger();
            map.forEach((k, v) -> sum.addAndGet(v));
        } catch (ConcurrentModificationException ex) {
            System.err.println("You got ConcurrentModificationException");
        }
    }
}
