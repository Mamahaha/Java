package org.led.tools.bmcDbOperator.utils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;







import org.led.tools.bmcDbOperator.common.BDCEvent;
import org.led.tools.bmcDbOperator.common.MWConfigObject;
import org.led.tools.bmcDbOperator.data.BDCEventType;
import org.led.tools.bmcDbOperator.data.SearchQuery;
import org.led.tools.bmcDbOperator.entity.ADF;
import org.led.tools.bmcDbOperator.entity.ActiveAlarm;
import org.led.tools.bmcDbOperator.entity.AdpdServiceUri;
import org.led.tools.bmcDbOperator.entity.AlarmRemedyText;
import org.led.tools.bmcDbOperator.entity.AlarmText;
import org.led.tools.bmcDbOperator.entity.AreaMapping;
import org.led.tools.bmcDbOperator.entity.BDC;
import org.led.tools.bmcDbOperator.entity.Bearer;
import org.led.tools.bmcDbOperator.entity.BmcParameter;
import org.led.tools.bmcDbOperator.entity.BmcRole;
import org.led.tools.bmcDbOperator.entity.BmcUser;
import org.led.tools.bmcDbOperator.entity.BmcUserSession;
import org.led.tools.bmcDbOperator.entity.Broadcast;
import org.led.tools.bmcDbOperator.entity.BroadcastArea;
import org.led.tools.bmcDbOperator.entity.CDN;
import org.led.tools.bmcDbOperator.entity.Cache;
import org.led.tools.bmcDbOperator.entity.CacheStatusType;
import org.led.tools.bmcDbOperator.entity.Content;
import org.led.tools.bmcDbOperator.entity.ContentStatusType;
import org.led.tools.bmcDbOperator.entity.Continuous;
import org.led.tools.bmcDbOperator.entity.DeviceCacheInfo;
import org.led.tools.bmcDbOperator.entity.DeviceContentInfo;
import org.led.tools.bmcDbOperator.entity.DeviceContinuousInfo;
import org.led.tools.bmcDbOperator.entity.DeviceDeliverySessionInstanceInfo;
import org.led.tools.bmcDbOperator.entity.ENodeB;
import org.led.tools.bmcDbOperator.entity.ENodeBGroup;
import org.led.tools.bmcDbOperator.entity.FECTemplate;
import org.led.tools.bmcDbOperator.entity.FRTemplate;
import org.led.tools.bmcDbOperator.entity.FileSchedule;
import org.led.tools.bmcDbOperator.entity.Frequency;
import org.led.tools.bmcDbOperator.entity.Gateway;
import org.led.tools.bmcDbOperator.entity.HistoryAlarm;
import org.led.tools.bmcDbOperator.entity.MWConfigContent;
import org.led.tools.bmcDbOperator.entity.MWConfigSchema;
import org.led.tools.bmcDbOperator.entity.MmeAddress;
import org.led.tools.bmcDbOperator.entity.Mpdis;
import org.led.tools.bmcDbOperator.entity.NotificationURL;
import org.led.tools.bmcDbOperator.entity.OccupiedPipe;
import org.led.tools.bmcDbOperator.entity.OnRequest;
import org.led.tools.bmcDbOperator.entity.OperationHistory;
import org.led.tools.bmcDbOperator.entity.OriginPipeResource;
import org.led.tools.bmcDbOperator.entity.PipeResource;
import org.led.tools.bmcDbOperator.entity.ProvisionConfig;
import org.led.tools.bmcDbOperator.entity.QoeMetrics;
import org.led.tools.bmcDbOperator.entity.RRTemplate;
import org.led.tools.bmcDbOperator.entity.ReportingMetadata;
import org.led.tools.bmcDbOperator.entity.SDCHService;
import org.led.tools.bmcDbOperator.entity.SDPInfo;
import org.led.tools.bmcDbOperator.entity.SaiEnodebMapping;
import org.led.tools.bmcDbOperator.entity.Service;
import org.led.tools.bmcDbOperator.entity.ServiceArea;
import org.led.tools.bmcDbOperator.entity.ServiceClass;
import org.led.tools.bmcDbOperator.entity.TMGI;
import org.led.tools.bmcDbOperator.entity.TmgiRange;
import org.led.tools.bmcDbOperator.entity.UserAlarmSearchCriteria;
import org.led.tools.bmcDbOperator.entity.UserService;
import org.led.tools.bmcDbOperator.entity.UserServiceInstance;
import org.led.tools.bmcDbOperator.entity.UserServiceInstanceOperType;


