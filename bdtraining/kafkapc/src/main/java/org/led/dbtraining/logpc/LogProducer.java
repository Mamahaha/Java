package org.led.dbtraining.logpc;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;



public class LogProducer {

	private static Random rand = new Random();
	private static String[] sentences = new String[] { "edi:I'm happy",
			"wow:I'm ERROR", "john:I'm sad", "ted:I'm excited", "sc:I'm ERROR too",
			"laden:I'm dangerous" };
	
	public void produce(String topicName) throws Exception{
		
		
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "1");
        //props.put("retries", 0);
        //props.put("batch.size", 16384);
        //props.put("linger.ms", 1);
        //props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        
        System.out.println("Start to send message to topic: " + topicName);
        try {
	        while (true) {
	        	String toSay = sentences[rand.nextInt(sentences.length)];
	        	System.out.println(" * Message: " + toSay);
	        	ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicName, toSay);
	        	producer.send(record);
	    		Thread.sleep(1000);    		
	        }
        } catch (Exception e) {
        	System.out.println(e);
        } finally {
        	System.out.println("Message sent successfully");
            producer.close();
        }
	}

}
