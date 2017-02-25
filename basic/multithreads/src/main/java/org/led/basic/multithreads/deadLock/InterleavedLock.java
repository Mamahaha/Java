package org.led.basic.multithreads.deadLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Sometimes it'll go into dead lock.
 * Note: Use below command to check dead lock 
 *      #jstack <pid>
 * @author led
 *
 */
public class InterleavedLock {
    private static Lock lockA = new ReentrantLock();
    private static Lock lockB = new ReentrantLock();
    
    static class LockAB implements Runnable{
        public void run() {
            lockA.lock();
            System.out.println("lockAB a");
            lockB.lock();
            System.out.println("lockAB b");
            lockA.unlock();
            System.out.println("lockAB unlock a");
            lockB.unlock();
            System.out.println("lockAB unlock b");
        }        
    }
    
    static class LockBA implements Runnable{
        public void run() {
            lockB.lock();
            System.out.println("lockBA b");
            lockA.lock();
            System.out.println("lockBA a");
            lockB.unlock();
            System.out.println("lockBA unlock b");
            lockA.unlock();
            System.out.println("lockBA unlock a");
        }        
    }

    public static void main(String[] args) {
        new Thread(new LockAB()).start();
        new Thread(new LockBA()).start();
    } 
}
