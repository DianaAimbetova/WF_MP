package producer;

import org.apache.commons.lang.RandomStringUtils;
import queue.BlockingObjectPool;

public class EventProducer extends Thread {
    private final BlockingObjectPool queue;

    public EventProducer(BlockingObjectPool queue) {
        this.queue = queue; }

    @Override
    public void run() {
        for (int i = 1; i <= queue.size; i++) {
            try {
                String generatedString = RandomStringUtils.randomAlphabetic(5);
                queue.put(generatedString);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}