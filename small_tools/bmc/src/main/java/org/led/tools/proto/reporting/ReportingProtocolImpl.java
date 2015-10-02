package org.led.tools.proto.reporting;

import java.io.IOException;

import org.apache.hadoop.ipc.ProtocolSignature;
import org.led.tools.proto.bmc.Broadcast.BroadcastInfo;

public class ReportingProtocolImpl implements ReportingProtocol{

	public ProtocolSignature getProtocolSignature(String protocol, long clientProtocolVersion,
			int hashCode) throws IOException {
		return new ProtocolSignature(ReportingProtocol.versionID, null);
	}

	public long getProtocolVersion(String arg0, long arg1) throws IOException {
		return ReportingProtocol.versionID;
	}

	public void startJob() throws IOException {
		System.out.println("Server start job");
		
	}

	public void setBroadcastInfo(BroadcastInfo info) throws IOException {
		System.out.println("Server setBroadcastInfo");	
	}

	public String getJobStatus() throws IOException {
		System.out.println("Server getJobStatus OK");
		return "ok";		
	}

}
