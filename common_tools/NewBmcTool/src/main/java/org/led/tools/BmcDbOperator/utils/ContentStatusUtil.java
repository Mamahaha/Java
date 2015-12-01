package org.led.tools.BmcDbOperator.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.led.tools.BmcDbOperator.common.BmcLogModule;
import org.led.tools.BmcDbOperator.common.BmcLogger;
import org.led.tools.BmcDbOperator.common.ContentPostStatusChangeUtil;
import org.led.tools.BmcDbOperator.data.BDCEventType;
import org.led.tools.BmcDbOperator.entity.Cache;
import org.led.tools.BmcDbOperator.entity.CacheStatusType;
import org.led.tools.BmcDbOperator.entity.Content;
import org.led.tools.BmcDbOperator.entity.ContentStatusType;
import org.led.tools.BmcDbOperator.entity.DeviceCacheInfo;
import org.led.tools.BmcDbOperator.entity.DeviceContentInfo;
import org.led.tools.BmcDbOperator.entity.DeviceDeliverySessionInstanceInfo;
import org.led.tools.BmcDbOperator.entity.OnRequest;
import org.led.tools.BmcDbOperator.entity.UserServiceInstance;


public final class ContentStatusUtil {

    private static PersistenceService persistencyService;
    
    private ContentStatusUtil(){}

    public static void setPersistenceService(PersistenceService persistenceService) {
        ContentStatusUtil.persistencyService = persistenceService;

    }

    /**
     * Update all cache status under broadcast call by broadcast BL trigger
     * 
     * @param serviceName
     * @param broadcastName
     * @param USI
     *            list
     * @param Cache
     *            status
     * @see CacheStatusType
     * 
     */
    public static void updateCacheStatus(String serviceName, String broadcastName, List<UserServiceInstance> userServiceInstanceList,
            CacheStatusType pendingStatus) {

        for (UserServiceInstance userServiceInstance : userServiceInstanceList) {

            OnRequest onrequest = persistencyService.getOnRequest(serviceName, broadcastName, userServiceInstance.getName());
            List<Cache> cacheList = persistencyService.getCaches(onrequest);

            for (Cache cache : cacheList) {
                persistencyService.updateCacheStatus(cache, pendingStatus);
                List<Long> deviceDsiPidList = persistencyService.getDeviceDeliverySessionInstancePIdsbyUSI(userServiceInstance);

                for (Long deviceDsiId : deviceDsiPidList) {
                    persistencyService.updateDeviceCacheStatus(cache, deviceDsiId, pendingStatus);
                }
            }
        }

    }

    /**
     * Update per bdc cache status when receive the event with file url dlieveryinstance id and event type And then update the cache status by bdc device cache
     * status
     * 
     * @param bdcName
     * @param deliveryInstanceId
     * @param fileURI
     * @param eventType
     * @see CacheStatusType
     * 
     */
    public static void updateCacheStatus(String bdcName, Long deliveryInstanceId, String fileURI, BDCEventType eventType) {
        long deviceDsiId = persistencyService.getDeviceDsiPid(deliveryInstanceId, bdcName);

        if (deviceDsiId == -1) {
            BmcLogger.eventWarn(BmcLogModule.BLCONTENT,
                    "Update cache status...failed(No deviceDsiId found)[fileUrl=%s,DeliveryInstanceId=%d,BdcName=%s,EventType=%s]", fileURI,
                    deliveryInstanceId, bdcName, eventType.name());
            return;
        }
        DeviceDeliverySessionInstanceInfo deviceInfo = persistencyService.getDeviceDeliverySessionInfoById(deviceDsiId);
        OnRequest onRequest = null;
        if (deviceInfo.getUserServiceInstance() != null) {
            onRequest = persistencyService.getOnRequest(deviceInfo.getUserServiceInstance());
        }

        if (onRequest == null) {
            BmcLogger.eventWarn(BmcLogModule.BLCONTENT,
                    "Update cache status...failed(No onrequest found)[fileUrl=%s,DeliveryInstanceId=%d,BdcName=%s,EventType=%s]", fileURI, deliveryInstanceId,
                    bdcName, eventType.name());
            return;
        }

        // get cache entity by file url and onrequest
        List<Cache> cacheList = persistencyService.getCaches(onRequest);

        Cache cacheToUpdate = null;
        for (Cache cache : cacheList) {
            if (cache.getFileUri().equals(fileURI)) {
                cacheToUpdate = cache;
                break;
            }
        }

        if (cacheToUpdate == null) {
            return;
        }

        persistencyService.updateDeviceCacheStatus(cacheToUpdate, deviceDsiId, CacheStatusType.getStatus(eventType));

        updateCacheStatusWithDeviceCacheInfoStatus(cacheToUpdate, onRequest);

        ContentPostStatusChangeUtil.postCacheChange(cacheToUpdate);
    }

