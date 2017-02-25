package org.led.javacore.core1.chapter5;

public class Viecle {
	public enum State {
		START, STOP, RUNNING, DISCARD; 
	}
	
	private State state;
	
	public void setState(State s) {
		state = s;
	}
	
	public State getState() {
		return state;
	}
}
