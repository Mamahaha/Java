package org.led.bigdata.reporting.job;

import java.io.IOException;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import org.led.hadoop.reporting.data.ReceptionReport;


public class ReportingMapper extends Mapper<AvroKey<ReceptionReport>, NullWritable, Text, IntWritable>{
	
	@Override
    public void map(AvroKey<ReceptionReport> key, NullWritable value, Context ctx) throws IOException, InterruptedException {
		String type = key.datum().getType().toString();
		int count = key.datum().getCount();
		ctx.write(new Text(type), new IntWritable(count));
	}
}
