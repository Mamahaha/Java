package org.led.basic.multithreads.sync;

public class Sync {	
	
	public static void test1() {
		/*
		 * 2 threads set 2 different items. The set operation is sync
		 * Result: no effect with each other
		 */
		Item item1 = new Item();
		Item item2 = new Item();
		
		Job j1 = new Job(item1, 6, 1);
		Job j2 = new Job(item2, 2, 1);
		Thread tj1 = new Thread(j1);
		Thread tj2 = new Thread(j2);
		
		tj1.start();
		tj2.start();
	}
	
	public static void test2() {
		/*
		 * 2 threads set 1 item. The set operation is sync
		 * Result: thread 1 will block thread 2
		 */
		Item item1 = new Item();
		Job j1 = new Job(item1, 6, 1);
		Job j2 = new Job(item1, 2, 1);
		Thread tj1 = new Thread(j1);
		Thread tj2 = new Thread(j2);
		
		tj1.start();
		tj2.start();
	}
	
	public static void test3() {
		/*
		 * 2 threads set 1 item. The set operation is async. But thread 1 will try to get item lock from outside
		 * Result: thread 1 will block thread 2
		 */
		Item item1 = new Item();
		Job j1 = new Job(item1, 6, 2);
		Job j2 = new Job(item1, 2, 2);
		
		Thread tj1 = new Thread(j1);
		Thread tj2 = new Thread(j2);
		
		tj1.start();
		tj2.start();
	}
	
	public static void test4() {
		/*
		 * 2 threads set 2 different items. The set operation is async, but Item class is synced.
		 * Result: thread 1 will block thread 2
		 */
		Item item1 = new Item();
		Item item2 = new Item();
		
		Job j1 = new Job(item1, 6, 3);
		Job j2 = new Job(item2, 2, 3);
		Thread tj1 = new Thread(j1);
		Thread tj2 = new Thread(j2);
		
		tj1.start();
		tj2.start();
	}
	
	public static void test5() {
		/*
		 * 2 threads set 1 item with 2 different sync operation
		 * Result: thread 1 will block thread 2
		 */
		Item item1 = new Item();
		Job j1 = new Job(item1, 6, 1);
		Job j2 = new Job(item1, 2, 4);
		Thread tj1 = new Thread(j1);
		Thread tj2 = new Thread(j2);
		
		tj1.start();
		tj2.start();
	}
}