public interface PersistenceService {

    // BDC
    void addBDC(BDC bdc);

    BDC getBDCByName(String bdcName);

    BDC getBDCByNameIgnoreCase(String name);

    List<BDC> getAllBdcList();

    List<BDC> searchBdcList(SearchQuery query);

    void updateBDC(BDC bdc);

    Set<BDC> getBDCListByBroadcastArea(String broadcastAreaName);

    Set<ServiceArea> getServiceAreaListByBroadcastArea(String broadcastAreaName);

    // ADF
    void addADF(ADF adf);

    List<ADF> getADFs();

    void deleteADFByName(String adfName);

    void deleteADFById(Long adfId);

    void updateADF(ADF adf);

    void deleteADFByServerNameAndBDCName(String adfName, String bdcName);

    // CDN
    void addCDN(CDN cdn);

    List<CDN> getCDNs();

    void deleteCDNByName(String cdnName);

    void updateCDN(CDN cdn);

    void persistenceTmgiRanges(List<TmgiRange> tmgiRangeToAdd, List<TmgiRange> tmgiRangeToUpdate, List<TmgiRange> tmgiRangeToDelete);

    List<TmgiRange> getTmgiRanges();

    // ServiceClass
    void addServiceClass(ServiceClass sc);

    void updateServiceClass(ServiceClass sc);

    void deleteServiceClass(String name);

    List<ReportingMetadata> getReportingMetadatas();

    List<ReportingMetadata> getReportingMetadata(String serviceName, String broadcastName);

    List<ReportingMetadata> getOldestReportingMetadata(int num);

    void deleteReportingMetadata(long metadataId);

    long getTotalNumServiceClass();

    ServiceClass getServiceClass(String name);

    ServiceClass getServiceClassIgnoreCase(String name);

    // ServiceArea
    ServiceArea getServiceArea(String serviceAreaName);

    ServiceArea getServiceArea(long serviceAreaId);

    List<ServiceArea> getServiceAreaSiblings(BigDecimal lowerleftlatitude, BigDecimal lowerleftlongitude, BigDecimal upperrightlatitude,
            BigDecimal upperrightlongitude, int limit);

    void addServiceArea(ServiceArea serviceArea);

    void updateServiceArea(ServiceArea serviceArea);

    void deleteServiceArea(long serviceAreaId);

    void updateUserServiceInstances(List<UserServiceInstance> userServiceInstances);

    void updateUserServiceInstance(UserServiceInstance userServiceInstance);

    List<UserServiceInstance> getUserServiceInstanceByServiceAreaId(long serviceAreaId);

    List<UserServiceInstance> getUserServiceInstancesByUserService(UserService userService);
    
    List<UserServiceInstance> getUserServiceInstanceByTmgi(String tmgi);

    void updateUserServiceInstanceVersion(UserServiceInstance userServiceInstance);

    List<Service> getServiceByServiceAreaId(long serviceAreaId);

    // BroadcastArea
    long getTotalNumBroadcastArea();

    BroadcastArea getBroadcastArea(String broadcastAreaName);

    BroadcastArea getBroadcastAreaIgnoreCase(String broadcastAreaName);

    void addBroadcastArea(BroadcastArea broadcastArea);

    void updateBroadcastArea(BroadcastArea broadcastArea);

    // Service

    List<Service> getServiceList(List<String> nameList);

    List<String> getServiceNameList(List<String> scNameList);

    void createService(Service service, Map<Bearer, List<UserService>> map);

    void createServiceWhenApproveBroadcast(Service service, Map<Bearer, List<UserServiceInstance>> map);

    Service getService(String serviceName);

    Service getServiceIgnoreCase(String serviceName);

    List<Service> getServiceList(SearchQuery restful);

    Long getTotalService(SearchQuery restful);

    // Bearer
    Bearer getBearer(String serviceName, String bearerName);

    List<Bearer> getBearersByServiceName(String serviceName);

    // UserService
    UserService getUserService(String serviceName, String bearerName, String userServiceName);

    Broadcast getBroadcastWithUserServiceInstance(String serviceName, String broadcastName);

    Broadcast getBroadcastWithUserServiceInstanceAndSas(String serviceName, String broadcastName);

    Long getEntityTotal(Class<?> entityClazz, SearchQuery restful);

    List<Broadcast> getBroadcastList(SearchQuery restful);

    void addBroadcast(Broadcast broadcast);

    List<Broadcast> getBroadcastList(List<String> serviceNameList);