    /**
     * Update bdc cache status for cache list under same onrequest content call after send cache operation to BDC And then update the cache status by bdc device
     * cache status
     * 
     * @param bdcName
     * @param deliveryInstanceId
     * @param fileURI
     * @param eventType
     * @see CacheStatusType
     * 
     */
    public static void updateCacheStatus(long bdcId, List<Cache> cacheList, CacheStatusType status) {
        UserServiceInstance usi = null;
        OnRequest onrequest = null;
        for (Cache cache : cacheList) {
            if (usi == null) {
                onrequest = cache.getOnRequest();
                if (onrequest != null) {
                    usi = onrequest.getUserServiceInstance();
                }
            }

            if (usi == null) {
                BmcLogger.eventWarn(BmcLogModule.BLCONTENT, "Update cache status...failed(No onrequest found)[fileUrl=%s,BdcId=%d]", cache.getFileUri(), bdcId);
                return;
            } else {
                List<DeviceDeliverySessionInstanceInfo> deviceDsiIdList = persistencyService.getDeviceDeliverySessionInfoListbyUSI(usi);

                for (DeviceDeliverySessionInstanceInfo deviceDsiId : deviceDsiIdList) {
                    if (bdcId == deviceDsiId.getBdc().getId()) {
                        persistencyService.updateDeviceCacheStatus(cache, deviceDsiId.getId(), status);
                    }
                }

                updateCacheStatusWithDeviceCacheInfoStatus(cache, onrequest);

            }
        }

    }

    /**
     * Update the cache status by aggregate bdc device cache status
     * 
     * @param cache
     * @param onrequest
     * 
     */
    public static void updateCacheStatusWithDeviceCacheInfoStatus(Cache cache, OnRequest onrequest) {
        // caculate the status in device info
        CacheStatusType statusForCache = CacheStatusType.PENDING;
        List<DeviceCacheInfo> deviceCacheInfoList = persistencyService.getDeviceCacheInfoList(cache);
        Set<CacheStatusType> statusGroup = new HashSet<CacheStatusType>();
        for (DeviceCacheInfo detail : deviceCacheInfoList) {
            statusGroup.add(detail.getStatus());
        }

        long cacheNum = persistencyService.getDeviceCacheInfoCount(cache, onrequest.getId());
        List<DeviceDeliverySessionInstanceInfo> dsiDeviceList = persistencyService.getDeviceDeliverySessionInfoListbyUSI(onrequest.getUserServiceInstance());
        long deliverySessionNum = dsiDeviceList.size();

        if (cacheNum != deliverySessionNum) {
            // apply lost device cache status failed.
            statusGroup.add(CacheStatusType.CACHED_FAILD);
        }
        statusForCache = CacheStatusType.getStatus(statusGroup);
        persistencyService.updateCacheStatus(cache, statusForCache);

    }

    /**
     * Update the cache status with cache
     * 
     * @param cache
     * 
     */
    public static void updateCacheStatus(Cache cache) {
        persistencyService.updateCacheStatus(cache, cache.getStatus());

    }

