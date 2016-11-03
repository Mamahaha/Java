package org.led.dbtraining.stormlog;

import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class HbaseBolt extends BaseBasicBolt{
	
	private Connection connection;
	private Table table;
	
	public void prepare(Map stormConf, TopologyContext ctx) {
		Configuration conf = HBaseConfiguration.create();
		try {
			connection = ConnectionFactory.createConnection(conf);
			table = connection.getTable(TableName.valueOf("Error"));
		} catch(Exception e) {
			System.out.println("Error when connecting to hbase: " + e);
		}
	}
	
	public void execute(Tuple input, BasicOutputCollector collector) {
		String line = input.getString(0);
		try {
			System.out.println("hbase bolt -- write line: " + line);
			Put put = new Put(Bytes.toBytes(line));
			put.addColumn(Bytes.toBytes("col"), Bytes.toBytes("line"), Bytes.toBytes(line));
			table.put(put);
		} catch (Exception e) {
			System.out.println("Error when writing to hbase: " + e);
		}
		
	}
	
	public void cleanup() {
		try {
			if (table != null) {
				table.close();
			}
		} catch (Exception e) {
			System.out.println("Error when closing table: " + e);
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				System.out.println("Error when closing connection: " + e);
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
