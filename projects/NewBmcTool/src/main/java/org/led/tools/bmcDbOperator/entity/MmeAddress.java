package org.led.tools.bmcDbOperator.entity;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class MmeAddress {
    @Id
    @GeneratedValue
    private long id;

    private String mmeSmIp;
    
    @ManyToMany(mappedBy = "mmeAddressList", fetch = FetchType.EAGER)
    private List<MmePool> mmePoolList;
    
    @ManyToMany(mappedBy = "mmeAddressList")
    private List<ServiceArea> saiList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMmeSmIp() {
        return mmeSmIp;
    }

    public void setMmeSmIp(String mmeSmIp) {
        this.mmeSmIp = mmeSmIp;
    }

    public List<MmePool> getMmePoolList() {
        return mmePoolList;
    }

    public void setMmePoolList(List<MmePool> mmePoolList) {
        this.mmePoolList = mmePoolList;
    }

    public List<ServiceArea> getSaiList() {
        return saiList;
    }

    public void setSaiList(List<ServiceArea> saiList) {
        this.saiList = saiList;
    }

   



}
