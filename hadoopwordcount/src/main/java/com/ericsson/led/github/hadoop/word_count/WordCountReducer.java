package com.ericsson.led.github.hadoop.word_count;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	public void reduce(Text text, Iterable<IntWritable> values, Context ctx) throws IOException, InterruptedException {
		int sum = 0;
		for (IntWritable value : values) {
			sum += value.get();
		}
		ctx.write(text, new IntWritable(sum));
	}
}
