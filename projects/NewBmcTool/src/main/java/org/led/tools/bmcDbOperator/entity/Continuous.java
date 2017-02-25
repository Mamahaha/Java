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
public class Continuous {

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

    private int maxMediaBitRate;

    private String webDavFolder;

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

    public String getWebDavFolder() {
        return webDavFolder;
    }

    public void setWebDavFolder(String webDavFolder) {
        this.webDavFolder = webDavFolder;
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
}
