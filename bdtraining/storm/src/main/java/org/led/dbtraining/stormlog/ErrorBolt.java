package org.led.dbtraining.stormlog;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class ErrorBolt extends BaseBasicBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7104101711336484131L;

	public void execute(Tuple input, BasicOutputCollector collector) {
		String line = (String) input.getValue(0);
		//System.out.println("bolt received: " + line);
		if (line.contains("ERROR")) {
			collector.emit(new Values(line));
		}		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("ErrorBolt"));
		
	}

}
