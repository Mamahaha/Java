package org.led.bigdata.reporting.job;


import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;


public class ReportingFileReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	private MultipleOutputs<Text, IntWritable> mo = null;
	
	@Override
	public void reduce(Text text, Iterable<IntWritable> values, Context ctx) throws IOException, InterruptedException {
		int sum = 0;
		for (IntWritable value : values) {
			sum += value.get();
		}
		mo.write(text, new IntWritable(sum), "/output/output.csv");
	}
	
	@Override
    protected void setup(Context context) throws IOException, InterruptedException {
        mo = new MultipleOutputs<Text, IntWritable>(context);
        super.setup(context);
    }
	
	@Override
    protected void cleanup(Context context) throws IOException,InterruptedException{
        mo.close();
        super.cleanup(context);
    }
}
