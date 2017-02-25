
package org.led.tools.bmcDbOperator.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class LockAssist {

    private static final int LENGTH = 1024;

    private static final String TIMESTAMP_ERROR_MSG = "timestamp with time zone";

    @Id
    @GeneratedValue
    private long id;

    private String key;

    private boolean isTarget;

    // Node's type.You could find some type in DbLockType.class. not use now.
    private String nodeType;

    // Node's identity.Its format likes 'rootA-sunB-targetC'.
    @Column(length = LENGTH)
    private String nodeValue;

    // When it's inserted into Db.
    @Column(columnDefinition = TIMESTAMP_ERROR_MSG)
    @Temporal(TemporalType.TIMESTAMP)
    private Date intime;

    private long expiredtime = 0;

    // Reserved Field.Maybe it will be helpful to lock the node in finer distinction.
    private String operationType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isTarget() {
        return isTarget;
    }

    public void setTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public long getExpiredtime() {
        return expiredtime;
    }

    /**
     * Default dblock expired time is 0.If is 0,it means effective permanently.Otherwise,it will be effictive to the time of intime+expiredtime*1000
     * 
     * @param expiredtime
     *            expiredtime unit is second
     */
    public void setExpiredtime(long expiredtime) {
        this.expiredtime = expiredtime;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return String.format("[nodeValue: %s,intime: %s,expiredtime: %s,key: %s]", nodeValue, intime, expiredtime, key);
    }
}
