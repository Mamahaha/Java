package org.led.storm.reporting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class RawDataSpout extends BaseRichSpout{
	private SpoutOutputCollector collector;
	
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
		
	}
	
	public void nextTuple() {
		
		String rootPath = "/home/led/work/github/Java/reporting/bmsccontents/adfservice/";
		String content = loadFile(rootPath);
		if (content != null) {
			//System.out.println("rawdata spout input: " + content);
			this.collector.emit(new Values(content));					
		}else {
			this.collector.emit(new Values("dummy data, open file failed"));
			deactivate();
		}
	}

	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}
		
	private String loadFile(String rootPath) {
		InputStreamReader reader;
		BufferedReader br;
		
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
