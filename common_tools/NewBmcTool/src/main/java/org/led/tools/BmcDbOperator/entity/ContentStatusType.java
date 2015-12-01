package org.led.tools.BmcDbOperator.entity;

import java.util.List;
import java.util.Set;

import org.led.tools.BmcDbOperator.data.BDCEventType;



public enum ContentStatusType {
    PENDING, IN_QUEUE, IN_PROCESS, DELIVERED, DELIVERY_FAILED, PARTIALLY_DELIVERED, CANCELLED;

    public static ContentStatusType getStatus(BDCEventType bdcEventType) {
        if (bdcEventType == null) {
            return ContentStatusType.IN_PROCESS;
        }

        switch (bdcEventType) {
        case FILE_DELIVERED:
            return ContentStatusType.DELIVERED;
        case FILE_TRANSMISSION_STOPPED:
        case CONTINUOUS_CONTENT_TRANSMISSION_FAILED:
            return ContentStatusType.DELIVERY_FAILED;
        case FILE_DELIVERY_STARTED:
            return ContentStatusType.IN_PROCESS;
        default:
            return ContentStatusType.PENDING;

        }
    }

    public static ContentStatusType getStatus(Set<ContentStatusType> statusGroup) {
        if (statusGroup.contains(IN_PROCESS)) {
            return ContentStatusType.IN_PROCESS;
        } else if (statusGroup.contains(IN_QUEUE) && (statusGroup.contains(DELIVERED) || statusGroup.contains(DELIVERY_FAILED))) {
            return ContentStatusType.IN_PROCESS;
        } else if (isSame(statusGroup)) {
            return (ContentStatusType) statusGroup.toArray()[0];
        }
        return ContentStatusType.PARTIALLY_DELIVERED;
    }

    public static boolean isDeliveredFailed(List<String> status) {
        return false;
    }

    private static boolean isSame(Set<ContentStatusType> events) {
        return events.size() == 1;
    }
}
