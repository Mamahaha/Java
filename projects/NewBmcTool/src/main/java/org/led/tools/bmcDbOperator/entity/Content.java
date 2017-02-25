package org.led.tools.bmcDbOperator.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class Content {
    
    private static final String TIMESTAMP_ERROR_MSG = "timestamp with time zone not null";
    @Id
    @GeneratedValue
    private long id;

    @Index
    private long contentId;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "onRequestId")
    private OnRequest onRequest;
	
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

    private String type;

    private int refreshInterval;

    private String cacheControl;
    
    private long contentIdOnDevice = -1;

    @Index
    @Enumerated(EnumType.STRING)
    private ContentStatusType status = ContentStatusType.PENDING;
    
    private String statusDescription="";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public OnRequest getOnRequest() {
        return onRequest;
    }

    public void setOnRequest(OnRequest onRequest) {
        this.onRequest = onRequest;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }  

    public ContentStatusType getStatus() {
        return status;
    }

    public void setStatus(ContentStatusType status) {
        this.status = status;
    }

    public boolean isInQueue() {
        return getStatus().equals(ContentStatusType.IN_QUEUE);
    }

    public boolean isPending() {
        return getStatus().equals(ContentStatusType.PENDING);
    }

    @Override
    public String toString() {
        return String.format("[contentPId:%s, contentId:%s, type:%s, startTime:%s, stopTime:%s]", getId(), getContentId(), getType(), getStartTime(),
                getStopTime());
    }

    public String getStatusDescription() {
        return statusDescription;
    }
    
    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public long getContentIdOnDevice() {
        return contentIdOnDevice;
    }

    public void setContentIdOnDevice(long contentIdOnDevice) {
        this.contentIdOnDevice = contentIdOnDevice;
    }

}
