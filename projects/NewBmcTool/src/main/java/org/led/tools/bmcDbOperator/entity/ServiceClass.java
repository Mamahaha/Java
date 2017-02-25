/**
 * 
 */
package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.openjpa.persistence.jdbc.Index;

/**
 * @author eweitan
 * 
 */
@Entity
public class ServiceClass {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Index
    private String serviceClass;

    private String description;

    public ServiceClass() {

    }

    public ServiceClass(String name, String serviceClass, String description) {
        super();
        this.name = name;
        this.serviceClass = serviceClass;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long serviceClassId) {
        this.id = serviceClassId;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }
    
}
