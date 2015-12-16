/**
 *
 */
package org.led.tools.bmcDbOperator.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ReportingMetadata {

    private static final String TIMESTAMP_ERROR_MSG = "timestamp with time zone not null";

    @Id
    @GeneratedValue
    private long id;

    private String serviceName;

    private String broadcastName;

    @Lob
    private String metadata;

    @Column(columnDefinition = TIMESTAMP_ERROR_MSG)
    @Temporal(TemporalType.TIMESTAMP)
    private Date triggerTime;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBroadcastName() {
        return broadcastName;
    }

    public void setBroadcastName(String broadcastName) {
        this.broadcastName = broadcastName;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Date getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Date triggerTime) {
        this.triggerTime = triggerTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        ReportingMetadata other = (ReportingMetadata) obj;
        return this.equalBDC(other);
    }

    private boolean equalBDC(ReportingMetadata other) {
        boolean b1 = this.id == other.id;
        boolean b2 = equalObject(this.serviceName, other.serviceName);
        boolean b3 = equalObject(this.broadcastName, other.broadcastName);
        boolean b123 = b1 && b2 && b3;

        boolean b4 = equalObject(this.metadata, other.metadata);
        boolean b5 = equalObject(this.triggerTime, other.triggerTime);
        boolean b45 = b4 && b5;

        return b123 && b45;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = calHashCodeIntoResult(prime, serviceName, result);
        result = calHashCodeIntoResult(prime, broadcastName, result);
        result = calHashCodeIntoResult(prime, metadata, result);
        result = calHashCodeIntoResult(prime, triggerTime, result);

        return result;
    }

    private int calHashCodeIntoResult(int prime, Object field, int result) {
        return prime * result + ((field == null) ? 0 : field.hashCode());
    }

    private boolean equalObject(Object thisObject, Object otherObject) {
        boolean b = false;
        if (thisObject != null) {
            b = thisObject.equals(otherObject);
        } else if (otherObject != null) {
            b = otherObject.equals(thisObject);
        } else {
            b = true;
        }
        return b;
    }
}
