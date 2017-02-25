package org.led.tools.bmcDbOperator.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class ENodeB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Index(unique = true)
    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String frequency;

    private boolean vpipe;
    
    private static final int GENER_HASH_CODE_FIRST_NUM = 1231;
    
    private static final int GENER_HASH_CODE_SECOND_NUM = 1237;

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public boolean isVpipe() {
        return vpipe;
    }

    public void setVpipe(boolean vpipe) {
        this.vpipe = vpipe;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (vpipe ? GENER_HASH_CODE_FIRST_NUM : GENER_HASH_CODE_SECOND_NUM);
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
        ENodeB other = (ENodeB) obj;
        if (frequency == null) {
            if (other.frequency != null) {
                return false;
            }
        } else if (!frequency.equals(other.frequency)) {
            return false;
        }
        if (latitude == null) {
            if (other.latitude != null) {
                return false;
            }
        } else if (!latitude.equals(other.latitude)) {
            return false;
        }
        if (longitude == null) {
            if (other.longitude != null) {
                return false;
            }
        } else if (!longitude.equals(other.longitude)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (vpipe != other.vpipe) {
            return false;
        }
        return true;
    }

    public boolean equalsValue(ENodeB obj) {
        if (obj == null) {
            return false;
        }

        if ((this.name.equals(obj.getName())) && (this.latitude.equals(obj.getLatitude())) && (this.longitude.equals(obj.getLongitude()))) {
            return true;
        }

        return false;
    }
}