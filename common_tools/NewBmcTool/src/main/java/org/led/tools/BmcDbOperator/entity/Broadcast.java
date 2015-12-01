package org.led.tools.BmcDbOperator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class Broadcast {

    private static final String TIMESTAMP_ERROR_MSG = "timestamp with time zone not null";
    
    @Id
    @GeneratedValue
    private long id;

    @Index
    private String name;

    private String description;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "SID")
    private Service service;

    @Column(columnDefinition = TIMESTAMP_ERROR_MSG)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(columnDefinition = TIMESTAMP_ERROR_MSG)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopTime;

    @Column(columnDefinition = TIMESTAMP_ERROR_MSG)
    @Temporal(TemporalType.TIMESTAMP)
    private Date announcedStartTime;

    @Column(columnDefinition = TIMESTAMP_ERROR_MSG)
    @Temporal(TemporalType.TIMESTAMP)
    private Date announcedStopTime;
    
    @Index
    @Column(columnDefinition = TIMESTAMP_ERROR_MSG)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;

    @Index
	private String adminState;

    @Index
    private String operState;

    private String userID;

    private String provisioningResult;

    @Lob
    private String provisioningResultDescritption;

    @OrderBy
    @OneToMany(mappedBy = "broadcast", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    private List<UserServiceInstance> userServiceInstances = new ArrayList<UserServiceInstance>();

    @OneToMany(mappedBy = "broadcast", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
    private List<NotificationURL> notificationURLs = new ArrayList<NotificationURL>();

    public void addNotificationURL(NotificationURL url) {
        this.notificationURLs.add(url);
    }

    public List<NotificationURL> getNotificationURLs() {
        return notificationURLs;
    }

    public void setNotificationURLs(List<NotificationURL> notificationURLs) {
        this.notificationURLs = notificationURLs;
    }

    public void addUserServiceInstance(UserServiceInstance usi) {
        userServiceInstances.add(usi);
    }

    public List<UserServiceInstance> getUserServiceInstances() {
        return userServiceInstances;
    }

    public void setUserServiceInstances(List<UserServiceInstance> userServiceInstances) {
        this.userServiceInstances = userServiceInstances;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getId() {
        return id;
    }

    public void setId(long serviceId) {
        this.id = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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

    public Date getAnnouncedStartTime() {
        return announcedStartTime;
    }

    public void setAnnouncedStartTime(Date announcedStartTime) {
        this.announcedStartTime = announcedStartTime;
    }

    public Date getAnnouncedStopTime() {
        return announcedStopTime;
    }

    public void setAnnouncedStopTime(Date announcedStopTime) {
        this.announcedStopTime = announcedStopTime;
    }


    public String getAdminState() {
        return adminState;
    }

    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }

    public String getOperState() {
        return operState;
    }

    public void setOperState(String operState) {
        this.operState = operState;
    }

    public String getProvisioningResult() {
        return provisioningResult;
    }

    public void setProvisioningResult(String provisioningResult) {
        this.provisioningResult = provisioningResult;
    }

    public String getProvisioningResultDescritption() {
        return provisioningResultDescritption;
    }

    public void setProvisioningResultDescritption(String provisioningResultDescritption) {
        this.provisioningResultDescritption = provisioningResultDescritption;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return String.format("[name: %s,startTime: %s, stopTime: %s,operState: %s]", name, startTime, stopTime, operState);
    }
}
