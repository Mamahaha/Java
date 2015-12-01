package org.led.tools.BmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class DeviceCacheInfo {
    
    @Index
    private long cacheId;
    
    @Index
    private long deviceDsiId;

    public long getDeviceDsiId() {
        return deviceDsiId;
    }

    public void setDeviceDsiId(long deviceDsiId) {
        this.deviceDsiId = deviceDsiId;
    }

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private CacheStatusType status = CacheStatusType.PENDING;

    public long getCache() {
        return cacheId;
    }

    public long getId() {
        return id;
    }

    public CacheStatusType getStatus() {
        return status;
    }

    public void setCache(long cacheId) {
        this.cacheId = cacheId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(CacheStatusType status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[cacheId: %s, deviceDsiId: %s, status:%s]", cacheId, deviceDsiId, status.toString());
    }
}
