package org.led.tools.BmcDbOperator.entity;

public enum ProvisioningStateType {

    PLANNED("Planned"), 
    VERIFIED("Verified");

    private final String value;

    ProvisioningStateType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
}
