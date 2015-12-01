/**
 * 
 */
package org.led.tools.BmcDbOperator.common;

import java.math.BigInteger;

import org.led.tools.BmcDbOperator.data.BDCEventType;

public class BDCEvent implements Event {

    private String name;

    private String version;

    private Long embmsSessionId;

    private Long deliverySessionId;

    private Long deliveryInstanceId;

    private String description;

    private String fileURI;

    private Long contentId;
    
    private Long deliverDuration;
    
    private BigInteger fileSize;

    private BDCEventType type;

    public BDCEvent(BDCEventType eventType) {
        type = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BDCEventType getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getEmbmsSessionId() {
        return embmsSessionId;
    }

    public void setEmbmsSessionId(Long embmsSessionId) {
        this.embmsSessionId = embmsSessionId;
    }

    public Long getDeliverySessionId() {
        return deliverySessionId;
    }

    public void setDeliverySessionId(Long deliverySessionId) {
        this.deliverySessionId = deliverySessionId;
    }

    public Long getDeliveryInstanceId() {
        return deliveryInstanceId;
    }

    public void setDeliveryInstanceId(Long deliveryInstanceId) {
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

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getDeliverDuration() {
        return deliverDuration;
    }

    public void setDeliverDuration(Long deliverDuration) {
        this.deliverDuration = deliverDuration;
    }

    public BigInteger getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigInteger fileSize) {
        this.fileSize = fileSize;
    }
    
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("[");
        stringBuffer.append("name: ").append(name);
        stringBuffer.append(",version: ").append(version);
        stringBuffer.append(",embmsSessionId: ").append(embmsSessionId);
        stringBuffer.append(",deliveryInstanceId: ").append(deliveryInstanceId);
        stringBuffer.append(",contentId: ").append(contentId);
        stringBuffer.append(",type: ").append(type);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
