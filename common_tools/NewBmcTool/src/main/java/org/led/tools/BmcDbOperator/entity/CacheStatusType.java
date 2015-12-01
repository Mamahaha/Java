package org.led.tools.BmcDbOperator.entity;

import java.util.Set;

import org.led.tools.BmcDbOperator.data.BDCEventType;



public enum CacheStatusType {
    PENDING, CACHING, CACHED, CACHED_FAILD, PARTICALLY_CACHED;

    public static CacheStatusType getStatus(BDCEventType bdcEventType) {
        if (bdcEventType == null) {
            return CacheStatusType.CACHING;
        }

        switch (bdcEventType) {
        case FILE_READY_FOR_DELIVERY:
            return CacheStatusType.CACHED;
        case FILE_PREPARATION_FAILED:
        case FILE_DOWNLOAD_FAILED:
            return CacheStatusType.CACHED_FAILD;
        default:
            return CacheStatusType.PENDING;

        }
    }

    public static CacheStatusType getStatus(Set<CacheStatusType> statusGroup) {
        if (statusGroup.size() == 0) {
            return CacheStatusType.PENDING;
        }
        if (CacheStatusType.isSame(statusGroup)) {
            return statusGroup.iterator().next();
        } else if (statusGroup.contains(CACHING)) {
            return CACHING;
        }
        return CacheStatusType.PARTICALLY_CACHED;
    }

    private static boolean isSame(Set<CacheStatusType> statusGroup) {

        if (statusGroup.size() == 1) {
            return true;
        }
        return false;

    }

}
