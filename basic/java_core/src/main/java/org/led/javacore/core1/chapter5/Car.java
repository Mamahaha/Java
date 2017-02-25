package org.led.javacore.core1.chapter5;

public class Car extends Viecle {
	private String engine;
	private String type;
	
	public Car() {}
	
	public Car(String engine, String type) {
		this.engine = engine;
		this.setType(type);
	}
	
	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
