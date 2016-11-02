package org.led.dbtraining.logpc;

public class Logger {

	public static void main(String[] args) throws Exception{
		if(args.length < 2){
	         System.out.println("Usage: ");
	         System.out.println("  $ java -jar kafkalogpc-0.0.1-SNAPSHOT-jar-with-dependencies.jar [p|c] <topic_name>");
	         return;
	    }
		
		String mode = args[0];
		String topicName = args[1];

		if (mode.equals("p")) {
			LogProducer p = new LogProducer();
			p.produce(topicName);
		} else if (mode.equals("c")) {
			LogConsumer c = new LogConsumer();
			c.consume(topicName);
		} else {
			System.out.println("Invalid mode: " + mode);
		}
	}

}
