package org.led.tools.BmcDbOperator.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class OccupiedPipe {

    private static final String TIMESTAMP_ERROR_MSG = "timestamp with time zone not null";

    private static final int MOD_NUMBER = 32;

    @Id
    @GeneratedValue
    private long id;

    @Index
    private String pipename;

    @Index
    private long sai;

    private float bandwidth;

    @Column(columnDefinition = TIMESTAMP_ERROR_MSG)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(columnDefinition = TIMESTAMP_ERROR_MSG)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopTime;

    private long bearerId;

    private String tmgi;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return pipename;
    }

    public void setName(String name) {
        this.pipename = name;
    }

    public float getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(float bandwidth) {
        this.bandwidth = bandwidth;
    }

    public long getSai() {
        return sai;
    }

    public void setSai(long sai) {
        this.sai = sai;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getTmgi() {
        return tmgi;
    }

    public void setTmgi(String tmgi) {
        this.tmgi = tmgi;
    }

    public long getBearerId() {
        return bearerId;
    }

    public void setBearerId(long bearerId) {
        this.bearerId = bearerId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(bandwidth);
        result = prime * result + (int) (bearerId ^ (bearerId >>> MOD_NUMBER));
        result = prime * result + (int) (id ^ (id >>> MOD_NUMBER));
        result = prime * result + ((pipename == null) ? 0 : pipename.hashCode());
        result = prime * result + (int) (sai ^ (sai >>> MOD_NUMBER));
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
        result = prime * result + ((stopTime == null) ? 0 : stopTime.hashCode());
        result = prime * result + ((tmgi == null) ? 0 : tmgi.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OccupiedPipe other = (OccupiedPipe) obj;
        if (Float.floatToIntBits(bandwidth) != Float.floatToIntBits(other.bandwidth)) {
            return false;
        }
        if (bearerId != other.bearerId) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (pipename == null) {
            if (other.pipename != null) {
                return false;
            }
        } else if (!pipename.equals(other.pipename)) {
            return false;
        }
        if (sai != other.sai) {
            return false;
        }
        if (startTime == null) {
            if (other.startTime != null) {
                return false;
            }
        } else if (!startTime.equals(other.startTime)) {
            return false;
        }
        if (stopTime == null) {
            if (other.stopTime != null) {
                return false;
            }
        } else if (!stopTime.equals(other.stopTime)) {
            return false;
        }
        if (tmgi == null) {
            if (other.tmgi != null) {
                return false;
            }
        } else if (!tmgi.equals(other.tmgi)) {
            return false;
        }
        return true;
    }

}
