

package org.led.tools.bmcDbOperator.common;

public class ConnectionObject {

    private byte[] contentBody = new byte[0];

    private String messageBody = null;

    private String connectionString = null;

    private String connectionMethod = null;

    private String deviceName = null;
    
    private Boolean isNcmConnection = null;
    
    private String ncmUser = null;
    
    private String ncmPassword = null;

    public ConnectionObject(byte[] contentBody, String connectionString, String connectionMethod) {
        if (contentBody != null) {
            this.contentBody = contentBody.clone();
        }
        this.connectionString = connectionString;
        this.connectionMethod = connectionMethod;
        this.isNcmConnection = false;
    }

    public ConnectionObject(String messageBody, String connectionString, String connectionMethod) {
        this.messageBody = messageBody;
        this.connectionString = connectionString;
        this.connectionMethod = connectionMethod;
        this.isNcmConnection = false;
    }

    public ConnectionObject() {
        this.isNcmConnection = false;
    }

    public byte[] getContentBody() {
        return contentBody;
    }

    public void setContentBody(byte[] contentBody) {
        if (contentBody != null) {
            this.contentBody = contentBody.clone();
        }
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionMethod(String connectionMethod) {
        this.connectionMethod = connectionMethod;
    }

    public String getConnectionMethod() {
        return connectionMethod;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Boolean getIsNcmConnection() {
        return isNcmConnection;
    }

    public void setIsNcmConnection(Boolean isNcmConnection) {
        this.isNcmConnection = isNcmConnection;
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

}
