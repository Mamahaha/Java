package org.led.tools.BmcDbOperator.entity;

import java.util.Collections;
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
import javax.persistence.ManyToOne;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class OriginPipeResource implements Comparable<OriginPipeResource> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Index
    @ManyToOne(fetch = FetchType.EAGER)
    @ForeignKey
    @JoinColumn(name = "ENODEB_ID")
    private ENodeB eNodeB;

    @Index
    private String pipename;

    private float gbr;

    @JoinTable(name = "TMGI_OriginPipeResource_Mapping", joinColumns = @JoinColumn(name = "OriginPipeId"), inverseJoinColumns = @JoinColumn(name = "TMGIId"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<TMGI> tmgiList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ENodeB geteNodeB() {
        return eNodeB;
    }

    public void seteNodeB(ENodeB eNodeB) {
        this.eNodeB = eNodeB;
    }

    public String getPipeName() {
        return pipename;
    }

    public void setPipeName(String pipeName) {
        this.pipename = pipeName;
    }

    public float getGbr() {
        return gbr;
    }

    public void setGbr(float gbr) {
        this.gbr = gbr;
    }

    public List<TMGI> getTmgiList() {
        return tmgiList;
    }

    public void setTmgiList(List<TMGI> tmgiList) {
        this.tmgiList = tmgiList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eNodeB == null) ? 0 : eNodeB.hashCode());
        result = prime * result + Float.floatToIntBits(gbr);
        result = prime * result + ((pipename == null) ? 0 : pipename.hashCode());
        result = prime * result + ((tmgiList == null) ? 0 : tmgiList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OriginPipeResource other = (OriginPipeResource) obj;
        if (eNodeB == null) {
            if (other.eNodeB != null) {
                return false;
            }
        } else if (!(eNodeB.getId()==other.eNodeB.getId())) {
            return false;
        }
        if (Float.floatToIntBits(gbr) != Float.floatToIntBits(other.gbr)) {
            return false;
        }
        if (pipename == null) {
            if (other.pipename != null) {
                return false;
            }
        } else if (!pipename.equals(other.pipename)) {
            return false;
        }
        if (tmgiList == null) {
            if (other.tmgiList != null) {
                return false;
            }
        } else {
            Collections.sort(tmgiList);
            if (other.tmgiList == null) {
                return false;
            }
            Collections.sort(other.tmgiList);
            if (!tmgiList.equals(other.tmgiList)) {
                return false;
            }
        }
        return true;
    }

    public int compareTo(OriginPipeResource o) {
        if (eNodeB.getId() == o.geteNodeB().getId()) {
            return pipename.compareTo(o.getPipeName());
        }
        return (int) (eNodeB.getId() - o.geteNodeB().getId());
    }
}
