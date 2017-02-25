/**
 *
 */
package org.led.tools.bmcDbOperator.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.led.tools.bmcDbOperator.common.BDCEvent;
import org.led.tools.bmcDbOperator.common.BmcLogModule;
import org.led.tools.bmcDbOperator.common.BmcLogger;
import org.led.tools.bmcDbOperator.common.CollectionUtils;
import org.led.tools.bmcDbOperator.common.StringUtils;
import org.led.tools.bmcDbOperator.common.TimeUtils;
import org.led.tools.bmcDbOperator.data.BDCEventType;
import org.led.tools.bmcDbOperator.data.RestfulQuery;
import org.led.tools.bmcDbOperator.data.SearchQuery;
import org.led.tools.bmcDbOperator.entity.ADF;
import org.led.tools.bmcDbOperator.entity.ActiveAlarm;
import org.led.tools.bmcDbOperator.entity.AdminStateType;
import org.led.tools.bmcDbOperator.entity.AdpdServiceUri;
import org.led.tools.bmcDbOperator.entity.AlarmRemedyText;
import org.led.tools.bmcDbOperator.entity.AlarmText;
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
import org.led.tools.bmcDbOperator.entity.Event;
import org.led.tools.bmcDbOperator.entity.FECTemplate;
import org.led.tools.bmcDbOperator.entity.FRTemplate;
import org.led.tools.bmcDbOperator.entity.FileSchedule;
import org.led.tools.bmcDbOperator.entity.Frequency;
import org.led.tools.bmcDbOperator.entity.Gateway;
import org.led.tools.bmcDbOperator.entity.HistoryAlarm;
import org.led.tools.bmcDbOperator.entity.LockAssist;
import org.led.tools.bmcDbOperator.entity.MWConfigContent;
import org.led.tools.bmcDbOperator.entity.MWConfigSchema;
import org.led.tools.bmcDbOperator.entity.MmeAddress;
import org.led.tools.bmcDbOperator.entity.Mpdis;
import org.led.tools.bmcDbOperator.entity.NotificationURL;
import org.led.tools.bmcDbOperator.entity.OccupiedPipe;
import org.led.tools.bmcDbOperator.entity.OnRequest;
import org.led.tools.bmcDbOperator.entity.OperStateType;
import org.led.tools.bmcDbOperator.entity.OperationHistory;
import org.led.tools.bmcDbOperator.entity.OriginPipeResource;
import org.led.tools.bmcDbOperator.entity.PipeLock;
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
import org.led.tools.bmcDbOperator.utils.ContentStatusUtil;
import org.led.tools.bmcDbOperator.utils.QueryBuilder;



/**
 */
/*
 * @NamedQueries ({
 *
 * @NamedQuery(name="",query=""),
 *
 * @NamedQuery(name="",query=""),
 *
 * @NamedQuery(name="",query=""), })
 */
public class DatabaseService {

    private static final String SUFFIX_COMMA = ",";

    private static final String JUDGE_ON_INTIME_SQL = " and lock.intime < :InTime and lock.intime is not null";

    private static final String AVOID_SELF_LOCK_EFFCTIVE = " and lock.key <> :LockKey";

    private static final String COULD_NOT_EXISTS_SQL = " lock.nodeValue = :NodeValue";

    private static final String UNDERLINE_STR = "_";

    private static final String NAME_STR = "name";

    private static final String SAI_STR = "sai";

    private static final String SERVICENAME_STR = "serviceName";

    private static final String PIPENAME_STR = "pipeName";

    private static final String SAIS_STR = "sais";

    private static final String BROADCAST_STR = "broadcast";

    private static final String STARTTIME_STR = "startTime";

    private static final String NOWTIME_STR = "nowTime";

    private static final String UNCHECKED_STR = "unchecked";

    private static final String CONTENTID_STR = "contentId";

    private static final String CACHEID_STR =  "cacheId";

    private static final String ONREQUEST_STR = "onRequest";

    private static final String USERSERVICEINSTANCE_STR = "userServiceInstance";

    private static final String BDC_STR = "bdc";

    private EntityManager em;

    public DatabaseService(EntityManager em) {
        this.em = em;
    }

    public void persistEntity(Object entity) {
        em.persist(entity);
    }

    public void persistAndFlush(Object entity) {
        em.persist(entity);
        em.flush();
    }

    public long getTotalNumBroadcastArea() {
        return em.createQuery("SELECT count(bc) FROM BroadcastArea as bc", Long.class)
                .getSingleResult().longValue();
    }

    public BroadcastArea getBroadcastArea(String broadcastAreaName) {
        TypedQuery<BroadcastArea> result = em
                .createQuery(
                        "SELECT DISTINCT bc FROM BroadcastArea as bc LEFT JOIN FETCH bc.sas WHERE bc.name = :name",
                        BroadcastArea.class).setParameter(NAME_STR, broadcastAreaName);
        return getSingleResult(result);
    }

    public BroadcastArea getBroadcastAreaIgnoreCase(String broadcastAreaName) {
        TypedQuery<BroadcastArea> result = em
                .createQuery(
                        "SELECT DISTINCT bc FROM BroadcastArea as bc LEFT JOIN FETCH bc.sas WHERE LOWER(bc.name) = :name",
                        BroadcastArea.class)
                .setParameter(NAME_STR, broadcastAreaName.toLowerCase());
        return getSingleResult(result);
    }

    public List<BroadcastArea> getAllBroadcastAreaList() {
        TypedQuery<BroadcastArea> result = em.createQuery(
                "SELECT DISTINCT bc FROM BroadcastArea as bc JOIN FETCH bc.sas",
                BroadcastArea.class);
        return getMultipleResult(result);
    }

    public List<BroadcastArea> getBroadcastAreaListWithServiceAreasForGivenSAI(long serviceAreaId) {
        TypedQuery<BroadcastArea> result = em
                .createQuery(
                        "SELECT DISTINCT ba FROM BroadcastArea as ba JOIN FETCH ba.sas JOIN ba.sas as sas WHERE sas.sai = :sai",
                        BroadcastArea.class).setParameter(SAI_STR, serviceAreaId);
        return getMultipleResult(result);
    }

    public List<ServiceArea> getAllServiceAreaWithMmeList() {
        TypedQuery<ServiceArea> result = em.createQuery(
                "SELECT sas FROM ServiceArea as sas order by sas.sai asc", ServiceArea.class);
        List<ServiceArea> serviceList = result.getResultList();
        loadMmeList(serviceList);
        return getMultipleResult(result);
    }

    private void loadMmeList(List<ServiceArea> serviceAreaList) {
        for (ServiceArea serviceArea : serviceAreaList) {
            serviceArea.getMmeAddressList();
        }
    }

    public ServiceArea getServiceAreaWithMmeList(long serviceAreaId) {
        TypedQuery<ServiceArea> result = em.createQuery(
                "SELECT sa FROM ServiceArea as sa WHERE sa.sai = :sai", ServiceArea.class)
                .setParameter(SAI_STR, serviceAreaId);
        List<ServiceArea> serviceList = result.getResultList();
        loadMmeList(serviceList);
        return getSingleResult(result);
    }

    public ServiceArea getServiceArea(String serviceAreaName) {
        TypedQuery<ServiceArea> result = em.createQuery(
                "SELECT sa FROM ServiceArea as sa WHERE sa.name = :name", ServiceArea.class)
                .setParameter(NAME_STR, serviceAreaName);
        return getSingleResult(result);
    }

    public ServiceArea getServiceAreaWithBDCs(String serviceAreaName) {
        TypedQuery<ServiceArea> result = em
                .createQuery(
                        "SELECT DISTINCT sa FROM ServiceArea as sa JOIN FETCH sa.bdcList WHERE sa.name = :name",
                        ServiceArea.class).setParameter(NAME_STR, serviceAreaName);
        return getSingleResult(result);
    }

    public ServiceArea getServiceArea(long serviceAreaId) {
        TypedQuery<ServiceArea> result = em.createQuery(
                "SELECT sa FROM ServiceArea as sa WHERE sa.sai = :sai", ServiceArea.class)
                .setParameter(SAI_STR, serviceAreaId);
        return getSingleResult(result);
    }

    public List<ServiceArea> getServiceAreaSiblings(BigDecimal lowerleftlatitude,
            BigDecimal lowerleftlongitude, BigDecimal upperrightlatitude,
            BigDecimal upperrightlongitude, int limit) {

        long resultCount = em
                .createQuery(
                        "SELECT COUNT(sa) FROM ServiceArea as sa WHERE sa.lowerLeftLatitude >= :lowerleftlatitude and sa.lowerLeftLongitude >= :lowerleftlongitude and sa.upperRightLatitude <= :upperrightlatitude and sa.upperRightLongitude <= :upperrightlongitude",
                        Long.class).setParameter("lowerleftlatitude", lowerleftlatitude)
                .setParameter("lowerleftlongitude", lowerleftlongitude)
                .setParameter("upperrightlatitude", upperrightlatitude)
                .setParameter("upperrightlongitude", upperrightlongitude).getSingleResult()
                .longValue();

        if (resultCount > 0) {

            if (resultCount > limit) {
                return new ArrayList<ServiceArea>();
            }

            TypedQuery<ServiceArea> result = em
                    .createQuery(
                            "SELECT sa FROM ServiceArea as sa WHERE sa.lowerLeftLatitude >= :lowerleftlatitude and sa.lowerLeftLongitude >= :lowerleftlongitude and sa.upperRightLatitude <= :upperrightlatitude and sa.upperRightLongitude <= :upperrightlongitude",
                            ServiceArea.class).setParameter("lowerleftlatitude", lowerleftlatitude)
                    .setParameter("lowerleftlongitude", lowerleftlongitude)
                    .setParameter("upperrightlatitude", upperrightlatitude)
                    .setParameter("upperrightlongitude", upperrightlongitude);
            result.setMaxResults(limit);
            return result.getResultList();
        } else {
            return new ArrayList<ServiceArea>();
        }
    }

    public List<ServiceArea> getServiceAreaListForGivenBDCName(String bdcName) {
        TypedQuery<ServiceArea> result = em
                .createQuery(
                        "SELECT DISTINCT sa FROM ServiceArea as sa JOIN FETCH sa.bdcList JOIN sa.bdcList as bdc WHERE bdc.name = :bdcName",
                        ServiceArea.class).setParameter("bdcName", bdcName);
        return getMultipleResult(result);
    }

