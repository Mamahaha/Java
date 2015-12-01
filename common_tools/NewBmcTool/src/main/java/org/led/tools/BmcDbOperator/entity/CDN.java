/**
 * 
 */
package org.led.tools.BmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 */
@Entity
public class CDN {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String hostname;

    private int port;

    private String userName;

    private String password;

    // SAFPath
    private String dir;

    private String version;

    private String vendor;

    private String sAFFileName;

    private String bootstrapPath;

    private String bootstrapFileName;

    private String ueConfigurePath;

    private String ueConfigureFileName;

    private String bootconfigPath;

    private String bootconfigFileName;

    public CDN(String h) {
        hostname = h;
    }
    public CDN(long i, String n, String h, int p, String u, String pwd, String d, String ver, String ven, String saf, String bsP, String bsFN, String ueCP,
            String ueCFN, String bcP, String bcFN) {
        id = i;
        name = n;
        hostname = h;
        port = p;
        userName = u;
        password = pwd;
        dir = d;
        version = ver;
        vendor = ven;
        sAFFileName = saf;
        bootstrapPath = bsP;
        bootstrapFileName = bsFN;
        ueConfigurePath = ueCP;
        ueConfigureFileName = ueCFN;
        bootconfigPath = bcP;
        bootconfigFileName = bcFN;
    }

    public CDN(String n, String h, int p, String u, String pwd, String d, String ver, String ven, String saf, String bsP, String bsFN, String ueCP,
            String ueCFN, String bcP, String bcFN) {
        name = n;
        hostname = h;
        port = p;
        userName = u;
        password = pwd;
        dir = d;
        version = ver;
        vendor = ven;
        sAFFileName = saf;
        bootstrapPath = bsP;
        bootstrapFileName = bsFN;
        ueConfigurePath = ueCP;
        ueConfigureFileName = ueCFN;
        bootconfigPath = bcP;
        bootconfigFileName = bcFN;
    }

    public CDN (){}
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

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getsAFFileName() {
        return sAFFileName;
    }

    public void setsAFFileName(String sAFFileName) {
        this.sAFFileName = sAFFileName;
    }

    public String getBootstrapPath() {
        return bootstrapPath;
    }

    public void setBootstrapPath(String bootstrapPath) {
        this.bootstrapPath = bootstrapPath;
    }

    public String getBootstrapFileName() {
        return bootstrapFileName;
    }

    public void setBootstrapFileName(String bootstrapFileName) {
        this.bootstrapFileName = bootstrapFileName;
    }

    public String getUeConfigurePath() {
        return ueConfigurePath;
    }

    public void setUeConfigurePath(String ueConfigurePath) {
        this.ueConfigurePath = ueConfigurePath;
    }

    public String getUeConfigureFileName() {
        return ueConfigureFileName;
    }

    public void setUeConfigureFileName(String ueConfigureFileName) {
        this.ueConfigureFileName = ueConfigureFileName;
    }

    public String getBootconfigPath() {
        return bootconfigPath;
    }

    public void setBootconfigPath(String bootconfigPath) {
        this.bootconfigPath = bootconfigPath;
    }

    public String getBootconfigFileName() {
        return bootconfigFileName;
    }

    public void setBootconfigFileName(String bootconfigFileName) {
        this.bootconfigFileName = bootconfigFileName;
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
        CDN other = (CDN) obj;

        return this.equalCDN(other);
    }

    private boolean equalCDN(CDN other) {

        boolean isBasicInfoEqual = isBasicInfoEqual(other.name, other.hostname, other.port);
        boolean isUserInfoEqual = isUserInfoEqual(other.userName, other.password);
        boolean isSAFInfoEqual = isSAFInfoEqual(other.sAFFileName, other.dir);
        boolean b1 = isBasicInfoEqual && isUserInfoEqual && isSAFInfoEqual;

        boolean isBootInfoEqual = isBootInfoEqual(other.bootstrapPath, other.bootstrapFileName, other.bootconfigPath, other.bootconfigFileName);
        boolean isUeInfoEqual = isUeInfoEqual(other.ueConfigurePath, other.ueConfigureFileName);
        boolean b2 = isBootInfoEqual && isUeInfoEqual;

        return b1 && b2;
    }

    private boolean isUeInfoEqual(String ueConfigurePath, String ueConfigureFileName) {
        boolean b1 = equalObject(this.ueConfigurePath, ueConfigurePath);
        boolean b2 = equalObject(this.ueConfigureFileName, ueConfigureFileName);

        return b1 && b2;
    }

    private boolean isBootInfoEqual(String bootstrapPath, String bootstrapFileName, String bootconfigPath, String bootconfigFileName) {
        boolean b1 = equalObject(this.bootstrapPath, bootstrapPath);
        boolean b2 = equalObject(this.bootstrapFileName, bootstrapFileName);
        boolean b3 = b1 && b2;

        boolean b4 = equalObject(this.bootconfigPath, bootconfigPath);
        boolean b5 = equalObject(this.bootconfigFileName, bootconfigFileName);
        boolean b6 = b4 && b5;

        return b3 && b6;
    }

    private boolean isSAFInfoEqual(String sAFFileName, String dir) {
        boolean b1 = equalObject(this.dir, dir);
        boolean b2 = equalObject(this.sAFFileName, sAFFileName);

        return b1 && b2;
    }

    private boolean isBasicInfoEqual(String name, String hostname, int port) {
        boolean b1 = equalObject(this.name, name);
        boolean b2 = equalObject(this.hostname, hostname);
        boolean b3 = equalObject(this.port, port);
        boolean b4 = b1 && b2 && b3;
        boolean b5 = equalObject(this.version, version);
        boolean b6 = equalObject(this.vendor, vendor);
        return b4 && b5 && b6;
    }

    private boolean isUserInfoEqual(String userName, String password) {
        boolean b1 = equalObject(this.userName, userName);
        boolean b2 = equalObject(this.password, password);
        return b1 && b2;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = calHashCodeIntoResult(prime, name, result);
        result = calHashCodeIntoResult(prime, hostname, result);
        result = prime * result + port;
        result = calHashCodeIntoResult(prime, userName, result);
        result = calHashCodeIntoResult(prime, password, result);
        result = calHashCodeIntoResult(prime, dir, result);
        result = calHashCodeIntoResult(prime, version, result);
        result = calHashCodeIntoResult(prime, vendor, result);
        result = calHashCodeIntoResult(prime, sAFFileName, result);
        result = calHashCodeIntoResult(prime, bootstrapPath, result);
        result = calHashCodeIntoResult(prime, bootstrapFileName, result);
        result = calHashCodeIntoResult(prime, ueConfigurePath, result);
        result = calHashCodeIntoResult(prime, ueConfigureFileName, result);
        result = calHashCodeIntoResult(prime, bootconfigPath, result);
        result = calHashCodeIntoResult(prime, bootconfigFileName, result);

        return result;
    }

    private int calHashCodeIntoResult(int prime, Object listField, int result) {
        return prime * result + ((listField == null) ? 0 : listField.hashCode());
    }

    public CDN deepCopy() {
        return new CDN(id, name, hostname, port, userName, password, dir, version, vendor, sAFFileName, bootstrapPath, bootstrapFileName, ueConfigurePath,
                ueConfigureFileName, bootconfigPath, bootconfigFileName);
    }
    
    public String toString() {
        return "CDN instance: " + id + "; "+ name + "; " + hostname + "; " + port + "; " + userName + "; " + sAFFileName + "; " + bootstrapPath;  
    }
}
