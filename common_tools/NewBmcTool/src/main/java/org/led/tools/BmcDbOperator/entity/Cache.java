package org.led.tools.BmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class Cache {
    
    private static final int INT_31 =31;
    private static final int INT_32 =32;

    @Id
    @GeneratedValue
    private long id;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "onRequestId")
    private OnRequest onRequest;

    @Index
    private String fileUri;

    @Index
    private int cacheId;

    @Index
    @Enumerated(EnumType.STRING)
    private CacheStatusType status = CacheStatusType.PENDING;

    private String statusDescription = "";

    private long estimatedTransTime;

    public int getCacheId() {
        return cacheId;
    }

    public String getFileUri() {
        return fileUri;
    }

    public long getId() {
        return id;
    }

    public OnRequest getOnRequest() {
        return onRequest;
    }

    public CacheStatusType getStatus() {
        return status;
    }

    public void setCacheId(int cacheId) {
        this.cacheId = cacheId;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOnRequest(OnRequest onRequest) {
        this.onRequest = onRequest;
    }

    public void setStatus(CacheStatusType status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = INT_31;
        int result = 1;
        result = prime * result + cacheId;
        result = prime * result + ((fileUri == null) ? 0 : fileUri.hashCode());
        int var = INT_32;
        result = prime * result + (int) (id ^ (id >>> var));
        result = prime * result + ((onRequest == null) ? 0 : onRequest.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!classEquals(obj)) {
            return false;
        }
        Cache other = (Cache) obj;
        if (cacheId != other.cacheId) {
            return false;
        }
        if (!fileUriEquals(other)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (!onRequestEquals(other)) {
            return false;
        }
        if (status != other.status) {
            return false;
        }
        return true;
    }

    private boolean fileUriEquals(Cache other) {
        if (fileUri == null) {
            if (other.fileUri != null) {
                return false;
            }
        } else if (!fileUri.equals(other.fileUri)) {
            return false;
        }
        return true;
    }

    private boolean classEquals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    private boolean onRequestEquals(Cache other) {
        if (onRequest == null) {
            if (other.onRequest != null) {
                return false;
            }
        } else if (!(onRequest.getId() == other.onRequest.getId())) {
            return false;
        }
        return true;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public long getEstimatedTransTime() {
        return estimatedTransTime;
    }

    public void setEstimatedTransTime(long estimatedTransTime) {
        this.estimatedTransTime = estimatedTransTime;
    }

    @Override
    public String toString() {
        return "Cache [id=" + id + ", fileUri=" + fileUri + ", cacheId=" + cacheId + ", status=" + status + "]";
    }

}
