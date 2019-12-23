package queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingObjectPool {
    final Lock lock = new ReentrantLock();

    final Condition produceCondition  = lock.newCondition();
    final Condition consumeCondition = lock.newCondition();

    private String[] array;
    int putIndex, takeIndex, count;
    public int size;

    public BlockingObjectPool(int size){
        this.size = size;
        array =  new String[size];
    }

    public void put(String message) throws InterruptedException {

        lock.lock();
        try {
            while (count == array.length){
                produceCondition.await();
            }

            array[putIndex] = message;
            System.out.println("Sending: "+putIndex+" message. Text is: " + message);
            putIndex++;
            if (putIndex == array.length){
                putIndex = 0;
            }
            ++count;
            consumeCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public String take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0){
                consumeCondition.await();
            }
            String message = array[takeIndex];
            System.out.println("Receiving: "+takeIndex+" message. Text is: " + message);
            takeIndex++;
            if (takeIndex == array.length){
                takeIndex = 0;
            }
            --count;
            produceCondition.signal();
            return message;
        } finally {
            lock.unlock();
        }
    }
}
