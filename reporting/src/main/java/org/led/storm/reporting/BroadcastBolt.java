package org.led.storm.reporting;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class BroadcastBolt extends BaseBasicBolt {
	private int indexId;
	private Integer totalLostCount = new Integer(0);
	private Integer totalReceivedCount = new Integer(0);

	public void prepare(Map stormConf, TopologyContext context) {
		this.indexId = context.getThisTaskId();
	}

	public void execute(Tuple input, BasicOutputCollector collector) {
		String bcId = input.getString(0);
		int curLostCount = Integer.parseInt(input.getString(2));
		int curReceivedCount = Integer.parseInt(input.getString(3));
		synchronized (totalLostCount) {
			totalLostCount = new Integer(totalLostCount.intValue()
					+ curLostCount);
		}
		synchronized (totalReceivedCount) {
			totalReceivedCount = new Integer(totalReceivedCount.intValue()
					+ curReceivedCount);
		}

		System.out
				.println(String
						.format("BroadcastBolt[%d] : id: %s, totalLostObjCount: %d, totalReceivedObjCount: %d",
								this.indexId, bcId, totalLostCount.intValue(),
								totalReceivedCount.intValue()));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("broadcast_id", "lost_obj", "received_obj"));
	}
}
