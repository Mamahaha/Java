package org.led.tools.proto.bmc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.led.tools.proto.reporting.ReportingProtocol;

public class ReportingClient {
	public  static void main(String[] args) throws HadoopIllegalArgumentException, IOException {
		InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 7766);
		Configuration conf = new Configuration();
		ReportingProtocol client = (ReportingProtocol)RPC.getProxy(ReportingProtocol.class, ReportingProtocol.versionID, addr, conf);
		
		String status = client.getJobStatus();
		System.out.println("Client get job status: " + status);
		
	}
}
