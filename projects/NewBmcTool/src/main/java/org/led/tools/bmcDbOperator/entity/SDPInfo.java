package org.led.tools.bmcDbOperator.entity;

import javax.persistence.*;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
@Table(
        name="sdpinfo",
        uniqueConstraints={@UniqueConstraint(columnNames={"multicastDestIP", "destPort"})}
)
public class SDPInfo {
    @Id
    @GeneratedValue
    private long id;
    
    private String ipAddressType;
    
    private String sourceIP; 
    
    @Index(columnNames={"multicastDestIP","destPort"})
    private String multicastDestIP;
    
    private int destPort;
    
    @Index
    private long tsi;
    
    private String tmgi;
    
    private Long startTime;
    
    private Long stopTime;

    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "QoeMetricsId")    
    private QoeMetrics qoeMetrics;

    public String getSourceIP() {
        return sourceIP;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public String getMulticastDestIP() {
        return multicastDestIP;
    }

    public void setMulticastDestIP(String multicastDestIP) {
        this.multicastDestIP = multicastDestIP;
    }

    public int getDestPort() {
        return destPort;
    }

    public void setDestPort(int destPort) {
        this.destPort = destPort;
    }

    public long getTsi() {
        return tsi;
    }

    public void setTsi(long tsi) {
        this.tsi = tsi;
    }

    public String getTmgi() {
        return tmgi;
    }

    public void setTmgi(String tmgi) {
        this.tmgi = tmgi;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }

    public String getIpAddressType() {
        return ipAddressType;
    }

    public void setIpAddressType(String ipAddressType) {
        this.ipAddressType = ipAddressType;
    }

    public QoeMetrics getQoeMetrics() {
        return qoeMetrics;
    }

    public void setQoeMetrics(QoeMetrics qoeMetrics) {
        this.qoeMetrics = qoeMetrics;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }    
}
