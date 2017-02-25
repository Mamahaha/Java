package org.led.tools.bmcDbOperator.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Index;

/**
 * @author exiaowu
 *
 */
@Entity
public class BmcUser {

    @Index
    private long id;

    @Id
    private String name;

    private String password;

    private String role;

    // lock unlock
    private String status;

    private String description;

    private int loginTimes;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date userValidateTo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordValidateTo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date latestlogintime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date latestpasswordupdate;

    @OrderBy("ASC")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "User_Service_mapping", joinColumns = @JoinColumn(name = "name"))
    @Column(name = "serviceName")
    private List<String> serviceList;

    @OrderBy("ASC")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "User_ServiceClass_mapping", joinColumns = @JoinColumn(name = "name"))
    @Column(name = "serviceClassName")
    private List<String> serviceClassList;

    public BmcUser() {
    }

    public BmcUser(String name, String password, String role) {
        super();
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public BmcUser(String name, String password, String role, List<String> serviceList, List<String> serviceClassList) {
        super();
        this.name = name;
        this.password = password;
        this.role = role;
        this.serviceList = serviceList;
        this.serviceClassList = serviceClassList;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String name) {
        this.password = name;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUserValidateTo() {
        return userValidateTo;
    }

    public void setUserValidateTo(Date validateTo) {
        this.userValidateTo = validateTo;
    }

    public Date getPasswordValidateTo() {
        return passwordValidateTo;
    }

    public void setPasswordValidateTo(Date validateTo) {
        this.passwordValidateTo = validateTo;
    }

    public Date getLatestlogintime() {
        return latestlogintime;
    }

    public void setLatestlogintime(Date latestlogintime) {
        this.latestlogintime = latestlogintime;
    }

    public Date getLatestpasswordupdate() {
        return latestpasswordupdate;
    }

    public void setLatestpasswordupdate(Date latestpasswordupdate) {
        this.latestpasswordupdate = latestpasswordupdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desr) {
        this.description = desr;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String rolename) {
        this.role = rolename;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getServiceList() {
        return this.serviceList;
    }

    public void setServiceList(List<String> servicelist) {
        this.serviceList = servicelist;
    }

    public List<String> getSCList() {
        return this.serviceClassList;
    }

    public void setSCList(List<String> serviceclasslist) {
        this.serviceClassList = serviceclasslist;
    }

    public int getLoginTimes() {
        return this.loginTimes;
    }

    public void setLoginTimes(int loginTimes) {
        this.loginTimes = loginTimes;
    }

    public void incrLoginTimes() {
        this.loginTimes++;
    }
}
