package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class DeviceDeliverySessionInstanceInfo {

    public DeviceDeliverySessionInstanceInfo(){
        this.deliverySessionId = -1L;
        this.eMBMSSessionId = -1L;
        this.deliverySessionInstanceId = -1L;
        this.flowId = 0;
        this.operState = DeliverySessionInstanceOperType.NOT_ACTIVATED.value();
    }

    @Id
    @GeneratedValue
    private long id;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "UserServiceInstanceId")
    private UserServiceInstance userServiceInstance;
    
    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "BDCId")
    private BDC bdc;
    
    @Index
    private long deliverySessionInstanceId;
    
    @Index
    private long eMBMSSessionId;
    
    @Index
    private long deliverySessionId;

    private long flowId;

    private String webDavFolder;

    private String operState;

    public String getWebDavFolder() {
        return webDavFolder;
    }

    public void setWebDavFolder(String webDavFolder) {
        this.webDavFolder = webDavFolder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserServiceInstance getUserServiceInstance() {
        return userServiceInstance;
    }

    public void setUserServiceInstance(UserServiceInstance userServiceInstance) {
        this.userServiceInstance = userServiceInstance;
    }

    public BDC getBdc() {
        return bdc;
    }

    public void setBdc(BDC bdc) {
        this.bdc = bdc;
    }

    public long getDeliverySessionInstanceId() {
        return deliverySessionInstanceId;
    }

    public void setDeliverySessionInstanceId(long deliverySessionInstanceId) {
        this.deliverySessionInstanceId = deliverySessionInstanceId;
    }

    public long geteMBMSSessionId() {
        return eMBMSSessionId;
    }

    public void seteMBMSSessionId(long eMBMSSessionId) {
        this.eMBMSSessionId = eMBMSSessionId;
    }

    public long getFlowId() {
        return flowId;
    }

    public void setFlowId(long flowId) {
        this.flowId = flowId;
    }

    public String getOperState() {
        return operState;
    }

    public void setOperState(String operState) {
        this.operState = operState;
    }
    
    public long getDeliverySessionId() {
        return deliverySessionId;
    }

    public void setDeliverySessionId(long deliverySessionId) {
        this.deliverySessionId = deliverySessionId;
    }

    @Override
    public String toString() {
        return String.format("[id: %s, UserServiceInstance: %s,operState: %s]", id, getUserServiceInstance().getId(), operState);
    }

}
