package org.led.tools.BmcDbOperator.common;

import java.util.Map;

import org.led.tools.BmcDbOperator.data.RestfulQuery;
import org.led.tools.BmcDbOperator.data.SearchQuery;

public class GenericBusinessLogicObject {

    public enum BusinessLogicSubObjectType {
        BroadcastArea, // networkplanning BL
        ServiceArea, // networkplanning BL
        SAIPorting, // networkplanning BL
        BDCDevice, // networkplanning BL
        ServiceClass, // networkplanning BL
        Pipe,
        Service, // service BL
        Bearer, // service BL
        UserService, // service BL
        
        Broadcast, // Broadcast BL
        UserServiceInstance, // Broadcast BL
        ContentChannel, // Broadcast BL
        Content, // Content BL
        LiveContent, // Content BL
        OnRequestContent, // Content BL
        ActiveAlarm, // Alarm BL
        AlarmSearchCriteria, //Alarm BL
        ActiveAlarmExport, //Alarm BL
        HistoryAlarm, // Alarm BL
        HistoryAlarmExport, //Alarm BL
        DownloadAlarmExport,//Alarm BL
        Role, // Administration BL
        User, // Administration BL
        RRTemplate, // Administration BL
        FRTemplate, // Administration BL
        FecTemplate, // Administration BL
        QoeTemplate, // Administration BL
        SystemSetting, // Administration BL
        SDCHService, // Administration BL
        CleanCache,  // Administration BL
        AllowGetBearInfo,  //Administration BL
        MiddlewareConfig,  //Administration BL
        
        INGEST_MPD, // content handler type
        MPD, // content handler type
        CACHE, // content handler type
        CONTENT_DELIVERY, // content handler type
        FILE_SCHEDULE, // content handler type
        COMBINED, // content handler type
        CONTENTREFRESH, // content handler type
        CONTENT_RE_PROVISIONING // content handler type

    }

    public enum BusinessLogicOpertionType {
        Create, Delete, Update, Get, AddContentTriggeredByBroadcastBL, // broadcast
        DeleteAllContentsTriggeredByBroadcstBL, // broadcast
        UpdateMpdTriggeredByBroadcastBL, // broadcast
        UpdateBroadcastAreaAndUSITriggeredByNetworkplanningBL, // service area
        UpdateMaxMediaBitRateTriggeredByServiceBL, // broadcast
        UpdateServiceAreaAndUSIByNetworkplanningBL,
        RemoveAllContentsTriggeredByBroadcstBL, // broadcast
        UpdateContinuousContentStatusByBroadcastBL, //broadcast
        
        BroadcastApproveTriggeredByBroadcastBL, //broadcast
        BroadcastCancelTriggeredByBroadcastBL, //broadcast
        BroadcastStoppedTriggeredByBroadcastBL, //broadcast
        BroadcastStartedTriggeredByBroadcastBL, //broadcast
        BroadcastStopTimeUpdateTriggeredByBroadcastBL, //broadcast
        BroadcastDeleteTriggeredByBroadcastBL, //broadcast
        BroadcastReprovisioningTriggeredByBroadcastBL, //broadcast
        UpdateUserAndServiceMappingTriggeredByServiceBL, //service
        UpdateUserAndServiceClassMappingTriggeredByServiceClassBL, //service class
        // Schedule, //broadcast
        // Pending, //broadcast
        // Approve, //broadcast
        // Cancel, //broadcast , content
        // Verify, //service area, broadcast area
        // Re-provision, //service, broadcast , content
        // Extend, //Broadcast time.
        // Refresh //Content
        
        ReprovisioningBroadcastTriggeredByBdcManagerBL // bdc manager
    }

    private Map<String, String> filterCondition;

    private RestfulQuery query;

    private SearchQuery searchQuery;

    private Map<String, String> reservedObj;

    private BusinessLogicSubObjectType objectType;

    private BusinessLogicOpertionType opertionType;

    public Map<String, String> getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(Map<String, String> filterCondition) {
        this.filterCondition = filterCondition;
    }

    public RestfulQuery getQuery() {
        return query;
    }

    public void setQuery(RestfulQuery query) {
        this.query = query;
    }

    public SearchQuery getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(SearchQuery searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Map<String, String> getReservedObj() {
        return reservedObj;
    }

    public void setReservedObj(Map<String, String> reservedObj) {
        this.reservedObj = reservedObj;
    }

    public BusinessLogicSubObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(BusinessLogicSubObjectType objectType) {
        this.objectType = objectType;
    }

    public BusinessLogicOpertionType getOpertionType() {
        return opertionType;
    }

    public void setOpertionType(BusinessLogicOpertionType opertionType) {
        this.opertionType = opertionType;
    }

}
