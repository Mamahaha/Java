package org.led.tools.bmcDbOperator.entity;

public enum TmgiRangeType {

    National("National"), Regional("Regional"), Local("Local"), Dynamic("Dynamic");

    private final String value;

    TmgiRangeType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}