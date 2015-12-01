package org.led.tools.BmcDbOperator.common;

public class BmcEventMessage {

    // session id
    private String sessionId;
    // transaction id
    private String transactionId;
    // domain or component
    private String domainOrComponent;
    // class information
    private String classInfo;
    // message content
    private String msgContent;

    private String errorCode = null;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDomainOrComponent() {
        return domainOrComponent;
    }

    public void setDomainOrComponent(String domainOrComponent) {
        this.domainOrComponent = domainOrComponent;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent, Object... arg) {

        if (arg == null || arg.length == 0) {
            this.msgContent = msgContent;
        }
        try {
            this.msgContent = String.format(msgContent, arg);
        } catch (Exception e) {
            this.msgContent = msgContent;
        }

    }

    public String formatCommon() {
        String msg = "";

        // optional item - session_ID
        if (this.getSessionId() != null) {
            msg = "[SessionId : " + this.getSessionId() + "]";
        }

        // optional item - transaction_ID
        if (this.getTransactionId() != null) {
            msg = msg + " [TransactionId : " + this.getTransactionId() + "]";
        }

        // mandatory items - domain or component name, message_text
        msg = msg + " [" + this.getDomainOrComponent() + "]" + (errorCode != null ? " [BMC-" + errorCode + "]" : "") + " [" + this.getMsgContent() + "]";

        return msg;
    }

    public String formatDebug() {
        // log content
        String msg = "";

        // optional item - session_ID
        if (this.getSessionId() != null) {
            msg = "[SessionId : " + this.getSessionId() + "]";
        }

        // optional item - transaction_ID
        if (this.getTransactionId() != null) {
            msg = msg + " [TransactionId : " + this.getTransactionId() + "]";
        }

        // mandatory items - domain or component name
        msg = msg + " [" + this.getDomainOrComponent() + "]";

        // optional item - class_Info
        if (this.getClassInfo() != null) {

            if (this.classInfo.contains("class")) {
                this.classInfo = this.classInfo.replace("class", "");
            }

            msg = msg + " [" + this.classInfo + "]";
        }

        // mandatory items - message_text
        msg = msg + " [" + this.getMsgContent() + "]";

        return msg;
    }

    public static String formatPerform(Object[] objects) {
        // TODO Auto-generated method stub
        return null;
    }

}
