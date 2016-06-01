package org.led.sreporting.data;

import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.mapreduce.AvroRecordReaderBase;
import org.led.sreporting.avro.ReceptionReport;

public class AvroReceptionReportKeyRecordReader extends AvroRecordReaderBase<AvroReceptionReportKey, EmptyWritable, ReceptionReport> {
    
    private final AvroReceptionReportKey mCurrentRecord;

    public AvroReceptionReportKeyRecordReader(Schema readerSchema) {
        super(readerSchema);
        mCurrentRecord = new AvroReceptionReportKey(null);
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        boolean hasNext = super.nextKeyValue();
        mCurrentRecord.datum();
        return hasNext;
    }

    @Override
    public AvroReceptionReportKey getCurrentKey() throws IOException, InterruptedException {
        return mCurrentRecord;
    }

    @Override
    public EmptyWritable getCurrentValue() throws IOException, InterruptedException {
        return EmptyWritable.get();
    }
    
}
