package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FRTemplate {

    @Id
    @GeneratedValue
    private long id;
    
    private String name;

    private int offsetTime;

    private int randomTimeWindow;

    private int estimatedAudienceSize;

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

    public int getRandomTimeWindow() {
        return randomTimeWindow;
    }

    public void setRandomTimeWindow(int randomTimeWindow) {
        this.randomTimeWindow = randomTimeWindow;
    }

    public int getEstimatedAudienceSize() {
        return estimatedAudienceSize;
    }

    public void setEstimatedAudienceSize(int estimatedAudienceSize) {
        this.estimatedAudienceSize = estimatedAudienceSize;
    }

}
