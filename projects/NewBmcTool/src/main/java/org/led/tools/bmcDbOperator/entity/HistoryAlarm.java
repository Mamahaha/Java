package org.led.tools.bmcDbOperator.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class HistoryAlarm {

    private static final int RESOURCE_ID_LENGTH = 1024;
    private static final int COMMENT_LENGTH = 16384;

    @Id
    private long id;

    @Index
    private String module;

    private String errorCode;

    @Index
    @Column(length = RESOURCE_ID_LENGTH)
    private String resourceId;

    @Column(columnDefinition = "timestamp with time zone not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(columnDefinition = "timestamp with time zone not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "modelDescriptionId")
    private AlarmText modelDescriptionText;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "activeDescriptionId")
    private AlarmText activeDescriptionText;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "proposedRepairId")
    private AlarmRemedyText proposedRepairActionText;

    private int eventType;

    private int probableCause;

    @Index
    private int severity;

    private String origSourceIp;

    // 1=alarm 2=event
    @Index
    private int alarmType;

    @Index
    private int operationType;

    @Column(length = COMMENT_LENGTH)
    private String comment;
    
    private int sequenceNo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public AlarmText getModelDescriptionText() {
        return modelDescriptionText;
    }

    public void setModelDescriptionText(AlarmText modelDescriptionText) {
        this.modelDescriptionText = modelDescriptionText;
    }

    public AlarmText getActiveDescriptionText() {
        return activeDescriptionText;
    }

    public void setActiveDescriptionText(AlarmText activeDescriptionText) {
        this.activeDescriptionText = activeDescriptionText;
    }

    public AlarmRemedyText getProposedRepairActionText() {
        return proposedRepairActionText;
    }

    public void setProposedRepairActionText(AlarmRemedyText proposedRepairActionText) {
        this.proposedRepairActionText = proposedRepairActionText;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getProbableCause() {
        return probableCause;
    }

    public void setProbableCause(int probableCause) {
        this.probableCause = probableCause;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public String getOrigSourceIp() {
        return origSourceIp;
    }

    public void setOrigSourceIp(String origSourceIp) {
        this.origSourceIp = origSourceIp;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }
    
}
