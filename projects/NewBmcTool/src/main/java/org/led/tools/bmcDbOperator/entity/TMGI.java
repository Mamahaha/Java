package org.led.tools.bmcDbOperator.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class TMGI implements Comparable<TMGI> {
    public static final int STATUS_FREE = 0;
    public static final int STATUS_USED = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Index
    private String value; // TMGI printable string

    private String mcc;

    private String mnc;

    private int mncLength;

    private long serviceId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public int getMncLength() {
        return mncLength;
    }

    public void setMncLength(int mncLength) {
        this.mncLength = mncLength;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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

        TMGI other = (TMGI) obj;
        return this.equalTMGI(other);
    }

    private boolean equalTMGI(TMGI other) {
        return value.equals(other.getValue());
    }

    public int compareTo(TMGI next) {
        return value.compareTo(next.getValue());
    }
}
