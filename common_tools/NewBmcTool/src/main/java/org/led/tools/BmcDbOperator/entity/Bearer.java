package org.led.tools.BmcDbOperator.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class Bearer {

    @Id
    @GeneratedValue
    private long id;

    @Index
    private String name;

    private String description;

    @Index(unique = true)
    private String tmgi;

    @Index
    private String pipeId;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "SID")
    private Service service;

    @OneToMany(mappedBy = "bearer", cascade = { CascadeType.PERSIST })
    private List<UserService> userService = new ArrayList<UserService>();

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

    public String getTmgi() {
        return tmgi;
    }

    public void setTmgi(String tmgi) {
        this.tmgi = tmgi;
    }

    public String getPipeId() {
        return pipeId;
    }

    public void setPipeId(String pipeId) {
        this.pipeId = pipeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<UserService> getUserService() {
        return userService;
    }

    public void setUserService(List<UserService> userService) {
        this.userService = userService;
    }
}