    void updateBroadcastUsiList(Broadcast broadcast);

    void updateBroadcastWithNotificationUrls(Broadcast broadcast, List<NotificationURL> newUrlList);

    void updateBroadcastWithOperState(Broadcast broadcast, String operState);

    void updateBroadcastWithOperStateAndExpiredTime(Broadcast broadcast, String adminState, Date expiredTime);

    void persistBroadcastMetaDataAndOperState(Broadcast broadcast, String operState, String metaData);

    void deleteBroadcast(String serviceName, String broadcastName);

    UserServiceInstance getUserServiceInstanceByDSI(long deliverySessionInstanceId);

    UserServiceInstance getUserServiceInstance(Broadcast broadcast, String userServiceInstanceName);

    UserServiceInstance getUserServiceInstance(String serviceName, String broadcastName, String userServiceInstanceName);

    UserServiceInstance getUserServiceInstancesById(long userServiceInstanceId);

    List<UserServiceInstance> getUserServiceInstancesByBroadcast(Broadcast broadcast);

    List<UserServiceInstance> getUserServiceInstancesByBroadcast(String serviceName, String broadcastName);

    // DeviceSessionInfo
    DeviceDeliverySessionInstanceInfo getDeviceDeliverySessionInfo(UserServiceInstance userServiceInstance, BDC bdc);

    List<DeviceDeliverySessionInstanceInfo> getDeviceDeliverySessionInfosbyEmbmsSessionIdAndBDC(long embmsSessionId, BDC bdc);

    List<Long> getDeviceDeliverySessionInstanceIdsbyUSI(UserServiceInstance userServiceInstance);

    List<Long> getDeviceDeliverySessionInstancePIdsbyUSI(UserServiceInstance userServiceInstance);

    void updateDeviceDeliverySessionInfo(DeviceDeliverySessionInstanceInfo deviceDeliverySessionInstanceInfo);

    List<String> getDeviceDeliverySessionInfoOperStateListByUsi(UserServiceInstance usi);

    Continuous getContinuousByUserServiceInstance(UserServiceInstance userServiceInstance);

    Continuous getContinuous(String serviceName, String broadcastName, String userServiceInstanceName);

    // Mpdis
    void addMpdis(Mpdis mpdis);

    List<Mpdis> getMpdisByContinuous(Continuous continuous);

    void updateMpd(Mpdis mpdis);

    void updateMpdFile(Mpdis newMpdFile, UserServiceInstance userServiceInstance);

    void deleteMpdisByContinuousId(long continuousId);

    // Device continuous content status
    void addDeviceContinuousInfo(DeviceContinuousInfo deviceContinuousInfo);

    void deleteDeviceContinuousInfoByMpdid(long mpdId);

    List<DeviceContinuousInfo> getDeviceContinuousInfoListByMpd(Mpdis mpd);

    void updateDeviceContinuousInfo(List<DeviceContinuousInfo> continuousInfoList);

    OnRequest getOnRequest(String serviceName, String broadcastName, String userServiceInstanceName);

    OnRequest getOnRequest(UserServiceInstance userServiceInstance);

    // Cache
    void addCache(Cache cache);

    Cache getCache(long cacheId);

    List<Cache> getCaches(OnRequest onRequest);

    DeviceCacheInfo getDeviceCacheInfo(long cacheId, long deviceDsiId);

    // Content
    void addContent(Content content);

    List<Content> getContentDeliveryByOnRequest(OnRequest onRequest);

    List<Content> getContentDeliveryByInterval(Date fromTime, Date untilTime, Boolean byStopTime);

    List<Broadcast> getOngoingBroadcastByInterval(Date fromTime, Date untilTime, boolean byStopTime);

    List<Broadcast> getNotStartBroadcastByInterval(Date fromTime, Date untilTime, boolean byStopTime);

    List<Content> getStoppedButNotInFinalStatusContentDeliveryByInterval(Date fromTime, Date untilTime);

    List<Broadcast> getCancelledAndProcessingBroadcastList();

    // FileSchedule
    void addFileSchedule(FileSchedule fileSchedule);

    // DeviceContentInfo

    void addQoeMetrics(QoeMetrics qoeMetrics);

    void addFecTemplate(FECTemplate fecTemplate);

    void addFrTemplate(FRTemplate frTemplate);

    void addRrTemplate(RRTemplate rrTemplate);

    // FECTemplate
    FECTemplate getFECTemplateByTemplateName(String name);

