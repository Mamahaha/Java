
package org.led.tools.bmcDbOperator.common;

/**
 * @author exiaowu
 * 
 */
public final class GlobalConst {

    public static final String ALL_BDC_EVENT_TOPIC = "org/led/bmc/bdc";
    public static final String ALL_BL_NEEDED_EVENT_TOPIC = "org/led/bmc/bdc/bl/*";
    
    //BEARER event
    public static final String BEARER_ACTIVATED_EVENT_TOPIC = "org/led/bmc/bdc/bl/bearer_activated";
    public static final String BEARER_START_TEMPORARY_FAILED_EVENT_TOPIC = "org/led/bmc/bdc/bl/bearer_start_temporary_failed";
    public static final String BEARER_START_PERMANENT_FAILED_EVENT_TOPIC = "org/led/bmc/bdc/bl/bearer_start_permanent_failed";
    public static final String BEARER_STOP_TEMPORARY_FAILED_EVENT_TOPIC = "org/led/bmc/bdc/bl/bearer_stop_temporary_failed";
    public static final String BEARER_STOP_PERMANENT_FAILED_EVENT_TOPIC = "org/led/bmc/bdc/bl/bearer_start_permanent_failed";
    public static final String BEARER_UNEXPECTED_STOP_EVENT_TOPIC = "org/led/bmc/bdc/bl/bearer_unexpect_stop";
    public static final String SESSION_UNEXPECTED_STOP_EVENT_TOPIC = "org/led/bmc/bdc/bl/session_unexpect_stop";
    
    //Cache event
    public static final String FILE_READY_FOR_DELIVERY_EVENT_TOPIC = "org/led/bmc/bdc/bl/file_ready_for_delivery";
    public static final String FILE_PREPARATION_FAILURE_EVENT_TOPIC = "org/led/bmc/bdc/bl/file_preparation_failure";
    public static final String FILE_DOWNLOAD_FAILED_EVENT_TOPIC = "org/led/bmc/bdc/bl/file_download_failed";
    public static final String BMSC_CACHE_AT_80_PERCENT_OCCUPANCY_EVENT_TOPIC = "org/led/bmc/bdc/bl/bmsc_cache_at_80_percent_occupancy";
    public static final String BMSC_CACHE_FULL_EVENT_TOPIC = "org/led/bmc/bdc/bl/bmsc_cache_full";

    //Content delivery event
    //content 1.x event(legacy) will only be handle by carousel
    public static final String FILE_TRANSMISSION_STOPPED_EVENT_TOPIC_1X = "org/led/bmc/bdc1x/bl/file_transmission_stopped";
    public static final String FILE_DELIVERED_EVENT_TOPIC_1X = "org/led/bmc/bdc1x/bl/file_delivered";
    public static final String FILE_DELIVERED_START_EVENT_TOPIC_1X = "org/led/bmc/bdc1x/bl/file_delivered_start";
    public static final String FILE_DELETE_EVENT_TOPIC_1X = "org/led/bmc/bdc1x/bl/file_delete";
    public static final String CONTINUOUS_CONTENT_DELIVERY_FAILURE_EVENT_TOPIC_1X = "org/led/bmc/bdc1x/bl/continuous_content_delivery_failure";//TODO carousel need handle?

    //content 2.0 event 
    public static final String FILE_TRANSMISSION_STOPPED_EVENT_TOPIC = "org/led/bmc/bdc/bl/file_transmission_stopped";
    public static final String FILE_DELIVERED_EVENT_TOPIC = "org/led/bmc/bdc/bl/file_delivered";
    public static final String FILE_DELIVERED_START_EVENT_TOPIC = "org/led/bmc/bdc/bl/file_delivered_start";
    public static final String FILE_DELETE_EVENT_TOPIC = "org/led/bmc/bdc/bl/file_delete";
    //TODO not exists
    public static final String CONTINUOUS_CONTENT_DELIVERY_FAILURE_EVENT_TOPIC = "org/led/bmc/bdc/bl/continuous_content_delivery_failure";
    
    //Websocket event
    public static final String WEB_SOCKECT_BROADCAST_LIST_CHANGED_EVENT_TOPIC = "org/led/bmc/ws/broadcast_list";
    public static final String WEB_SOCKECT_BROADCAST_DETAIL_CHANGED_EVENT_TOPIC = "org/led/bmc/ws/broadcast_detail";
    public static final String WEB_SOCKECT_ALARM_LIST_CHANGED_EVENT_TOPIC = "org/led/bmc/ws/alarm_list";

    //Broadcast stop event for Reporting
    public static final String BROADCAST_STOP_EVENT_TOPIC = "org/led/bmc/bl/broadcast_stop";

    private GlobalConst() {
    }

}
