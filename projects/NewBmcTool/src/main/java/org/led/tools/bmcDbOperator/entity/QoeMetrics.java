package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class QoeMetrics {

    @Id
    @GeneratedValue
    private long id;

    @Index
    private String name;

    private boolean nwResource;

    private boolean lossOfObjects;

    private int resolution;

    private String range;

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

    public boolean isNwResource() {
        return nwResource;
    }

    public void setNwResource(boolean nwResource) {
        this.nwResource = nwResource;
    }

    public boolean isLossOfObjects() {
        return lossOfObjects;
    }

    public void setLossOfObjects(boolean lossOfObjects) {
        this.lossOfObjects = lossOfObjects;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

}
