package org.led.tools.BmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FECTemplate {

    @Id
    @GeneratedValue
    private long id;
    
    private String name;

    private int encodingId;

    private int overhead;

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

    public int getEncodingId() {
        return encodingId;
    }

    public void setEncodingId(int encodingId) {
        this.encodingId = encodingId;
    }

    public int getOverhead() {
        return overhead;
    }

    public void setOverhead(int overhead) {
        this.overhead = overhead;
    }

}
