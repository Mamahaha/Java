package org.led.bigdata.proto.reporting;

import java.io.IOException;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;

public class ReportingServer {
	public  static void main(String[] args) throws HadoopIllegalArgumentException, IOException {
		Configuration conf = new Configuration();
		Server server = new RPC.Builder(conf).setProtocol(ReportingProtocol.class).setInstance(new ReportingProtocolImpl())
				.setBindAddress("127.0.0.1")
				.setPort(7766)
				.setNumHandlers(5).build();
		
		server.start();
		
	}
}