    public static void updateContentStatus(String bdcName, Long deliveryInstanceId, long contentId, BDCEventType eventType) {
        //BmcLogger.eventDebug(BmcLogModule.SVCEVENT, ContentStatusUtil.class.toString(),"getDeviceDsiPid for bdc: %s, deliveryInstanId: %s, contentId: %s, eventType%s", bdcName, deliveryInstanceId, contentId, eventType.toString());    
        long deviceDsiId = persistencyService.getDeviceDsiPid(deliveryInstanceId, bdcName);
        if (deviceDsiId == -1) {
            BmcLogger.eventWarn(BmcLogModule.BLCONTENT,
                    "Content[contentId=%s,DeliveryInstanceId=%d,BdcName=%s,EventType=%s] status update......failed(not found deviceDsiId)", contentId,
                    deliveryInstanceId, bdcName, eventType.name());
            return;
        }
        //BmcLogger.eventDebug(BmcLogModule.SVCEVENT, ContentStatusUtil.class.toString(),"getDeviceDeliverySessionInfoById for bdc: %s, deliveryInstanId: %s, contentId: %s, eventType%s", bdcName, deliveryInstanceId, contentId, eventType.toString());    
        DeviceDeliverySessionInstanceInfo deviceInfo = persistencyService.getDeviceDeliverySessionInfoById(deviceDsiId);
        OnRequest onRequest = null;
        if (deviceInfo.getUserServiceInstance() != null) {
            onRequest = persistencyService.getOnRequest(deviceInfo.getUserServiceInstance());
        }

        if (onRequest == null) {
            BmcLogger.eventWarn(BmcLogModule.BLCONTENT,
                    "Content[contentId=%s,DeliveryInstanceId=%d,BdcName=%s,EventType=%s] status update......failed(not found onrequest)", contentId,
                    deliveryInstanceId, bdcName, eventType.name());
            return;
        }
        //BmcLogger.eventDebug(BmcLogModule.SVCEVENT, ContentStatusUtil.class.toString(),"getContentDeliveryByOnRequest for bdc: %s, deliveryInstanId: %s, contentId: %s, eventType%s", bdcName, deliveryInstanceId, contentId, eventType.toString());   
        // get cache entity by file url and onrequest
        List<Content> contentList = persistencyService.getContentDeliveryByOnRequest(onRequest);

        for (Content content : contentList) {
            if (content.getContentId() == contentId) {
                //BmcLogger.eventDebug(BmcLogModule.SVCEVENT, ContentStatusUtil.class.toString(),"updateDeviceContentStatus for bdc: %s, deliveryInstanId: %s, contentId: %s, eventType%s", bdcName, deliveryInstanceId, contentId, eventType.toString());   
                persistencyService.updateDeviceContentStatus(content, deviceDsiId, ContentStatusType.getStatus(eventType));
                //BmcLogger.eventDebug(BmcLogModule.SVCEVENT, ContentStatusUtil.class.toString(),"updateContentStatusWithDeviceContentInfoStatus for bdc: %s, deliveryInstanId: %s, contentId: %s, eventType%s", bdcName, deliveryInstanceId, contentId, eventType.toString()); 
                updateContentStatusWithDeviceContentInfoStatus(content, onRequest);
                //BmcLogger.eventDebug(BmcLogModule.SVCEVENT, ContentStatusUtil.class.toString(),"updateContentStatus finished for bdc: %s, deliveryInstanId: %s, contentId: %s, eventType%s", bdcName, deliveryInstanceId, contentId, eventType.toString());
                ContentPostStatusChangeUtil.postContentChange(content);
                break;
            }
        }
    }

    public static void updateContentStatusWithDeviceContentInfoStatus(Content content, OnRequest onrequest) {
        List<DeviceContentInfo> deviceContentInfoList = persistencyService.getDeviceContentInfoList(content);
        Set<ContentStatusType> statusGroup = new HashSet<ContentStatusType>();
        for (DeviceContentInfo detail : deviceContentInfoList) {
            statusGroup.add(detail.getStatus());
        }

        long contentNum = deviceContentInfoList.size();
        List<DeviceDeliverySessionInstanceInfo> dsiDeviceList = persistencyService.getDeviceDeliverySessionInfoListbyUSI(onrequest.getUserServiceInstance());
        long deliverySessionNum = dsiDeviceList.size();

        if (contentNum != deliverySessionNum) {
            // apply lost device content status failed.
            statusGroup.add(ContentStatusType.DELIVERY_FAILED);
        }
        persistencyService.updateContentStatus(content, ContentStatusType.getStatus(statusGroup));

    }

    public static void updateContentStatus(long bdcId, Content content, ContentStatusType status) {
        UserServiceInstance usi = null;
        OnRequest onrequest = content.getOnRequest();
        if (onrequest != null) {
            usi = onrequest.getUserServiceInstance();
        }
        if (usi == null) {
            BmcLogger.eventWarn(BmcLogModule.BLCONTENT, "Content status update...failed(Not found onrequest)[contentId=%s,BdcId=%d]", content.getContentId(),
                    bdcId);
            return;
        }
        List<DeviceDeliverySessionInstanceInfo> deviceDsiIdList = persistencyService.getDeviceDeliverySessionInfoListbyUSI(usi);

        for (DeviceDeliverySessionInstanceInfo deviceDsiId : deviceDsiIdList) {
            if (bdcId == deviceDsiId.getBdc().getId()) {
                persistencyService.updateDeviceContentStatus(content, deviceDsiId.getId(), status);
            }
        }
        updateContentStatusWithDeviceContentInfoStatus(content, onrequest);
    }

}
