package org.led.tools.proto.reporting;

import org.apache.hadoop.yarn.event.AbstractEvent;

public class ReportingJobEvent extends AbstractEvent<ReportingJobEventType>{
	private String jobId;
	
	public ReportingJobEvent(String jobId, ReportingJobEventType type) {
		super(type);		
		this.jobId = jobId;
	}
	
	public String getJobId() {
		return jobId;
	}

}
