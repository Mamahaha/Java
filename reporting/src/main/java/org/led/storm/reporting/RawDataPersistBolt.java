package org.led.storm.reporting;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class RawDataPersistBolt extends BaseBasicBolt {
	private int indexId;
	
	public void prepare(Map stormConf, TopologyContext context) {
		this.indexId = context.getThisTaskId();
	}

	public void execute(Tuple input, BasicOutputCollector collector) {
		String rec = input.getString(0);
		System.out.println(String.format("Bolt[%d] String received: %s", this.indexId, rec));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}
}
