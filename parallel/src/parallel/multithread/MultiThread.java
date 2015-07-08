package parallel.multithread;

public class MultiThread {
	/*
	 * Points:
	 * 1. 2 ways of creating a new thread. The second one is preferred.
	 * 2. Usage of join(), which is used for main thread to wait until the specified sub-thread quit or timeout
	 */
	
	public static class MyThread1 extends Thread{
		int interval;
		
		public MyThread1(int i) {
			interval = i * 1000;
		}
		
		@Override
		public void run() {
			int i = 0;
			while(true) {
				i++;
				if((i % 50) == 0) {
					System.out.println("thread1 obj: " + this + " ; i: " + i);
					try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}		
	} 
	
	public class MyThread2 implements Runnable {
		int interval;
		
		public MyThread2(int i) {
			interval = i * 1000;
		}
		
		@Override
		public void run() {
			int i = 0;
			while(true) {
				i++;
				if((i % 50) == 0) {
					System.out.println("thread2 obj: " + this + " ; i: " + i);
					try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}			
		}		
	}
	
	public static void test(){
		//way 1
		
		MyThread1 mt1 = new MyThread1(3);
		MyThread1 mt2 = new MyThread1(6);
		System.out.println("start thread 1");
		mt1.start();
		System.out.println("start thread 2");
		mt2.start();
		
		System.out.println("start waiting for thread 2");
		try {
			mt2.join(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end of waiting for thread 2");
		
		//way 2
		MultiThread mt = new MultiThread();
		MyThread2 mt3 = mt.new MyThread2(4);
		MyThread2 mt4 = mt.new MyThread2(2);
		Thread t1 = new Thread(mt3);
		Thread t2 = new Thread(mt4);
		
		System.out.println("start thread 3");
		t1.start();
		System.out.println("start thread 4");
		t2.start();
		
		System.out.println("end main thread");
	}
}
