package org.led.basic.multithreads.producerconsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of Producer&Consumer using sychronized policy
 * Note: once you synchronize an object, you should use its own wait() and notifyAll()
 * @author led
 *
 */
class SyncEventQueue {
    private List<String> q;
    private int maxSize;
    
    public SyncEventQueue(List<String> list, int size) {
        this.q = list;
        maxSize = size;
    } 
    
    public void put(String msg) {
        synchronized (q) {
            try {
                while (q.size() == maxSize) {
                    System.out.println("eq put wait: " + q.size());
                    q.wait();
                }
                System.out.println("eq put : " + msg + ", size: " + q.size());
                q.add(msg);
                System.out.println("eq put notifyAll size: " + q.size());
                q.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String get() {
        synchronized (q) {
            try {
                while (q.size() == 0) {
                    System.out.println("eq get wait: " + q.size());
                    q.wait();
                }
                String msg = q.remove(0);
                System.out.println("eq get : " + msg + ", size: " + q.size());
                System.out.println("eq get notifyAll size: " + q.size());
                q.notifyAll();
                return msg;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public int size() {
        return q.size();
    }
}

class SyncProducer implements Runnable {
    private SyncEventQueue eq;
    private int maxConsumer = 10;
    private String[] ss = {
            "Create Delivery Session",
            "Create EMBMS Session",
            "Create Delivery Session Instance",
            "Cache Add",
            "Add Content"
    };
    public SyncProducer(SyncEventQueue eq, int maxConsumer) {
        this.eq = eq;
        this.maxConsumer = maxConsumer;
    }
    
    public void run() {
        for(String s : ss) {
            eq.put(s);
            System.out.println("SyncProducer put: " + s);
        }
        for(int i=0; i<maxConsumer; i++) {
        	eq.put("Done!");
        }
    }    
}

class SyncConsumer implements Runnable {
    SyncEventQueue eq;

    public SyncConsumer(SyncEventQueue eq) {
        this.eq = eq;
    }
    
    public void run() {
        String s = null;
        while(!("Done!".equals(s=eq.get()))){
            System.out.println("SyncConsumer get: " + s);
        }
    }    
}

public class SyncListPC {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        SyncEventQueue eq = new SyncEventQueue(list, 3);
        new Thread(new SyncProducer(eq, 2)).start();
        new Thread(new SyncConsumer(eq)).start();
        new Thread(new SyncConsumer(eq)).start();
    } 
}
