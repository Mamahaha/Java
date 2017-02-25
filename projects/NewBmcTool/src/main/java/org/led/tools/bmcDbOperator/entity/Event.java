package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private long id;

    private String bdcIpAddress;
    private String version;
    private String notificationType;
    private long embmsSessionId;
    private long deliverySessionId;
    private long deliveryInstanceId;
    private String description;
    private String fileURI;
    private long contentId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBdcIpAddress() {
        return bdcIpAddress;
    }

    public void setBdcIpAddress(String bdcIpAddress) {
        this.bdcIpAddress = bdcIpAddress;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public long getEmbmsSessionId() {
        return embmsSessionId;
    }

    public void setEmbmsSessionId(long embmsSessionId) {
        this.embmsSessionId = embmsSessionId;
    }

    public long getDeliverySessionId() {
        return deliverySessionId;
    }

    public void setDeliverySessionId(long deliverySessionId) {
        this.deliverySessionId = deliverySessionId;
    }

    public long getDeliveryInstanceId() {
        return deliveryInstanceId;
    }

    public void setDeliveryInstanceId(long deliveryInstanceId) {
        this.deliveryInstanceId = deliveryInstanceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileURI() {
        return fileURI;
    }

    public void setFileURI(String fileURI) {
        this.fileURI = fileURI;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    /**
     * utility class for building Event
     * 
     * @author echonga
     * 
     */
    public static class Builder {

        private Event event = new Event();

        public Builder bdcIpAddress(String bdcIpAddress) {
            event.setBdcIpAddress(bdcIpAddress);
            return this;
        }

        public Builder version(String version) {
            event.setVersion(version);
            return this;
        }

        public Builder notificationType(String notificationType) {
            event.setNotificationType(notificationType);
            return this;
        }

        public Builder embmsSessionId(long embmsSessionId) {
            event.setEmbmsSessionId(embmsSessionId);
            return this;
        }

        public Builder deliverySessionId(long deliverySessionId) {
            event.setDeliverySessionId(deliverySessionId);
            return this;
        }

        public Builder deliveryInstanceId(long deliveryInstanceId) {
            event.setDeliveryInstanceId(deliveryInstanceId);
            return this;
        }

        public Builder description(String description) {
            event.setDescription(description);
            return this;
        }

        public Builder fileURI(String fileURI) {
            event.setFileURI(fileURI);
            return this;
        }

        public Builder contentId(long contentId) {
            event.setContentId(contentId);
            return this;
        }

        public Event get() {
            return event;
        }
    }
}
