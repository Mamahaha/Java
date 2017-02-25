package org.led.basic.multithreads.sync;

public class Job implements Runnable{
	int itl;
	Item item;
    int syncMode = 0;
    
	public Job(Item item, int interval, int syncMode) {
		itl = interval;
		this.item = item;
		this.syncMode = syncMode;
	}
	
	@Override
	public void run() {
		switch(syncMode) {
		case 0:
			this.item.setValue1(itl);  //obj not sync
			break;
		case 1:
			this.item.setValue2(itl);  //obj inner sync
			break;
		case 2:
			synchronized(this.item){
				this.item.setValue1(itl); //obj outer sync
			}
			break;
		case 3:
			synchronized(Item.class){
				this.item.setValue1(itl); //obj outer sync
			}
			break;
		case 4:
			this.item.setValue3(itl); //another obj inner sync
			break;
		default:
			break;
		}
	}

}
