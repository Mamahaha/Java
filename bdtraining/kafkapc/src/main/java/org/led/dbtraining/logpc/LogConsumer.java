package org.led.dbtraining.logpc;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class LogConsumer {
	public void consume(String topicName) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "bbb");
		props.put("enable.auto.commit", "true");
	    props.put("auto.commit.interval.ms", "1000");
	    props.put("session.timeout.ms", "30000");
	    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
	    	    
	    consumer.subscribe(Arrays.asList(topicName));
	    
	    System.out.println("Start to subscribe to topic: " + topicName);
	    
	    try {
	    	while (true) {
		    	ConsumerRecords<String, String> records = consumer.poll(200);
		        for (ConsumerRecord<String, String> record : records) {
		        	System.out.printf("  * Offset: " + record.offset() + ", message: " + record.value() + "\n");
		        }
	    	}
	    } catch (Exception e) {
	    	System.out.println(e);
	    } finally {
	    	System.out.println("Message received successfully");
	    	consumer.close();
	    }	    
	}
}
