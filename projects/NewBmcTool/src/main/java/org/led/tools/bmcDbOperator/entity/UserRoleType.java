package org.led.tools.bmcDbOperator.entity;

public enum UserRoleType {
    ADMIN("Admin"), OPERATOR("Operator"), ASP("ASP"), CSP("CSP"), VIEWER("Viewer");
    private final String value;

    UserRoleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
}
