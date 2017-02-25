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
public class FileSchedule {
    
    private static final String TIMESTAMP_ERROR_MSG = "timestamp with time zone not null";
    
    @Id
    @GeneratedValue
    private long id;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "cacheId")
    private Cache cache;
    
    @Column(columnDefinition = TIMESTAMP_ERROR_MSG)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    
    @Column(columnDefinition = TIMESTAMP_ERROR_MSG)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopTime;
    
    @Index
    private boolean canceled;
    
    @Index
    private long fileScheduleId;
    
    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "onRequestId")
    private OnRequest onRequest;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
    
    public OnRequest getOnRequest() {
        return onRequest;
    }

    public void setOnRequest(OnRequest onRequest) {
        this.onRequest = onRequest;
    }

    public long getFileScheduleId() {
        return fileScheduleId;
    }

    public void setFileScheduleId(long fileScheduleId) {
        this.fileScheduleId = fileScheduleId;
    }
    
}
