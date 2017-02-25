package org.led.javacore.core1.chapter5;

public enum Event {
	START("S", 1), PAUSE("P", 2), RESUME("R", 3), TERMINATE("T", 4);
	
	private Event(String abbrev, int index) {
		this.abbrev = abbrev;
		this.index = index;
	}
	
	public String getAbbrev() {
		return abbrev;
	}
	
	public int getIndex() {
		return index;
	}
	
	private String abbrev;
	private int index;
}