    // FRTemplate
    FRTemplate getFRTemplateByTemplateName(String name);

    // RRTemplate
    RRTemplate getRRTemplateByTemplateName(String name);

    // Create Content
    void addOnRequestContent(List<Cache> cacheList, List<Content> contentSet, List<FileSchedule> fileScheduleList);

    // Event

    // for user
    void addOrUpdateUser(BmcUser user);

    void deleteUser(BmcUser user);

    BmcUser getUser(String username);

    BmcUser getUserIgnoreCase(String username);

    List<BmcUser> getUsers();

    Long getUserTotal();

    void updateUser(BmcUser user);

    List<BmcUser> searchUserList(SearchQuery query);

    Long searchUserTotal(SearchQuery query);

    List<BmcRole> getRoles();

    void addRole(BmcRole role);

    BmcRole getRole(String bmcRole);

    // BMC parameter
    void addBmcParameter(BmcParameter bmcParameter);

    List<BmcParameter> getBmcParameters();

    BmcParameter getBmcParameter(String name);

    void updateBmcParameter(BmcParameter bmcParameter);

    List<FileSchedule> getFileSchedulesByUSI(UserServiceInstance userServiceInstance);

    List<Mpdis> getMpdisByUSI(UserServiceInstance userServiceInstance);

    List<Mpdis> getMpdisByConditionBeforeDate(String condition, Date datetime);

    List<FileSchedule> getFileSchedulesByConditionBeforeDate(String condition, Date datetime);

    List<UserServiceInstance> getUSIsByConditionBeforeDate(String condition, Date datetime);

    List<SDCHService> getBootstrapSDCHServices();

    UserServiceInstance getUSIBySDCHService(SDCHService sdchService);

    // For control fragment
    List<AdpdServiceUri> getAdpdServiceUrisByUSI(UserServiceInstance userServiceInstance, BDC bdc);

    void createSession(BmcUserSession userSession);

    void removeSession(String sessionId);

    void removeSessionByUserName(String userName);

    List<BmcUserSession> getSessionNotUpdateAfter(Date now);

    BmcUserSession getUserSession(String sessionId);

    void updateUserSession(BmcUserSession userSession);

    /**
     * @param parseLong
     */
    void deleteBroadcastArea(BroadcastArea parseLong);

    /**
     *
     * @param entityClazz
     *            The entity's class Object, like BroadcastArea.class.
     * @param offset
     *            Offset.It means get the entity list since which one.
     * @param pageSize
     *            PageSize.It means the max number of the entity list you get.
     * @param parameterMap
     *            Key is field,and value is parameter value.
     *
     *            <pre>
     * <b>Key must be the field in the entity class, not the field in DB.
     * For example,in BroadcastArea,there is a "regionType" field, not a "regiontype" which is in DB.</b>
     * </pre>
     * @param orderBy
     *            This must be a field in the entity class!Not the filed in DB!See above!Could be empty, like "".
     * @return In some case,maybe return null.
     */
    List<?> getPagingEntityList(Class<?> entityClazz, int offset, int pageSize, Map<String, String> parameterMap, String orderBy, String sortType);

    /**
     * Select a total number of the entities in your call, with some parameters for filtering.
     *
     * @param entityClazz
     *            The entity's class Object, like BroadcastArea.class.
     * @param parameterMap
     *            Key is field,and value is parameter value.
     *
     *            <pre>
     * <b>Key must be the field in the entity class, not the field in DB.
     * For example,in BroadcastArea,there is a "regionType" field, not a "regiontype" which is in DB.</b>
     * </pre>
     * @return
     */
    Long getTotalCount(Class<?> entityClazz, Map<String, String> parameterMap);

    /**
     * @return
     */
    List<AreaMapping> getAreaMapping();

    /**
     * @return
     */
    List<ServiceArea> getAllServiceAreaList();

    void approveBroadcast(String serviceName, Broadcast broadcast, List<DeviceDeliverySessionInstanceInfo> deviceDeliverySessionInfoList,
            List<AdpdServiceUri> adpdServiceUriList);

    void reprovisioningBroadcast(String serviceName, Broadcast broadcast, List<DeviceDeliverySessionInstanceInfo> deviceDeliverySessionInfoToUpdate,
            List<DeviceDeliverySessionInstanceInfo> deviceDeliverySessionInfoToRemove, List<UserServiceInstance> userServiceInstanceEntityList,
            List<AdpdServiceUri> adpdServiceUriList);

