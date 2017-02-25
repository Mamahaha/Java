package org.led.bigdata.reporting;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.Job;

public class MainApp extends Configured implements Tool
{
	private static final int COMPRESS_LEVEL = 9;
	private static final int DATA_SYNC_INTERVAL = 102400;
	
	public static void main( String[] args )
    {
        int ret;
		try {
			ret = ToolRunner.run(new MainApp(), args);
			System.exit(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}        
    }
	
	
	private int runExample(String ip, String op) throws Exception{
		Path inputDir = new Path(ip);
    	Path outputDir = new Path(op);
    	
		String jobName = "Process folder: " + inputDir;
		Configuration conf = new Configuration();
		
		Job job = Job.getInstance(conf, jobName);
		job.setJarByClass(MainApp.class);
        job.setMapperClass(ReportingMapper.class);
        job.setReducerClass(ReportingFileReducer.class);
        
        job.setNumReduceTasks(1);
        
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
	
	private int runRR(String ip, String op) throws Exception {
		Path inputDir = new Path(ip);
    	Path outputDir = new Path(op);
    	
		String jobName = "Process folder: " + inputDir;
		Configuration conf = new Configuration();
		
		Job job = Job.getInstance(conf, jobName);
		job.setJarByClass(MainApp.class);
        job.setMapperClass(ReportingMapper.class);
        //job.setReducerClass(ReportingFileReducer.class);
        job.setReducerClass(ReportingDBReducer.class);
        
        job.setNumReduceTasks(1);
        
        job.setOutputKeyClass(Text.class);
    	job.setOutputValueClass(IntWritable.class);
    	
    	//Input
    	FileInputFormat.addInputPath(job, inputDir);
    	job.setInputFormatClass(AvroKeyInputFormat.class);
    	
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
	
    

	public int run(String[] args) throws Exception {
		//return runExample(args[0], args[1]);
		return runRR(args[0], args[1]);
	}
	
	private void mergeAvro(List<File> fileList, String outPath) throws Exception{
		File outputFile = new File(outPath);

        // for report file
        DatumWriter<ReceptionReport> combineDatumWriter =
                new SpecificDatumWriter<ReceptionReport>(ReceptionReport.class);
        DataFileWriter<ReceptionReport> combinedFileWriter =
                new DataFileWriter<ReceptionReport>(combineDatumWriter);
        combinedFileWriter.setSyncInterval(DATA_SYNC_INTERVAL);
        // set compression method
        combinedFileWriter.setCodec(CodecFactory.deflateCodec(COMPRESS_LEVEL));

        combinedFileWriter.create(ReceptionReport.getClassSchema(),
        		outputFile);
		
        for (File f : fileList) {
            DatumReader<ReceptionReport> reader =
                    new SpecificDatumReader<ReceptionReport>(ReceptionReport.class);  
            DataFileReader<ReceptionReport> dataFileReader =
                    new DataFileReader<ReceptionReport>(f,reader);
            Iterator<ReceptionReport> it = dataFileReader.iterator();

            while(it.hasNext()) {
                combinedFileWriter.append(it.next());
            }
            dataFileReader.close();
            // delete the avro file
            //f.delete();
        }
        
        combinedFileWriter.flush();
        combinedFileWriter.close();
	}
	
	private void toAvro(String inPath, String outPath) throws Exception {
		
	}
	
	private boolean waitJobComplete(List<Job> jobs) {
        boolean jobSuccessful = true;
        try {
            while(jobs.size() > 0) {
                List<Job> finishJobList = new ArrayList<Job>();
                for (Job job : jobs) {
                    if(job.isComplete()) {
                        finishJobList.add(job);
                        jobSuccessful = jobSuccessful && job.isSuccessful(); 
                    }
                }

                if (finishJobList.size() > 0) {
                    jobs.removeAll(finishJobList);
                    if (jobs.size() == 0) {
                        break;
                    }
                }
                Thread.sleep(1000);
            }
        } catch(IOException e) {
            jobSuccessful = false;
        } catch(InterruptedException e) {
        }
        return jobSuccessful;
    }
	
}
