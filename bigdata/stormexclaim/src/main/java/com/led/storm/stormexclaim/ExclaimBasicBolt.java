package com.led.storm.stormexclaim;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

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
