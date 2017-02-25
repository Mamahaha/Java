package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class Mpdis {

    @Id
    @GeneratedValue
    private long id;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "continuousID")
    private Continuous continuous;

    private String representationID;

    private String fragmentURI;

    @Lob
    @Column(name = "clob")
    private String clob;

    private long astDelay;

    @Index
    private String mediaURI;

    @Index
    @Enumerated(EnumType.STRING)
    private ContentStatusType status;

    private long lastModifyTime;

    private String md5;

    public ContentStatusType getStatus() {
        return status;
    }

    public void setStatus(ContentStatusType status) {
        this.status = status;
    }

    public String getMediaURI() {
        return mediaURI;
    }

    public void setMediaURI(String mediaURI) {
        this.mediaURI = mediaURI;
    }

    public long getAstDelay() {
        return astDelay;
    }

    public void setAstDelay(long astDelay) {
        this.astDelay = astDelay;
    }

    public String getClob() {
        return clob;
    }

    public void setClob(String clob) {
        this.clob = clob;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Continuous getContinuous() {
        return continuous;
    }

    public void setContinuous(Continuous continuous) {
        this.continuous = continuous;
    }

    public String getRepresentationID() {
        return representationID;
    }

    public void setRepresentationID(String representationID) {
        this.representationID = representationID;
    }

    public String getFragmentURI() {
        return fragmentURI;
    }

    public void setFragmentURI(String fragmentURI) {
        this.fragmentURI = fragmentURI;
    }
    
    public long getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
