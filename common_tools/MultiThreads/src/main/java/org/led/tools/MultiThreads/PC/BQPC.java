package org.led.tools.MultiThreads.PC;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * An implementation of Producer&Consumer using BlockingQueue
 * Good points: No lock
 * @author led
 *
 */
class BQProducer implements Runnable{
    private BlockingQueue<String> queue;
    private int maxSize;
    private String[] ss = {
            "Create Delivery Session",
            "Create EMBMS Session",
            "Create Delivery Session Instance",
            "Cache Add",
            "Add Content"
    };
    public BQProducer(BlockingQueue<String> bq, int size) {
        queue = bq;
        this.maxSize = size;
    }

    public void run() {
        try {
            for (String s : ss) {
                System.out.println("BQProducer put: " + s + ", size: " + queue.size());
                queue.put(s);
            }
            for (int i=0; i<maxSize; i++) {
            	queue.put("Done!");
            }
        } catch (InterruptedException e) {
            System.out.println("BQProducer Interrupted : full! " + queue.size());
        }
    }    
}

class BQConsumer implements Runnable{
    private BlockingQueue<String> queue;
    
    public BQConsumer(BlockingQueue<String> b) {
        queue = b;
    }

    public void run() {
        try {
            String msg = null;
            while(!("Done!".equals(msg=queue.take()))) {
                System.out.println("BQConsumer get: " + msg + ", size: " + queue.size());
            }
            
        } catch (InterruptedException e) {
            System.out.println("BQConsumer Interrupted : full! " + queue.size());
        }
    }
}

public class BQPC {
    public static void main(String[] args) {
    	BlockingQueue<String> q = new ArrayBlockingQueue<String>(1, true);
        new Thread(new BQProducer(q, 2)).start();
        new Thread(new BQConsumer(q)).start();
        new Thread(new BQConsumer(q)).start();  
    }
}
