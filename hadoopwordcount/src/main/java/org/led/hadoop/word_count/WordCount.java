package org.led.hadoop.word_count;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;



/**
 * Word Count
 *
 */
public class WordCount extends Configured implements Tool
{
    public static void main( String[] args ) throws IOException, ClassNotFoundException, InterruptedException
    {
    	try {
			int ret = ToolRunner.run(new WordCount(), args);
			System.exit(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

	public int run(String[] args) throws Exception {
		Path inputDir = new Path(args[0]);
    	Path outputDir = new Path(args[1]);
    	
    	//Create configuration
    	Configuration conf = new Configuration();
    	
    	//Create job
    	Job job = Job.getInstance(conf, "WordCount");
    	job.setJarByClass(WordCountMapper.class);
    	
    	//Setup MapReduce
    	job.setMapperClass(WordCountMapper.class);
    	job.setReducerClass(WordCountReducer.class);
    	
    	//Specify key / value
    	job.setOutputKeyClass(Text.class);
    	job.setOutputValueClass(IntWritable.class);
    	
    	//Input
    	FileInputFormat.addInputPath(job, inputDir);
    	job.setInputFormatClass(TextInputFormat.class);
    	
    	//Output
    	FileOutputFormat.setOutputPath(job, outputDir);
    	job.setOutputFormatClass(TextOutputFormat.class);
    	
    	//Delete output if exists
    	FileSystem hdfs = FileSystem.get(conf);
    	if (hdfs.exists(outputDir)) {
    		hdfs.delete(outputDir, true);
    	}
    	
    	//Execute job
    	int code = job.waitForCompletion(true) ? 0 : 1;

		return code;
	}
}
