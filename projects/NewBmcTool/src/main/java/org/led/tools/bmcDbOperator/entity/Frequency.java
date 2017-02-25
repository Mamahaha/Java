/**
 * 
 */
package org.led.tools.bmcDbOperator.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.openjpa.persistence.jdbc.Index;

/**
 * 
 */
@Entity
public class Frequency {

    @Id
    @GeneratedValue
    private long id;

    @Index
    private String frequencyName;

    // service announcement will use the earfcn by searching in the frequency table
    private int earfcn;

    private boolean isDefault;

    public Frequency(long i, String n, int e, boolean d) {
        id = i;
        frequencyName = n;
        earfcn = e;
        isDefault = d;
    }

    public Frequency() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFrequencyName() {
        return frequencyName;
    }

    public void setFrequencyName(String frequencyName) {
        this.frequencyName = frequencyName;
    }

    public int getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(int earfcn) {
        this.earfcn = earfcn;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
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
        Frequency other = (Frequency) obj;
        
        return this.equalFrequency(other);
    }

    private boolean equalFrequency(Frequency other) {
        boolean b1 = equalStrings(this.frequencyName, other.frequencyName);
        boolean b2 = this.earfcn == other.earfcn;
        boolean b3 = this.isDefault == other.isDefault;
        return b1 && b2 && b3;
    }

    private boolean equalStrings(String thisString, String otherString) {
        boolean b = false;
        if (thisString != null) {
            b = thisString.equals(otherString);
        } else if (otherString != null) {
            b = otherString.equals(thisString);
        } else {
            b = true;
        }
        return b;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((frequencyName == null) ? 0 : frequencyName.hashCode());
        result = prime * result + earfcn;
        result = prime * result + (!isDefault ? 0 : 1);
        
        return result;
    }
    
    public Frequency deepCopy(){
        return new Frequency(id, frequencyName, earfcn, isDefault);
    }
}
