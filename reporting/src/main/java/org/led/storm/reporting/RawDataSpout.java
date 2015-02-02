package org.led.storm.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class RawDataSpout extends BaseRichSpout{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
		
	}
	
	public void nextTuple() {
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String rootPath = "./bmsccontents/test/";
		String content = loadFile(rootPath);
		if (content != null) {
			System.out.println("================Ready to emit new raw data.===================");
			this.collector.emit(new Values(content));
		}else {
			System.out.println("ERROR: No raw data to emit");
		}
	}

	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}
		
	private String loadFile(String rootPath) {
		String filePath = rootPath + "1.log";
		File file = new File(filePath);
		Long fileLength = file.length();
		byte[] fileContent = new byte[fileLength.intValue()];
		
		try {
			FileInputStream is = new FileInputStream(file);
			is.read(fileContent);
			is.close();

			String content = new String(fileContent);
			//System.out.println("rawdata spout read line: " + content);
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}	
	}
}
