package org.led.bigdata.sparkavro.data;

import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class AvroFileUriRecKeyInputFormat extends FileInputFormat<AvroFileUriRecKey, EmptyWritable> {

    @Override
    public RecordReader<AvroFileUriRecKey, EmptyWritable> createRecordReader(
            InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        Schema readerSchema = AvroJob.getInputKeySchema(context.getConfiguration());
        if (null == readerSchema) {
        	System.out.println("Reader schema was not set. Use AvroJob.setInputKeySchema() if desired.");
        	System.out.println("Using a reader schema equal to the writer schema.");
        }
        return new AvroFileUriRecKeyRecordReader(readerSchema);
    }
}