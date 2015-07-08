package parallel.reentrant;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantData {
	private int value;
	private int interval = 2000;
	
	private final static int QUEUE_SIZE = 5;
	private int[] queue;
	private int count;
	private int getPos;
	private int putPos;
	
	private Lock lock = new ReentrantLock();
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();
	
	private ReadWriteLock rwLock = new ReentrantReadWriteLock();	
	

	private void showTime(String prefix) {
		Date now = new Date();
		DateFormat df = DateFormat.getTimeInstance();
		String str = df.format(now);
		
		System.out.println(prefix + " obj: " + Thread.currentThread().getName() + " ; now: " + str);
	}
	
	public ReentrantData(int value) {
		this.value = value;
		queue = new int[QUEUE_SIZE];
	}
	//=====================================================
	public int getValue() {
		showTime("start getvalue: " + this.value);
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		showTime("end getvalue: " + this.value);
		return value;
	}

	public void setValue(int value) {
		showTime("start setValue: " + this.value);
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.value ++;
		showTime("end setValue: " + this.value);
	}
	//=====================================================
	public synchronized int getValue0() {
		showTime("start getvalue0: " + this.value);
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		showTime("end getvalue0: " + this.value);
		return value;
	}

	public synchronized void setValue0(int value) {
		showTime("start setValue0: " + this.value);
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.value ++;
		showTime("end setValue0: " + this.value);
	}
	//=====================================================
	public int getValue1() {
		lock.lock();
		try {
			showTime("start getvalue1: " + this.value);
			try {
				//int ts = new Random().nextInt(10);
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			showTime("end getvalue1: " + this.value);
		} finally {
			lock.unlock();
		}
		
		return value;
	}

	public void setValue1(int value) {
		lock.lock();
		
		try {
			showTime("start setValue1: " + this.value);
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.value ++;
			showTime("end setValue1: " + this.value);
		} finally {
			lock.unlock();
		}		
	}
	//=====================================================
	public int getValue2() {
		rwLock.readLock().lock();
		try {
			showTime("start getvalue2: " + this.value);
			try {
				//int ts = new Random().nextInt(10);
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			showTime("end getvalue2: " + this.value);
		} finally {
			rwLock.readLock().unlock();
		}
		
		return value;
	}

	public void setValue2(int value) {
		rwLock.writeLock().lock();
		try {
			showTime("start setValue2: " + this.value);
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.value ++;
			showTime("end setValue2: " + this.value);
		} finally {
			rwLock.writeLock().unlock();
		}		
	}
	//=====================================================
	public void setValue3(int value) {

		try {
			try {
				rwLock.writeLock().lockInterruptibly();
				showTime("start setValue3: " + this.value);
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.value ++;
			showTime("end setValue3: " + this.value);
		} finally {
			rwLock.writeLock().unlock();
		}		
	}
	//=====================================================
	public int getFromQueue() {
		int value = -1;
		lock.lock();
		try {
			showTime("start getFromQueue");
			while(count == 0) {
				notEmpty.await();
			}
			value = queue[getPos];
			count--;
			if (getPos == 0) {
				getPos = queue.length - 1;
			} else {
				getPos--;
			}
			showTime("getFromQueue with value: " + value);
			Thread.sleep(interval);
			notFull.signal();
			showTime("End of getFromQueue");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		
		return value;
	}

	public void putToQueue(int value) {
		lock.lock();
		try {
			showTime("start putToQueue");
			while(count == queue.length) {
				notFull.await();
			}
			queue[putPos] = value;
			count++;
			if (putPos == queue.length - 1) {
				putPos = 0;
			} else {
				putPos++;
			}
			showTime("putToQueue with value: " + value);
			Thread.sleep(interval);
			notEmpty.signal();
			showTime("End of putToQueue");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}		
	}
	//=====================================================
	
}
