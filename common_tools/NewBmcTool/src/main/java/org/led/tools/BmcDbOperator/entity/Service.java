package org.led.tools.BmcDbOperator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class Service {

    @Id
    @GeneratedValue
    private long id;

    @Index(unique = true)
    private String name;

    private String description;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "SCID")
    private ServiceClass serviceClass;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "BAID")
    private BroadcastArea broadcastArea;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "CreatedBy")
    private BmcUser user;

    @Column(columnDefinition = "timestamp with time zone not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @OrderBy
    @OneToMany(mappedBy = "service")
    private List<Bearer> bearerList = new ArrayList<Bearer>();

    @OneToMany(mappedBy = "service")
    private List<Broadcast> broadcastList = new ArrayList<Broadcast>();

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

    public ServiceClass getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(ServiceClass serviceClass) {
        this.serviceClass = serviceClass;
    }

    public BroadcastArea getBroadcastArea() {
        return broadcastArea;
    }

    public void setBroadcastArea(BroadcastArea broadcastArea) {
        this.broadcastArea = broadcastArea;
    }

    public BmcUser getUser() {
        return user;
    }

    public void setUser(BmcUser user) {
        this.user = user;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public List<Bearer> getBearerList() {
        return bearerList;
    }

    public void setBearerList(List<Bearer> bearerList) {
        this.bearerList = bearerList;
    }

    public List<Broadcast> getBroadcastList() {
        return broadcastList;
    }

    public void setBroadcastList(List<Broadcast> broadcastList) {
        this.broadcastList = broadcastList;
    }

}
