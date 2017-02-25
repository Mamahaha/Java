package org.led.tools.bmcDbOperator.entity;

import java.util.Date;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BmcUserSession {

    private static final int MAX_TOP = 99999999;
    private static final int MIN_TOP = 10000000;
    @Id
    private int sessionId;

    private String userName;

    private Date loginTime;

    private Date latestUpdateTime;

    public BmcUserSession() {
    }

    public BmcUserSession(String userName, Date loginTime) {
        super();

        Random random = new Random();
        this.sessionId = random.nextInt(MAX_TOP) % (MAX_TOP - MIN_TOP + 1) + MIN_TOP;
        this.userName = userName;
        this.loginTime = loginTime;
    }

    public long getId() {
        return sessionId;
    }

    public void setId() {
        Random random = new Random();
        this.sessionId = random.nextInt(MAX_TOP) % (MAX_TOP - MIN_TOP + 1) + MIN_TOP;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public void setLatestTime(Date latestTime) {

        this.setLatestUpdateTime(latestTime);
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLatestUpdateTime(Date latestUpdateTime) {
        this.latestUpdateTime = latestUpdateTime;
    }

    public Date getLatestUpdateTime() {
        return latestUpdateTime;
    }

}