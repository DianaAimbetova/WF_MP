package consumer;

import queue.BlockingObjectPool;

public class EventConsumer extends Thread{
    private final BlockingObjectPool queue;

    public EventConsumer(BlockingObjectPool queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 1; i <= queue.size; i++) {
            try {
                queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

