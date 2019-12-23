package runner;

import consumer.EventConsumer;
import producer.EventProducer;

import queue.BlockingObjectPool;


public class Runner {
    public static void main(String[] args) {
        BlockingObjectPool queue = new BlockingObjectPool(10);

        EventProducer producer = new EventProducer(queue);
        EventConsumer consumer = new EventConsumer(queue);

        producer.start();
        consumer.start();
    }

}
