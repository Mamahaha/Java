/**
 * 
 */
package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.apache.openjpa.persistence.jdbc.ForeignKey;

/**
 * @author exxnccn
 * 
 */
@Entity
public class SDCHService {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @ForeignKey
    @JoinColumn(name = "SID")
    private Service service;
    
    private String type;
    
    private boolean bootstrap;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBootstrap() {
        return bootstrap;
    }

    public void setBootstrap(boolean bootstrap) {
        this.bootstrap = bootstrap;
    }
    
}
