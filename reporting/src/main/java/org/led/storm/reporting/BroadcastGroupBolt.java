package org.led.storm.reporting;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class BroadcastGroupBolt {
	private int indexId;
	private static Integer totalLostCount;
	public void prepare(Map stormConf, TopologyContext context) {
		this.indexId = context.getThisTaskId();
	}

	public void execute(Tuple input, BasicOutputCollector collector) {
		String bcId = input.getString(0);
		int curLostCount = Integer.parseInt(input.getString(2));
		synchronized(totalLostCount) {
			totalLostCount = new Integer(totalLostCount.intValue() + curLostCount);
			System.out.println(String.format("Bolt[%d] : id: %s, totalcount: %d ", this.indexId, bcId, totalLostCount.intValue()));
			collector.emit(new Values(bcId, totalLostCount.intValue()));
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("broadcast_id", "lost_obj"));	
	}
}