    public List<UserServiceInstance> getUserServiceInstanceByServiceAreaId(long serviceAreaId) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT usi FROM UserServiceInstance as usi JOIN usi.broadcast.service.broadcastArea.sas as sas WHERE sas.sai = :sai",
                        UserServiceInstance.class).setParameter(SAI_STR, serviceAreaId);
        return getMultipleResult(result);
    }

    public List<UserServiceInstance> getUserServiceInstanceByService(String serviceName) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT usi FROM UserServiceInstance as usi JOIN usi.broadcast.service as service WHERE service.name = :serviceName",
                        UserServiceInstance.class).setParameter(SERVICENAME_STR, serviceName);
        return getMultipleResult(result);
    }
    
    public List<UserServiceInstance> getUserServiceInstancesByTmgi(String tmgi) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT usi FROM UserServiceInstance as usi WHERE usi.tmgi = :tmgi",
                        UserServiceInstance.class).setParameter("tmgi", tmgi);
        return getMultipleResult(result);
    }

    public List<Service> getServiceByServiceAreaId(long serviceAreaId) {
        TypedQuery<Service> result = em
                .createQuery(
                        "SELECT service FROM Service as service JOIN service.broadcastArea.sas as sas WHERE sas.sai = :sai",
                        Service.class).setParameter(SAI_STR, serviceAreaId);
        return getMultipleResult(result);
    }

    public long getTotalNumServiceClass() {
        return em.createQuery("SELECT count(sc) FROM ServiceClass as sc", Long.class)
                .getSingleResult().longValue();
    }

    public ServiceClass getServiceClass(String name) {
        TypedQuery<ServiceClass> result = em.createQuery(
                "SELECT sc FROM ServiceClass as sc WHERE sc.name = :name", ServiceClass.class)
                .setParameter(NAME_STR, name);
        return getSingleResult(result);
    }

    public ServiceClass getServiceClassIgnoreCase(String name) {
        TypedQuery<ServiceClass> result = em.createQuery(
                "SELECT sc FROM ServiceClass as sc WHERE LOWER(sc.name) = :name",
                ServiceClass.class).setParameter(NAME_STR, name.toLowerCase());
        return getSingleResult(result);
    }

    public Service getService(String serviceName) {
        TypedQuery<Service> result = em.createQuery(
                "SELECT s FROM Service as s WHERE s.name = :name", Service.class).setParameter(
                NAME_STR, serviceName);
        List<Service> serviceList = result.getResultList();
        loadServiceData(serviceList);
        return getSingleResult(result);
    }

    public Service getServiceIgnoreCase(String serviceName) {
        TypedQuery<Service> result = em.createQuery(
                "SELECT s FROM Service as s WHERE LOWER(s.name) = :name", Service.class)
                .setParameter(NAME_STR, serviceName.toLowerCase());
        List<Service> serviceList = result.getResultList();
        loadServiceData(serviceList);
        return getSingleResult(result);
    }

    private void loadServiceData(List<Service> serviceList) {
        for (Service service : serviceList) {
            List<Broadcast> broadcastList = service.getBroadcastList();
            for (Broadcast broadcast : broadcastList) {
                broadcast.getId();
            }
            service.getBearerList();
            List<Bearer> bearerList = service.getBearerList();
            for (Bearer bear : bearerList) {
                List<UserService> usList = bear.getUserService();
                for (UserService us : usList) {
                    us.getBearer();
                }
            }
        }
    }

    public List<Service> getServiceList(SearchQuery restfulQuery) {
        TypedQuery<Service> listQuery = QueryBuilder.getQueryForSearch(em, restfulQuery,
                Service.class);
        List<Service> serviceList = listQuery.getResultList();
        for (Service service : serviceList) {
            List<Broadcast> broadcastList = service.getBroadcastList();
            for (Broadcast broadcast : broadcastList) {
                broadcast.getId();
            }
            List<Bearer> bearerList = service.getBearerList();
            for (Bearer bear : bearerList) {
                List<UserService> usList = bear.getUserService();
                for (UserService us : usList) {
                    us.getBearer();
                }
            }
        }
        return serviceList;
    }

    public Long getTotalService(SearchQuery restfulQuery) {
        TypedQuery<Long> totalQuery = QueryBuilder
                .getQueryForTotal(em, restfulQuery, Service.class);
        return totalQuery.getSingleResult().longValue();

    }

    public Broadcast getBroadcast(String serviceName, String broadcastName) {
        TypedQuery<Broadcast> result = em
                .createQuery(
                        "SELECT bc FROM Broadcast as bc JOIN bc.service as s WHERE bc.name = :broadcast_name and s.name = :service_name",
                        Broadcast.class).setParameter("service_name", serviceName)
                .setParameter("broadcast_name", broadcastName);
        return getSingleResult(result);
    }

    public Broadcast isBroadcastExistedIgnoreCaseOfBroadcastName(String serviceName,
            String broadcastName) {
        TypedQuery<Broadcast> result = em
                .createQuery(
                        "SELECT bc FROM Broadcast as bc JOIN bc.service as s WHERE LOWER(bc.name) = LOWER(:broadcast_name) and s.name = :service_name",
                        Broadcast.class).setParameter("service_name", serviceName)
                .setParameter("broadcast_name", broadcastName);
        return getSingleResult(result);
    }

    public Broadcast getBroadcastWithUserServiceInstance(String serviceName, String broadcastName) {
        Broadcast broadcast = getBroadcast(serviceName, broadcastName);
        if (broadcast != null) {
            List<UserServiceInstance> usiList = broadcast.getUserServiceInstances();
            for (UserServiceInstance usi : usiList) {
                List<DeviceDeliverySessionInstanceInfo> ddsiList = usi
                        .getDeviceDeliverySessionInstanceInfos();
                for (DeviceDeliverySessionInstanceInfo ddsi : ddsiList) {
                    ddsi.getWebDavFolder();
                }
            }
        }
        return broadcast;
    }

    public Broadcast getBroadcastWithUserServiceInstanceAndSas(String serviceName,
            String broadcastName) {
        Broadcast broadcast = getBroadcastWithUserServiceInstance(serviceName, broadcastName);
        for (ServiceArea serviceArea : broadcast.getService().getBroadcastArea().getSas()) {
            serviceArea.getSai();
            serviceArea.getBas();
        }
        return broadcast;
    }

    /*
     * private <T> T getSingleResult(TypedQuery<T> result) { if (result.getResultList().size() != 0) { return result.getSingleResult(); } else { return null; }
     * }
     */

    private <T> T getSingleResult(TypedQuery<T> result) {
        List<T> list = result.getResultList();
        if (list.size() != 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    private <T> List<T> getMultipleResult(TypedQuery<T> result) {
        if (result.getResultList().size() > 0) {
            return result.getResultList();
        } else {
            return new ArrayList<T>();
        }
    }

    public Bearer getBearer(Service service, String bearerName) {
        TypedQuery<Bearer> result = em
                .createQuery(
                        "SELECT bearer FROM Bearer as bearer WHERE bearer.service = :service and bearer.name = :name",
                        Bearer.class);
        result.setParameter("service", service);
        result.setParameter(NAME_STR, bearerName);
        return getSingleResult(result);
    }

    public UserService getUserService(Bearer bearer, String userServiceName) {
        TypedQuery<UserService> result = em.createQuery(
                "SELECT us FROM UserService as us WHERE us.bearer = :bearer and us.name = :name",
                UserService.class);
        result.setParameter("bearer", bearer);
        result.setParameter(NAME_STR, userServiceName);
        return getSingleResult(result);
    }

    public List<Bearer> getBearerListByServiceName(String serviceName) {
        TypedQuery<Bearer> result = em.createQuery(
                "SELECT bearer FROM Bearer as bearer WHERE bearer.service.name = :serviceName",
                Bearer.class);
        result.setParameter(SERVICENAME_STR, serviceName);
        return getMultipleResult(result);
    }

    public UserService getUserServiceBydeliverySession(long deliverySessionId) {
        TypedQuery<UserService> result = em.createQuery(
                "SELECT us FROM UserService as us WHERE us.deliverSessionId = :deliverSessionId",
                UserService.class).setParameter("deliverSessionId", deliverySessionId);
        return getSingleResult(result);
    }

    public UserServiceInstance getUserServiceInstanceByDeliverySessionInstanceId(
            long deliverySessionInstanceId) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT usi FROM UserServiceInstance as usi WHERE usi.deliverySessionInstanceId = :deliverySessionInstanceId",
                        UserServiceInstance.class).setParameter("deliverySessionInstanceId",
                        deliverySessionInstanceId);
        return getSingleResult(result);
    }

    public UserServiceInstance getUserServiceInstanceBySDPInfo(SDPInfo sdp) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT usi FROM UserServiceInstance as usi WHERE usi.sdpInfo = :sdpInfo",
                        UserServiceInstance.class).setParameter("sdpInfo",
                        sdp);
        return getSingleResult(result);
    }

    public UserServiceInstance getUserServiceInstance(String serviceName, String broadcastName,
            String userServiceInstanceName) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT usi FROM UserServiceInstance as usi WHERE usi.name = :usiname AND usi.broadcast.name = :broadcastname AND usi.broadcast.service.name = :servicename",
                        UserServiceInstance.class);
        result.setParameter("usiname", userServiceInstanceName);
        result.setParameter("broadcastname", broadcastName);
        result.setParameter("servicename", serviceName);
        return getSingleResult(result);
    }

    public Continuous getContinuousByDeliverySessionInstanceId(long deliverySessionInstanceId) {
        TypedQuery<Continuous> result = em
                .createQuery(
                        "SELECT continuous FROM Continuous as continuous WHERE continuous.deliverySessionInstanceId = :deliverySessionInstanceId",
                        Continuous.class).setParameter("deliverySessionInstanceId",
                        deliverySessionInstanceId);
        return getSingleResult(result);
    }

    public Cache getCache(long id) {
        TypedQuery<Cache> result = em.createQuery(
                "SELECT cache FROM Cache as cache WHERE cache.id = :id", Cache.class).setParameter(
                "id", id);
        return getSingleResult(result);
    }

    public long getMaxContentId() {
        TypedQuery<Long> result = em.createQuery(
                "select max(content.contentId) from Content content", Long.class);
        if (result.getResultList().size() > 0) {
            return result.getSingleResult();
        } else {
            return 0;
        }
    }

    public Content getContentByContentUid(String uid) {
        TypedQuery<Content> result = em.createQuery(
                "SELECT content FROM Content as content WHERE content.uid = :uid", Content.class)
                .setParameter("uid", uid);
        return getSingleResult(result);
    }

    public Broadcast getBroadcastByBroadcastUid(long uid) {
        TypedQuery<Broadcast> result = em.createQuery(
                "SELECT broadcast FROM Broadcast as broadcast WHERE broadcast.id = :uid",
                Broadcast.class).setParameter("uid", uid);
        return getSingleResult(result);
    }

    public List<Broadcast> getAllBroadcastByAdminState(String adminState) {
        TypedQuery<Broadcast> result = em
                .createQuery(
                        "SELECT broadcast FROM Broadcast as broadcast WHERE broadcast.adminState = :adminState",
                        Broadcast.class).setParameter("adminState", adminState);
        return getMultipleResult(result);
    }

    public List<UserServiceInstance> getUserServiceInstancesByBroadcast(Broadcast broadcast) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT userServiceInstance FROM UserServiceInstance as userServiceInstance WHERE userServiceInstance.broadcast = :broadcast",
                        UserServiceInstance.class).setParameter(BROADCAST_STR, broadcast);
        return getMultipleResult(result);
    }

    public List<UserServiceInstance> getUserServiceInstancesByUserService(UserService userService) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT userServiceInstance FROM UserServiceInstance as userServiceInstance WHERE userServiceInstance.userService = :userService",
                        UserServiceInstance.class).setParameter("userService", userService);
        return getMultipleResult(result);
    }

    public List<Content> getContentByOnRequest(OnRequest onRequest) {
        TypedQuery<Content> result = em.createQuery(
                "SELECT content FROM Content as content WHERE content.onRequest = :onRequest",
                Content.class).setParameter(ONREQUEST_STR, onRequest);
        return getMultipleResult(result);
    }

    public List<Mpdis> getMpdisByContinuous(Continuous continuous) {
        TypedQuery<Mpdis> result = em.createQuery(
                "SELECT mpdis FROM Mpdis as mpdis WHERE mpdis.continuous = :continuous",
                Mpdis.class).setParameter("continuous", continuous);
        return getMultipleResult(result);
    }

    public Long getEntityTotal(Class<?> entityClazz, SearchQuery restfulQuery) {
        TypedQuery<Long> totalQuery = QueryBuilder.getQueryForTotal(em, restfulQuery, entityClazz);
        return totalQuery.getSingleResult().longValue();
    }

    public List<Broadcast> getBroadcastList(SearchQuery restfulQuery) {

        TypedQuery<Broadcast> listQuery = QueryBuilder.getQueryForSearch(em, restfulQuery,
                Broadcast.class);

        List<Broadcast> broadcastList = listQuery.getResultList();
        for (Broadcast broadcast : broadcastList) {
            broadcast.getUserServiceInstances();
            List<UserServiceInstance> usiList = broadcast.getUserServiceInstances();
            for (UserServiceInstance usi : usiList) {
                List<DeviceDeliverySessionInstanceInfo> ddsiList = usi
                        .getDeviceDeliverySessionInstanceInfos();
                for (DeviceDeliverySessionInstanceInfo ddsi : ddsiList) {
                    ddsi.getWebDavFolder();
                }
            }
        }
        return broadcastList;
    }

    public Continuous getContinuousByUserServiceInstance(UserServiceInstance userServiceInstance) {
        TypedQuery<Continuous> result = em
                .createQuery(
                        "SELECT continuous FROM Continuous as continuous WHERE continuous.userServiceInstance = :userServiceInstance",
                        Continuous.class).setParameter(USERSERVICEINSTANCE_STR, userServiceInstance);
        return getSingleResult(result);
    }

    public UserServiceInstance getUserServiceInstance(Broadcast broadcast,
            String userServiceInstanceName) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT usi FROM UserServiceInstance as usi WHERE usi.broadcast = :broadcast and usi.name = :name",
                        UserServiceInstance.class);
        result.setParameter(BROADCAST_STR, broadcast);
        result.setParameter(NAME_STR, userServiceInstanceName);
        return getSingleResult(result);
    }

    public OnRequest getOnRequest(UserServiceInstance userServiceInstance) {
        TypedQuery<OnRequest> result = em
                .createQuery(
                        "SELECT onRequest FROM OnRequest as onRequest WHERE onRequest.userServiceInstance = :userServiceInstance",
                        OnRequest.class).setParameter(USERSERVICEINSTANCE_STR, userServiceInstance);
        return getSingleResult(result);
    }

    public List<Cache> getCaches(OnRequest onRequest) {
        TypedQuery<Cache> result = em.createQuery(
                "SELECT cache FROM Cache as cache WHERE cache.onRequest = :onRequest", Cache.class)
                .setParameter(ONREQUEST_STR, onRequest);
        return getMultipleResult(result);
    }

    public Cache getCache(String fileURI, OnRequest onRequest) {
        TypedQuery<Cache> result = em
                .createQuery(
                        "SELECT cache FROM Cache as cache WHERE cache.onRequest = :onRequest and cache.fileUri = :fileUri",
                        Cache.class);
        result.setParameter(ONREQUEST_STR, onRequest);
        result.setParameter("fileUri", fileURI);
        return getSingleResult(result);
    }

    public DeviceDeliverySessionInstanceInfo getDeviceDeliverySessionInfo(
            UserServiceInstance userServiceInstance, BDC bdc) {
        TypedQuery<DeviceDeliverySessionInstanceInfo> result = em
                .createQuery(
                        "SELECT deviceDeliverySessionInstanceInfo FROM DeviceDeliverySessionInstanceInfo as deviceDeliverySessionInstanceInfo WHERE"
                                + " deviceDeliverySessionInstanceInfo.userServiceInstance = :userServiceInstance and deviceDeliverySessionInstanceInfo.bdc = :bdc",
                        DeviceDeliverySessionInstanceInfo.class);
        result.setParameter(USERSERVICEINSTANCE_STR, userServiceInstance);
        result.setParameter(BDC_STR, bdc);
        return getSingleResult(result);
    }

    public List<DeviceCacheInfo> getCacheStatus(Cache cache) {
        TypedQuery<DeviceCacheInfo> result = em.createQuery(
                "SELECT devicecacheinfo FROM DeviceCacheInfo as devicecacheinfo WHERE"
                        + " devicecacheinfo.cacheId = :cacheId", DeviceCacheInfo.class)
                .setParameter(CACHEID_STR, cache.getId());
        return getMultipleResult(result);
    }

    public DeviceDeliverySessionInstanceInfo getDeviceDeliverySessionInfobyDsiId(long dsiId) {
        TypedQuery<DeviceDeliverySessionInstanceInfo> result = em
                .createQuery(
                        "SELECT deviceDeliverySessionInstanceInfo FROM DeviceDeliverySessionInstanceInfo as deviceDeliverySessionInstanceInfo WHERE"
                                + " deviceDeliverySessionInstanceInfo.deliverySessionInstanceId = :dsiId",
                        DeviceDeliverySessionInstanceInfo.class);
        result.setParameter("dsiId", dsiId);
        return getSingleResult(result);
    }

    public List<String> getDeviceDeliverySessionInfoOperStateListByUsi(UserServiceInstance usi) {
        TypedQuery<String> result = em
                .createQuery(
                        "SELECT deviceDeliverySessionInstanceInfo.operState FROM DeviceDeliverySessionInstanceInfo as deviceDeliverySessionInstanceInfo WHERE"
                                + " deviceDeliverySessionInstanceInfo.userServiceInstance = :usi",
                        String.class);
        result.setParameter("usi", usi);
        return getMultipleResult(result);
    }

    public List<DeviceDeliverySessionInstanceInfo> getDeviceDeliverySessionInstanceInfoListByUsi(
            UserServiceInstance usi) {
        TypedQuery<DeviceDeliverySessionInstanceInfo> result = em
                .createQuery(
                        "SELECT deviceDeliverySessionInstanceInfo FROM DeviceDeliverySessionInstanceInfo as deviceDeliverySessionInstanceInfo WHERE"
                                + " deviceDeliverySessionInstanceInfo.userServiceInstance = :usi",
                        DeviceDeliverySessionInstanceInfo.class);
        result.setParameter("usi", usi);
        return getMultipleResult(result);
    }

    public DeviceDeliverySessionInstanceInfo getDeviceDeliverySessionInfo(BDC bdc,
            long eMBMSSessionId) {
        TypedQuery<DeviceDeliverySessionInstanceInfo> result = em
                .createQuery(
                        "SELECT deviceDeliverySessionInstanceInfo FROM DeviceDeliverySessionInstanceInfo as deviceDeliverySessionInstanceInfo WHERE"
                                + " deviceDeliverySessionInstanceInfo.eMBMSSessionId = :eMBMSSessionId and deviceDeliverySessionInstanceInfo.bdc = :bdc",
                        DeviceDeliverySessionInstanceInfo.class);
        result.setParameter("eMBMSSessionId", eMBMSSessionId);
        result.setParameter(BDC_STR, bdc);
        return getSingleResult(result);
    }

    public long addEvemt(Event event) {
        persistAndFlush(event);
        return event.getId();
    }

    public List<DeviceContentInfo> getDeviceContentInfos(Content content) {
        content.getOnRequest().getUserServiceInstance().getDeviceDeliverySessionInstanceInfos();
        TypedQuery<DeviceContentInfo> result = em.createQuery(
                "SELECT dc FROM DeviceContentInfo dc WHERE" + " dc.contentId = :contentId",
                DeviceContentInfo.class).setParameter(CONTENTID_STR, content.getId());
        return getMultipleResult(result);
    }

    public DeviceContentInfo getDeviceContentInfo(long contentId, long devicedsiid) {
        TypedQuery<DeviceContentInfo> result = em
                .createQuery(
                        "SELECT dc FROM DeviceContentInfo dc WHERE dc.contentId = :contentId And dc.deviceDsiId=:devicedsiid",
                        DeviceContentInfo.class).setParameter(CONTENTID_STR, contentId)
                .setParameter("devicedsiid", devicedsiid);
        DeviceContentInfo info = getSingleResult(result);

        if (info == null) {
            info = new DeviceContentInfo();
        }
        return info;
    }

    public UserServiceInstance getUserServiceInstancesById(long id) {
        TypedQuery<UserServiceInstance> result = em.createQuery(
                "SELECT usi FROM UserServiceInstance as usi WHERE usi.id = :id",
                UserServiceInstance.class);
        result.setParameter("id", id);
        return getSingleResult(result);
    }

    public BmcUser getUser(String username) {
        TypedQuery<BmcUser> result = em.createQuery(
                "SELECT user FROM BmcUser as user WHERE user.name = :name", BmcUser.class)
                .setParameter(NAME_STR, username);
        return getSingleResult(result);
    }

    public BmcUser getUserIgnoreCase(String username) {
        TypedQuery<BmcUser> result = em.createQuery(
                "SELECT user FROM BmcUser as user WHERE LOWER(user.name) = LOWER(:name)",
                BmcUser.class).setParameter(NAME_STR, username);
        return getSingleResult(result);
    }

    public List<BmcUser> getUsers() {
        TypedQuery<BmcUser> result = em.createQuery("SELECT user FROM BmcUser user", BmcUser.class);
        return getMultipleResult(result);
    }

    public Long getUserTotal() {
        TypedQuery<Long> result = em
                .createQuery("SELECT COUNT(user) FROM BmcUser user", Long.class);
        return getSingleResult(result).longValue();
    }

    public List<BmcUser> getUserList(SearchQuery restfulQuery) {
        @SuppressWarnings(UNCHECKED_STR)
        TypedQuery<BmcUser> result = QueryBuilder
                .getQueryForSearch(em, restfulQuery, BmcUser.class);
        return getMultipleResult(result);
    }

    public Long searchUserTotal(SearchQuery restfulQuery) {
        @SuppressWarnings(UNCHECKED_STR)
        TypedQuery<Long> result = QueryBuilder.getQueryForTotal(em, restfulQuery, BmcUser.class);
        return getSingleResult(result).longValue();
    }

    public long getServiceCountByGivenServiceSet(Set<String> serviceSet) {
        TypedQuery<Long> result = em.createQuery(
                "SELECT count(*) FROM Service service where service.name in (:serviceName)",
                Long.class);
        result.setParameter(SERVICENAME_STR, serviceSet);
        return result.getSingleResult().longValue();
    }

    public long getServiceClassCountByGivenServiceClassSet(Set<String> serviceClassSet) {
        TypedQuery<Long> result = em
                .createQuery(
                        "SELECT count(*) FROM ServiceClass serviceClass where serviceClass.name in (:serviceClassName)",
                        Long.class);
        result.setParameter("serviceClassName", serviceClassSet);
        return result.getSingleResult().longValue();
    }

    public BmcRole getBmcRole(String rolename) {
        TypedQuery<BmcRole> result = em.createQuery(
                "SELECT role FROM BmcRole as role WHERE role.name = :name", BmcRole.class)
                .setParameter(NAME_STR, rolename);
        return getSingleResult(result);
    }

    public List<BmcRole> getBmcRoles() {
        TypedQuery<BmcRole> result = em.createQuery("SELECT role FROM BmcRole role", BmcRole.class);
        return getMultipleResult(result);
    }

    public List<UserServiceInstance> getUserServiceInstancesByAdminState(String adminState) {
        // userServiceInstance.bcid = broadcast.bcid and
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT userServiceInstance FROM UserServiceInstance as userServiceInstance JOIN userServiceInstance.broadcast broadcast WHERE broadcast.adminState = :adminState",
                        UserServiceInstance.class).setParameter("adminState", adminState);
        return getMultipleResult(result);

    }

    public List<FileSchedule> getFileScheduleByUSI(UserServiceInstance userServiceInstance) {
        TypedQuery<FileSchedule> result = em
                .createQuery(
                        "SELECT fileSchedule FROM FileSchedule as fileSchedule JOIN fileSchedule.cache.onRequest as onrequest WHERE onrequest.userServiceInstance = :userServiceInstance",
                        FileSchedule.class)
                .setParameter(USERSERVICEINSTANCE_STR, userServiceInstance);
        return getMultipleResult(result);
    }

    public List<Mpdis> getMpdByUSIStatus(UserServiceInstanceOperType status1, UserServiceInstanceOperType status2) {
        TypedQuery<Mpdis> result = em.createQuery(
                "SELECT mpdis FROM Mpdis as mpdis JOIN mpdis.continuous.userServiceInstance.broadcast as broadcast WHERE (lower(mpdis.representationID) = 'mpd') AND (broadcast.operState = :operState)",
                                Mpdis.class).setParameter("operState", OperStateType.processing.value());
        return getMultipleResult(result);
    }

    public List<Mpdis> getMpdisByUSI(UserServiceInstance userServiceInstance) {
        TypedQuery<Mpdis> result = em
                .createQuery(
                        "SELECT mpdis FROM Mpdis as mpdis JOIN mpdis.continuous as continuous WHERE continuous.userServiceInstance = :userServiceInstance",
                        Mpdis.class).setParameter(USERSERVICEINSTANCE_STR, userServiceInstance);
        return getMultipleResult(result);
    }

    public List<Mpdis> getMpdisByAdminState(String adminState) {
        TypedQuery<Mpdis> result = em
                .createQuery(
                        "SELECT mpdis FROM Mpdis as mpdis JOIN mpdis.continuous.userServiceInstance.broadcast as broadcast WHERE broadcast.adminState = :adminState",
                        Mpdis.class).setParameter("adminState", adminState);
        return getMultipleResult(result);
    }

    public BDC getBDCByNameIgnoreCase(String name) {
        TypedQuery<BDC> result = em.createQuery(
                "SELECT bdc FROM BDC as bdc WHERE LOWER(bdc.name) = :name", BDC.class);
        result.setParameter(NAME_STR, name.toLowerCase());
        return getSingleResult(result);
    }

    public ADF getADF(String serverName) {
        TypedQuery<ADF> result = em.createQuery(
                "SELECT adf FROM ADF as adf WHERE adf.serverName = :serverName", ADF.class);
        result.setParameter("serverName", serverName);
        return getSingleResult(result);
    }

    public ADF getADFWithBDC(String adfName, String bdcName) {
        TypedQuery<ADF> result = em
                .createQuery(
                        "SELECT adf FROM ADF as adf JOIN adf.bdc as bdc WHERE adf.serverName = :adf_name and bdc.name = :bdc_name",
                        ADF.class).setParameter("adf_name", adfName)
                .setParameter("bdc_name", bdcName);
        return getSingleResult(result);
    }

    public List<ADF> getADFs() {
        TypedQuery<ADF> result = em.createQuery("SELECT adf FROM ADF adf", ADF.class);
        return getMultipleResult(result);
    }

    public CDN getCDN(String name) {
        TypedQuery<CDN> result = em.createQuery(
                "SELECT cdn FROM CDN as cdn WHERE cdn.name = :name", CDN.class);
        result.setParameter(NAME_STR, name);
        return getSingleResult(result);
    }

    public List<CDN> getCDNs() {
        TypedQuery<CDN> result = em.createQuery("SELECT cdn FROM CDN cdn order by cdn.name asc",
                CDN.class);
        return getMultipleResult(result);
    }

    public List<TmgiRange> getTmgiRanges() {
        TypedQuery<TmgiRange> result = em.createQuery("SELECT tmgiRange FROM TmgiRange tmgiRange",
                TmgiRange.class);
        return getMultipleResult(result);
    }

    public void persistenceTmgiRanges(List<TmgiRange> tmgiRangeToAdd,
            List<TmgiRange> tmgiRangeToUpdate, List<TmgiRange> tmgiRangeToDelete) {
        if (!tmgiRangeToDelete.isEmpty()) {
            for (TmgiRange tmgiRange : tmgiRangeToDelete) {
                TypedQuery<TmgiRange> result = em.createQuery(
                        "SELECT tmgiRange FROM TmgiRange tmgiRange where tmgiRange.id = :id",
                        TmgiRange.class).setParameter("id", tmgiRange.getId());
                TmgiRange savedTmgiRange = result.getSingleResult();
                em.remove(savedTmgiRange);
            }
        }

        if (!tmgiRangeToUpdate.isEmpty()) {
            for (TmgiRange tmgiRange : tmgiRangeToUpdate) {
                em.merge(tmgiRange);
            }
        }

        if (!tmgiRangeToAdd.isEmpty()) {
            for (TmgiRange tmgiRange : tmgiRangeToAdd) {
                em.persist(tmgiRange);
            }
        }
    }

    public List<Mpdis> getMpdisByConditionBeforeDate(String condition, Date datetime) {
        TypedQuery<Mpdis> result = em
                .createQuery(
                        "SELECT mpdis FROM Mpdis as mpdis JOIN mpdis.continuous.userServiceInstance.broadcast as broadcast WHERE (broadcast.announcedStartTime < :datetime) AND (CURRENT_TIMESTAMP < broadcast.expireTime) AND "
                                + condition, Mpdis.class).setParameter("datetime", datetime);
        return getMultipleResult(result);
    }

    public List<FileSchedule> getFileSchedulesByConditionBeforeDate(String condition, Date datetime) {
        TypedQuery<FileSchedule> result = em
                .createQuery(
                        "SELECT fileSchedule FROM FileSchedule as fileSchedule JOIN fileSchedule.cache.onRequest.userServiceInstance.broadcast as broadcast WHERE (broadcast.announcedStartTime < :datetime) AND (CURRENT_TIMESTAMP < broadcast.expireTime) AND "
                                + condition, FileSchedule.class).setParameter("datetime", datetime);
        return getMultipleResult(result);
    }

    public List<UserServiceInstance> getUSIsByConditionBeforeDate(String condition, Date datetime) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT userServiceInstance FROM UserServiceInstance as userServiceInstance JOIN userServiceInstance.broadcast broadcast WHERE (broadcast.announcedStartTime < :datetime) AND (CURRENT_TIMESTAMP < broadcast.expireTime) AND "
                                + condition, UserServiceInstance.class).setParameter("datetime",
                        datetime);
        return getMultipleResult(result);
    }

    public List<SDCHService> getBootstrapSDCHServices() {
        TypedQuery<SDCHService> result = em
                .createQuery(
                        "SELECT sdchService FROM SDCHService as sdchService WHERE sdchService.bootstrap = :bootstrap",
                        SDCHService.class).setParameter("bootstrap", true);
        return getMultipleResult(result);
    }

    public UserServiceInstance getUSIBySDCHService(SDCHService sdchService) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT userServiceInstance FROM UserServiceInstance as userServiceInstance WHERE userServiceInstance.broadcast.service = :service",
                        UserServiceInstance.class)
                .setParameter("service", sdchService.getService());
        return getSingleResult(result);
    }

    public List<AdpdServiceUri> getAdpdServiceUrisByUSI(UserServiceInstance userServiceInstance,
            BDC bdc) {
        TypedQuery<AdpdServiceUri> result = em
                .createQuery(
                        "SELECT serviceUri FROM AdpdServiceUri as serviceUri WHERE serviceUri.userServiceInstance = :userServiceInstance and serviceUri.bdc = :bdc",
                        AdpdServiceUri.class);
        result.setParameter(USERSERVICEINSTANCE_STR, userServiceInstance);
        result.setParameter(BDC_STR, bdc);
        return getMultipleResult(result);
    }

    public List<Service> getServiceList(List<String> nameList) {
        TypedQuery<Service> result = em.createQuery(
                "SELECT s FROM Service as s WHERE s.name IN (:name)", Service.class).setParameter(
                NAME_STR, nameList);
        return getMultipleResult(result);
    }

    public List<String> getServiceNameList(List<String> scNameList) {
        TypedQuery<String> result = em.createQuery(
                "SELECT s.name FROM Service as s WHERE s.serviceClass.name IN (:scname)",
                String.class).setParameter("scname", scNameList);
        return getMultipleResult(result);
    }

    public List<Broadcast> getBroadcastList(List<Service> serviceList) {
        TypedQuery<Broadcast> result = em
                .createQuery(
                        "SELECT broadcast FROM Broadcast as broadcast WHERE broadcast.service IN (:service)",
                        Broadcast.class).setParameter("service", serviceList);
        return getMultipleResult(result);
    }

    public BmcUserSession getUserSession(String sessionId) {
        TypedQuery<BmcUserSession> result = em.createQuery(
                "select session from BmcUserSession session where session.sessionId = :Id ",
                BmcUserSession.class).setParameter("Id", Integer.parseInt(sessionId));
        return getSingleResult(result);
    }

    public List<BmcUserSession> getUserSessionByUserName(String userName) {
        TypedQuery<BmcUserSession> result = em.createQuery(
                "select session from BmcUserSession session where session.userName = :userName ",
                BmcUserSession.class).setParameter("userName", userName);
        return getMultipleResult(result);
    }

    public List<Content> getContentByInterval(Date fromTime, Date untilTime, Boolean isStopTime,
            ContentStatusType status) {

        String timetype = "content.startTime";
        String statusCondition = "";
        if (isStopTime) {
            timetype = "content.stopTime";
        }
        if (status != null) {
            statusCondition = " and content.status = :status";
        }
        TypedQuery<Content> result = em.createQuery("SELECT content FROM Content content WHERE "
                + timetype + " BETWEEN :fromTime AND :untilTime " + statusCondition, Content.class);

        result.setParameter("fromTime", fromTime);
        result.setParameter("untilTime", untilTime);
        if (status != null) {
            result.setParameter("status", status);
        }

        return getMultipleResult(result);
    }

    public List<Broadcast> getBroadcastByInterval(Date fromTime, Date untilTime,
            Boolean isAnnouncedStopTime, String operStatus, String adminStatus) {

        String timetype = "broadcast.startTime";
        String statusCondition = "";

        if (isAnnouncedStopTime) {
            timetype = "broadcast.announcedStopTime";
        }

        if (!operStatus.isEmpty()) {
            statusCondition = " and broadcast.operState = :operstatus and broadcast.adminState = :adminstatus";
        }

        TypedQuery<Broadcast> result = em.createQuery(
                "SELECT broadcast FROM Broadcast broadcast WHERE " + timetype
                        + " BETWEEN :fromTime AND :untilTime " + statusCondition, Broadcast.class);
        result.setParameter("fromTime", fromTime);
        result.setParameter("untilTime", untilTime);
        if (!operStatus.isEmpty()) {
            result.setParameter("operstatus", operStatus);
            result.setParameter("adminstatus", adminStatus);
        }
        return getMultipleResult(result);
    }

    public List<NotificationURL> getNotificationURLs(Broadcast broadcast) {
        TypedQuery<NotificationURL> result = em.createQuery(
                "SELECT url FROM NotificationURL as url WHERE url.broadcast = :broadcast",
                NotificationURL.class).setParameter(BROADCAST_STR, broadcast);
        return getMultipleResult(result);
    }

    public List<BmcParameter> getBmcParameters() {
        TypedQuery<BmcParameter> result = em.createQuery(
                "SELECT bmcParameters FROM BmcParameter bmcParameters", BmcParameter.class);
        return getMultipleResult(result);
    }

    public BmcParameter getBmcParameters(String name) {
        TypedQuery<BmcParameter> result = em
                .createQuery(
                        "SELECT bmcParameters FROM BmcParameter bmcParameters where bmcParameters.name = :name ",
                        BmcParameter.class).setParameter(NAME_STR, name);
        return getSingleResult(result);
    }

    public List<Service> getServiceByBroadcastAreaName(String broadcastAreaName) {
        TypedQuery<Service> result = em
                .createQuery(
                        "SELECT service FROM Service as service JOIN service.broadcastArea as ba WHERE ba.name = :name",
                        Service.class).setParameter(NAME_STR, broadcastAreaName);
        return getMultipleResult(result);
    }

    /**
     * @param relatedNodeNameList
     * @param lockKey
     * @return
     */
    public List<LockAssist> getEffectiveLocks(List<String> relatedNodeNameList, Date intime,
            String lockKey) {
        StringBuffer sql = new StringBuffer("SELECT lock FROM LockAssist lock where ");
        if (relatedNodeNameList.size() == 1) {
            sql.append(COULD_NOT_EXISTS_SQL).append(JUDGE_ON_INTIME_SQL)
                    .append(AVOID_SELF_LOCK_EFFCTIVE);
            TypedQuery<LockAssist> result = em.createQuery(sql.toString(), LockAssist.class)
                    .setParameter("NodeValue", relatedNodeNameList.get(0))
                    .setParameter("InTime", intime).setParameter("LockKey", lockKey);
            return getMultipleResult(result);
        } else {
            sql.append("(");
            for (int a = 0; a < relatedNodeNameList.size() - 1; a++) {
                sql.append(" ( lock.nodeValue = :NodeValue" + a + " and lock.isTarget = true ) ")
                        .append(" or ");
            }
            sql.append(COULD_NOT_EXISTS_SQL + (relatedNodeNameList.size() - 1) + " ")
                    .append(")" + JUDGE_ON_INTIME_SQL).append(AVOID_SELF_LOCK_EFFCTIVE);

            TypedQuery<LockAssist> result = em.createQuery(sql.toString(), LockAssist.class);
            int count = 0;
            for (String relatedNodeName : relatedNodeNameList) {
                result.setParameter("NodeValue" + count, relatedNodeName);
                count++;
            }
            result.setParameter("InTime", intime).setParameter("LockKey", lockKey);
            return getMultipleResult(result);
        }
    }

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
    public Long getTotalCount(Class<?> entityClazz, Map<String, String> parameterMap) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> critQuery = criteriaBuilder.createQuery(Long.class);
        Root<?> root = critQuery.from(entityClazz);
        critQuery.select(criteriaBuilder.count(root));
        Predicate predicate = null;

        if (parameterMap != null && !parameterMap.isEmpty()) {
            List<String> columns = DatabaseServiceHelper.getInstance().getFuzzyQueryColumns(
                    entityClazz.getSimpleName());
            for (Entry<String, String> parameter : parameterMap.entrySet()) {
                boolean isFuzzyQuery = false;
                if (columns != null) {
                    for (String column : columns) {
                        if (column.equalsIgnoreCase(parameter.getKey())) {
                            isFuzzyQuery = true;
                            break;
                        }
                    }
                }
                if (isFuzzyQuery) {
                    if (predicate == null) {
                        predicate = criteriaBuilder.and(criteriaBuilder.like(
                                root.<String> get(parameter.getKey()), "%" + parameter.getValue()
                                        + "%"));
                    } else {
                        predicate = criteriaBuilder.and(
                                criteriaBuilder.like(root.<String> get(parameter.getKey()), "%"
                                        + parameter.getValue() + "%"), predicate);
                    }
                } else {
                    Predicate nextPredicate = getNextPredicate(parameter, criteriaBuilder, root);
                    if (predicate == null) {
                        predicate = nextPredicate;
                    } else {
                        predicate = criteriaBuilder.and(predicate, nextPredicate);
                    }
                }
            }
        }

        if (predicate != null) {
            return em.createQuery(critQuery.where(predicate)).getSingleResult().longValue();
        } else {
            return em.createQuery(critQuery).getSingleResult().longValue();
        }
    }

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
    public List<?> getPagingEntityList(Class<?> entityClazz, int offset, int pageSize,
            Map<String, String> parameterMap, String orderBy, String sortType) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<?> critQuery = criteriaBuilder.createQuery(entityClazz);
        Root<?> root = critQuery.from(entityClazz);
        TypedQuery<?> typeQuery = null;

        setQuery(orderBy, sortType, critQuery, criteriaBuilder, root);

        Predicate predicate = parsePredicate(parameterMap, criteriaBuilder,
                entityClazz.getSimpleName(), root);

        if (predicate != null) {
            typeQuery = em.createQuery(critQuery.where(predicate));
        } else {
            typeQuery = em.createQuery(critQuery);
        }
        if (offset > -1) {
            typeQuery.setFirstResult(offset);
        }
        if (pageSize > 0) {
            typeQuery.setMaxResults(pageSize);
        }
        return getMultipleResult(typeQuery);
    }

    private Predicate parsePredicate(Map<String, String> parameterMap,
            CriteriaBuilder criteriaBuilder, String className, Root<?> root) {
        Predicate predicate = null;
        if (parameterMap != null && !parameterMap.isEmpty()) {
            List<String> columns = DatabaseServiceHelper.getInstance().getFuzzyQueryColumns(
                    className);
            for (Entry<String, String> parameter : parameterMap.entrySet()) {
                boolean isFuzzyQuery = false;
                if (columns != null) {
                    for (String column : columns) {
                        if (column.equalsIgnoreCase(parameter.getKey())) {
                            isFuzzyQuery = true;
                            break;
                        }
                    }
                }
                if (isFuzzyQuery) {
                    if (predicate == null) {
                        predicate = criteriaBuilder.and(criteriaBuilder.like(
                                root.<String> get(parameter.getKey()), "%" + parameter.getValue()
                                        + "%"));
                    } else {
                        predicate = criteriaBuilder.and(
                                criteriaBuilder.like(root.<String> get(parameter.getKey()), "%"
                                        + parameter.getValue() + "%"), predicate);
                    }
                } else {
                    Predicate nextPredicate = getNextPredicate(parameter, criteriaBuilder, root);
                    if (predicate == null) {
                        predicate = nextPredicate;
                    } else {
                        predicate = criteriaBuilder.and(predicate, nextPredicate);
                    }
                }
            }
        }
        return predicate;
    }

    private Predicate getNextPredicate(Entry<String, String> parameter,
            CriteriaBuilder criteriaBuilder, Root<?> root) {
        Predicate nextPredicate = null;

        String keyStr = parameter.getKey();
        if (keyStr.indexOf(UNDERLINE_STR) > 0) {
            String[] keyStrArray = keyStr.split(UNDERLINE_STR);
            if (keyStrArray[1].equalsIgnoreCase("long")) {
                nextPredicate = criteriaBuilder.and(criteriaBuilder.equal(root.get(keyStrArray[0]),
                        Long.valueOf(parameter.getValue())));
            }
        } else {
            nextPredicate = criteriaBuilder.and(criteriaBuilder.equal(root.get(parameter.getKey()),
                    parameter.getValue()));
        }
        return nextPredicate;
    }

    private void setQuery(String orderBy, String sortType, CriteriaQuery<?> critQuery,
            CriteriaBuilder criteriaBuilder, Root<?> root) {
        if (!orderBy.isEmpty()) {
            if (!sortType.isEmpty()) {
                if (sortType.equalsIgnoreCase(RestfulQuery.ORDERBY_DESC)) {
                    critQuery.orderBy(criteriaBuilder.desc(root.get(orderBy)));
                } else {
                    critQuery.orderBy(criteriaBuilder.asc(root.get(orderBy)));
                }
            } else {
                critQuery.orderBy(criteriaBuilder.asc(root.get(orderBy)));
            }
        }
    }

    public FECTemplate getFECTemplate(String name) {
        TypedQuery<FECTemplate> query = em.createQuery(
                "select f from FECTemplate f where f.name = :name", FECTemplate.class)
                .setParameter(NAME_STR, name);
        return getSingleResult(query);
    }

    public FECTemplate getFECTemplateIgnoreCase(String name) {
        TypedQuery<FECTemplate> query = em.createQuery(
                "select f from FECTemplate f where LOWER(f.name) = :name", FECTemplate.class)
                .setParameter(NAME_STR, name.toLowerCase());
        return getSingleResult(query);
    }

    public FRTemplate getFRTemplate(String name) {
        TypedQuery<FRTemplate> query = em.createQuery(
                "select f from FRTemplate f where f.name = :name", FRTemplate.class).setParameter(
                NAME_STR, name);
        return getSingleResult(query);
    }

    public FRTemplate getFRTemplateByTemplateNameIgnoreCase(String name) {
        TypedQuery<FRTemplate> query = em.createQuery(
                "select f from FRTemplate f where LOWER(f.name) = :name", FRTemplate.class)
                .setParameter(NAME_STR, name.toLowerCase());
        return getSingleResult(query);
    }

    public RRTemplate getRRTemplate(String name) {
        TypedQuery<RRTemplate> query = em.createQuery(
                "select r from RRTemplate r where r.name = :name", RRTemplate.class).setParameter(
                NAME_STR, name);
        return getSingleResult(query);
    }

    public RRTemplate getRRTemplateByTemplateNameIgnoreCase(String name) {
        TypedQuery<RRTemplate> query = em.createQuery(
                "select r from RRTemplate r where LOWER(r.name) = :name", RRTemplate.class)
                .setParameter(NAME_STR, name.toLowerCase());
        return getSingleResult(query);
    }

    public String getUserServiceMode(String serviceName, String bearerName, String usName) {
        TypedQuery<String> query = em
                .createQuery(
                        "select us.mode from UserService us where us.name = :usname and us.bearer.id in "
                                + "(select b.id from Bearer b where b.name = :bearername and b.service.id in "
                                + "(select s.id from Service s where s.name = :servicename))",
                        String.class);
        query.setParameter("usname", usName).setParameter("bearername", bearerName)
                .setParameter("servicename", serviceName);
        return getSingleResult(query);
    }

    public static final class DatabaseServiceHelper {
        private static final String TABLE_SERVICE_CLASS = "serviceclass";
        private static final String TABLE_BROADCAST_AREA = "broadcastarea";
        private Map<String, List<String>> fuzzyQueryTables;
        private static final DatabaseServiceHelper INSTANCE = new DatabaseServiceHelper();

        public static DatabaseServiceHelper getInstance() {
            return INSTANCE;
        }

        private DatabaseServiceHelper() {
            fuzzyQueryTables = new HashMap<String, List<String>>();
            init();
        }

        private void init() {
            List<String> fuzzyQueryColumns = new ArrayList<String>();
            fuzzyQueryColumns.add(NAME_STR);
            fuzzyQueryTables.put(TABLE_SERVICE_CLASS.toLowerCase(), fuzzyQueryColumns);
            List<String> baFuzzyQueryColumns = new ArrayList<String>();
            baFuzzyQueryColumns.add(NAME_STR);
            fuzzyQueryTables.put(TABLE_BROADCAST_AREA.toLowerCase(), baFuzzyQueryColumns);
            fuzzyQueryColumns = new ArrayList<String>();
            fuzzyQueryColumns.add("frequencies");
            fuzzyQueryTables
                    .put(ServiceArea.class.getSimpleName().toLowerCase(), fuzzyQueryColumns);
        }

        public List<String> getFuzzyQueryColumns(String tableName) {
            return fuzzyQueryTables.get(tableName.toLowerCase());
        }
    }

    public ProvisionConfig getProvisionConfig(String name) {
        TypedQuery<ProvisionConfig> result = em
                .createQuery("SELECT pc FROM ProvisionConfig as pc WHERE pc.name = :name",
                        ProvisionConfig.class);
        result.setParameter(NAME_STR, name);
        return getSingleResult(result);
    }

    public List<Bearer> getBearerListByTMGI(String tmgiName) {
        TypedQuery<Bearer> result = em.createQuery(
                "SELECT bearer FROM Bearer as bearer WHERE bearer.tmgi = :tmgi", Bearer.class)
                .setParameter("tmgi", tmgiName);
        return getMultipleResult(result);
    }

    public List<SDPInfo> getSDPInfoByMulticastDestIP(String multicastDestIp) {
        TypedQuery<SDPInfo> result = em.createQuery(
                "SELECT si FROM SDPInfo as si WHERE si.multicastDestIP = :multicastDestIp",
                SDPInfo.class).setParameter("multicastDestIp", multicastDestIp);
        return getMultipleResult(result);
    }

    public SDPInfo getSDPInfoByMulticastIPAndPort(String ip, int destPort) {
        TypedQuery<SDPInfo> result = em
                .createQuery(
                        "SELECT si FROM SDPInfo as si WHERE si.multicastDestIP = :multicastIp AND si.destPort = :destPort",
                        SDPInfo.class).setParameter("multicastIp", ip)
                .setParameter("destPort", destPort);
        return getSingleResult(result);
    }

    public SDPInfo getSDPInfoByTsi(Long tsi) {
        TypedQuery<SDPInfo> result = em.createQuery(
                "SELECT si FROM SDPInfo as si WHERE si.tsi = :tsi", SDPInfo.class).setParameter(
                "tsi", tsi);
        return getSingleResult(result);
    }

    public List<UserServiceInstance> getUserServiceInstanceByDeliverySessionId(long dsId) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT usi FROM UserServiceInstance as usi WHERE usi.deliverSessionId = :deliverySessionId",
                        UserServiceInstance.class).setParameter("deliverySessionId", dsId);
        return getMultipleResult(result);
    }

    public List<UserServiceInstance> getUserServiceInstanceByBroadcastDeliverySessionId(
            Broadcast broadcast, long dsId) {
        TypedQuery<UserServiceInstance> result = em
                .createQuery(
                        "SELECT usi FROM UserServiceInstance as usi WHERE usi.broadcast = :broadcast AND usi.deliverSessionId = :deliverySessionId",
                        UserServiceInstance.class).setParameter(BROADCAST_STR, broadcast)
                .setParameter("deliverySessionId", dsId);
        return getMultipleResult(result);
    }

    public QoeMetrics getQoeMetricsByTemplateName(String name) {
        TypedQuery<QoeMetrics> result = em.createQuery(
                "SELECT qm FROM QoeMetrics as qm WHERE qm.name = :name", QoeMetrics.class)
                .setParameter(NAME_STR, name);
        return getSingleResult(result);
    }

    public QoeMetrics getQoeMetricsByTemplateNameIgnoreCase(String name) {
        TypedQuery<QoeMetrics> result = em.createQuery(
                "SELECT qm FROM QoeMetrics as qm WHERE LOWER(qm.name) = :name", QoeMetrics.class)
                .setParameter(NAME_STR, name.toLowerCase());
        return getSingleResult(result);
    }

    public BDC getBDCByName(String bdcName) {
        TypedQuery<BDC> result = em.createQuery(
                "SELECT bdc FROM BDC as bdc WHERE bdc.name = :name", BDC.class).setParameter(
                NAME_STR, bdcName);
        return getSingleResult(result);
    }

    public List<BDC> getAllBdcList() {
        TypedQuery<BDC> result = em.createQuery("SELECT bdcs FROM BDC bdcs order by bdcs.name asc",
                BDC.class);
        return getMultipleResult(result);
    }

    public List<BDC> getBdcList(SearchQuery restfulQuery) {
        TypedQuery<BDC> result = QueryBuilder.getQueryForSearch(em, restfulQuery, BDC.class);
        return getMultipleResult(result);
    }

    public List<UserService> getUserServicesByBearerId(long bearerid) {
        TypedQuery<UserService> result = em.createQuery(
                "SELECT us FROM UserService as us WHERE us.bearer.id = :bearerid",
                UserService.class);
        result.setParameter("bearerid", bearerid);
        return getMultipleResult(result);
    }

    public void deleteSdpInfoById(long id) {

        em.createQuery("DELETE FROM SDPInfo as si WHERE si.id = :id", SDPInfo.class)
                .setParameter("id", id).executeUpdate();
        em.flush();
    }

    public void deleteUserServiceById(long id) {
        em.createQuery("DELETE FROM UserService as us WHERE us.id = :id", UserService.class)
                .setParameter("id", id).executeUpdate();
        em.flush();
    }

    public void deleteBearerById(long id) {

        em.createQuery("DELETE FROM Bearer as bear WHERE bear.id = :id", Bearer.class)
                .setParameter("id", id).executeUpdate();
        em.flush();
    }

    public void deleteServiceByName(String serviceName) {

        em.createQuery("DELETE FROM Service as service WHERE service.name = :name", Service.class)
                .setParameter(NAME_STR, serviceName).executeUpdate();
        em.flush();
    }

    public List<Content> getJustStopContentList(Date mayBeCrashTime, Date utcTimeNow) {
        TypedQuery<Content> result = em
                .createQuery(
                        "SELECT content FROM Content as content WHERE content.stopTime BETWEEN :mayBeCrashTime AND :utcTimeNow",
                        Content.class);
        result.setParameter("mayBeCrashTime", mayBeCrashTime);
        result.setParameter("utcTimeNow", utcTimeNow);
        return getMultipleResult(result);
    }

    public List<Content> getAlreadyStartContentList(Date sysStartTime) {
        TypedQuery<Content> result = em
                .createQuery(
                        "SELECT content FROM Content as content WHERE content.startTime < :sysStartTime and content.stopTime > :sysStartTime",
                        Content.class);
        result.setParameter("sysStartTime", sysStartTime);
        return getMultipleResult(result);
    }

    public List<FileSchedule> getFileSchedulesByCacheId(long cacheId) {

        TypedQuery<FileSchedule> result = em
                .createQuery(
                        "SELECT fileschedule FROM FileSchedule as fileschedule WHERE fileschedule.cache.id = :cacheId",
                        FileSchedule.class).setParameter(CACHEID_STR, cacheId);

        return getMultipleResult(result);

    }

    public List<Content> getContentByCacheId(long cacheId) {

        TypedQuery<Content> result = em.createQuery(
                "SELECT content FROM Content as content WHERE content.cache.id = :cacheId",
                Content.class).setParameter(CACHEID_STR, cacheId);

        return getMultipleResult(result);

    }

    public List<FileSchedule> getFileSchedulesByOnRequest(OnRequest onRequest) {
        TypedQuery<FileSchedule> result = em.createQuery(
                "SELECT file FROM FileSchedule as file WHERE file.onRequest = :onRequest",
                FileSchedule.class).setParameter(ONREQUEST_STR, onRequest);
        return getMultipleResult(result);
    }

    public List<Frequency> getFrequenciesByDefault(boolean isDefault) {
        TypedQuery<Frequency> result = em
                .createQuery(
                        "SELECT frequency FROM Frequency as frequency WHERE frequency.isDefault = :isDefault",
                        Frequency.class).setParameter("isDefault", isDefault);
        return getMultipleResult(result);
    }

    public List<OnRequest> getOnRequestByFecId(long fecId) {
        TypedQuery<OnRequest> result = em
                .createQuery(
                        "SELECT onRequest FROM OnRequest as onRequest WHERE onRequest.fecTemplate.id = :fecId",
                        OnRequest.class).setParameter("fecId", fecId);
        return getMultipleResult(result);
    }

    public List<OnRequest> getOnRequestByFrId(long frId) {
        TypedQuery<OnRequest> result = em
                .createQuery(
                        "SELECT onRequest FROM OnRequest as onRequest WHERE onRequest.frTemplate.id = :frId",
                        OnRequest.class).setParameter("frId", frId);
        return getMultipleResult(result);
    }

    public List<OnRequest> getOnRequestByRrId(long rrId) {
        TypedQuery<OnRequest> result = em
                .createQuery(
                        "SELECT onRequest FROM OnRequest as onRequest WHERE onRequest.rrTemplate.id = :rrId",
                        OnRequest.class).setParameter("rrId", rrId);
        return getMultipleResult(result);
    }

    public List<Continuous> getContinuousByFecId(long fecId) {
        TypedQuery<Continuous> result = em
                .createQuery(
                        "SELECT continuous FROM Continuous as continuous WHERE continuous.fecTemplate.id = :fecId",
                        Continuous.class).setParameter("fecId", fecId);
        return getMultipleResult(result);
    }

    public List<Continuous> getContinuousByRrId(long rrId) {
        TypedQuery<Continuous> result = em
                .createQuery(
                        "SELECT continuous FROM Continuous as continuous WHERE continuous.rrTemplate.id = :rrId",
                        Continuous.class).setParameter("rrId", rrId);
        return getMultipleResult(result);
    }

    public List<Continuous> getContinuousByMediaUri(String mediaUri) {
        TypedQuery<Continuous> result = em.createQuery(
                "SELECT DISTINCT continuous FROM Mpdis as mpdis WHERE mpdis.mediaURI = :mediaURI",
                Continuous.class).setParameter("mediaURI", mediaUri);
        return getMultipleResult(result);
    }

    public List<UserService> getUserServiceByQoeMatricsId(long qoeMatricsId) {
        TypedQuery<UserService> result = em.createQuery(
                "SELECT us FROM UserService as us WHERE us.qoeMetrics.id = :id", UserService.class)
                .setParameter("id", qoeMatricsId);
        return getMultipleResult(result);
    }

    public Long getTotalQoeMetricsTempate(SearchQuery restfulQuery) {
        TypedQuery<Long> totalQuery = QueryBuilder.getQueryForTotal(em, restfulQuery,
                QoeMetrics.class);
        return totalQuery.getSingleResult().longValue();
    }

    public List<QoeMetrics> getQoeMetricsList(SearchQuery restfulQuery) {
        TypedQuery<QoeMetrics> listQuery = QueryBuilder.getQueryForSearch(em, restfulQuery,
                QoeMetrics.class);
        return listQuery.getResultList();
    }

    public Long getTotalFecTempate(SearchQuery restfulQuery) {
        TypedQuery<Long> totalQuery = QueryBuilder.getQueryForTotal(em, restfulQuery,
                FECTemplate.class);
        return totalQuery.getSingleResult().longValue();
    }

    public Long getTotalFrTempate(SearchQuery restfulQuery) {
        TypedQuery<Long> totalQuery = QueryBuilder.getQueryForTotal(em, restfulQuery,
                FRTemplate.class);
        return totalQuery.getSingleResult().longValue();
    }

    public void deleteQoeTemplate(String qoeName) {
        em.createQuery("DELETE FROM QoeMetrics qoe where qoe.name = :qoeName")
                .setParameter("qoeName", qoeName).executeUpdate();
        em.flush();
    }

    public void deleteFecTemplate(String fecName) {
        em.createQuery("DELETE FROM FECTemplate fec where fec.name = :fecName")
                .setParameter("fecName", fecName).executeUpdate();
        em.flush();
    }

    public void deleteFrTemplate(String frName) {
        em.createQuery("DELETE FROM FRTemplate fr where fr.name = :frName")
                .setParameter("frName", frName).executeUpdate();
        em.flush();
    }

    public List<FECTemplate> getFECTemplateList(SearchQuery restfulQuery) {
        TypedQuery<FECTemplate> listQuery = QueryBuilder.getQueryForSearch(em, restfulQuery,
                FECTemplate.class);
        return listQuery.getResultList();
    }

    public List<FRTemplate> getFRTemplateList(SearchQuery restfulQuery) {
        TypedQuery<FRTemplate> listQuery = QueryBuilder.getQueryForSearch(em, restfulQuery,
                FRTemplate.class);
        return listQuery.getResultList();
    }

    public void updateContinuousStatus(long deliveryInstanceId, String bdcName, BDCEventType event) {
        long deviceDsiId = getDeviceDsiId(deliveryInstanceId, bdcName);
        updateDeviceContinuousStatus(deviceDsiId, deliveryInstanceId, event);
    }

    private void updateDeviceContinuousStatus(long deviceDsiId, long deliveryInstanceId,
            BDCEventType status) {
        updateDeviceContinuousStatus(deviceDsiId, deliveryInstanceId,
                ContentStatusType.getStatus(status));
    }

    private Mpdis getMpd(long deviceDsiId) {
        long usiId = getUsiIdFromDsi(deviceDsiId);
        if (usiId == -1) {
            return null;
        }
        List<Mpdis> isList = getMpdisByUSI(getUserServiceInstancesById(usiId));
        Mpdis mpd = null;
        for (Mpdis mpdis : isList) {
            if (mpdis.getRepresentationID().equalsIgnoreCase("mpd")) {
                mpd = mpdis;
            }
        }
        return mpd;
    }

    private long getUsiIdFromDsi(long deviceDsiId) {
        long usiId = 0;
        String sql = "SELECT userserviceinstanceid FROM devicedeliverysessioninstanceinfo WHERE id = "
                + deviceDsiId;
        Query q = em.createNativeQuery(sql);
        @SuppressWarnings(UNCHECKED_STR)
        List<Long> results = q.getResultList();
        if (results.size() == 0) {
            usiId = -1;
            return usiId;
        }
        usiId = results.get(0);

        return usiId;
    }

    private void updateDeviceContinuousStatus(long deviceDsiId, long deliveryInstanceId,
            ContentStatusType status) {
        Mpdis mpd = getMpd(deviceDsiId);
        if (mpd == null) {
            return;
        }
        if ((deviceDsiId < 0) || (deliveryInstanceId < 0)) {
            return;
        }
        long mpdId = mpd.getId();
        DeviceContinuousInfo info = getDeviceContinuousInfo(deviceDsiId);
        info.setDeviceDsiId(deviceDsiId);
        info.setMpdId(mpdId);
        info.setStatus(status);
        em.merge(info);
        em.flush();
    }

    public DeviceContinuousInfo getDeviceContinuousInfo(long devicedsiid) {
        TypedQuery<DeviceContinuousInfo> result = em.createQuery(
                "SELECT dci FROM DeviceContinuousInfo dci WHERE dci.deviceDsiId=:devicedsiid",
                DeviceContinuousInfo.class).setParameter("devicedsiid", devicedsiid);
        DeviceContinuousInfo info = getSingleResult(result);
        if (info == null) {
            info = new DeviceContinuousInfo();
        }
        return info;
    }

    public Set<ContentStatusType> getContentStatusGroup(long contentId) {

        String sql = "SELECT status FROM devicecontentinfo WHERE contentid=" + contentId
                + " GROUP BY status";
        Query q = em.createNativeQuery(sql);
        @SuppressWarnings(UNCHECKED_STR)
        List<String> statusStrGroup = q.getResultList();
        Set<ContentStatusType> statusGroup = new HashSet<ContentStatusType>();
        for (String statusStr : statusStrGroup) {
            statusGroup.add(ContentStatusType.valueOf(statusStr));
        }
        return statusGroup;

    }

    public void mergeDeviceContentStatus(long deviceDsiId, long contentId, ContentStatusType status) {
        DeviceContentInfo info = getDeviceContentInfo(contentId, deviceDsiId);
        info.setDeviceDsiId(deviceDsiId);
        info.setContentId(contentId);
        info.setStatus(status);
        em.merge(info);
        em.flush();
    }

    private void updateStatusInTableContent(long contentId, long onRequestIndex,
            ContentStatusType status) {
        String sql = "UPDATE content SET status='" + status + "' WHERE contentid=" + contentId
                + " AND onrequestid=" + onRequestIndex;
        int result = em.createNativeQuery(sql).executeUpdate();
        em.flush();
        if (result == 0) {
            BmcLogger
                    .eventWarn(
                            BmcLogModule.SVCPERSISTENCE,
                            "Update content status in content table...failed(Not Found content)[contentId=%s,onRequestIndex=%d,status=%s]",
                            contentId, onRequestIndex, status.name());
        }

    }

    public long queryDeviceCacheInfoCount(long cacheId, long onRequestIndex) {
        String sql = "SELECT COUNT(*) FROM onrequest AS oq "
                + "JOIN devicedeliverysessioninstanceinfo AS ddsi ON oq.id=%d AND ddsi.userserviceinstanceid=oq.usiid "
                + "JOIN devicecacheinfo AS dci ON ddsi.id=dci.devicedsiid AND dci.cacheid=%d";
        sql = String.format(sql, onRequestIndex, cacheId);
        Query q = em.createNativeQuery(sql);
        return (Long) q.getSingleResult();
    }

    public long queryBDCCountForOnRequest(long onRequestIndex) {
        String sql = "SELECT COUNT(*) FROM onrequest AS oq "
                + "JOIN userserviceinstance AS usi ON oq.usiid = usi.id AND oq.id=%d "
                + "JOIN broadcast AS bc ON usi.bcid=bc.id "
                + "JOIN service AS svc ON bc.sid=svc.id "
                + "JOIN broadcastarea AS bca ON svc.baid=bca.id "
                + "JOIN areamapping AS am ON bca.id=am.id "
                + "JOIN bdc_servicearea_mapping AS bdcsvamp ON bdcsvamp.sai=am.sai ";
        sql = String.format(sql, onRequestIndex);
        Query q = em.createNativeQuery(sql);
        return (Long) q.getSingleResult();
    }

    public void mergeDeviceCacheStatus(long cacheId, long deviceDsiId, CacheStatusType status) {
        DeviceCacheInfo info = getdeviceCacheInfo(deviceDsiId, cacheId);
        info.setDeviceDsiId(deviceDsiId);
        info.setCache(cacheId);
        info.setStatus(status);
        em.merge(info);
        em.flush();
    }

    public DeviceCacheInfo getdeviceCacheInfo(long deviceDsiId, long cacheId) {

        TypedQuery<DeviceCacheInfo> result = em
                .createQuery(
                        "SELECT dc FROM DeviceCacheInfo dc WHERE dc.cacheId = :cacheId And dc.deviceDsiId=:devicedsiid",
                        DeviceCacheInfo.class).setParameter(CACHEID_STR, cacheId)
                .setParameter("devicedsiid", deviceDsiId);
        DeviceCacheInfo info = getSingleResult(result);

        if (info == null) {
            info = new DeviceCacheInfo();
        }
        return info;

    }

    public DeviceDeliverySessionInstanceInfo getDeviceDeliverySessionInstanceInfoByEvent(
            BDCEvent event) {
        TypedQuery<DeviceDeliverySessionInstanceInfo> result = em
                .createQuery(
                        "SELECT ddsii FROM DeviceDeliverySessionInstanceInfo"
                                + " ddsii WHERE ddsii.deliverySessionInstanceId = :deviceDsiId AND ddsii.bdc.name = :bdcName",
                        DeviceDeliverySessionInstanceInfo.class);
        result.setParameter("deviceDsiId", event.getDeliveryInstanceId());
        result.setParameter("bdcName", event.getName());
        return getSingleResult(result);
    }

    /**
     * Query DeviceDsiId
     *
     * @param deliverInsance
     * @param bdcName
     * @return -1 when it's not found.
     */

    public long getDeviceDsiId(long deliverInsance, String bdcName) {
        BDC bdc = getBDCByNameIgnoreCase(bdcName);
        if (bdc == null) {
            return -1;
        }
        return getDeviceDsiId(deliverInsance, bdc.getId());
    }

    private long getDeviceDsiId(long deliverInsance, long bdcId) {
        String condition1 = "deliverysessioninstanceid=" + deliverInsance;
        String condition2 = "bdcid=" + bdcId;
        return getDeviceDsiId(condition1, condition2);
    }

    private long getDeviceDsiId(String condition1, String condition2) {
        String sql = "SELECT id FROM devicedeliverysessioninstanceinfo WHERE " + condition1
                + " AND " + condition2;
        Query result = em.createNativeQuery(sql);
        @SuppressWarnings(UNCHECKED_STR)
        List<Long> results = result.getResultList();
        if (results.size() == 0) {
            return -1;
        }
        return results.get(0);
    }

    public long getDeviceDsiIdByUsiAndBDC(long usiId, long bdcId) {
        String condition1 = "userserviceinstanceid=" + usiId;
        String condition2 = "bdcid=" + bdcId;
        return getDeviceDsiId(condition1, condition2);
    }

    public List<DeviceDeliverySessionInstanceInfo> getDeviceDeliverySessionInfosbyEmbmsSessionIdAndBDC(
            long eMBMSSessionId, BDC bdc) {
        TypedQuery<DeviceDeliverySessionInstanceInfo> result = em
                .createQuery(
                        "SELECT deviceDeliverySessionInstanceInfo FROM DeviceDeliverySessionInstanceInfo as deviceDeliverySessionInstanceInfo WHERE"
                                + " deviceDeliverySessionInstanceInfo.eMBMSSessionId = :eMBMSSessionId and deviceDeliverySessionInstanceInfo.bdc = :bdc",
                        DeviceDeliverySessionInstanceInfo.class);
        result.setParameter("eMBMSSessionId", eMBMSSessionId);
        result.setParameter(BDC_STR, bdc);
        return getMultipleResult(result);
    }

    public List<Long> getDeviceDeliverySessionInstanceIdsbyUSI(
            UserServiceInstance userServiceInstance) {
        TypedQuery<Long> result = em
                .createQuery(
                        "SELECT deviceDeliverySessionInstanceInfo.deliverySessionInstanceId FROM DeviceDeliverySessionInstanceInfo "
                                + "as deviceDeliverySessionInstanceInfo WHERE deviceDeliverySessionInstanceInfo.userServiceInstance = :userServiceInstance",
                        Long.class);
        result.setParameter(USERSERVICEINSTANCE_STR, userServiceInstance);
        return getMultipleResult(result);
    }

    public List<Long> getDeviceDeliverySessionInstancePIdsbyUSI(
            UserServiceInstance userServiceInstance) {
        TypedQuery<Long> result = em
                .createQuery(
                        "SELECT deviceDeliverySessionInstanceInfo.id FROM DeviceDeliverySessionInstanceInfo "
                                + "as deviceDeliverySessionInstanceInfo WHERE deviceDeliverySessionInstanceInfo.userServiceInstance = :userServiceInstance",
                        Long.class);
        result.setParameter(USERSERVICEINSTANCE_STR, userServiceInstance);
        return getMultipleResult(result);
    }

    /**
     * It also try to update device content status. It means it will not have any exceptions or warnings when it's not success because of lost any per
     * conditions.
     *
     * @param content
     */
    public void updateContentStatus(Content content) {
        OnRequest onRequest = content.getOnRequest();
        Content contentInDB = getContent(onRequest, content.getContentId());
        updateStatusInTableContent(content.getContentId(), onRequest.getId(), content.getStatus());
        List<DeviceDeliverySessionInstanceInfo> infos = onRequest.getUserServiceInstance()
                .getDeviceDeliverySessionInstanceInfos();
        for (DeviceDeliverySessionInstanceInfo info : infos) {
            mergeDeviceContentStatus(info.getId(), contentInDB.getId(), content.getStatus());
        }
    }

    public List<RRTemplate> getRRTemplateList(SearchQuery restfulQuery) {
        TypedQuery<RRTemplate> listQuery = QueryBuilder.getQueryForSearch(em, restfulQuery,
                RRTemplate.class);
        return listQuery.getResultList();
    }

    public Long getTotalRRTempate(SearchQuery restfulQuery) {
        TypedQuery<Long> totalQuery = QueryBuilder.getQueryForTotal(em, restfulQuery,
                RRTemplate.class);
        return totalQuery.getSingleResult().longValue();
    }

    public void deleteRrTemplate(String rrName) {
        em.createQuery("DELETE FROM RRTemplate rr where rr.name = :rrName")
                .setParameter("rrName", rrName).executeUpdate();
        em.flush();
    }

    public CacheStatusType getDeviceCacheStatus(long deviceDsiId, long cacheId) {
        return getdeviceCacheInfo(deviceDsiId, cacheId).getStatus();
    }

    public void deleteCachesAndDeviceCacheInfo(List<Cache> caches) {
        for (Cache cache : caches) {
            deleteDeviceCacheInfos(cache.getId(), cache.getOnRequest().getUserServiceInstance()
                    .getId());
            em.remove(getCacheFromDB(cache));
            em.flush();
        }
    }

    public void deleteDeviceCacheInfos(long cacheId, long userServiceInstanceId) {
        String sql = "DELETE FROM devicecacheinfo AS dci WHERE dci.id IN (Select dci.id FROM devicecacheinfo AS dci JOIN devicedeliverysessioninstanceinfo AS dsi ON dci.devicedsiid=dsi.id WHERE dci.cacheid=%d AND dsi.userserviceinstanceid=%d)";
        em.createNativeQuery(String.format(sql, cacheId, userServiceInstanceId)).executeUpdate();
        em.flush();
    }

    public void deleteCaches(long usiId, long bdcId, List<Cache> caches) {
        deleteDeviceCacheInfo(usiId, bdcId, caches);

        for (Cache cache : caches) {
            OnRequest onrequest = cache.getOnRequest();
            if (queryDeviceCacheInfoCount(cache.getId(), onrequest.getId()) == 0) {
                // if no related devicecacheinfo , delete cache
                deleteCacheInTableCache(cache);
            } else {
                // update the cache status after remove the device cache info.
                ContentStatusUtil.updateCacheStatusWithDeviceCacheInfoStatus(cache, onrequest);
            }
        }

    }

    public void deleteDeviceCacheInfo(long usiId, long bdcId, List<Cache> caches) {
        String cacheIdList = "-1";
        for (Cache cache : caches) {
            cacheIdList = cacheIdList + SUFFIX_COMMA + cache.getId();
        }
        String sql = "DELETE FROM devicecacheinfo AS dci WHERE id IN "
                + "(SELECT dci.id FROM devicecacheinfo AS dci "
                + "JOIN devicedeliverysessioninstanceinfo as dsi ON dci.devicedsiid=dsi.id AND dsi.bdcid=%d AND userserviceinstanceid=%d AND dci.cacheid IN (%s))";
        String sqlStr = String.format(sql, bdcId, usiId, cacheIdList);
        em.createNativeQuery(sqlStr).executeUpdate();
        em.flush();
    }

    private void deleteCacheInTableCache(Cache cache) {
        em.remove(getCacheFromDB(cache));
        em.flush();
    }

    private Cache getCacheFromDB(Cache cache) {
        return em.find(Cache.class, cache.getId());
    }

    public int getBDCNumberByUsi(long usiId) {
        String sql = "SElECT COUNT(id) FROM devicedeliverysessioninstanceinfo WHERE userserviceinstanceid=%d";
        Query q = em.createNativeQuery(String.format(sql, usiId));
        return (Integer) q.getSingleResult();
    }

    public Long getBroadcastCountsUnderService(String serviceName) {
        long resultCount = em
                .createQuery(
                        "SELECT COUNT(broadcast.id) FROM Broadcast as broadcast JOIN broadcast.service service WHERE service.name = :serviceName",
                        Long.class).setParameter(SERVICENAME_STR, serviceName).getSingleResult()
                .longValue();
        return resultCount;
    }

    public ContentStatusType getContentStatus(Long contentId, Long deliveryInstanceId) {
        String sql = "SELECT status FROM content AS c "
                + "JOIN onrequest AS oq ON c.contentid=%d AND oq.id=c.onrequestid "
                + "JOIN devicedeliverysessioninstanceinfo AS ddsii ON ddsii.userserviceinstanceid=oq.usiid AND ddsii.deliverysessioninstanceid=%d";
        sql = String.format(sql, contentId, deliveryInstanceId);
        ContentStatusType status = ContentStatusType.PENDING;
        Query q = em.createNativeQuery(sql);

        if (!q.getResultList().isEmpty()) {
            status = ContentStatusType.valueOf((String) q.getResultList().get(0));
        }

        return status;
    }

    public Content getContent(OnRequest onRequest, long contentId) {
        String querySQL = "SELECT content FROM Content as content WHERE content.onRequest = :onRequest and content.contentId = :contentId";
        TypedQuery<Content> result = em.createQuery(querySQL, Content.class)
                .setParameter(ONREQUEST_STR, onRequest).setParameter(CONTENTID_STR, contentId);
        return result.getSingleResult();
    }

    public List<Content> getCheckDeliveryStatusContent(Date endTime) {
        TypedQuery<Content> result = em.createQuery(
                "SELECT content FROM Content content WHERE content.stopTime <= : endTime",
                Content.class);
        result.setParameter("endTime", endTime);
        return getMultipleResult(result);
    }

    public List<DeviceContentInfo> getCheckDeviceContentInfos(Content content) {
        TypedQuery<DeviceContentInfo> result = em.createQuery(
                "SELECT dc FROM DeviceContentInfo dc WHERE dc.contentId=:contentId",
                DeviceContentInfo.class);
        result.setParameter(CONTENTID_STR, content.getContentId());
        return getMultipleResult(result);
    }

    public Long getTotalServiceClass(SearchQuery searchQuery) {
        TypedQuery<Long> totalQuery = QueryBuilder.getQueryForTotal(em, searchQuery,
                ServiceClass.class);
        return totalQuery.getSingleResult().longValue();
    }

    public List<ServiceClass> getServiceClassList(SearchQuery searchQuery) {
        TypedQuery<ServiceClass> listQuery = QueryBuilder.getQueryForSearch(em, searchQuery,
                ServiceClass.class);
        return listQuery.getResultList();
    }

    public List<BroadcastArea> getBroadcastAreaList(SearchQuery searchQuery) {
        TypedQuery<BroadcastArea> listQuery = QueryBuilder.getQueryForSearch(em, searchQuery,
                BroadcastArea.class);
        List<BroadcastArea> broadcastAreaList = listQuery.getResultList();
        for (BroadcastArea broadcastArea : broadcastAreaList) {
            broadcastArea.getSas();
        }
        return broadcastAreaList;
    }

    public Long getTotalBroadcastArea(SearchQuery searchQuery) {
        TypedQuery<Long> totalQuery = QueryBuilder.getQueryForTotal(em, searchQuery,
                BroadcastArea.class);
        return totalQuery.getSingleResult().longValue();
    }

    public List<Broadcast> getAlreadyStartBroadcastList() {
        TypedQuery<Broadcast> result = em
                .createQuery(
                        "SELECT broadcast FROM Broadcast broadcast WHERE broadcast.announcedStartTime < :nowTime and broadcast.announcedStopTime > :nowTime ORDER BY broadcast.announcedStopTime DESC",
                        Broadcast.class);
        result.setParameter(NOWTIME_STR, TimeUtils.getUTCTimeNow());
        return getMultipleResult(result);
    }

    public List<Broadcast> getWillStartBroadcastList(int startTimeOffsetInSecond) {
        TypedQuery<Broadcast> result = em
                .createQuery(
                        "SELECT broadcast FROM Broadcast broadcast WHERE broadcast.announcedStartTime >= :nowTime and broadcast.announcedStartTime <= :offsetTime ORDER BY broadcast.announcedStopTime DESC",
                        Broadcast.class);
        result.setParameter(NOWTIME_STR, TimeUtils.getUTCTimeNow());
        result.setParameter("offsetTime",
                TimeUtils.add(TimeUtils.getUTCTimeNow(), startTimeOffsetInSecond));
        return getMultipleResult(result);
    }

    public List<Content> getWillStartContentList(int contentStartTimeOffsetInSecond) {
        TypedQuery<Content> result = em
                .createQuery(
                        "SELECT content FROM Content content WHERE content.startTime >= :nowTime and content.startTime <= :startTime",
                        Content.class);
        result.setParameter(NOWTIME_STR, TimeUtils.getUTCTimeNow());
        result.setParameter(STARTTIME_STR,
                TimeUtils.add(TimeUtils.getUTCTimeNow(), contentStartTimeOffsetInSecond));
        return getMultipleResult(result);
    }

    public List<Content> getOngoingContentList() {
        TypedQuery<Content> result = em
                .createQuery(
                        "SELECT content FROM Content content WHERE content.startTime < :nowTime and content.stopTime > :nowTime and content.refreshInterval = :refreshInterval",
                        Content.class);

        result.setParameter(NOWTIME_STR, TimeUtils.getUTCTimeNow());
        int refreshInterval = 0;
        result.setParameter("refreshInterval", refreshInterval);
        return getMultipleResult(result);
    }

    /**
     * get content by record id (note: id is not contentId)
     *
     * @param id
     * @return
     */
    public Content getContentByRecordId(long id) {
        return em.find(Content.class, id);
    }

    public CacheStatusType getCacheStatus(String fileURI, Long deliveryInstanceId) {
        String sql = "SELECT DISTINCT status FROM cache AS c "
                + "JOIN onrequest AS oq ON c.fileuri='%s' AND oq.id=c.onrequestid "
                + "JOIN devicedeliverysessioninstanceinfo AS ddsii ON ddsii.userserviceinstanceid=oq.usiid AND ddsii.deliverysessioninstanceid=%d";
        sql = String.format(sql, fileURI, deliveryInstanceId);

        CacheStatusType status = CacheStatusType.PENDING;
        Query q = em.createNativeQuery(sql);

        if (!q.getResultList().isEmpty()) {
            status = CacheStatusType.valueOf((String) q.getSingleResult());
        }

        return status;
    }

    public List<SDCHService> getSDCHService() {
        TypedQuery<SDCHService> result = em.createQuery("SELECT sd FROM SDCHService sd",
                SDCHService.class);
        List<SDCHService> sdchServiceList = result.getResultList();
        for (SDCHService sdchservice : sdchServiceList) {
            Service service = sdchservice.getService();
            List<Broadcast> broadcastList = service.getBroadcastList();
            for (Broadcast broadcast : broadcastList) {
                broadcast.getUserServiceInstances();
            }
            List<Bearer> bearerList = service.getBearerList();
            for (Bearer bear : bearerList) {
                bear.getUserService();
            }
        }
        return sdchServiceList;
    }

    public String getServiceClassByValue(String serviceClass) {
        TypedQuery<String> query = em.createQuery(
                "SELECT sc.name FROM ServiceClass sc WHERE sc.serviceClass = :serviceClass",
                String.class).setParameter("serviceClass", serviceClass);
        return getSingleResult(query);
    }

    public String getFECTemplateByOverhead(int overhead) {
        TypedQuery<String> query = em.createQuery(
                "select f.name from FECTemplate f where f.overhead = :overhead", String.class)
                .setParameter("overhead", overhead);
        return getSingleResult(query);
    }

    public void deleteSDCHService(String serviceName) {
        String sql = "DELETE FROM sdchservice AS t WHERE sid IN (SELECT s.id FROM service AS s WHERE s.name = '%s')";
        sql = String.format(sql, serviceName);
        em.createNativeQuery(sql).executeUpdate();
        em.flush();
    }

    public void updateCacheStatus(Cache cache, CacheStatusType cacheStatus) {
        Cache cacheDB = em.find(Cache.class, cache.getId());
        cacheDB.setStatus(cacheStatus);
        em.merge(cacheDB);
    }

    public void updateCacheEstimatedTransTime(Cache cache, long estimatedTransTime) {
        Cache cacheDB = em.find(Cache.class, cache.getId());
        cacheDB.setEstimatedTransTime(estimatedTransTime);
        em.merge(cacheDB);
    }

    public List<DeviceDeliverySessionInstanceInfo> getDeviceDeliverySessionInfoListbyUSI(
            UserServiceInstance userServiceInstance) {
        TypedQuery<DeviceDeliverySessionInstanceInfo> result = em
                .createQuery(
                        "SELECT deviceDeliverySessionInstanceInfo FROM DeviceDeliverySessionInstanceInfo as deviceDeliverySessionInstanceInfo WHERE"
                                + " deviceDeliverySessionInstanceInfo.userServiceInstance = :userServiceInstance",
                        DeviceDeliverySessionInstanceInfo.class);
        result.setParameter(USERSERVICEINSTANCE_STR, userServiceInstance);
        return getMultipleResult(result);
    }

    public DeviceDeliverySessionInstanceInfo getDeviceDeliverySessionInstanceInfoListbyUSIAndBdc(
            UserServiceInstance userServiceInstance, BDC bdc) {
        TypedQuery<DeviceDeliverySessionInstanceInfo> result = em
                .createQuery(
                        "SELECT deviceDeliverySessionInstanceInfo FROM DeviceDeliverySessionInstanceInfo as deviceDeliverySessionInstanceInfo WHERE"
                                + " deviceDeliverySessionInstanceInfo.userServiceInstance = :userServiceInstance AND deviceDeliverySessionInstanceInfo.bdc = :bdc",
                        DeviceDeliverySessionInstanceInfo.class);
        result.setParameter(USERSERVICEINSTANCE_STR, userServiceInstance);
        result.setParameter(BDC_STR, bdc);
        return getSingleResult(result);
    }

    public List<Content> getContents(OnRequest onRequest) {
        TypedQuery<Content> result = em.createQuery(
                "SELECT content FROM Content as content WHERE content.onRequest = :onRequest",
                Content.class).setParameter(ONREQUEST_STR, onRequest);
        return getMultipleResult(result);
    }

    public Content getContentDeliveryByContentIdAndDeliveryInstanceId(long contentId,
            long deliveryInstanceId, String bdcName) {
        long deviceDsiId = getDeviceDsiId(deliveryInstanceId, bdcName);
        if (deviceDsiId < 0) {
            return null;
        }

        DeviceDeliverySessionInstanceInfo deviceInfo = em.find(
                DeviceDeliverySessionInstanceInfo.class, deviceDsiId);
        if ((deviceInfo != null) && (deviceInfo.getUserServiceInstance() != null)) {
            OnRequest onRequest = getOnRequest(deviceInfo.getUserServiceInstance());

            List<Content> contentList = getContents(onRequest);
            for (Content content : contentList) {
                if (content.getContentId() == contentId) {
                    return content;
                }
            }
        }
        return null;
    }

    public void updateContentStatus(Content content, ContentStatusType status) {
        Content contentInDB = em.find(Content.class, content.getId());
        contentInDB.setStatus(status);
        em.merge(contentInDB);
    }

    public List<Gateway> getGateway() {
        TypedQuery<Gateway> query = em.createQuery("select g from Gateway g ", Gateway.class);
        return getMultipleResult(query);
    }

    public List<MmeAddress> getMmeAddress() {
        TypedQuery<MmeAddress> query = em.createQuery("select m from MmeAddress m ",
                MmeAddress.class);
        return getMultipleResult(query);
    }

    public List<BDC> getBDCListByGwAddress(String gwAddress) {
        TypedQuery<BDC> result = em.createQuery(
                "SELECT bdc FROM BDC bdc WHERE bdc.gwAddress = :gwAddress ", BDC.class)
                .setParameter("gwAddress", gwAddress);
        return getMultipleResult(result);
    }

    public Gateway getGwByHost(String gwAddress) {
        TypedQuery<Gateway> query = em.createQuery(
                "select gw from Gateway gw where gw.host = :gwAddress", Gateway.class)
                .setParameter("gwAddress", gwAddress);
        return getSingleResult(query);
    }

    public List<ServiceArea> getServiceAreaWithBDC(long bdcId) {
        TypedQuery<BDC> result = em.createQuery("SELECT b FROM BDC as b WHERE b.id = :id",
                BDC.class).setParameter("id", bdcId);
        return getSingleResult(result).getSas();
    }

    public List<BDC> getBDCListFromDeviceDeliverySessionInfoByUserServiceAndStatus(
            UserService userService, String status) {
        TypedQuery<BDC> result = em
                .createQuery(
                        "SELECT deviceDeliverySessionInfo.bdc FROM DeviceDeliverySessionInfo as deviceDeliverySessionInfo WHERE"
                                + " deviceDeliverySessionInfo.userService = :userService and deviceDeliverySessionInfo.status = :status",
                        BDC.class);
        result.setParameter("userService", userService);
        result.setParameter("status", status);
        return getMultipleResult(result);
    }

    public List<MmeAddress> getMmeAddressWithSai() {
        TypedQuery<MmeAddress> result = em.createQuery("select m from MmeAddress m ",
                MmeAddress.class);
        loadSaiList(getMultipleResult(result));
        return getMultipleResult(result);
    }

    private void loadSaiList(List<MmeAddress> multipleResult) {
        for (MmeAddress mmeAddress : multipleResult) {
            mmeAddress.getSaiList();
        }
    }

    public void deleteDeviceContentInfo(long contentRecordId,
            long deviceDeliverySessionInstanceInfoRecordId) {
        em.createQuery(
                "delete from DeviceContentInfo deviceContentInfo where deviceContentInfo.contentId = :contentId and deviceContentInfo.deviceDsiId = :deviceDsiId",
                DeviceContentInfo.class).setParameter(CONTENTID_STR, contentRecordId)
                .setParameter("deviceDsiId", deviceDeliverySessionInstanceInfoRecordId)
                .executeUpdate();
        em.flush();
    }

    public void removeDeviceContentInfoList(Content content) {
        List<DeviceContentInfo> deviceContentInfoList = getDeviceContentInfos(content);
        if (deviceContentInfoList != null && !deviceContentInfoList.isEmpty()) {
            for (DeviceContentInfo deviceInfo : deviceContentInfoList) {
                em.remove(deviceInfo);
            }
        }
    }

    public List<Service> getServicesbyServiceClass(ServiceClass sc) {
        TypedQuery<Service> result = em.createQuery(
                "select service FROM Service service where service.serviceClass = :serviceclass",
                Service.class);
        result.setParameter("serviceclass", sc);
        return getMultipleResult(result);
    }

    public List<Bearer> getBearers() {
        TypedQuery<Bearer> result = em.createQuery("SELECT bearer FROM Bearer as bearer",
                Bearer.class);
        return getMultipleResult(result);
    }

    public AlarmText getAlarmTextByText(String text) {
        TypedQuery<AlarmText> result = em.createQuery(
                "SELECT at FROM AlarmText as at WHERE at.text = :text", AlarmText.class)
                .setParameter("text", text);
        return getSingleResult(result);
    }

    ActiveAlarm getActiveAlarmById(long id) {
        TypedQuery<ActiveAlarm> result = em.createQuery(
                "SELECT activeAlarm FROM ActiveAlarm as activeAlarm WHERE activeAlarm.id = :id",
                ActiveAlarm.class).setParameter("id", id);
        return getSingleResult(result);
    }

    ActiveAlarm getActiveAlarmByCombinedId(String moduleId, String errorCode, String resourceId,
            String origSourceIp) {
        TypedQuery<ActiveAlarm> result = em
                .createQuery(
                        "SELECT aa FROM ActiveAlarm as aa WHERE aa.module = :module and aa.errorCode = :errorCode and aa.resourceId = :resourceId and aa.origSourceIp = :origSourceIp",
                        ActiveAlarm.class).setParameter("module", moduleId)
                .setParameter("errorCode", errorCode).setParameter("resourceId", resourceId)
                .setParameter("origSourceIp", origSourceIp);
        return getSingleResult(result);
    }

    HistoryAlarm getHistoryAlarmById(long id) {
        TypedQuery<HistoryAlarm> result = em.createQuery(
                "SELECT ha FROM HistoryAlarm as ha WHERE ha.id = :id", HistoryAlarm.class)
                .setParameter("id", id);
        return getSingleResult(result);
    }

    public List<Object> getTotalActiveAlarm(SearchQuery searchQuery) {
        TypedQuery<Object> listQuery = QueryBuilder.getQueryForGroupByAndTotal(em, searchQuery,
                ActiveAlarm.class, Object.class);
        return listQuery.getResultList();
    }

    public List<Object> getTotalHistoryAlarm(SearchQuery searchQuery) {
        TypedQuery<Object> listQuery = QueryBuilder.getQueryForGroupByAndTotal(em, searchQuery,
                HistoryAlarm.class, Object.class);
        return listQuery.getResultList();
    }

    public List<ActiveAlarm> getActiveAlarmList(SearchQuery searchQuery) {
        TypedQuery<ActiveAlarm> listQuery = QueryBuilder.getQueryForSearch(em, searchQuery,
                ActiveAlarm.class);
        return listQuery.getResultList();
    }

    public List<HistoryAlarm> getHistoryAlarmList(SearchQuery searchQuery) {
        TypedQuery<HistoryAlarm> listQuery = QueryBuilder.getQueryForSearch(em, searchQuery,
                HistoryAlarm.class);
        return listQuery.getResultList();
    }

    public UserAlarmSearchCriteria getAlarmSearchCriteria(BmcUser user, int operationType) {
        TypedQuery<UserAlarmSearchCriteria> result = em
                .createQuery(
                        "SELECT sc FROM UserAlarmSearchCriteria as sc WHERE sc.user = :user and sc.operationType = :operationType",
                        UserAlarmSearchCriteria.class).setParameter("user", user)
                .setParameter("operationType", operationType);
        return getSingleResult(result);
    }

    public boolean isConflictedTmgiInDB(List<String> tmgiNameList) {
        TypedQuery<Bearer> result = em.createQuery(
                "SELECT bearer FROM Bearer bearer WHERE bearer.tmgi IN (:tmgis)", Bearer.class);
        result.setParameter("tmgis", tmgiNameList);
        if (getMultipleResult(result).size() == 0) {
            return false;
        }
        return true;
    }

    public void deleteAlarmSearchCriteria(BmcUser user) {
        em.createQuery("DELETE FROM UserAlarmSearchCriteria sc WHERE sc.user = :user",
                UserAlarmSearchCriteria.class).setParameter("user", user).executeUpdate();
        em.flush();
    }

    public List<Broadcast> getCancelledAndProcessingBroadcast() {
        TypedQuery<Broadcast> result = em
                .createQuery(
                        "SELECT broadcast FROM Broadcast broadcast WHERE broadcast.operState = :operstatus and broadcast.adminState = :adminstatus",
                        Broadcast.class);
        result.setParameter("operstatus", OperStateType.processing.value());
        result.setParameter("adminstatus", AdminStateType.CANCELLED.value());
        return getMultipleResult(result);
    }

    public List<ActiveAlarm> getActiveAlarmByMutipleIds(List<Integer> activeAlarmId) {
        TypedQuery<ActiveAlarm> result = em.createQuery(
                "SELECT a FROM ActiveAlarm a where a.id in (:alarmId)", ActiveAlarm.class);
        result.setParameter("alarmId", activeAlarmId);
        return result.getResultList();
    }

    public List<AlarmRemedyText> getAlarmRemedyText(String module, String errorCode) {
        TypedQuery<AlarmRemedyText> result = em
                .createQuery(
                        "SELECT a FROM AlarmRemedyText a where a.module = :module and a.errorCode = :errorCode",
                        AlarmRemedyText.class);
        result.setParameter("module", module).setParameter("errorCode", errorCode);
        return result.getResultList();
    }

    public List<MWConfigContent> getMWConfigContent() {
        TypedQuery<MWConfigContent> result = em.createQuery(
                "SELECT mwconfigcontent FROM MWConfigContent mwconfigcontent",
                MWConfigContent.class);
        return result.getResultList();
    }

    public List<MWConfigSchema> getMWConfigSchema() {
        TypedQuery<MWConfigSchema> result = em.createQuery(
                "SELECT mwconfigschema FROM MWConfigSchema mwconfigschema ORDER BY mwconfigschema.id ASC", MWConfigSchema.class);
        return result.getResultList();
    }

    public MWConfigSchema getMWConfigSchemaByName(String name) {
        TypedQuery<MWConfigSchema> result = em
                .createQuery(
                        "SELECT mwconfigschema FROM MWConfigSchema as mwconfigschema WHERE mwconfigschema.schemaName = :schemaName",
                        MWConfigSchema.class);
        result.setParameter("schemaName", name);
        return getSingleResult(result);
    }

    public void storeOperationHistory(OperationHistory operhis) {
        em.persist(operhis);
        em.flush();
    }

    public List<OperationHistory> getOperHistory(String serviceName, String broadcastName) {
        String sql = "SELECT oh FROM OperationHistory as oh WHERE oh.serviceName=:serviceName AND oh.expired=FALSE";
        if (broadcastName == null) {
            sql += " AND oh.broadcastName is null";
        } else {
            sql += " AND oh.broadcastName=:broadcastName";
        }
        sql += " ORDER BY oh.time desc";
        TypedQuery<OperationHistory> result = em.createQuery(sql, OperationHistory.class);
        result.setParameter(SERVICENAME_STR, serviceName);
        if (broadcastName != null) {
            result.setParameter("broadcastName", broadcastName);
        }
        return getMultipleResult(result);
    }

    public List<ReportingMetadata> getAllReportingMetadataDesc() {
        TypedQuery<ReportingMetadata> result = em.createQuery(
                "SELECT rmd FROM ReportingMetadata as rmd order by rmd.triggerTime desc",
                ReportingMetadata.class);
        return getMultipleResult(result);
    }

    public List<ReportingMetadata> getReportingMetadataByTriggerTimeAsc(String serviceName,
            String broadcastName) {
        TypedQuery<ReportingMetadata> result = em
                .createQuery(
                        "SELECT rmd FROM ReportingMetadata as rmd WHERE rmd.serviceName=:serviceName AND rmd.broadcastName=:broadcastName order by rmd.triggerTime asc",
                        ReportingMetadata.class);
        result.setParameter(SERVICENAME_STR, serviceName);
        result.setParameter("broadcastName", broadcastName);
        return getMultipleResult(result);
    }

    public List<ReportingMetadata> getOldestReportingMetadata(int num) {
        TypedQuery<ReportingMetadata> result = em.createQuery(
                "SELECT rmd FROM ReportingMetadata as rmd order by rmd.triggerTime asc",
                ReportingMetadata.class);
        result.setMaxResults(num);
        return getMultipleResult(result);
    }

    public void updateOperationHistoryExpired(String serviceName, String broadcastName) {
        String sql = "UPDATE operationhistory SET expired=TRUE WHERE serviceName='%s' AND broadcastName='%s' AND expired=FALSE;";
        if (broadcastName == null) {
            sql = "UPDATE operationhistory SET expired=TRUE WHERE serviceName='%s' AND expired=FALSE;";
        }
        em.createNativeQuery(String.format(sql, serviceName, broadcastName)).executeUpdate();
    }

    public void deleteActiveAlarms(int days) {
        String activeSql = "DELETE FROM ActiveAlarm active WHERE date_trunc('day',(current_date - active.lastUpdateTime)) > interval '"
                + days + " days'";
        em.createNativeQuery(activeSql).executeUpdate();
        em.flush();
    }

    public void deleteHistoryAlarms(int days) {
        String historySql = "DELETE FROM HistoryAlarm history WHERE date_trunc('day',(current_date - lastUpdateTime)) > interval '"
                + days + " days'";
        em.createNativeQuery(historySql).executeUpdate();
        em.flush();
    }

    public void deleteUnUsedAlarmText() {
        String sql = "DELETE FROM AlarmText as t where not exists(select h.id from HistoryAlarm as h where h.modelDescriptionid=t.id or h.activeDescriptionid=t.id) and "
                + "not exists(select a.id from ActiveAlarm as a where (a.modelDescriptionid=t.id or a.activeDescriptionid=t.id))";
        em.createNativeQuery(sql).executeUpdate();
        em.flush();
    }

    public ENodeB getEnodeByName(String name) {
        TypedQuery<ENodeB> result = em.createQuery(
                "SELECT a FROM ENodeB as a WHERE a.name = :name", ENodeB.class).setParameter(
                NAME_STR, name);
        return getSingleResult(result);
    }

    public List<Long> getSaiListByEnodebId(long id) {
        TypedQuery<Long> result = em.createQuery(
                "SELECT a.sai FROM SaiEnodebMapping as a WHERE a.eNodeB.id = :id", Long.class)
                .setParameter("id", id);
        List<Long> resultList = getMultipleResult(result);
        List<Long> returnList = new ArrayList<Long>();
        for (Long curSai : resultList) {
            returnList.add(curSai);
        }
        Collections.sort(returnList);
        return returnList;
    }

    public void removeSaiEnodebMappingByENodeBId(long eNodeBId) {
        em.createQuery("delete from SaiEnodebMapping t where  t.eNodeB.id = :id",
                SaiEnodebMapping.class).setParameter("id", eNodeBId).executeUpdate();
        em.flush();
    }

    public void removeSaiEnodebMapping(Long sai, String name) {
        em.createQuery(
                "delete from SaiEnodebMapping t where t.sai = :sai and t.eNodeB.name = :name",
                SaiEnodebMapping.class).setParameter(SAI_STR, sai).setParameter(NAME_STR, name)
                .executeUpdate();
        em.flush();
    }

    public List<OriginPipeResource> getOriginPipeListByEnodebId(long id) {
        TypedQuery<OriginPipeResource> result = em.createQuery(
                "SELECT a FROM OriginPipeResource as a WHERE a.eNodeB.id = :id ",
                OriginPipeResource.class).setParameter("id", id);
        List<OriginPipeResource> resultList = getMultipleResult(result);
        List<OriginPipeResource> returnList = new ArrayList<OriginPipeResource>();
        for (OriginPipeResource resource : resultList) {
            returnList.add(resource);
        }
        return returnList;
    }

    public void removeOriginPipeResourceByEnodebIdAndPipeId(long enodebId, String pipename) {
        em.createQuery(
                "delete from OriginPipeResource t where t.eNodeB.id = :enodebId and t.pipename = :pipeName",
                OriginPipeResource.class).setParameter("enodebId", enodebId)
                .setParameter(PIPENAME_STR, pipename).executeUpdate();
        em.flush();
    }

    public void removeOriginPipeResourceByEnodeBId(long eNodeBId) {
        em.createQuery("delete from OriginPipeResource t where t.eNodeB.id = :enodebId",
                OriginPipeResource.class).setParameter("enodebId", eNodeBId).executeUpdate();
        em.flush();
    }

    public List<PipeResource> getPipeResourcesByServiceAreas(String pipeName,
            Set<ServiceArea> serviceAreas) {
        String sql = "select p from PipeResource p where p.sai in (:sais) and p.pipename = :pipeName";
        return em.createQuery(sql, PipeResource.class)
                .setParameter(SAIS_STR, CollectionUtils.getAllFieldValues(serviceAreas, SAI_STR))
                .setParameter(PIPENAME_STR, pipeName).getResultList();
    }

    public List<PipeResource> getPipeResourcesByServiceAreas(Set<ServiceArea> serviceAreas) {
        String sql = "select p from PipeResource p where p.sai in (:sais)";
        return em.createQuery(sql, PipeResource.class)
                .setParameter(SAIS_STR, CollectionUtils.getAllFieldValues(serviceAreas, SAI_STR))
                .getResultList();
    }

    public List<ENodeB> getENodeBsByServiceAreas(Set<ServiceArea> serviceAreas) {
        Set<Long> sais = new HashSet<Long>();
        for (ServiceArea serviceArea : serviceAreas) {
            sais.add(serviceArea.getSai());
        }
        String sql = "select s.eNodeB from SaiEnodebMapping s where s.sai in (:sais)";
        return em.createQuery(sql, ENodeB.class).setParameter(SAIS_STR, sais).getResultList();
    }

    public List<ENodeB> getENodeBsByServiceAreaID(long serviceAreaId) {
        String sql = "select s.eNodeB from SaiEnodebMapping s where s.sai = :serviceAreaID";
        return em.createQuery(sql, ENodeB.class).setParameter("serviceAreaID", serviceAreaId)
                .getResultList();
    }

    public List<SaiEnodebMapping> getSaiENodeBMappingsByENodeBs(List<ENodeB> eNodeBs) {
        String sql = "select s from SaiEnodebMapping s join fetch s.eNodeB where s.eNodeB in (:eNodeBs)";
        return em.createQuery(sql, SaiEnodebMapping.class).setParameter("eNodeBs", eNodeBs)
                .getResultList();
    }

    public List<ENodeBGroup> getENodeBGroupByPipeNameAndENodeBNameList(String pipeName,
            List<String> eNodeBNameList) {
        String sql = "select e from ENodeBGroup e where e.pipeName = :pipeName and e.firstenodebingroup in :eNodeBNameList";
        return em.createQuery(sql, ENodeBGroup.class).setParameter(PIPENAME_STR, pipeName)
                .setParameter("eNodeBNameList", eNodeBNameList).getResultList();
    }

    public OriginPipeResource getOriginPipeResource(String pipeName, ENodeB eNodeB) {
        StringBuilder sql = new StringBuilder("select p from OriginPipeResource p where 1=1 ");
        Map<String, Object> parameterKeyValues = CollectionUtils.getMap();
        if (CollectionUtils.isNotEmpty(pipeName)) {
            sql.append(" and p.pipename = :pipeName ");
            parameterKeyValues.put(PIPENAME_STR, pipeName);
        }
        if (eNodeB != null) {
            sql.append(" and p.eNodeB = :eNodeB ");
            parameterKeyValues.put("eNodeB", eNodeB);
        }
        TypedQuery<OriginPipeResource> query = em.createQuery(sql.toString(), OriginPipeResource.class);
        for (Map.Entry<String, Object> entry : parameterKeyValues.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return getSingleResult(query);
    }

    public List<OccupiedPipe> getOccupiedPipes(Date broadcastStarttime, Date broadcastStopTime,
            Set<ServiceArea> serviceAreas, Collection<String> pipeNames) {
        if (broadcastStarttime == null || broadcastStopTime == null) {
            // if broadcastStarttime or broadcastStopTime is null, return empty result
            return CollectionUtils.getList();
        }
        StringBuilder sql = new StringBuilder("select o from OccupiedPipe o where 1=1 ");
        Map<String, Object> parameterKeyValues = CollectionUtils.getMap();
        if (!CollectionUtils.isEmpty(serviceAreas)) {
            // use o.sai.sai instead o.sai to avoid N+1 sql to improve efficient
            sql.append(" and o.sai in (:sais) ");
            // type of o.sai is long
            parameterKeyValues
                    .put(SAIS_STR, CollectionUtils.getAllFieldValues(serviceAreas, SAI_STR));
        }
        if (!CollectionUtils.isEmpty(pipeNames)) {
            sql.append(" and o.pipename in (:pipeNames) ");
            parameterKeyValues.put("pipeNames", pipeNames);
        }
        TypedQuery<OccupiedPipe> query = null;
        if (broadcastStarttime != null && broadcastStopTime != null) {
            sql.append(" and ((o.startTime < :broadcastStarttime and o.stopTime > :broadcastStarttime) or (o.startTime >= :broadcastStarttime and o.startTime < :broadcastStopTime)) ");
            query = em.createQuery(sql.toString(), OccupiedPipe.class);
            parameterKeyValues.put("broadcastStarttime", broadcastStarttime);
            parameterKeyValues.put("broadcastStopTime", broadcastStopTime);
        } else {
            throw new IllegalArgumentException("broadcasat startTime/stopTime can not be null");
        }
        for (Map.Entry<String, Object> entry : parameterKeyValues.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    public void addOccupiedPipe(OccupiedPipe occupiedPipe) {
        persistAndFlush(occupiedPipe);
    }

    public int releasePipeAndTMGI(long bearerId, Date startTime, Date stopTime) {
        String sql = "DELETE FROM OccupiedPipe o WHERE o.bearerId=:bearerId AND o.startTime=:startTime AND o.stopTime=:stopTime";
        int count = em.createQuery(sql).setParameter("bearerId", bearerId).setParameter(STARTTIME_STR, startTime).setParameter("stopTime", stopTime)
                .executeUpdate();
        em.flush();
        return count;
    }

    public List<PipeResource> getPipeByName(String pipeName) {
        String sql = "SELECT pipe FROM PipeResource as pipe where pipe.pipename=:pipeName";
        TypedQuery<PipeResource> result = em.createQuery(sql, PipeResource.class);
        result.setParameter(PIPENAME_STR, pipeName);
        return getMultipleResult(result);
    }

    public List<String> getENBNameListBySai(Long sai) {
        TypedQuery<String> result = em.createQuery(
                "SELECT s.eNodeB.name FROM SaiEnodebMapping as s WHERE s.sai = :sai", String.class)
                .setParameter(SAI_STR, sai);
        return getMultipleResult(result);
    }

    public List<OriginPipeResource> getPipeByENBName(String eName) {
        TypedQuery<OriginPipeResource> result = em.createQuery(
                "SELECT opr FROM OriginPipeResource as opr  WHERE opr.eNodeB.name = :eName",
                OriginPipeResource.class).setParameter("eName", eName);
        return getMultipleResult(result);
    }

    public PipeResource getPipeBySaiAndPipe(Long sai, String pipename) {
        TypedQuery<PipeResource> result = em
                .createQuery(
                        "SELECT pr FROM PipeResource as pr  WHERE pr.sai.sai=:sai and pr.pipename = :pipeName",
                        PipeResource.class).setParameter(SAI_STR, sai)
                .setParameter(PIPENAME_STR, pipename);
        return getSingleResult(result);
    }

    public List<PipeResource> getPipeBySai(Long sai) {
        TypedQuery<PipeResource> result = em.createQuery(
                "SELECT pr FROM PipeResource as pr  WHERE pr.sai.sai=:sai", PipeResource.class)
                .setParameter(SAI_STR, sai);
        return getMultipleResult(result);
    }

    public List<OriginPipeResource> getOriginPipeResourceWithPipeAndEnodebName(String pipeName,
            List<String> eNodeBName) {
        String sql = "select p from OriginPipeResource p where p.pipename = :pipeName and p.eNodeB.name in (:eNodeBName)";
        return getMultipleResult(em.createQuery(sql, OriginPipeResource.class)
                .setParameter(PIPENAME_STR, pipeName).setParameter("eNodeBName", eNodeBName));
    }

    public TMGI getTMGIByValue(String tmgiValue) {
        TypedQuery<TMGI> result = em.createQuery("SELECT t FROM TMGI as t  WHERE t.value=:value",
                TMGI.class).setParameter("value", tmgiValue);
        return getSingleResult(result);
    }

    public List<OccupiedPipe> getOccupiedPipesBySaiAndPipe(long sai, String pipeName) {
        String sql = "select o from OccupiedPipe o where o.pipename = :pipeName and o.sai in (:sai)";
        return getMultipleResult(em.createQuery(sql, OccupiedPipe.class)
                .setParameter(PIPENAME_STR, pipeName).setParameter(SAI_STR, sai));
    }

    public List<OccupiedPipe> getOccupiedPipesBySaiIdListAndPipeList(Date broadcastStarttime,
            Date broadcastStopTime, List<Long> saiList, Set<String> pipeNames) {
        StringBuilder sql = new StringBuilder("select o from OccupiedPipe o where 1=1 ");

        Map<String, Object> parameterKeyValues = CollectionUtils.getMap();

        if (!CollectionUtils.isEmpty(saiList)) {
            sql.append(" and o.sai in ("
                    + StringUtils.collectionToDelimitedString(saiList, SUFFIX_COMMA) + ") ");
            // type of o.sai.sai is long
        }
        if (!CollectionUtils.isEmpty(pipeNames)) {
            sql.append(" and o.pipename in (:pipeNames) ");
            parameterKeyValues.put("pipeNames",
                    StringUtils.collectionToDelimitedString(pipeNames, SUFFIX_COMMA));
        }

        if (broadcastStarttime != null && broadcastStopTime != null) {
            sql.append(" and ((o.startTime < :broadcastStarttime and o.stopTime > :broadcastStarttime) or (o.startTime >= :broadcastStarttime and o.startTime < :broadcastStopTime)) ");
            parameterKeyValues.put("broadcastStarttime", broadcastStarttime);
            parameterKeyValues.put("broadcastStopTime", broadcastStopTime);
        }

        TypedQuery<OccupiedPipe> query = em.createQuery(sql.toString(), OccupiedPipe.class);

        for (Map.Entry<String, Object> entry : parameterKeyValues.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    public List<String> getAllInUsedTMGI() {
        String sql = "SELECT tmgi FROM OccupiedPipe WHERE stoptime >= '%s'";
        Query q = em.createNativeQuery(String.format(sql, TimeUtils.getUTCNowString(0) + "+00"));
        @SuppressWarnings(UNCHECKED_STR)
        List<String> allInUsedTmgi = q.getResultList();
        return allInUsedTmgi;
    }

    public List<PipeResource> getPipeBySaiAndBandwidth(long sai, float bandwidth) {
        TypedQuery<PipeResource> result = em
                .createQuery(
                        "SELECT pr FROM PipeResource as pr  WHERE pr.sai.sai=:sai and pr.bandwidth >= :bandwidth",
                        PipeResource.class).setParameter(SAI_STR, sai)
                .setParameter("bandwidth", bandwidth);
        return getMultipleResult(result);
    }

    public List<PipeResource> getAvailablePipeList(SearchQuery searchQuery) {
        TypedQuery<PipeResource> listQuery = QueryBuilder.getQueryForSearch(em, searchQuery,
                PipeResource.class);
        return listQuery.getResultList();
    }

    public List<PipeLock> getOverlappedPipeLocks(String pipeName, Date startTime, Date stopTime) {
        String sql = "SELECT p FROM PipeLock as p where p.pipeName = :pipeName and "
                + "((p.startTime < :startTime and p.stopTime > :startTime) or (p.startTime >= :startTime and p.startTime < :stopTime))";
        TypedQuery<PipeLock> result = em.createQuery(sql, PipeLock.class)
                .setParameter(PIPENAME_STR, pipeName).setParameter(STARTTIME_STR, startTime)
                .setParameter("stopTime", stopTime);
        return getMultipleResult(result);
    }

    public void removePipeLockById(long pipeLockId) {
        // remove by id
        PipeLock pipeLockInDB = em.find(PipeLock.class, pipeLockId);
        if (pipeLockInDB != null) {
            em.remove(pipeLockInDB);
        }
    }

    public void removePipeLock(String pipeName, Date startTime, Date stopTime) {
        String sql = "delete from PipeLock p where p.pipeName = :pipeName and p.startTime = :startTime and p.stopTime = :stopTime";
        em.createQuery(sql).setParameter(PIPENAME_STR, pipeName).setParameter(STARTTIME_STR, startTime)
                .setParameter("stopTime", stopTime).executeUpdate();
    }

    public void deleteUnusedTMGI() {
        String sql = "DELETE FROM TMGI AS t WHERE t.id NOT IN (SELECT m.tmgiid FROM tmgi_originpiperesource_mapping AS m)";
        em.createNativeQuery(sql).executeUpdate();
        em.flush();
    }

    public Frequency getFrequencyByEarfcn(int nEarfcn) {
        TypedQuery<Frequency> result = em.createQuery(
                "SELECT frequency FROM Frequency as frequency WHERE frequency.earfcn = :nEarfcn",
                Frequency.class).setParameter("nEarfcn", nEarfcn);
        return getSingleResult(result);
    }

    public List<ServiceArea> getServiceAreaByVpipe(boolean vpipe) {
        String sql = "select sa from ServiceArea sa where sa.vpipe = :vpipe";
        return getMultipleResult(em.createQuery(sql, ServiceArea.class)
                .setParameter("vpipe", vpipe));
    }

    public List<String> getEricENBNameListBySai(Long sai) {
        TypedQuery<String> result = em
                .createQuery(
                        "SELECT s.eNodeB.name FROM SaiEnodebMapping as s WHERE s.sai = :sai AND s.eNodeB.vpipe = false",
                        String.class).setParameter("sai", sai);
        return getMultipleResult(result);
    }

    public ENodeBGroup getEnodeBGroup(String pipeName, float bandwidth, String saigroup) {
        TypedQuery<ENodeBGroup> result = em
                .createQuery(
                        "SELECT t FROM ENodeBGroup as t  WHERE t.pipeName=:pipeName and t.bandwidth=:bandwidth and t.saigroup=:saigroup",
                        ENodeBGroup.class).setParameter(PIPENAME_STR, pipeName)
                .setParameter("bandwidth", bandwidth).setParameter("saigroup", saigroup);
        if (result == null) {
            return null;
        }
        return getSingleResult(result);
    }

    public Long getAvailablePipeTotal(SearchQuery searchQuery) {
        @SuppressWarnings(UNCHECKED_STR)
        TypedQuery<PipeResource> listQuery = QueryBuilder.getQueryForSearch(em, searchQuery,
                PipeResource.class);
        return (long) (listQuery.getResultList().size());
    }

    public Long getTotalPipeList(SearchQuery restfulQuery) {
        TypedQuery<Long> totalQuery = QueryBuilder.getQueryForTotal(em, restfulQuery,
                PipeResource.class);
        return totalQuery.getSingleResult().longValue();
    }

    public List<BroadcastArea> getBroadcastAreasByServiceAreas(Collection<Long> sais) {
        TypedQuery<BroadcastArea> result = em.createQuery("SELECT DISTINCT b FROM BroadcastArea b JOIN b.sas s WHERE s.sai in :sais", BroadcastArea.class)
                .setParameter(SAIS_STR, sais);
        return getMultipleResult(result);
    }

    public Broadcast getBroadcastByOccupiedpipe(OccupiedPipe op) {
        Service service = getServiceByBearerId(op.getBearerId());
        if (service == null) {
            return null;
        }
        return getBroadcastByStartTimeStopTimeAndService(op.getStartTime(), op.getStopTime(), service);
    }

    private Broadcast getBroadcastByStartTimeStopTimeAndService(Date startTime, Date stopTime, Service service) {
        TypedQuery<Broadcast> result = em
                .createQuery(
                        "SELECT b FROM Broadcast AS b WHERE b.service = :service AND b.startTime = :startTime AND b.stopTime = :stopTime",
                        Broadcast.class).setParameter("service", service).setParameter("startTime", startTime).setParameter("stopTime", stopTime);

        return getSingleResult(result);
    }

    private Service getServiceByBearerId(Long bearerId) {
        TypedQuery<Service> result = em
                .createQuery(
                        "SELECT b.service FROM Bearer AS b WHERE b.id = :bearerId",
                        Service.class).setParameter("bearerId", bearerId);

        return getSingleResult(result);
    }
}
