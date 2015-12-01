package org.led.tools.BmcDbOperator.entity;

public enum UserStatusType {
    UN_LOCKED("Unlocked"), LOCKED("Locked");
    private final String value;

    UserStatusType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
}
