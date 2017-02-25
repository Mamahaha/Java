package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProvisionConfig {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique=true, nullable=false)
    private String name;
    
    private String value; 
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStringValue() {
        return value;
    }

    public void setStringValue(String strValue) {
        this.value = strValue;
    }    

}
