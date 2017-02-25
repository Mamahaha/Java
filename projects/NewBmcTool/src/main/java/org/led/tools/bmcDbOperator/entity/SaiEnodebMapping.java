package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class SaiEnodebMapping {
    @Id
    @GeneratedValue
    private long id;
    
    @Index
    private long sai;

    @Index
    @ManyToOne(fetch = FetchType.LAZY)
    @ForeignKey
    @JoinColumn(name = "ENODEB_ID")
    private ENodeB eNodeB;

    public long getSai() {
        return sai;
    }

    public void setSai(long sai) {
        this.sai = sai;
    }

    public ENodeB geteNodeB() {
        return eNodeB;
    }

    public void seteNodeB(ENodeB eNodeB) {
        this.eNodeB = eNodeB;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
