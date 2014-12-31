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
	private File fileName;
	private InputStreamReader reader;
	private BufferedReader br;
	private boolean fileFlag = false;
	private SpoutOutputCollector collector;
	
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
		
	}

	
	public void nextTuple() {
		if (fileFlag) {
			String line;
			try {
				line = br.readLine();
				System.out.println("rawdata spout input: " + line);
				if (line != null) {
					this.collector.emit(new Values(line));
				}
			} catch (IOException e) {
				this.collector.emit(new Values("dummy data, read file failed"));
				e.printStackTrace();
			}			
		}else {
			this.collector.emit(new Values("dummy data, open file failed"));
		}
	}

	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}
	
	@Override
    public void activate() {
		fileFlag = loadFile();
    }
	
	@Override
    public void deactivate() {
		
    }
	
	private boolean loadFile() {
		String rootPath = "/home/led/work/github/Java/reporting/bmsccontents/adfservice/";
		String filePath = rootPath + "1.log";
		
		fileName = new File(filePath);
		try {
			reader = new InputStreamReader(new FileInputStream(fileName));
			BufferedReader br = new BufferedReader(reader);
			String line = br.readLine();
			System.out.println("rawdata spout read line: " + line);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	
	}
}
