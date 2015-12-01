/**
 * 
 */
package org.led.tools.BmcDbOperator.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.apache.openjpa.persistence.jdbc.Index;

import com.google.gson.annotations.Expose;

/**
 */
@Entity
public class ServiceArea {
    
    private static final int FREQUENCIES_LENGTH = 16384;

    @Expose
    @Id
    private long sai;

    private String surveyState;

    private Long maxBitrate;

    private Long fecOverhead;

    // frequency name list 
    // In DB, stored as name1, name2, name3 ...
    // service announcement will use the earfcn by searching in the frequency table
    @Expose
    @Column(length=FREQUENCIES_LENGTH)  
    private String frequencies;
    
    private boolean isImplicitFrequency;

    private String networkState;

    private String description;
    
    @Index
    private BigDecimal lowerLeftLongitude;

    @Index
    private BigDecimal lowerLeftLatitude;

    @Index
    private BigDecimal upperRightLongitude;

    @Index
    private BigDecimal upperRightLatitude;
    
    private boolean vpipe;
    
    public boolean isVpipe() {
        return vpipe;
    }

    public void setVpipe(boolean vpipe) {
        this.vpipe = vpipe;
    }

    public ServiceArea() {
    }

    public ServiceArea(long id, String surveyState, long maxBitrate, long fecOverhead, String frequencies, String networkState, String description,
            BigDecimal lowerLeftLongitude, BigDecimal lowerLeftLatitude, BigDecimal upperRightLongitude, BigDecimal upperRightLatitude) {
        this.sai = id;
        this.surveyState = surveyState;
        this.maxBitrate = maxBitrate;
        this.fecOverhead = fecOverhead;
        this.frequencies = frequencies;
        this.networkState = networkState;
        this.description = description;
        this.lowerLeftLongitude = lowerLeftLongitude;
        this.lowerLeftLatitude = lowerLeftLatitude;
        this.upperRightLongitude = upperRightLongitude;
        this.upperRightLatitude = upperRightLatitude;
    }

    @ManyToMany(mappedBy = "sas")
    private List<BroadcastArea> bas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "BDC_ServiceArea_Mapping", joinColumns = @JoinColumn(name = "sai"), inverseJoinColumns = @JoinColumn(name = "bdcid"))
    private List<BDC> bdcList;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "serviceArea", orphanRemoval = true)
    private List<ServiceAreaPolygon> serviceAreaPolygonList;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ServiceArea_MmeAddress_Mapping",joinColumns = @JoinColumn(name = "sai"), inverseJoinColumns = @JoinColumn(name = "maid"))
    private List<MmeAddress> mmeAddressList;

    public long getSai() {
        return sai;
    }

    public void setSai(long sai) {
        this.sai = sai;
    }

    public String getSurveyState() {
        return surveyState;
    }

    public void setSurveyState(String surveyState) {
        this.surveyState = surveyState;
    }

    public Long getMaxBitrate() {
        return maxBitrate;
    }

    public void setMaxBitrate(Long maxBitrate) {
        this.maxBitrate = maxBitrate;
    }

    public Long getFecOverhead() {
        return fecOverhead;
    }

    public void setFecOverhead(Long fecOverhead) {
        this.fecOverhead = fecOverhead;
    }

    public String getNetworkState() {
        return networkState;
    }

    public void setNetworkState(String networkState) {
        this.networkState = networkState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BroadcastArea> getBas() {
        return bas;
    }

    public void setBas(List<BroadcastArea> bas) {
        this.bas = bas;
    }

    public List<BDC> getBdcList() {
        return bdcList;
    }

    public void setBdcList(List<BDC> bdcList) {
        this.bdcList = bdcList;
    }

    public String getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(String frequencies) {
        this.frequencies = frequencies;
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

    public List<ServiceAreaPolygon> getServiceAreaPolygonList() {
        return serviceAreaPolygonList;
    }

    public void setServiceAreaPolygonList(List<ServiceAreaPolygon> serviceAreaPolygonList) {
        this.serviceAreaPolygonList = serviceAreaPolygonList;
    }

    public boolean isImplicitFrequency() {
        return isImplicitFrequency;
    }

    public void setImplicitFrequency(boolean isImplicitFrequency) {
        this.isImplicitFrequency = isImplicitFrequency;
    }

    public List<MmeAddress> getMmeAddressList() {
        return mmeAddressList;
    }

    public void setMmeAddressList(List<MmeAddress> mmeAddressList) {
        this.mmeAddressList = mmeAddressList;
    }

}
