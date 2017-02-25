package org.led.bigdata.reporting.data;

import java.io.File;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class ReportingGenerator {
	private static void generateRR() throws Exception{		
		for (int i=0; i<3; i++) {
			String name = String.valueOf(0xF0000213 + i);
			String type = "iphone";
			int count = (i + 10) % 3 + 1;
			String path = "/home/led/tmp/reporting/rr" + i + ".avro";
			writeRR(path, name, type, count);
		}
		for (int i=3; i<7; i++) {
			String name = String.valueOf(0xF0000213 + i);
			String type = "huawei";
			int count = (i + 10) % 3 + 1;
			String path = "/home/led/tmp/reporting/rr" + i + ".avro";
			writeRR(path, name, type, count);
		}
		for (int i=7; i<10; i++) {
			String name = String.valueOf(0xF0000213 + i);
			String type = "samsung";
			int count = (i + 10) % 3 + 1;
			String path = "/home/led/tmp/reporting/rr" + i + ".avro";
			writeRR(path, name, type, count);
		}
	}
	private static void writeRR(String outFile, String name, String type, int count) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("<![CDATA[MIME-Version: 1.0\n")
		.append("Content-Type: multipart/mixed; boundary=frontier\n\n")
		.append("--frontier\n\n")
		.append("Content-Type: text/xml\n\n")
		.append("<g1:receptionReport xmlns=\"urn:3gpp:ns:metadata\" xmlns:g1=\"urn:3gpp:metadata:2008:MBMS:receptionreport\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:3gpp:metadata:2008:MBMS:receptionreport receptionReportingRequestRestricted.xsd\">\n")
		.append("<g1:statisticalReport clientId=\"26101712002828001\" serviceId=\"urn:3GPP:metadata-service2-2\">\n")
		.append("<g1:fileURI Content-MD5=\"ZjFjOTY0NWRiYzE0ZWZkZGM3ZDhhMzIyNjg1ZjI2ZWI=\" receptionSuccess=\"true\">http://10.175.183.121/10M.mp4</g1:fileURI>\n")
		.append("<g1:qoeMetrics networkResourceCellId=\"0010112ad00 \" sessionStartTime=\"3649717620\" sessionStopTime=\"3649717679\"></g1:qoeMetrics>\n")
		.append("</g1:statisticalReport>\n")
		.append("</g1:receptionReport>\n")
		.append("--frontier\n")
		.append("]]>\n");

		ReceptionReport rr = new ReceptionReport();
		rr.setName(name);
		rr.setType(type);
		rr.setCount(count);
		rr.setArriveTime(2015090201);
		rr.setRequestURI("http://10.175.183.125:80/reportingService/test_bdc/F00002130184_0_20150902013200000_16777221");
		rr.setSourceAddress("http://10.175.183.121/10M.mp4");
		rr.setRequestXML(sb.toString());

		
		File f = new File(outFile);
		DatumWriter<ReceptionReport> datumWriter = new SpecificDatumWriter<ReceptionReport>(ReceptionReport.class);
		DataFileWriter<ReceptionReport> dataWriter = new DataFileWriter<ReceptionReport>(datumWriter);
		
		dataWriter.create(rr.getSchema(), f);
		dataWriter.append(rr);
		dataWriter.close();		
	}
	
	private static void readRR(String inFile) throws Exception {
		File f = new File(inFile); 
		DatumReader<ReceptionReport> datumReader = new SpecificDatumReader<ReceptionReport>(ReceptionReport.class);
		DataFileReader<ReceptionReport> dataReader = new DataFileReader<ReceptionReport>(f, datumReader);
		
		ReceptionReport rr = null;
		while (dataReader.hasNext()) {
			System.out.println("Read RR");
			rr = dataReader.next(rr);
			System.out.println(rr.getType() + ": " + rr.getCount());
		}		
	}
	
	public static void main(String[] args) throws Exception{
		//generateRR();
		//writeRR();
		for (int i=0; i<10; i++) {
			readRR("/home/led/tmp/reporting/rr" + i + ".avro");
		}
	}
}
