/**
 * 
 */
package org.led.tools.bmcDbOperator.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.openjpa.persistence.jdbc.Index;

/**
 */
@Entity
public class ServiceAreaPolygon {

    @Id
    @GeneratedValue
    private long id;

    @Index
    private long sai;

    private BigDecimal longitude;

    private BigDecimal latitude;
    
    @Index
    @ManyToOne()
    @JoinColumn(name = "sai")
    private ServiceArea serviceArea;

    public ServiceAreaPolygon() {
    }

    public ServiceAreaPolygon(long sai, BigDecimal longitude, BigDecimal latitude) {
        this.sai = sai;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSai() {
        return sai;
    }

    public void setSai(long sai) {
        this.sai = sai;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public ServiceArea getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(ServiceArea serviceArea) {
        this.serviceArea = serviceArea;
    }
    
}