    List<Service> getServiceByBroadcastAreaName(String broadcastAreaName);

    void updateProvisionConfig(ProvisionConfig prov);

    void addProvisionConfig(ProvisionConfig prov);

    ProvisionConfig getProvisionConfig(String name);

    List<Bearer> getBearerByTMGI(String tmgiName);

    List<BroadcastArea> getBroadcastAreaListWithServiceAreasWithGivenSAI(long sai);

    void updateBroadcastAreaAdminState(BroadcastArea broadcastArea);

    SDPInfo getSDPInfoByMulticastIPAndPort(String ip, int destPort);

    void updateBroadcastAreaPolygonInfo(BroadcastArea broadcastArea);

    SDPInfo getSDPInfoByTsi(Long tsi);

    boolean isSDCHService(long serviceId);

    List<UserServiceInstance> getUserServiceInstanceByDeliverySessionId(long dsId);

    UserServiceInstance getUserServiceInstanceBySDPInfo(SDPInfo sdp);

    List<UserServiceInstance> getUserServiceInstanceByBroadcastAndDeliverySessionId(Broadcast broadcast, long dsId);

    QoeMetrics getQoeMetricsByTemplateName(String name);

    List<UserService> getUserServicesByBearerId(long bearerid);

    void deleteService(String serviceName, Map<Bearer, List<UserService>> bearerWithUS);

    List<Frequency> getFrequencies();

    void updateService(Service service, Map<Bearer, List<UserService>> map);

    void modifyOnRequestContent(OnRequest onRequest, List<Content> contents, List<FileSchedule> fileSchedules);

    void updateContentByPrimaryid(Content content);

    List<UserServiceInstance> getUserServiceInstanceByService(String serviceName);

    int generateContentCacheId(OnRequest onRequest);

    int generateContentDeliveryId(OnRequest onRequest);

    int generateContentFileScheduleId(OnRequest onRequest);

    List<ServiceArea> getServiceAreaListForGivenBDCName(String name);

    void deleteBDC(String bdcName);

    void addFrequency(Frequency frequency);

    void deleteFrequency(long id);

    void updateFrequency(Frequency frequency);

    List<FileSchedule> getFileSchedulesByCacheId(long id);

    void removeContentDelivery(long id);

    List<Content> getContentDeliveryByCacheId(long cacheId);

    void removeContentFileSchedule(long id);

    void deleteCachesAndDeviceCacheInfo(List<Cache> caches);

    List<FileSchedule> getFileSchedulesByOnRequest(OnRequest onRequest);

    void cancelFileSchedule(long id);

    List<Frequency> getFrequenciesByDefault(boolean isDefault);

    List<ServiceArea> getServiceAreaListByIsImplicitFrequency(boolean isImplicitFrequency);

    List<ServiceArea> getServiceAreaListByIsImplicitFrequencyAndFrequencyName(boolean isImplicitFrequency, String frequencyName);

    List<OnRequest> getOnRequestByFecId(long fecId);

    List<OnRequest> getOnRequestByFrId(long frId);

    List<OnRequest> getOnRequestByRrId(long rrId);

    List<Continuous> getContinuousByFecId(long fecId);

    List<Continuous> getContinuousByRrId(long rrId);

    List<Continuous> getContinuousByMediaUri(String mediaUri);

    List<UserService> getUserServiceByQoeMatricsId(long qoeMatricsId);

    Long getTotalQoeMetricsTemplate(SearchQuery restful);

    void updateFecTemplate(FECTemplate fecTemplate);

    Long getTotalFecTemplate(SearchQuery restful);

    void updateQoeTemplate(QoeMetrics qoeTemplate);

    List<QoeMetrics> getQoeMetricsList(SearchQuery restful);

    List<FECTemplate> getFECTemplateList(SearchQuery restful);

    void removeFileSchedule(FileSchedule fileSchedule);

    void deleteQoeTemplate(String qoeName);

    Broadcast getBroadcast(String serviceName, String broadcastName);

    Broadcast isBroadcastExistedIgnoreCaseOfBroadcastName(String serviceName, String broadcastName);

    List<Broadcast> getBroadcastByInterval(Date fromTime, Date untilTime, Boolean byStopTime);

    Broadcast getBroadcastByBroadcastUid(long id);

    List<Content> getJustStopContentList(Date mayBeCrashTime, Date utcTimeNow);

    List<Content> getAlreadyStartContentList(Date sysStartTime);

    void deleteFecTemplate(String fecName);

