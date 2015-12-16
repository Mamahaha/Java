/**
 * 
 */
package org.led.tools.bmcDbOperator.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.openjpa.persistence.jdbc.Index;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * @author eweitan
 * 
 */
@Entity
public class BroadcastArea {

    private Gson gson = new Gson();

    @Expose
    @Id
    @GeneratedValue
    private long id;

    @Expose
    private String name;

    private String regionType;

    private String adminState;

    private String description;
    
    @Index
    private BigDecimal lowerLeftLongitude;

    @Index
    private BigDecimal lowerLeftLatitude;

    @Index
    private BigDecimal upperRightLongitude;

    @Index
    private BigDecimal upperRightLatitude;    

    public BroadcastArea() {

    }

    public BroadcastArea(String name, String regionType, String adminState, String description) {
        this.name = name;
        this.regionType = regionType;
        this.adminState = adminState;
        this.description = description;
    }

    @Expose
    @ManyToMany
    @JoinTable(name = "AreaMapping", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "sai"))
    private List<ServiceArea> sas;

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

    public String getRegionType() {
        return regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public String getAdminState() {
        return adminState;
    }

    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ServiceArea> getSas() {
        return sas;
    }

    public void setSas(List<ServiceArea> sas) {
        this.sas = sas;

    }

    public BigDecimal getLowerLeftLongitude() {
        return lowerLeftLongitude;
    }

    public void setLowerLeftLongitude(BigDecimal lowerLeftLongitude) {
        this.lowerLeftLongitude = lowerLeftLongitude;
    }

    public BigDecimal getLowerLeftLatitude() {
        return lowerLeftLatitude;
    }

    public void setLowerLeftLatitude(BigDecimal lowerLeftLatitude) {
        this.lowerLeftLatitude = lowerLeftLatitude;
    }

    public BigDecimal getUpperRightLongitude() {
        return upperRightLongitude;
    }

    public void setUpperRightLongitude(BigDecimal upperRightLongitude) {
        this.upperRightLongitude = upperRightLongitude;
    }

    public BigDecimal getUpperRightLatitude() {
        return upperRightLatitude;
    }

    public void setUpperRightLatitude(BigDecimal upperRightLatitude) {
        this.upperRightLatitude = upperRightLatitude;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

}
