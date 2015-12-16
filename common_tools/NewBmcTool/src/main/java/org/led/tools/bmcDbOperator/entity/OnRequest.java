package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class OnRequest {

    @Id
    @GeneratedValue
    private long id;

    @Index
    @OneToOne
    @ForeignKey
    @JoinColumn(name = "USIID")
    private UserServiceInstance userServiceInstance;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "FECID")
    private FECTemplate fecTemplate;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "RRID")
    private RRTemplate rrTemplate;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "FRID")
    private FRTemplate frTemplate;

    private int maxMediaBitRate;

    private long totalMediaBytes;

    private int maxContentDeliveryId;

    private int maxContentCacheId;

    private int maxFileScheduleId;

    private int t;

    private int g;

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

    public FECTemplate getFecTemplate() {
        return fecTemplate;
    }

    public void setFecTemplate(FECTemplate fecTemplate) {
        this.fecTemplate = fecTemplate;
    }

    public RRTemplate getRrTemplate() {
        return rrTemplate;
    }

    public void setRrTemplate(RRTemplate rrTemplate) {
        this.rrTemplate = rrTemplate;
    }

    public int getMaxMediaBitRate() {
        return maxMediaBitRate;
    }

    public void setMaxMediaBitRate(int mediaBitRate) {
        this.maxMediaBitRate = mediaBitRate;
    }

    public FRTemplate getFrTemplate() {
        return frTemplate;
    }

    public void setFrTemplate(FRTemplate frTemplate) {
        this.frTemplate = frTemplate;
    }

    public long getTotalMediaBytes() {
        return totalMediaBytes;
    }

    public void setTotalMediaBytes(long totalMediaBytes) {
        this.totalMediaBytes = totalMediaBytes;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getMaxContentDeliveryId() {
        return maxContentDeliveryId;
    }

    public void increMaxContentDeliveryId() {
        this.maxContentDeliveryId++;
    }

    public int getMaxContentCacheId() {
        return maxContentCacheId;
    }

    public void increMaxContentCacheId() {
        this.maxContentCacheId++;
    }

    public int getMaxFileScheduleId() {
        return maxFileScheduleId;
    }

    public void increMaxFileScheduleId() {
        this.maxFileScheduleId++;
    }

}
