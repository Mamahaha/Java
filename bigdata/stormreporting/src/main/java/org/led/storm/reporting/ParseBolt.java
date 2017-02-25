package org.led.storm.reporting;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class ParseBolt extends BaseBasicBolt {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(Tuple input, BasicOutputCollector collector) {
		String sentence = (String) input.getValue(0);
		//System.out.println("parse bolt input: " + sentence);
		String[] items = sentence.split("\n");
		for (int i = 1; i < items.length; i++) {
			String[] params = items[i].split(",");
			collector.emit(new Values(params[0], params[1], params[4], params[5]));
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("broadcast_id", "client_id", "lost_obj", "received_obj"));		
	}
}