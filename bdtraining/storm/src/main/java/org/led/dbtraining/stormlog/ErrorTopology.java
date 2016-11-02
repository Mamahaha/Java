package org.led.dbtraining.stormlog;

import java.util.Properties;
import java.util.UUID;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class ErrorTopology {
	private static String defaultTopologyName = "ErrorTopology";
	
	public static void kafka(String topologyName, String topicName) throws Exception {
		BrokerHosts hosts = new ZkHosts("127.0.0.1:2181");
		SpoutConfig spoutConfig = new SpoutConfig(hosts, topicName, "/kafkastorm", UUID.randomUUID().toString());
		Config conf = new Config();
		Properties props = new Properties();
		//props.put("metadata.broker.list", "127.0.0.1:9092");
		//props.put("request.required.acks", 1);
		//props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("bootstrap.server", "127.0.0.1:9092");
		props.put("acks", 1);
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //conf.put("kafka.broker.properties", props);
        
        //conf.put("topic", topicName);
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("source", new KafkaSpout(spoutConfig));
        builder.setBolt("error", new ErrorBolt()).shuffleGrouping("source");
        builder.setBolt("print", new PrintBolt(), 3).shuffleGrouping("error");  
        //builder.setBolt("print", new KafkaBolt<String, Integer>()).shuffleGrouping("error");

        
        if (topologyName != null) {  
	        conf.setNumWorkers(1);	
	        StormSubmitter.submitTopology(topologyName, conf, builder.createTopology());
	    } else {  
	        LocalCluster cluster = new LocalCluster();  
	        cluster.submitTopology(defaultTopologyName, conf, builder.createTopology());  
	        Utils.sleep(100000);  
	        cluster.killTopology(defaultTopologyName);  
	        cluster.shutdown();  
	    }
	}
	
	public static void testKafka(String topicName)  throws Exception {
		Config config = new Config();
		  config.setDebug(true);
		  config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		  String zkConnString = "localhost:2181";
		  BrokerHosts hosts = new ZkHosts(zkConnString);
		  
		  SpoutConfig kafkaSpoutConfig = new SpoutConfig (hosts, topicName, "/" + topicName,    
		     UUID.randomUUID().toString());
		  kafkaSpoutConfig.bufferSizeBytes = 1024 * 1024 * 4;
		  kafkaSpoutConfig.fetchSizeBytes = 1024 * 1024 * 4;
		  kafkaSpoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		
		  TopologyBuilder builder = new TopologyBuilder();
	      builder.setSpout("source", new KafkaSpout(kafkaSpoutConfig));
	      builder.setBolt("error", new ErrorBolt()).shuffleGrouping("source");
	      builder.setBolt("print", new PrintBolt(), 3).shuffleGrouping("error"); 
		     
	      StormSubmitter.submitTopology("testtop", config, builder.createTopology());
		
		
	}
	
	public static void local(String topologyName) throws Exception {
		
		TopologyBuilder builder = new TopologyBuilder();  
        
        builder.setSpout("source", new LocalErrorSpout());  
        builder.setBolt("error", new ErrorBolt(), 2).shuffleGrouping("source");  
        builder.setBolt("print", new PrintBolt(), 3).shuffleGrouping("error");  
  
        Config conf = new Config();  
        conf.setDebug(false);  
  
        if (topologyName == null) {
        	topologyName = defaultTopologyName;
        }
        
        conf.setNumWorkers(1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(topologyName, conf, builder.createTopology());
        Utils.sleep(180000);
        cluster.killTopology(topologyName);
        cluster.shutdown();
        
//        if (topologyName != null) {  
//            conf.setNumWorkers(1);  
//  
//            StormSubmitter.submitTopology(topologyName, conf, builder.createTopology());  
//        } else {  
//            LocalCluster cluster = new LocalCluster();  
//            cluster.submitTopology(defaultTopologyName, conf, builder.createTopology());  
//            Utils.sleep(100000);  
//            cluster.killTopology(defaultTopologyName);  
//            cluster.shutdown();  
//        }
	}
	
	public static void main(String[] args) throws Exception {
		
		String topologyName = null;
		String topicName = null;
		if (args.length == 0) {
			System.out.println("Invalid parameters count: " + args.length);
			return;
		} else if (args.length == 2) {
			topologyName = args[0];
		} else if (args.length == 3) {
			topologyName = args[0];
			topicName = args[2];
		}		

		if (args[1].equals("local")) {
			local(topologyName);
		} else if (args[1].equals("kafka")) {
			System.out.println("Start kafka mode with topology name: " + topologyName + "; topic name: " + topicName);
			kafka(topologyName, topicName);
			//testKafka(topicName);
		} else {
			System.out.println("Invalid mode: " + args[1] + " with name: " + topologyName);
		}
		
	}
}
