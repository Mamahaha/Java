package org.led.dbtraining.stormlog;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class ErrorScheme implements Scheme {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Fields getOutputFields() {
		return new Fields("ErrorScheme");
	}

	public List<Object> deserialize(ByteBuffer ser) {
		String str = ser.toString();
		return new Values(str);
	}
}
