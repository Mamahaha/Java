package org.led.tools.BmcDbOperator.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.apache.openjpa.persistence.jdbc.ForeignKey;

@Entity
public class MmePool {
    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MmePool_Mmeaddress_mapping" ,joinColumns = @JoinColumn(name = "mpid"), inverseJoinColumns = @JoinColumn(name = "maid"))
    private List<MmeAddress> mmeAddressList;

    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "GWID")
    private Gateway gateway;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<MmeAddress> getMmeAddressList() {
        return mmeAddressList;
    }

    public void setMmeAddressList(List<MmeAddress> mmeAddressList) {
        this.mmeAddressList = mmeAddressList;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

}
