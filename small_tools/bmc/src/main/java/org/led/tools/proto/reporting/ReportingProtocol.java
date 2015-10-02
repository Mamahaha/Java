package org.led.tools.proto.reporting;

import java.io.IOException;

import org.led.tools.proto.bmc.Broadcast.BroadcastInfo;

public interface ReportingProtocol extends org.apache.hadoop.ipc.VersionedProtocol{
	public static final long versionID = 2L;
	
	void startJob() throws IOException;
	void setBroadcastInfo(BroadcastInfo info) throws IOException;
	String getJobStatus() throws IOException;
}
