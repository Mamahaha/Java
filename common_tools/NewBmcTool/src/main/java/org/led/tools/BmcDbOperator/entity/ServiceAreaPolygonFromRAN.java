package org.led.tools.BmcDbOperator.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class ServiceAreaPolygonFromRAN implements Comparable<ServiceAreaPolygonFromRAN> {
    @Id
    @GeneratedValue
    private long id;

    private BigDecimal longitude; // unit is meter

    private BigDecimal latitude; // unit is meter
    
    private static final double MULTIPLE_NUM = 100.0;

    @Index
    @ManyToOne()
    @JoinColumn(name = "sai")
    private ServiceArea sai;

    public ServiceAreaPolygonFromRAN(float longitude, float latitude) {
        this.longitude = new BigDecimal(longitude);
        this.latitude = new BigDecimal(latitude);
    }

    public ServiceAreaPolygonFromRAN() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public ServiceArea getSai() {
        return sai;
    }

    public void setSai(ServiceArea sai) {
        this.sai = sai;
    }

    public int compareTo(ServiceAreaPolygonFromRAN p) {
        if (this.getLongitude().floatValue() == p.getLongitude().floatValue()) {
            return (int) ((this.getLatitude().floatValue() - p.getLatitude().floatValue()) * MULTIPLE_NUM);
        } else {
            return (int) ((this.getLongitude().floatValue() - p.getLongitude().floatValue()) * MULTIPLE_NUM);
        }
    }
}
