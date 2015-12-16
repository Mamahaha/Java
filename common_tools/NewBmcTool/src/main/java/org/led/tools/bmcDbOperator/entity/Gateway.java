package org.led.tools.bmcDbOperator.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Gateway {
    @Id
    @GeneratedValue
    private long id;

    private String ipStack;

    private String host;

    private String realm;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "gateway",cascade = CascadeType.REMOVE)
    private List<MmePool> mmePoolList = new ArrayList<MmePool>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIpStack() {
        return ipStack;
    }

    public void setIpStack(String ipStack) {
        this.ipStack = ipStack;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public List<MmePool> getMmePoolList() {
        return mmePoolList;
    }

    public void setMmePoolList(List<MmePool> mmePoolList) {
        this.mmePoolList = mmePoolList;
    }
}
