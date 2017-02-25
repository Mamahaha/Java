package org.led.basic.multithreads.sync;

import java.text.DateFormat;
import java.util.Date;

public class Item {
	int value;

	public int getValue() {
		return value;
	}

	private void showTime(String prefix) {
		Date now = new Date();
		//Calendar cal = Calendar.getInstance();
		DateFormat df = DateFormat.getTimeInstance();
		String str = df.format(now);
		
		System.out.println(prefix + " SA obj: " + Thread.currentThread().getName() + " ; now: " +str);
	}
	
	public void setValue1(int value) {
		showTime("before 1");
		this.value += value;
		try {
			Thread.sleep(value*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		showTime("after 1");
	}
	
	synchronized public void setValue2(int value) {
		showTime("before 2");
		this.value += value;
		try {
			Thread.sleep(value*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		showTime("after 2");
	}
	
	synchronized public void setValue3(int value) {
		showTime("before 3");
		this.value += value;
		try {
			Thread.sleep(value*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		showTime("after 3");
	}
}
