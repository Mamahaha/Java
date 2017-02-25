package org.led.bigdata.sparkavro.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.led.bigdata.sparkavro.avro.ReceptionReport;

public class AvroReceptionReportKey extends AvroKey<ReceptionReport> implements Serializable{
	public AvroReceptionReportKey() {
        this(null);
    }

    private transient ReceptionReport __datum;

    public AvroReceptionReportKey(ReceptionReport datum) {
        super(datum);

        __datum = datum;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        byte[] data = toAvroByteArray(datum());

        out.writeInt(data.length);
        out.write(data);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int len = in.readInt();
        byte[] data = new byte[len];
        int readLen = in.read(data);

        __datum = fromAvroByteArray(data);
        this.datum(__datum);
    }

    public static byte[] toAvroByteArray(ReceptionReport record) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DatumWriter<ReceptionReport> writer = new SpecificDatumWriter<ReceptionReport>(ReceptionReport.class);
        BinaryEncoder tmpEncoder = EncoderFactory.get().binaryEncoder(out, null);
        writer.write(record, tmpEncoder);
        tmpEncoder.flush();
        out.close();
        return out.toByteArray();
    }

    public static ReceptionReport fromAvroByteArray(byte[] data) throws IOException {
        DatumReader<ReceptionReport> reader = new SpecificDatumReader<ReceptionReport>(ReceptionReport.class);
        BinaryDecoder tmpDecoder = DecoderFactory.get().binaryDecoder(data, null);
        return reader.read(null, tmpDecoder);
    }
}
