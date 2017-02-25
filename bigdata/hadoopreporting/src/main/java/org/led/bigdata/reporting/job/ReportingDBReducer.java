package org.led.bigdata.reporting.job;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import org.led.hadoop.reporting.db.ConfigLoader;
import org.led.hadoop.reporting.db.EntityManagerUtil;
import org.led.hadoop.reporting.db.ReportingTypeSummaryEntity;


public class ReportingDBReducer extends Reducer<Text, IntWritable, NullWritable, NullWritable>{
	@Override
    protected void setup(Context context) throws IOException, InterruptedException {
		ConfigLoader.initContent("/tmp/db.conf");
        super.setup(context);
    }
	
	@Override
	public void reduce(Text text, Iterable<IntWritable> values, Context ctx) throws IOException, InterruptedException {
		int sum = 0;
		for (IntWritable value : values) {
			sum += value.get();
		}
		
		ReportingTypeSummaryEntity entity = new ReportingTypeSummaryEntity();
		entity.setType(text.toString());
		entity.setCount(sum);
		EntityManagerUtil.addTypeSummaryRecord(entity);
	}
}
