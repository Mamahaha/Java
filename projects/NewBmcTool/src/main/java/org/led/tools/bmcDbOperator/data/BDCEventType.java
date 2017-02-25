package org.led.tools.bmcDbOperator.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum BDCEventType {
    // General
    BMSC_UP_AND_RUNNING(10, "up and running"), BMSC_OUT_OF_SERVICE(11, "BM-SC out of service"), BMSC_CACHE_AT_80_PERCENT_OCCUPANCY(12,
            "BM-SC cache at 80% occupancy"), BMSC_CACHE_FULL(13, "BM-SC cache full"),

    // Bearer

    BEARER_ACTIVATED(20, "Bearer activated"), BEARER_DELETED(21, "Bearer deleted"), BEARER_CHECKING_TIME_OK(22, "Bearer checking time OK"), 
            BEARER_START_TEMPORARY_FAILED(23,"Bearer start temporary failed"), BEARER_START_PERMANENT_FAILED(24,"Bearer start permanent failed"),
            BEARER_STOP_TEMPORARY_FAILED(25,"Bearer stop temporary failed"), BEARER_STOP_PERMANENT_FAILED(26,"Bearer stop permanent failed"),
            BEARER_UNEXPECTED_STOP(29, "Unexpected stop - bearer"),

    // Session
    INSTANCE_DELETED(31, "Instance deleted"), INSTANCE_UNEXPECTED_STOP(39, "Unexpected stop - session"),

    // Content
    FILE_DOWNLOADED(40, "File downloaded"), FILE_READY_FOR_DELIVERY(41, "File ready for delivery"), FILE_DELIVERED(42, "File delivered"), FILE_DELETED(43,
            "File deleted"), WEBDAV_NO_FILE_PUSHED(44, "No file pushed over Web-DAV"), WEBDAV_TOO_BIG_FILE_PUSHED(45, "Too big file pushed over Web-DAV"), WEBDAV_DIR_AT_80_PERCENT_OCCUPANCY(
            46, "Web-DAV directory at 80% occupancy"), WEBDAV_DIR_FULL(47, "Web-DAV directory full"), CONTENT_UNEXPECTED_DELETE(48,
            "Unexpected content deleted"), FILE_DELIVERY_STARTED(49, "File delivery started"), FILE_TRANSMISSION_STOPPED(50, "File transmission stopped"), FILE_PREPARATION_FAILED(
            51, "File preparation failed"), FILE_DOWNLOAD_FAILED(52, "File download failed"), CONTINUOUS_CONTENT_DELIVERY_STARTED(53,
            "Continuous content delivery started"), CONTINUOUS_CONTENT_TRANSMISSION_FAILED(54, "Continuous content transmission failed"), UNKNOWN_EVENT(999,
            "Unknown event");

    private static final Map<Integer, BDCEventType> CODE_MAP = new HashMap<Integer, BDCEventType>();
    static {
        for (BDCEventType type : values()) {
            CODE_MAP.put(type.code, type);
        }
    }

    private final int code;
    private final String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    private BDCEventType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static BDCEventType lookup(int code) {
        BDCEventType event = CODE_MAP.get(code);
        if (event == null) {
            event = UNKNOWN_EVENT;
        }
        return event;
    }

    public static boolean isSame(List<BDCEventType> eventTypes) {
        if (eventTypes.size() == 1){
            return true;
        }

        for (BDCEventType type : eventTypes) {
            if (!eventTypes.get(0).equals(type)) {
                return false;
            }
        }
        return true;
    }
}
