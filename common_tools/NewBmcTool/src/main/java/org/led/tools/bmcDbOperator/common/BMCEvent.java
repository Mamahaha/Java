package org.led.tools.bmcDbOperator.common;

import java.util.HashMap;
import java.util.Map;

public class BMCEvent implements Event{

	private String topic;

    private Map<String, String> options = new HashMap<String, String>();

    public Map<String, String> getOptions() {
        return options;
    }

    public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
}
