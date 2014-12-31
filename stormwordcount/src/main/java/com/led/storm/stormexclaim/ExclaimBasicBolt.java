package com.led.storm.stormexclaim;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class ExclaimBasicBolt extends BaseBasicBolt{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2045461794226323757L;

	public void execute(Tuple input, BasicOutputCollector collector) {
		String sentence = (String) input.getValue(0);
		String out = sentence + "!";
		collector.emit(new Values(out));		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("excl_sentence"));		
	}

}
