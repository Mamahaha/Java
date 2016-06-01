package org.led.sreporting.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerialClientId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> cl = new ArrayList<String>();
	
	public SerialClientId() {
		
	}
	
	public SerialClientId(String clientId) {
		cl.add(clientId);
	}
	
	public void add(String clientId) {
		cl.add(clientId);
	}
	
	public String toString() {
		if (cl.size() == 0) {
			return " client-id-list is empty";
		}
		StringBuilder bb = new StringBuilder();
		bb.append(" client-id-list: ");
		for (String item : cl) {
			bb.append(item + "; ");
		}
		return bb.toString();
	}

}