    FECTemplate getFECTemplateByTemplateNameIgnoreCase(String name);

    QoeMetrics getQoeMetricsByTemplateNameIgnoreCase(String name);

    void stopBroadcast(Broadcast broadcast, List<UserServiceInstance> userServiceInstanceEntityList,
            List<DeviceDeliverySessionInstanceInfo> deletedDeviceDeliverySessionInstanceInfoList,
            List<DeviceDeliverySessionInstanceInfo> updatedDeviceDeliverySessionInstanceInfoList);

    FRTemplate getFRTemplateByTemplateNameIgnoreCase(String name);

    RRTemplate getRRTemplateByTemplateNameIgnoreCase(String name);

    DeviceDeliverySessionInstanceInfo getDeviceDeliverySessionInstanceInfoById(long deviceDeliverySessionInstanceId);

    List<DeviceDeliverySessionInstanceInfo> getDeviceDeliverySessionInstanceInfoListByUsi(UserServiceInstance userServiceInstance);

    void updateContentStatus(Content content);

    void updateContinuousStatus(long deliveryInstanceId, String bdcName, BDCEventType eventType);

    long getDeviceDsiPid(long deliverInsance, String bdcName);

    DeviceDeliverySessionInstanceInfo getDeviceDeliverySessionInstanceInfoByEvent(BDCEvent event);

    List<RRTemplate> getRRTemplateList(SearchQuery restful);

    Long getTotalRRTemplate(SearchQuery restful);

    void updateRrTemplate(RRTemplate rrTemplate);

    Long getTotalFrTemplate(SearchQuery restful);

    List<FRTemplate> getFRTemplateList(SearchQuery restful);

    void deleteFrTemplate(String frName);

    void updateFrTemplate(FRTemplate frTemplate);

    void deleteRrTemplate(String rrName);

    CacheStatusType getDeviceCacheStatus(long deviceDsiId, Cache cache);

    void deleteCachesPerBDC(long usiId, long bdcId, List<Cache> caches);

    Long getBroadcastCountsUnderService(String serviceName);

    void cancelBroadcast(String serviceName, String broadcastName, List<UserServiceInstance> userServiceInstanceEntityList, Date expireTime);

    ContentStatusType getContentDeliveryStatus(Long contentId, Long deliveryInstanceId);

    List<ServiceClass> getServiceClassList(SearchQuery searchQuery);

    Long getTotalServiceClass(SearchQuery restful);

    List<BroadcastArea> getBroadcastAreaList(SearchQuery query);

    Long getTotalBroadcastArea(SearchQuery searchQuery);

    List<Broadcast> getAlreadyStartBroadcastList();

    List<Broadcast> getWillStartBroadcastList(int startTimeOffsetInSecond);

    List<Content> getWillStartContentList(int contentStartTimeOffsetInSecond);

    List<Content> getOngoingContentList();

    /**
     * get content by record id (note: id is not contentId)
     *
     * @param id
     * @return
     */
    Content getContentDeliveryByPrimaryKeyId(long id);

    CacheStatusType getCacheStatus(String fileURI, Long deliveryInstanceId);

    String getServiceClassByValue(String serviceClass);

    List<SDCHService> getSDCHService();

    String getFECTemplateByOverhead(int overhead);

    void saveSDCHService(SDCHService sdchService);

    void deleteSDCHService(String serviceName);

    void deleteDeviceCacheInfo(long usiId, long bdcId, List<Cache> caches);

    long getDeviceCacheInfoCount(Cache cache, long onRequestIndex);

    void updateDeviceCacheStatus(Cache cache, long deviceDsiId, CacheStatusType cacheStatus);

    void updateCacheEstimatedTransTime(Cache cache, long estimatedTransTime);

    void updateCacheStatus(Cache cache, CacheStatusType cacheStatus);

    List<DeviceCacheInfo> getDeviceCacheInfoList(Cache cache);

    List<DeviceDeliverySessionInstanceInfo> getDeviceDeliverySessionInfoListbyUSI(UserServiceInstance userServiceInstance);

    DeviceDeliverySessionInstanceInfo getDeviceDeliverySessionInstanceInfobyUSIAndBdc(UserServiceInstance userServiceInstance, BDC bdc);

    DeviceDeliverySessionInstanceInfo getDeviceDeliverySessionInfoById(long deviceDsiId);

    List<ServiceArea> getAllServiceAreaWithMmeList();

    Content getContentDeliveryByContentIdAndDeliveryInstanceId(long contentId, long deliveryInstanceId, String bdcName);

