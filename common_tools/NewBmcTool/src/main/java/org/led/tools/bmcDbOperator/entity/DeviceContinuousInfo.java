package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class DeviceContinuousInfo {

    @Id
    @GeneratedValue
    private long id;
    
    @Index
    private long mpdid;
    
    @Index
    private long deviceDsiId;

    public long getDeviceDsiId() {
        return deviceDsiId;
    }

    public void setDeviceDsiId(long deviceDsiId) {
        this.deviceDsiId = deviceDsiId;
    }

    @Enumerated(EnumType.STRING)
    private ContentStatusType status;

    public long getMpdId() {
        return mpdid;
    }

    public long getId() {
        return id;
    }

    public ContentStatusType getStatus() {
        return status;
    }

    public void setMpdId(long mpdId) {
        this.mpdid = mpdId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(ContentStatusType status) {
        this.status = status;
    }

}
