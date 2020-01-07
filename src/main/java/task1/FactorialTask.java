package task1;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Diana_Aimbetova on 1/5/2020
 **/

public class FactorialTask extends RecursiveTask {
    private Integer facIndex;
    private static AtomicInteger taskCounter = new AtomicInteger(0);

    private Map<Integer, BigInteger> facMap = new HashMap<>();

    {
        facMap.put(0,  new BigInteger("1"));
        facMap.put(1,  new BigInteger("1"));
        facMap.put(2,  new BigInteger("2"));
        facMap.put(3,  new BigInteger("6"));
        facMap.put(4,  new BigInteger("24"));
    }

    public FactorialTask(Integer facIndex) {
        this.facIndex = facIndex;
    }

    @Override
    protected BigInteger compute() {
        if (facMap.containsKey(facIndex)) {
            return facMap.get(facIndex);
        }

        FactorialTask factorialTask = new FactorialTask(facIndex - 1);
        factorialTask.fork();
        BigInteger result =  new BigInteger(facIndex.toString()).multiply((BigInteger) factorialTask.join());
        taskCounter.getAndIncrement();
        System.out.println("Intermediate result is: " + result + " from thread: " + Thread.currentThread().getName());
        return result;
    }
}
