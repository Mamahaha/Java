package org.led.tools.proto.bmc;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.led.tools.proto.bmc.BmcInfo.Bmc;
import org.led.tools.proto.bmc.BmcInfo.Bmc.Bdc;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BmcHandler {
	public static void handleBmcInfo() {
		Bmc bmc = Bmc.newBuilder()
				.setName("bmc1")
				.setIp("10.175.183.203")
				.setPort("8088")
				.addBdc(Bdc.newBuilder()
						.setName("bdc1")
						.setIp("10.175.183.206")
						.setPort("8080")
						.setDesc("Local BDC 1"))
				.addBdc(Bdc.newBuilder()
						.setName("bdc2")
						.setIp("10.175.183.207")
						.setPort("8080")
						.setDesc("Local BDC 2")).build();
			    
	    String fileName = "bmc.out";
	    try {
	    	System.out.println("Start serializing data...");
	    	FileOutputStream output = new FileOutputStream(fileName);
	    	bmc.writeTo(output);
	    	output.close();
	    	System.out.println("Stop serializing data...");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
	    try {
	    	System.out.println("Start unserializing data...");
	    	FileInputStream input = new FileInputStream(fileName);
	    	Bmc bmc2 = Bmc.parseFrom(input);
	    	System.out.println(bmc2);
	    	System.out.println("Stop unserializing data...");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }	
	}
	
	public static void main(String[] args) throws IOException {
		ServerSocket listener = new ServerSocket(12223);
        Socket socket = listener.accept();
		//Socket socket = new Socket();
		socket.setSoTimeout(10);
        int to = socket.getSoTimeout();
        System.out.println("socket timeout: " + to);
	}
}
