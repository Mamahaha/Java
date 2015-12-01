package org.led.tools.BmcDbOperator.entity;


public enum UserServiceInstanceOperType {
    NOT_ACTIVATED("Not-Activated"),
    ACTIVATED("Activated"),
    PARTIALLY_ACTIVATED("Partially-Activated"),
    DEACTIVATING("Deactivating"),
    STOPPED("Stopped");
    private final String value;

    UserServiceInstanceOperType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
}
