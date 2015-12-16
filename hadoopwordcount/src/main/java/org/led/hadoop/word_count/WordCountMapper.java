package org.led.hadoop.word_count;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {
	private final IntWritable ONE = new IntWritable(1);
	private Text word = new Text();
	
	public void map(Object key, Text value, Context ctx) throws IOException, InterruptedException {
		String[] csv = value.toString().split(" ");
		for (String str : csv) {
			word.set(str);
			ctx.write(word, ONE);
		}
	}
	
}
