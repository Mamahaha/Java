package org.led.storm.reporting;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

/**
 * Hello world!
 *
 */
public class Analyzer 
{
    public static void main( String[] args )
    {
    	TopologyBuilder builder = new TopologyBuilder();  
        
        builder.setSpout("rawdataspout", new RawDataSpout());  
        builder.setBolt("unifyrawdata", new RawDataUnifyBolt(), 2).shuffleGrouping("rawdataspout");  
        builder.setBolt("persistrawdata", new RawDataPersistBolt(), 3).shuffleGrouping("unifyrawdata");  
  
        Config conf = new Config();  
        conf.setDebug(false);  
  
        if (args != null && args.length > 0) {  
            conf.setNumWorkers(3);  
  
            try {
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			} catch (AlreadyAliveException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidTopologyException e) {
				// TODO Auto-generated catch block
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
}
