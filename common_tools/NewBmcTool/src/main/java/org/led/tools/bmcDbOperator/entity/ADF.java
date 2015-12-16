package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class ADF {

    @Id
    @GeneratedValue
    private long id;

    private String serverName;

    private String protocol;

    private String address;

    private Integer port;

    private String userName;

    private String password;

    private String baseFolder;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "bdcId")
    private BDC bdc;

    public ADF() {
    }

    public ADF(long id, String serverName, String protocol, String address, Integer port, String userName, String password, String baseFolder, BDC bdc) {
        super();
        this.id = id;
        this.serverName = serverName;
        this.protocol = protocol;
        this.address = address;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.baseFolder = baseFolder;
        this.bdc = bdc;
    }

    public BDC getBdc() {
        return bdc;
    }

    public void setBdc(BDC bdc) {
        this.bdc = bdc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseFolder() {
        return baseFolder;
    }

    public void setBaseFolder(String baseFolder) {
        this.baseFolder = baseFolder;
    }

    public ADF deepCopy() {
        return new ADF(id, serverName, protocol, address, port, userName, password, baseFolder, bdc);
    }

}
