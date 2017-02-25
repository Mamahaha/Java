package org.led.bigdata.sparkavro.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.led.bigdata.sparkavro.avro.ReceptionReport;


public class SerialReceptionReport extends ReceptionReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SerialReceptionReport() {

	}

	public SerialReceptionReport(ReceptionReport avroPojo) {
		setValues(avroPojo);
	}

	private void setValues(ReceptionReport avroPojo) {
		setClientId(avroPojo.getClientId());
		setServiceId(avroPojo.getServiceId());
		setServiceURI(avroPojo.getServiceURI());
		setSessionType(avroPojo.getSessionType());
		setNetworkResourceCellId(avroPojo.getNetworkResourceCellId());
		setSessionId(avroPojo.getSessionId());
		setDeliverySessionInstanceInfoId(avroPojo.getDeliverySessionInstanceInfoId());
		setNumOfLostObjects(avroPojo.getNumOfLostObjects());
		setNumOfReceivedObjects(avroPojo.getNumOfReceivedObjects());
		setFileUriList(avroPojo.getFileUriList());
		setSessionStartTime(avroPojo.getSessionStartTime());
		setSessionStopTime(avroPojo.getSessionStopTime());
		
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		DatumWriter<ReceptionReport> writer = new SpecificDatumWriter<ReceptionReport>(ReceptionReport.class);
		Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		writer.write(this, encoder);
		encoder.flush();
	}

	@SuppressWarnings("unused")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		DatumReader<ReceptionReport> reader = new SpecificDatumReader<ReceptionReport>(ReceptionReport.class);
		Decoder decoder = DecoderFactory.get().binaryDecoder(in, null);
		setValues(reader.read(null, decoder));
	}
	
	@Override
    public int compareTo( SpecificRecord o )
    {
        try
        {
            if ( this == o )
                return 0;
            if ( o instanceof SerialReceptionReport )
            {
            	SerialReceptionReport that = (SerialReceptionReport)o;
                if (!this.clientId.equals(that.clientId)) {
                	return -1;
                }
                if (!this.serviceURI.equals(that.serviceURI)) {
                	return -1;
                }
                if (this.sessionStartTime != that.sessionStartTime) {
                	return -1;
                }
                if (this.sessionStopTime != that.sessionStopTime) {
                	return -1;
                }

                return 0;
            }
            throw new IllegalArgumentException( "Can only compare two Reception report" ); //$NON-NLS-1$

        }
        catch(Exception e)
        {
            throw new RuntimeException( e );
        }
    }
	
	@SuppressWarnings( { "nls", "deprecation" } )
    @Override
    public String toString()
    {
        StringBuilder builder2 = new StringBuilder();
        builder2.append( "SerialReceptionRecord [clientId=" );
        builder2.append( clientId );
        builder2.append( ", serviceURI=" );
        builder2.append( serviceURI );
        builder2.append( ", sessionStartTime=" );
        builder2.append( sessionStartTime );
        builder2.append( ", sessionStopTime=" );
        builder2.append( sessionStopTime );
        builder2.append( "]" );
        return builder2.toString();
    }
}