    // SAI
    void saveGateway(List<Gateway> saveGateway, List<Gateway> deleteGateway);

    List<Gateway> getGateway();

    List<MmeAddress> getMmeAddress();

    void updateMmeAddress(List<MmeAddress> mmeAddressList);

    void updateDeviceContentStatus(Content content, long deviceDsiId, ContentStatusType status);

    List<DeviceContentInfo> getDeviceContentInfoList(Content content);

    void updateContentStatus(Content content, ContentStatusType status);

    Set<BDC> getBDCListByServiceArea(long serviceAreaId);

    List<BDC> getBDCListByGwAddress(String gwAddress);

    Gateway getGatewayByHost(String gwAddress);

    ServiceArea getServiceAreaWithMmeList(long serviceAreaId);

    List<ServiceArea> getServiceAreaByBDC(long bdcId);

    DeviceContentInfo getDeviceContentInfo(long id, long deliverySessionInstanceId);

    List<BDC> getBDCListFromDeviceDeliverySessionInfoByUserServiceAndStatus(UserService userService, String status);


    void updateDeliverySessionInstanceInfo(DeviceDeliverySessionInstanceInfo ddsi);
    void deleteUnusedMmeaddress(List<MmeAddress> unusedmmeAddressList);

    List<MmeAddress> getMmeAddressWithSai();

    Content getContentDelivery(OnRequest onRequest, long contentId);

    /**
     * delete
     *
     * @param contentRecordId
     * @param deviceDeliverySessionInstanceInfoRecordId
     */
    void deleteDeviceContentInfo(long contentRecordId, long deviceDeliverySessionInstanceInfoRecordId);

    /**
     * remove devicecontentinfo refered to given content
     *
     * @param content
     */
    void removeDeviceContentInfoList(Content content);

    /**
     * remove devicecontentinfo refered to given content list
     *
     * @param contents
     */
    void removeDeviceContentInfoByContentList(List<Content> contents);

    List<Service> getServicesbyServiceClass(ServiceClass sc);

    /**
     * @param id
     *            unique key
     */
    void deleteUserServiceInstance(long id);

    List<Bearer> getBearers();

    AlarmText getAlarmTextByText(String text);

    boolean isConflictedTmgiInDB(List<String> tmgiNameList);

    void addActiveAlarm(ActiveAlarm activeAlarm);

    void addHistoryAlarm(HistoryAlarm historyAlarm);

    ActiveAlarm getActiveAlarmByCombinedId(String moduleId, String errorCode, String resourceId, String origSourceIp);

    HistoryAlarm getHistoryAlarmById(long id);

    void deleteActiveAlarm(long id);

    List<Object> getTotalActiveAlarm(SearchQuery searchQuery);

    List<Object> getTotalHistoryAlarm(SearchQuery searchQuery);

    List<ActiveAlarm> getActiveAlarmList(SearchQuery searchQuery);

    List<HistoryAlarm> getHistoryAlarmList(SearchQuery searchQuery);

    ActiveAlarm getActiveAlarmById(long activeAlarmId);

    UserAlarmSearchCriteria getAlarmSearchCriteria(BmcUser user, int operationType);

    void deleteAlarmSearchCriteria(BmcUser user);

    void createAlarmSearchCriteria(UserAlarmSearchCriteria newSc);

    List<ActiveAlarm> getActiveAlarmByMutipleIds(List<Integer> activeAlarmId);

    void updateActiveAlarm(ActiveAlarm activeAlarm);

    void moveActiveAlarmToHistory(HistoryAlarm historyAlarm, long id);

    void addAlarmText(AlarmText alarmText);

    List<AlarmRemedyText> getAlarmRemedyText(String module, String errorCode);

    List<MWConfigContent> getMWConfigContent();

    List<MWConfigSchema> getMWConfigSchema();

    void updateMWConfig(boolean isContentUpdated);

    void updateMWConfigDeleteMWSchema(MWConfigObject mwConfigObject, boolean isContentUpdated);

    void updateMWConfigAddMWSchema(MWConfigObject mwConfigObject, boolean isContentUpdated);

    void updateMWConfigDeleteReAddMWSchema(MWConfigObject mwConfigObject, boolean isContentUpdated);

    void updateMWConfigUpdateMWSchema(MWConfigObject mwConfigObject, boolean isContentUpdated);

    public void storeOperationHistory(OperationHistory operhis);

    public List<OperationHistory> getOperHistory(String serviceName, String broadcastName);

