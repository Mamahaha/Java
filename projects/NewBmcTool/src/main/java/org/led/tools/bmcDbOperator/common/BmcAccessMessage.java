package org.led.tools.bmcDbOperator.common;



public class BmcAccessMessage {

    private ConnectionObject co;

    private ConnectionResponse cr;

    public void addAccessRequest(ConnectionObject co) {
        this.co = co;
    }

    public void addAccessResponse(ConnectionResponse cr) {
        this.cr = cr;
    }

    public String format() {

        String connStr = co.getConnectionString();
        String requestMsgStr = co.getMessageBody();
        int responseCode = 0;
        String responseStr = "Can not get the response, please check the bmc.log for detail";
        String deviceName = co.getDeviceName();

        if (cr != null) {
            responseCode = cr.getStatusCode();
            responseStr = cr.getContent();
        }

        if (deviceName == null) {
            deviceName = "Unknown";
        }

        StringBuilder msg = new StringBuilder("\n[ " + deviceName + " ] ========= Request  =========\n");
        if (connStr != null) {
            msg.append(connStr);
        }

        if (requestMsgStr != null) {
            msg.append(requestMsgStr);
        }

        msg.append("\n");
        msg.append("[ " + deviceName + " ] ========= Response ========= \n");

        if (responseCode != 0) {
            msg.append(responseCode);
        }

        if (responseStr != null) {
            msg.append("\n");
            msg.append(responseStr);
        }

        return msg.toString();
    }

}
