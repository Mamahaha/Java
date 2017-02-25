package org.led.storm.reporting;

import storm.trident.TridentState;
import storm.trident.TridentTopology;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

/**
 * Hello world!
 *
 */
public class Analyzer {
	
	private static void singleJob(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("rawdata", new RawDataSpout());
		builder.setBolt("parse", new ParseBolt(), 4).shuffleGrouping("rawdata");
		builder.setBolt("broadcast_filter", new BroadcastBolt(), 3)
				.fieldsGrouping("parse", new Fields("broadcast_id"));
		builder.setBolt("client_filter", new ClientBolt(), 10).fieldsGrouping(
				"parse", new Fields("client_id"));

		Config conf = new Config();
		conf.setDebug(false);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(3);
			try {
				StormSubmitter.submitTopology(args[0], conf,
						builder.createTopology());
			} catch (AlreadyAliveException e) {
				e.printStackTrace();
			} catch (InvalidTopologyException e) {
				e.printStackTrace();
			}
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("test", conf, builder.createTopology());
			Utils.sleep(30000);
			cluster.killTopology("test");
			cluster.shutdown();
		}
		
	}
	
	private static void tridentJob(String[] args) {
		/*TridentTopology topology = new TridentTopology();
		TridentState parseState = topology.newStream("rawdata", new RawDataSpout())
									.each(new Fields(), filter)*/
		
	}
	
	public static void main(String[] args) {
		singleJob(args);
	}
}
