package org.led.tools.bmcDbOperator.entity;

public enum OperStateType {
    notstart("Not-Started"),
    processing("In-Process"),
    stopped("Stopped");
    
    private final String value;

    OperStateType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
}
