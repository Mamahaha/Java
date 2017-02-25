package org.led.basic.multithreads.reentrant;

public class ReentrantWriteJob implements Runnable {
    private ReentrantData data;
    private int value;
    private int mode;
    
    public ReentrantWriteJob(ReentrantData data, int value, int mode) {
        this.data = data;
        this.value = value;
        this.mode = mode;
    }
    
    public void run() {     
        switch(mode) {
        case 0:
            data.setValue(value);
            break;
        case 1:
            data.setValue0(value);
            break;
        case 2:
            data.setValue1(value);
            break;
        case 3:
            data.setValue2(value);
            break;
        case 4:
            data.setValue3(value);
            break;
        case 5:
            data.putToQueue(value);
            break;
        default:
            break;
        }
    }
}
