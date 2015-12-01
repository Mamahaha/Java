package org.led.tools.BmcDbOperator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class ENodeBGroup {

    private static final int SAIGROUP_LENGTH = 4096;

    @Id
    @GeneratedValue
    private long id;

    private String pipeName;

    @Column(length = SAIGROUP_LENGTH)
    private String saigroup;

    private float bandwidth;

    private String firstenodebingroup;

    @Lob
    @Column(name = "enodebgroup")
    // enodebname1, enodebname2,..... enodebnameN
    private String enodebgroup;

    public String getFirstENodebinGroup() {
        return firstenodebingroup;
    }

    public void setFirstENodebinGroup(String firstENodebinGroup) {
        this.firstenodebingroup = firstENodebinGroup;
    }

    public void addToGroup(String eNodebName) {
        if (enodebgroup == null) {
            enodebgroup = "";
        }
        if (!enodebgroup.isEmpty()) {
            String tmp = "," + eNodebName;
            eNodebName = tmp;
        }
        enodebgroup += eNodebName;
    }

    public void setENodebGroup(String enodebgroup) {
        this.enodebgroup = enodebgroup;
    }

    public String getGroup() {
        return enodebgroup;
    }

    public String getPipeName() {
        return pipeName;
    }

    public void setPipeName(String pipeName) {
        this.pipeName = pipeName;
    }

    public String getSaigroup() {
        return saigroup;
    }

    public void setSaigroup(String saigroup) {
        this.saigroup = saigroup;
    }

    public float getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(float bandwidth) {
        this.bandwidth = bandwidth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
