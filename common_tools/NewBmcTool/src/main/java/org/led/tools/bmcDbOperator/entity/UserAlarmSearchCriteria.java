package org.led.tools.bmcDbOperator.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class UserAlarmSearchCriteria {

    private static final int STRING_LENGTH = 16384;
    private static final String TIMESTAMP_DEFINITION = "timestamp with time zone";

    @Id
    @GeneratedValue
    private long id;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "CreatedBy")
    private BmcUser user;

    @Column(length = STRING_LENGTH)
    private String origSourceIp;

    @Column(length = STRING_LENGTH)
    private String module;

    //Critical,Major
    @Column(length = STRING_LENGTH)
    private String severity;

    @Column(length = STRING_LENGTH)
    private String alarmType;

    private int errorCode;
    
    @Column(length = STRING_LENGTH)
    private String alarmText;
    
    //resourceId1,resourceId2
    @Column(length = STRING_LENGTH)
    private String resourceId;

    @Column(columnDefinition = TIMESTAMP_DEFINITION)
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDate;

    @Column(columnDefinition = TIMESTAMP_DEFINITION)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    // 1=active alarm 2=history alarm
    @Index
    private int operationType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BmcUser getUser() {
        return user;
    }

    public void setUser(BmcUser user) {
        this.user = user;
    }

    public String getOrigSourceIp() {
        return origSourceIp;
    }

    public void setOrigSourceIp(String origSourceIp) {
        this.origSourceIp = origSourceIp;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getAlarmText() {
        return alarmText;
    }

    public void setAlarmText(String alarmText) {
        this.alarmText = alarmText;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
