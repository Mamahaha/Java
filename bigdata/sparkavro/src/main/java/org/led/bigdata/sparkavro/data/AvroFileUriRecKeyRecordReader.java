package org.led.bigdata.sparkavro.data;

import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.mapreduce.AvroRecordReaderBase;
import org.led.bigdata.sparkavro.avro.FileUriRec;

public class AvroFileUriRecKeyRecordReader extends AvroRecordReaderBase<AvroFileUriRecKey, EmptyWritable, FileUriRec> {
    
    private final AvroFileUriRecKey mCurrentRecord;

    public AvroFileUriRecKeyRecordReader(Schema readerSchema) {
        super(readerSchema);
        mCurrentRecord = new AvroFileUriRecKey(null);
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        boolean hasNext = super.nextKeyValue();
        mCurrentRecord.datum();
        return hasNext;
    }

    @Override
    public AvroFileUriRecKey getCurrentKey() throws IOException, InterruptedException {
        return mCurrentRecord;
    }

    @Override
    public EmptyWritable getCurrentValue() throws IOException, InterruptedException {
        return EmptyWritable.get();
    }
    
}
