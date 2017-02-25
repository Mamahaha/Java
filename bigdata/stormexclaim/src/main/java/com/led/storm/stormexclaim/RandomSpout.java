package com.led.storm.stormexclaim;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 * Hello world!
 * 
 */
public class RandomSpout extends BaseRichSpout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2147837964012030052L;

	private SpoutOutputCollector collector;

	private Random rand;

	private static String[] sentences = new String[] { "edi:I'm happy",
			"marry:I'm angry", "john:I'm sad", "ted:I'm excited",
			"laden:I'm dangerous" };

	
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
		this.rand = new Random();
	}

	
	public void nextTuple() {
		String toSay = sentences[rand.nextInt(sentences.length)];
		this.collector.emit(new Values(toSay));
	}

	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}

}