package org.led.tools.bmcDbOperator.entity;

public enum ContentModeType {
    Continuous("Continuous"), 
    OnRequest("OnRequest");
    
    private final String value;
    
    ContentModeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContentModeType fromValue(String v) {
        for (ContentModeType c: ContentModeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
