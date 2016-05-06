package org.led.sreporting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;


public class MainApp 
{
//    mvn clean install
//	jar umf src/META-INF/MANIFEST.MF target/sreporting-0.0.1-SNAPSHOT.jar
//	go cp target/sreporting-0.0.1-SNAPSHOT.jar 200:/var/tmp/reporting
//	/opt/spark/default/bin/spark-submit --class "org.led.sreporting.MainApp" --master local[4] sreporting-0.0.1-SNAPSHOT.jar 2>&1 | grep "==="
	private static void countRecord() {
		long start = System.currentTimeMillis();
	    SparkConf conf = new SparkConf().setAppName("CountRecord");
	    JavaSparkContext sc = new JavaSparkContext(conf);
	    JavaPairRDD<String, String> logs = sc.wholeTextFiles("/user/reporting").cache();
	    
	    long numAs = logs.keys().filter(new Function<String, Boolean>() {
	      public Boolean call(String s) { return s.contains("a"); }
	    }).count();

	    long numBs = logs.keys().filter(new Function<String, Boolean>() {
	      public Boolean call(String s) { return s.contains("b"); }
	    }).count();
	    
	    long cost = System.currentTimeMillis() - start;
	    System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs + " with time cost: " + cost);
	}
	
	private static void test() {
		long start = System.currentTimeMillis();
	    SparkConf conf = new SparkConf().setAppName("CountRecord");
	    JavaSparkContext sc = new JavaSparkContext(conf);
	    JavaPairRDD<String, String> logs = sc.wholeTextFiles("/user/reporting").cache();
	    System.out.println("===app=== values: " +logs.values());

	    JavaRDD<String> fullLog = logs.flatMapValues(new FlatMapFunction<String, String>() {
			public Iterable<String> call(String arg0) throws Exception {
				String[] lines = arg0.split("\n");
				return Arrays.asList(lines);
			}
	    });
	    
	    List<String> contents = logs.values().collect();
	    List<String> lines = new ArrayList<String>();
	    for (String content : contents) {
	    	String[] subLines = content.split("\n");
	    	lines.addAll(Arrays.asList(subLines));
	    }
	    //JavaRDD<String> fullLog = sc.parallelize(lines);
	    
	    long numAs = fullLog.filter(new Function<String, Boolean>() {
	      public Boolean call(String s) { return s.contains("a"); }
	    }).count();
	    
	    long cost = System.currentTimeMillis() - start;
	    System.out.println("===app=== Lines with a: " + numAs + " with time cost: " + cost);
	}
	
	public static void main(String[] args) {
		test();
		//countRecord();
   	}
}
