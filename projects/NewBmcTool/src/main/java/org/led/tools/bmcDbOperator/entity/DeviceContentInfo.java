package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class DeviceContentInfo {

    @Id
    @GeneratedValue
    private long id;

    /**
     * id for the content
     */
    @Index
    private long contentId;

    @Index
    private long deviceDsiId;

    public long getDeviceDsiId() {
        return deviceDsiId;
    }

    public void setDeviceDsiId(long deviceDsiId) {
        this.deviceDsiId = deviceDsiId;
    }

    @Enumerated(EnumType.STRING)
    private ContentStatusType status = ContentStatusType.PENDING;

    public long getContentId() {
        return contentId;
    }

    public long getId() {
        return id;
    }

    public ContentStatusType getStatus() {
        return status;
    }

    /**
     * 
     * @param contentId id for the content record
     */
    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(ContentStatusType status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[contentId: %s, deviceDsiId: %s, status:%s]", contentId, deviceDsiId, status.toString());
    }

}