    void deleteDeprecateHistoryAlarm(int days);

    void deleteDeprecateActiveAlarm(int days);

    void deleteUnUsedAlarmText();

    public List<PipeResource> getPipeByName(String pipeName);

    ENodeB getEnodeByName(String name);

    void updateEnodeB(ENodeB newEnodeB);

    void addEnodeB(ENodeB newEnodeB);

    List<Long> getSaiListByEnodebId(long id);

    void removeSaiEnodebMapping(List<Long> tmpOldSaiList, ENodeB oldEnodeB);

    void removeSaiEnodebMappingByENodeBId(long eNodeBId);

    void addSaiEnodebMapping(SaiEnodebMapping saiEnodebMapping);

    List<OriginPipeResource> getOriginPipeListByEnodebId(long id);

    void removeOriginPipeResourceByEnodebIdAndPipeId(long id, String pipeId);

    void removeOriginPipeResourceByEnodeBId(long eNodeBId);

    void addOriginPipeResource(OriginPipeResource p);

    void updateOriginPipeResource(OriginPipeResource p);

    List<PipeResource> getPipeResourcesByServiceAreas(Set<ServiceArea> serviceAreas);

    List<PipeResource> getPipeResourcesByServiceAreasAndPipeName(String pipeName, Set<ServiceArea> serviceAreas);

    List<ENodeB> getENodeBsByServiceAreas(Set<ServiceArea> serviceAreas);

    List<ENodeB> getENodeBsByServiceAreaID(long serviceAreaId);

    List<ENodeBGroup> getENodeBGroupByPipeNameAndENodeBNameList(String pipeName, List<String> eNodeBNameList);

    List<SaiEnodebMapping> getSaiENodeBMappingsByENodeBs(List<ENodeB> eNodeBs);

    OriginPipeResource getOriginPipeResource(String pipeName, ENodeB eNodeB);

    List<OccupiedPipe> getOccupiedPipes(Date broadcastStarttime, Date broadcastStopTime, Set<ServiceArea> serviceAreas, Collection<String> pipeNames);

    void addOccupiedPipe(OccupiedPipe occupiedPipe);

    int releasePipeAndTMGI(long bearerId, Date startTime, Date stopTime);

    List<String> getENBNameListBySai(Long sai);

    List<OriginPipeResource> getPipeByENBName(String name);

    PipeResource getPipeBySaiAndPipe(Long sai, String pipe);

    List<PipeResource> getPipeBySai(Long sai);

    List<OriginPipeResource> getOriginPipeResourceWithPipeAndEnodebName(String pipeName, List<String> eNodeBName);

    void addPipeResource(PipeResource p);

    void removePipeResource(PipeResource p);

    void updatePipeResource(PipeResource p);

    List<String> getAllInUsedTMGI();

    TMGI getTMGIByValue(String tmgiValue);

    void updateTMGI(TMGI tmgi);

    void removeOriginPipeResource(OriginPipeResource pipe);

    List<OccupiedPipe> getOccupiedPipesBySaiAndPipe(long sai, String pipeName);

    List<OccupiedPipe> getOccupiedPipesBySaiIdListAndPipeList(Date broadcastStarttime, Date broadcastStopTime, List<Long> newSaiList, Set<String> pipeSet);

    void addEnodeBGroup(ENodeBGroup newEnodeBGroup);

    ENodeBGroup getEnodeBGroup(String pipeName, float bandwidth, String saigroup);

    void removeEnodeBGroup(ENodeBGroup enodebGroup);

    void updateEnodeBGroup(ENodeBGroup enodebGroup);

    List<PipeResource> getPipeBySaiAndBandwidth(long sai, float bandwidth);

    List<PipeResource> getAvailablePipeList(SearchQuery query);

    void deleteUnusedTMGI();

    Frequency getFrequencyByEarfcn(int nEarfcn);
    List<ServiceArea> getServiceAreaByVpipe(boolean vpipe);

    List<String> getEricENBNameListBySai(Long sai);

    Long getAvailablePipeTotal(SearchQuery query);

    Long getTotalPipeList(SearchQuery restfulQuery);

    String getUserServiceMode(String serviceName, String bearerName, String usName);
    void deleteENodeB(ENodeB oldEnodeB);

    List<BroadcastArea> getBroadcastAreasByServiceAreas(Collection<Long> sais);

    Broadcast getBroadcastByOccupiedpipe(OccupiedPipe op);

    List<Mpdis> getMpdByActivitedUSI();
    
}
