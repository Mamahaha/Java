package org.led.tools.BmcDbOperator.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


import org.apache.openjpa.persistence.jdbc.Index;

// pipe in each SAI (The pipe should be available in every PMCH of every eNodeB in this SAI)  

@Entity
public class PipeResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Index
    private String pipename;

    @Index
    private long sai;
    
    private float bandwidth;

    @JoinTable(name = "TMGI_PipeResource_Mapping", joinColumns = @JoinColumn(name = "PipeId"), inverseJoinColumns = @JoinColumn(name = "TMGIId"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<TMGI> tmgiList;

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

    public String getPipeName() {
        return pipename;
    }

    public void setPipeName(String name) {
        this.pipename = name;
    }

    public float getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(float bandwidth) {
        this.bandwidth = bandwidth;
    }

    public List<TMGI> getTmgiList() {
        return tmgiList;
    }

    public void setTmgiList(List<TMGI> tmgiList) {
        this.tmgiList = tmgiList;
    }
}
