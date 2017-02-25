package org.led.tools.bmcDbOperator.common;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = -1425229623234017895L;

    private Object response;

    private ResponseStatus status;
    
    private BmcErrorCode errorCode;
    
    private String cause;

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public void setResponseStatus(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getResponseStatus() {
        return status;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    public BmcErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(BmcErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
