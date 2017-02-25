package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RRTemplate {

    @Id
    @GeneratedValue
    private long id;
    
    private String name;

    private int offsetTime;

    private int randomTimewindow;

    private String reportType;

    private int samplePercent;

    private boolean forceTimeIndependance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getOffsetTime() {
        return offsetTime;
    }

    public void setOffsetTime(int offsetTime) {
        this.offsetTime = offsetTime;
    }

    public int getRandomTimewindow() {
        return randomTimewindow;
    }

    public void setRandomTimewindow(int randomTimewindow) {
        this.randomTimewindow = randomTimewindow;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public int getSamplePercent() {
        return samplePercent;
    }

    public void setSamplePercent(int samplePercent) {
        this.samplePercent = samplePercent;
    }

    public boolean isForceTimeIndependance() {
        return forceTimeIndependance;
    }

    public void setForceTimeIndependance(boolean forceTimeIndependance) {
        this.forceTimeIndependance = forceTimeIndependance;
    }

}
