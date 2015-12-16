package org.led.tools.bmcDbOperator.entity;

import javax.persistence.*;

import org.apache.openjpa.persistence.jdbc.ForeignKey;

@Entity
public class AdpdServiceUri {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "USIID")
    private UserServiceInstance userServiceInstance;
    
    @OneToOne
    @ForeignKey
    @JoinColumn(name = "BDCId")
    private BDC bdc;

    @Lob
    private String serviceUri;
    
    private String type; // FR or RR
    
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

    public String getServiceUri() {
        return serviceUri;
    }

    public void setServiceUri(String serviceUri) {
        this.serviceUri = serviceUri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
