package org.led.tools.MultiThreads.DeadLock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Note: jstack doesn't think this is DeadLock, but the consequence is the same.
 * Reason: ThreadPool size is too small. All tasks in pool are waiting for the result of task not in pool.
 * @author led
 *
 */
public class StarvationLock {
    private ExecutorService pool = Executors.newSingleThreadExecutor(); 
    
    class GoodManager implements Runnable{        
        public void run() {
            System.out.println("Manager starts to work ");
            pool.submit(new Worker());
            System.out.println("Manager ends to work ");
        }        
    }
    
    class BadManager implements Callable<String> {
        public String call() throws Exception {
            System.out.println("Manager starts to work ");
            Future<String> result = pool.submit(new Worker());
            System.out.println("Manager got worker's response: " + result.get());
            return "Manager over";
        }
    }
    
    class Worker implements Callable<String> {
        public String call() throws Exception {
            System.out.println("Worker starts to work");
            return "Worker over";
        }
    }
    
    public static void main(String[] args) {
        StarvationLock instance = new StarvationLock();
        //No dead lock
        instance.pool.submit(instance.new GoodManager());
        //Dead lock
        //instance.pool.submit(instance.new BadManager());
        //instance.pool.shutdown();
    }
}
