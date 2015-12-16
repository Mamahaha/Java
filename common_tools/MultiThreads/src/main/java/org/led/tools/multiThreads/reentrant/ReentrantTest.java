package org.led.tools.multiThreads.reentrant;

import java.text.DateFormat;
import java.util.Date;

public class ReentrantTest {
private static int count = 5;
    
    private static void showTime(String prefix) {
        Date now = new Date();
        DateFormat df = DateFormat.getTimeInstance();
        String str = df.format(now);
        
        System.out.println(prefix + " obj: " + Thread.currentThread().getName() + " ; now: " + str);
    }
    
    public static void test0() {
        /*
         * no lock
         */
        ReentrantData data = new ReentrantData(0);
        Thread[] wThreads = new Thread[count];
        Thread[] rThreads = new Thread[count];
        for (int i=0; i<count; i++) {
            ReentrantWriteJob wj = new ReentrantWriteJob(data, i, 0);
            wThreads[i] = new Thread(wj);
            wThreads[i].start();                        
        }
        
        for (int i=0; i<count; i++) {
            ReentrantReadJob rj = new ReentrantReadJob(data, 0);
            rThreads[i] = new Thread(rj);
            rThreads[i].start();
        }
    }
    
    public static void test1() {
        /*
         * synchronized
         */
        ReentrantData data = new ReentrantData(0);
        Thread[] wThreads = new Thread[count];
        Thread[] rThreads = new Thread[count];
        for (int i=0; i<count; i++) {
            ReentrantWriteJob wj = new ReentrantWriteJob(data, i, 1);
            wThreads[i] = new Thread(wj);
            wThreads[i].start();                
        }
        
        for (int i=0; i<count; i++) {
            ReentrantReadJob rj = new ReentrantReadJob(data, 1);
            rThreads[i] = new Thread(rj);
            rThreads[i].start();
        }
    }
    
    public static void test2() {
        /*
         * reentrant lock
         */
        ReentrantData data = new ReentrantData(0);
        Thread[] wThreads = new Thread[count];
        Thread[] rThreads = new Thread[count];
        for (int i=0; i<count; i++) {
            ReentrantWriteJob wj = new ReentrantWriteJob(data, i, 2);
            wThreads[i] = new Thread(wj);
            wThreads[i].start();                
        }
        
        for (int i=0; i<count; i++) {
            ReentrantReadJob rj = new ReentrantReadJob(data, 2);
            rThreads[i] = new Thread(rj);
            rThreads[i].start();
        }
    }
    
    public static void test3() {
        /*
         * reentrant read-write lock
         */
        ReentrantData data = new ReentrantData(0);
        Thread[] wThreads = new Thread[count];
        Thread[] rThreads = new Thread[count];
        for (int i=0; i<count; i++) {
            ReentrantWriteJob wj = new ReentrantWriteJob(data, i, 3);
            wThreads[i] = new Thread(wj);
            wThreads[i].start();            
        }
        
        for (int i=0; i<count; i++) {
            ReentrantReadJob rj = new ReentrantReadJob(data, 3);
            rThreads[i] = new Thread(rj);
            rThreads[i].start();
        }
    }
    
    public static void test4() {
        /*
         * reentrant lock interrupt
         */
        ReentrantData data = new ReentrantData(0);
        Thread[] wThreads = new Thread[2];
        for (int i=0; i<2; i++) {
            ReentrantWriteJob wj = new ReentrantWriteJob(data, i, 4);
            wThreads[i] = new Thread(wj);
            wThreads[i].start();            
        }
        
        showTime("before main thread sleep");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        showTime("start interrupt wThread 1");
        wThreads[1].interrupt();
        showTime("stop interrupt wThread 1");
    }
    
    public static void test5() {
        /*
         * reentrant lock condition
         */
        ReentrantData data = new ReentrantData(0);
        int newCount = 2*count;
        Thread[] wThreads = new Thread[newCount];
        Thread[] rThreads = new Thread[newCount];
        for (int i=0; i<newCount; i++) {
            ReentrantWriteJob wj = new ReentrantWriteJob(data, i, 5);
            wThreads[i] = new Thread(wj);
            wThreads[i].start();            
        }
        
        for (int i=0; i<newCount; i++) {
            ReentrantReadJob rj = new ReentrantReadJob(data, 5);
            rThreads[i] = new Thread(rj);
            rThreads[i].start();
        }
    }
}
