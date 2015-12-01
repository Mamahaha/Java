package org.led.tools.BmcDbOperator.entity;


public enum BroadcastProvisioningResultType {

    PROVISIONED("Provisioned"),
    PARTIALLY_PROVISIONED("Partially Provisioned");
    
    private final String value;

    BroadcastProvisioningResultType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
}
