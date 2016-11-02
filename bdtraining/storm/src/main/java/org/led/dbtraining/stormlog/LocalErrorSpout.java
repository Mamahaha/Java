package org.led.dbtraining.stormlog;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class LocalErrorSpout extends BaseRichSpout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4098933606311809998L;
	private SpoutOutputCollector collector;
	private Random rand;
	private static String[] sentences = new String[] { "edi:I'm happy",
			"wow:I'm ERROR", "john:I'm sad", "ted:I'm excited", "sc:I'm ERROR too",
			"laden:I'm dangerous" };
	
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
		this.rand = new Random();		
	}

	public void nextTuple() {
		String toSay = sentences[rand.nextInt(sentences.length)];
		Utils.sleep(1000);
		this.collector.emit(new Values(toSay));
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}

}
