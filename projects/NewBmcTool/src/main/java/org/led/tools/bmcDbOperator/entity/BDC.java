/**
 *
 */
package org.led.tools.bmcDbOperator.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.openjpa.persistence.jdbc.Index;


import org.led.tools.bmcDbOperator.data.BdcObject;

import com.google.gson.Gson;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class BDC {

    private Gson gson = new Gson();

    @Id
    @GeneratedValue
    private long id;

    private String description;

    @Index
    private String name;

    private String vendor;

    private String connectionProtocol;

    private String version;

    private String saiProvisioningUrl;

    private String ncmUser;

    private String ncmPassword;

    private String gwAddress;

    @Lob
    private String connectionDetail;

    private String connectionDetailType;

    @ManyToMany(mappedBy = "bdcList")
    private List<ServiceArea> sas;

    @OrderBy
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.EAGER, mappedBy = "bdc")
    private List<ADF> adfs = new ArrayList<ADF>();

    public List<ADF> getAdfs() {
        return adfs;
    }

    public void setAdfs(List<ADF> adfs) {
        this.adfs = adfs;
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

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public List<ServiceArea> getSas() {
        return sas;
    }

    public void setSas(List<ServiceArea> sas) {
        this.sas = sas;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getConnectionProtocol() {
        return connectionProtocol;
    }

    public void setConnectionProtocol(String connectionProtocol) {
        this.connectionProtocol = connectionProtocol;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConnectionDetail() {
        return connectionDetail;
    }

    public void setConnectionDetail(String connectionDetail) {
        this.connectionDetail = connectionDetail;
    }

    public String getHostname() {
        if (connectionDetail == null || connectionDetail.isEmpty()) {
            return null;
        } else {
            return covertFromJson(connectionDetail).getAddress();
        }
    }

    public void setHostname(String hostName) {
        BdcObject.BDCConnectionDetail bdcConnectionDetail = new BdcObject.BDCConnectionDetail();
        if (connectionDetail == null || connectionDetail.isEmpty()) {
            bdcConnectionDetail.setAddress(hostName);
        } else {
            bdcConnectionDetail = covertFromJson(connectionDetail);
            bdcConnectionDetail.setAddress(hostName);
        }
        this.connectionDetail = covertToJson(bdcConnectionDetail);
    }

    public int getPort() {
        if (connectionDetail == null || connectionDetail.isEmpty()) {
            return 0;
        } else {
            return covertFromJson(connectionDetail).getPort();
        }
    }

    public void setPort(int port) {
        BdcObject.BDCConnectionDetail bdcConnectionDetail = new BdcObject.BDCConnectionDetail();
        if (connectionDetail == null || connectionDetail.isEmpty()) {
            bdcConnectionDetail.setPort(port);
        } else {
            bdcConnectionDetail = covertFromJson(connectionDetail);
            bdcConnectionDetail.setPort(port);
        }
        this.connectionDetail = covertToJson(bdcConnectionDetail);
    }

    public String getHttpProtocol() {
        if (connectionDetail == null || connectionDetail.isEmpty()) {
            return "http";
        }
        return covertFromJson(connectionDetail).isSsl() ? "https" : "http";
    }

    public String getBaseUrl() {
        if (connectionDetail == null || connectionDetail.isEmpty()) {
            return null;
        } else {
            return covertFromJson(connectionDetail).getBasePath();
        }
    }

    public void setBaseUrl(String basePath) {
        BdcObject.BDCConnectionDetail bdcConnectionDetail = new BdcObject.BDCConnectionDetail();
        if (connectionDetail == null || connectionDetail.isEmpty()) {
            bdcConnectionDetail.setBasePath(basePath);
        } else {
            bdcConnectionDetail = covertFromJson(connectionDetail);
            bdcConnectionDetail.setBasePath(basePath);
        }
        this.connectionDetail = covertToJson(bdcConnectionDetail);
    }

    public String getWebDavMountPoint() {
        if (connectionDetail == null || connectionDetail.isEmpty()) {
            return null;
        } else {
            return covertFromJson(connectionDetail).getWebDavMountPoint();
        }
    }

    public void setWebDavMountPoint(String webDavMountPoint) {
        BdcObject.BDCConnectionDetail bdcConnectionDetail = new BdcObject.BDCConnectionDetail();
        if (connectionDetail == null || connectionDetail.isEmpty()) {
            bdcConnectionDetail.setWebDavMountPoint(webDavMountPoint);
        } else {
            bdcConnectionDetail = covertFromJson(connectionDetail);
            bdcConnectionDetail.setWebDavMountPoint(webDavMountPoint);
        }
        this.connectionDetail = covertToJson(bdcConnectionDetail);
    }

    // convert json to object
    private BdcObject.BDCConnectionDetail covertFromJson(String connectionDetailString) {
        return gson.fromJson(connectionDetailString, BdcObject.BDCConnectionDetail.class);
    }

    // convert object to json string
    private String covertToJson(BdcObject.BDCConnectionDetail bdcConnectionDetail) {
        return gson.toJson(bdcConnectionDetail);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getConnectionDetailType() {
        return connectionDetailType;
    }

    public void setConnectionDetailType(String connectionDetailType) {
        this.connectionDetailType = connectionDetailType;
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

        BDC other = (BDC) obj;
        return this.equalBDC(other);
    }

    private boolean equalBDC(BDC other) {
        boolean b1 = this.id == other.id;
        boolean b2 = equalObject(this.name, other.name);
        boolean b3 = equalObject(this.description, other.description);
        boolean b123 = b1 && b2 && b3;

        boolean b4 = equalObject(this.vendor, other.vendor);
        boolean b5 = equalObject(this.version, other.version);
        boolean b45 = b4 && b5;

        boolean b6 = equalObject(this.connectionDetail, other.connectionDetail);
        boolean b7 = equalObject(this.connectionDetailType, other.connectionDetailType);
        boolean b8 = equalObject(this.connectionProtocol, other.connectionProtocol);
        boolean b678 = b6 && b7 && b8;

        return b123 && b45 && b678;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = calHashCodeIntoResult(prime, name, result);
        result = calHashCodeIntoResult(prime, description, result);
        result = calHashCodeIntoResult(prime, version, result);
        result = calHashCodeIntoResult(prime, vendor, result);
        result = calHashCodeIntoResult(prime, connectionDetail, result);
        result = calHashCodeIntoResult(prime, connectionDetailType, result);
        result = calHashCodeIntoResult(prime, connectionProtocol, result);

        return result;
    }

    private int calHashCodeIntoResult(int prime, Object field, int result) {
        return prime * result + ((field == null) ? 0 : field.hashCode());
    }

    private boolean equalObject(Object thisObject, Object otherObject) {
        boolean b = false;
        if (thisObject != null) {
            b = thisObject.equals(otherObject);
        } else if (otherObject != null) {
            b = otherObject.equals(thisObject);
        } else {
            b = true;
        }
        return b;
    }

    public String getSaiProvisioningUrl() {
        return saiProvisioningUrl;
    }

    public void setSaiProvisioningUrl(String saiProvisioningUrl) {
        this.saiProvisioningUrl = saiProvisioningUrl;
    }

    public String getNcmUser() {
        return ncmUser;
    }

    public void setNcmUser(String ncmUser) {
        this.ncmUser = ncmUser;
    }

    public String getNcmPassword() {
        return ncmPassword;
    }

    public void setNcmPassword(String ncmPassword) {
        this.ncmPassword = ncmPassword;
    }

    public String getGwAddress() {
        return gwAddress;
    }

    public void setGwAddress(String gwAddress) {
        this.gwAddress = gwAddress;
    }
}
