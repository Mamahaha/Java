package org.led.bigdata.proto.reporting;

import org.apache.hadoop.yarn.event.AbstractEvent;

public class ReportingTaskEvent extends AbstractEvent<ReportingTaskEventType> {
	private String taskId;
	
	public ReportingTaskEvent(String taskId, ReportingTaskEventType type) {
		super(type);
		this.taskId = taskId;		
	}
	
	public String getTaskId() {
		return taskId;
	} 
	
}
