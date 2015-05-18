package org.led.hellorest;

import org.led.hellorest.inbound.RestServer;

public class MainApp {
	private static RestServer server = null;
	
	public static void startServer() {
		server = new RestServer();
		server.start();
	}
	
	public static void main(String[] args) {
		startServer();
	}
}
