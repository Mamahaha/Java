
package org.led.tools.bmcDbOperator.entity;

public enum UserServiceMode {
    ONREQUEST("OnRequest"), CONTINUOUS("Continuous");

    private String value;

    UserServiceMode(String v) {
        this.value = v;
    }

    public String value() {
        return value;
    }
}
