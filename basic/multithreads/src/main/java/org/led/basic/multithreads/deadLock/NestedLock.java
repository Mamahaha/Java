package org.led.basic.multithreads.deadLock;


/**
 * Sometimes it'll go into dead lock.
 * Note: Basic type cannot be used as sync object, for example int. Only reference type is allowed
 * Note: Reference type object cannot be used as sync object if it's null
 * Note: Use below command to check dead lock 
 *      #jstack <pid>
 * @author led
 *
 */
public class NestedLock {
    private String a = "a";
    private String b = "b";
    
    class SyncAB implements Runnable {
        public void run() {
            synchronized(a) {
                System.out.println("SyncAB a");
                synchronized(b) {
                    System.out.println("SyncAB b");
                }
            }
        }        
    }
    
    class SyncBA implements Runnable {
        public void run() {
            synchronized(b) {
                System.out.println("SyncBA b");
                synchronized(a) {
                    System.out.println("SyncBA a");
                }
            }
        }        
    }
    
    public static void main(String[] args) {
        NestedLock instance = new NestedLock();
        new Thread(instance.new SyncAB()).start();
        new Thread(instance.new SyncBA()).start();
    }
    
}

