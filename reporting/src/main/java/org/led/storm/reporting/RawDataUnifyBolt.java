package org.led.storm.reporting;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class RawDataUnifyBolt extends BaseBasicBolt {
	public void execute(Tuple input, BasicOutputCollector collector) {
		String sentence = (String) input.getValue(0);
		System.out.println("unify bolt input: " + sentence);
		String out = sentence + "!";
		collector.emit(new Values(out));		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("excl_sentence"));		
	}
}
