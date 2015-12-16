package org.led.tools.bmcDbOperator.entity;

public enum AdminStateType {

    PENDING("Pending"),
    APPROVED("Approved"),
    CANCELLED("Cancelled");

    private final String value;

    AdminStateType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AdminStateType fromValue(String v) {
        for (AdminStateType c: AdminStateType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }    
}
