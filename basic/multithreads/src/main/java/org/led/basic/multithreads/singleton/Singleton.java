package org.led.basic.multithreads.singleton;

/**
 * List some thread safe and unsafe Singleton implementations
 * @author led
 *
 */

class Unsafe1 {
	private static Unsafe1 instance;
	private Unsafe1(){}
	
	public static Unsafe1 getInstance() {
		if (instance == null) {
			instance = new Unsafe1();
		}
		return instance;
	}
}

/**
 * Note: Instance is created at startup
 */
class Safe1 {
	private static Safe1 instance = new Safe1();
	private Safe1(){}
	
	public static Safe1 getInstance() {
		return instance;
	}
}

/**
 * Note: Delay instance creation.
 * Note: Big performance affection
 */
class Safe2 {
	private static Safe2 instance;
	private Safe2(){}
	
	public synchronized static Safe2 getInstance() {
		if (instance == null) {
			instance = new Safe2();
		}
		return instance;
	}
}

/**
 * Note: Delay instance creation.
 * Note: Small performance affection
 */
class Safe3 {
	private static Safe3 instance;
	private Safe3(){}
	
	public static Safe3 getInstance() {
		if (instance == null) {
			synchronized(instance) {
				if (instance == null) {
					instance = new Safe3();
				}
			}			
		}
		return instance;
	}
}

public class Singleton {

}
