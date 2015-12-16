package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class AlarmRemedyText {

    private static final int TEXT_LENGTH = 16384;
    
    @Id
    @GeneratedValue
    private long id;

    @Index
    private String module;

    @Index
    private String errorCode;

    @Column(length = TEXT_LENGTH)
    private String description;
    
    private String alarmingObject;
    
    @Column(length = TEXT_LENGTH)
    private String raisedBy; 
    
    @Column(length = TEXT_LENGTH)
    private String clearedBy; 
    
    @Column(length = TEXT_LENGTH)
    private String proposedRepairAction;   
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlarmingObject() {
        return alarmingObject;
    }

    public void setAlarmingObject(String alarmingObject) {
        this.alarmingObject = alarmingObject;
    }

    public String getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(String raisedBy) {
        this.raisedBy = raisedBy;
    }

    public String getClearedBy() {
        return clearedBy;
    }

    public void setClearedBy(String clearedBy) {
        this.clearedBy = clearedBy;
    }

    public String getProposedRepairAction() {
        return proposedRepairAction;
    }

    public void setProposedRepairAction(String proposedRepairAction) {
        this.proposedRepairAction = proposedRepairAction;
    }
    
}
