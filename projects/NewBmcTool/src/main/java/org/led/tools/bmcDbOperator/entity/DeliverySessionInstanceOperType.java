package org.led.tools.bmcDbOperator.entity;


public enum DeliverySessionInstanceOperType {
    NOT_ACTIVATED("Not-Activated"),
    ACTIVATED("Activated"),
    UNEXPECT_STOP("Unexpect-stop"),
    TEMPORARY_FAILED("Temporary-failed"),
    PERMANENT_FAILED("Permanent-failed");
    private final String value;

    DeliverySessionInstanceOperType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
}
