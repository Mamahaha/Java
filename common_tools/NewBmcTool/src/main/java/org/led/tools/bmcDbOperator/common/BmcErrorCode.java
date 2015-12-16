
package org.led.tools.bmcDbOperator.common;


/**
 * all the error code enum used for asp error code
 */
public enum BmcErrorCode {
    /**
     * 1000-1999 is generic error
     * 2000-2999 is networkplanning business logic error
     * 3000-3999 is service business logic error
     * 4000-4999 is broadcast business logic error
     * 5000-5999 is content business logic error
     * 6000-6999 is other business logic error
     *
     *
     */

    //DATABASE_CONNECTION_ERROR("1000", "The database is disconnected when %s, please check the database state."),
    //BDC_CONNECTION_ERROR("1001", "The BDC has a connection error when %s, please check the BDC device state."),
    //FAILED_TO_GET_DATA_FROM_DB("1002", "Failed to get the data from the database when %s"),
    //USER_AUTHENTICATION_FAILURE("1003", "Failed to pass the authentication when %s"),
    APPLICATION_INTERNAL_ERROR("1004", "The application has an internal error when %s, check the BMC log."),
    REQUEST_HAS_CONFLICT("1005", "The request has a conflict when %s"),
    BDC_LIST_IS_EMPTY("1006", "Cannot find any BDC device when %s"),
    VALIDATE_REQUEST_PARAMETER_FAILED("1007","Failed to validate the request parameter, reason: %s"),
    LIMIT_OUT_OF_RANGE("1008","Too many results return and exceed the limitation."),
    LIMIT_OR_OFFSET_INVALID("1009","The limit or offset parameter is invalid, both of them need to be set correctly."),
    LICENSE_ERROR("1010","The license is invalid."),
    MAX_DELETION_CAPACITY("1011", "Delete too many items in one request, %s"),
    OBJECT_NAME_SHOULD_NOT_CHANGED("1012","The name of %s should not be changed."),


    //network planning
    SERVICE_AREA_NOT_EXIST("2000", "The service area %s does not exist."),
    BDC_NOT_EXIST("2001", "The BDC %s does not exist."),
    SERVICE_AREA_ALREADY_EXIST("2002", "The service area %s already exists."),
    BDC_ALREADY_EXIST("2003", "The BDC %s already exists."),
    SERVICE_AREA__IS_USED_BY_BROADCAST_AREA("2004", "The service area %s is used by the broadcast area."),
    SERVICE_AREA_IS_ASSCIATED_WITH_ENB("2005", "The service area %s is associated with the eNb."),
    SERVICE_AREA__IS_ILLEGAL("2006", "The service area is illegal."),
    SERVICE_CLASS_CAPACITY_OVERLFOW("2007","The capacity of service class overflow,the max number of service class is %s."),
    //SERVICE_AREA__MAX_DETETION_CAPACITY("2008", Delete too many service areas in one request. The system deletion capacity in one request is %s"),
    //SERVICE_AREA_SAI_SHOULD_NOT_BE_CHANGED("2009", "The service area SAI %s should not be changed."),
    BDC_IS_USED_BY_SERVICE_AREA("2010", "The BDC %s is used by the service area."),
    SOME_BDCS_ARE_USED_BY_SERVICE_AREA("6133", "Some BDCs are used by the service area."),
    BDC_VENDOR_IS_ILLEGAL("2011", "The BDC vendor %s is not supported in the BMC."),
    BDC_CONNECTION_VERSION_IS_ILLEGAL("2012", "The BDC connection version %s is not supported in the BMC."),
    BDC_DELETION_MIXED_ERROR("2013", "Failed to delete the BDCs, reason: %s"),
    //BDC_PORT_IS_ILLEGAL("2014", "The BDC port %s is invalid, it should be 0<port<=65535."),
    BDC_NUMBER_EXCEED("2015", "Cannot create the BDCs more than %s in the BMC."),
    BDC_CONNECTION_PROTOCOL_IS_ILLEGAL("2016", "BDC connection protocol %s is not support in BMC."),
    ALU_BDC_NCM_OR_SAI_NOT_EMPTY("2017", "The NCM user, NCM password and SAI provisioning URL should be all empty for the Alcatel-Lucent BDC."),
    BDC_NAME_LENGTH_EXCEED("2018", "The BDC name %s exceeds 127 characters."),
    BDC_ORIGIN_IS_ILLEGAL("2019", "The BDC origin %s is invalid, it must not be empty and it must be unique for all BDC(s)."),
    //BDC_NAME_SHOULD_NOT_BE_CHANGED("2021", "The BDC name %s can not be changed."),
    FREQUENCY_NOT_EXIST("2022", "The frequency %s does not exist or no default frequency is in the settings."),
    BDC_BASE_PATH_IS_ILLEGAL("2023", "The BDC base path %s is invalid, it must not have any initial or trailing '/'."),
    ALL_BDCS_ARE_USED_BY_SERVICE_AREA("2024", "The BDCs are used by the service area."),
    INVALID_GATEWAY("2025", "Different gatways must have different hosts."),
    IPADDRESSS_IS_NOT_MATCHED("2026", "The IP address is different from the IP stack."),
    IPADDRESSS_IS_DUPLICATE("2027", "The IP address can not be in more than one pool in one gateway."),
    ALL_BDCS_NOT_FOUND("2028", "No BDC exists."),
    SERVICE_AREA_WITH_TOO_MUCH_BDC_POLYGON_OR_FREQUENCY("2029", "The service area is with too much BDCs, polygon or frequency: %s"),

    //service
    SERVICE_CLASS_NOT_EXIST("3000", "The service class %s does not exist."),
    SERVICE_CLASS_ALREADY_EXIST("3001", "The service class %s already exists."),
    BROADCAST_AREA_NOT_EXIST("3002", "The broadcast area %s does not exist."),
    BROADCAST_AREA_ALREADY_EXIST("3003", "The broadcast area %s already exist."),
    SERVICE_NOT_EXIST("3004", "The service %s does not exist."),
    SERVICE_ALREADY_EXIST("3005", "The service %s already exist."),
    BROADCAST_AREA_IS_USED_BY_SERVICE("3006", "The broadcast area %s is used by service."),
    BROADCAST_AREA_CAPACITY_OVERLFOW("3007", "The capacity of broadcast area overflow, the max number of broadcast area is %s."),
    BROADCAST_AREA_WITH_OVERLFOWED_SA("3008","while creating one broadcast area, the number of the associated service area is %s, which is more than the max capacity %s."),
    //BROADCAST_AREA_NAME_SHOULD_NOT_BE_CHANGED("3007", "The broadcast area name %s should not be changed."),
    //BROADCAST_AREA__MAX_DETETION_CAPACITY("3008", "Delete too many broadcast area in one request."),
    FAILED_TO_PROVISIONING_BDCS("3009", "Failed to provision the BDCs: %s"),
    FAILED_TO_DELETE_SERVICES("3010", "Failed to delete services, reason: %s"),
    SERVICE_ASSOCIATED_WITH_BROADCAST("3011", "The service '%s' is associated with the broadcast."),
    //SERVICE_MAX_DETETION_CAPACITY("3012", "Delete too many services in one request, the system deletion capacity in one request is %s"),
    FAILED_TO_DELETE_SERVICE_CLASS("3013", "Failed to delete the service classes, reason: %s"),
    FAILED_TO_DELETE_SERVICE_AREA("3014", "Failed to delete service areas, reason: %s"),
    FAILED_TO_DELETE_BROADCAST_AREA("3015", "Failed to delete broadcast areas, reason: %s"),

    BROADCAST_AREA_NOT_VERIFIED("3016", "The broadcast area %s is not verified."),
    FAILED_TO_CREATE_SERVICE_CLASS("3017", "Failed to create service class %s"),
    FAILED_TO_CREATE_SDCH_SERVICE("3018", "Failed to create the SDCH service %s when creating %s, reason: %s"),
    FAILED_TO_ROLLBACK_AFTER_CREATING_SDCH_SERVICE_FAILED("3019", "Failed to create SDCH service %s when creating %s, reason: %s. After that, also failed to rollback %s caused by: %s"),
    USER_SERVICE_NOT_EXIST("3020", "The user service: %s does not exist."),
    BROADCAST_AREA_AND_MEDIABITRATE_CAN_NOT_UPDATED("3021", "Can't update the broadcast area or max media bitrate of service %s, because the status of associated broadcast is approved or cancelling."),
    //SERVICE_CLASS_NAME_SHOULD_NOT_BE_CHANGED("3021", "The service area name %s should not be changed."),
    // Service error code 3110-3149
    //INVALID_BROADCAST_AREA_STATUS_FOR_SERVICE("3110", "The broadcast area state is invalid for the service(%s) operation."),
    QOE_METRICS_TEMPLATE_DOES_NOT_EXIST("3111", "QoE Metrics Template '%s' does not exist."),
    INVALID_LOSS_OF_OBJECTS_WITH_ONREQUEST_MODE_ON_SERVICE("3112","The loss of objects is invalid with on-request mode for the service."),
    BEARER_COUNT_MAX_FOR_SERVICE("3113","The bear number under the service reaches the limitation. The maximum number is %s"),
    USER_SERVICE_COUNT_MAX_FOR_BEAR("3114","The user service number under the bearer reaches the limitation. The maximum number is %s"),
    INVALID_MAX_BIT_RATE_RANGE("3115","The bit rate range is [20-20000]."),
    NOT_EXIST_MCC_OR_MNC("3116","The MCC or MNC is not configured."),
    NOT_EXIST_URL_OR_URN("3117","The base URL or base URN is not configured."),
    CONFLICTED_TMGI_FOR_BEARER("3118","TMGI number has already been used."),
    INVALID_BROADCAST_STATUS_FOR_SERVICE("3119","The operation not allowed because there is more than one approved broadcast %s"),
    //SERVICE_CLASS__MAX_DELETION_CAPACITY("3120", "Delete too many service classes in one request. The system deletion capacity in one request is %s"),
    SERVICE_CLASS_IN_USED("3121", "The service class is used by the service: %s ..."),
    SERVICE_NAME_ILLEGAL("3122", "The service name '%s' contains invalid characters."),
    FR_TEMPLATE_SHOULD_NULL_WHEN_CONTINUEOUS_MODE("3123", "FrTemplate should not be set when UserService mode is Continueous."),

    //Broadcast related
    USER_SERVICE_INSTANCE_IS_INCOMPLETE("4000", "The %s User Service Instance (USI) of the %s broadcast for the %s service is incomplete."),
    BROADCAST_VALIDATION_FAILS("4001", "Failed to validate the broadcast %s under the service %s, caused by: %s"),
    BROADCAST_NOT_EXIST("4002", "The broadcast %s does not exist."),
    BROADCAST_ALREADY_EXIST("4003", "The broadcast '%s' under the service '%s' already existes."),
    BEARER_DOES_NOT_EXIST("4004", "The bearer %s does not exist."),
    INVALID_SERVICE_STATUS_FOR_BROADCAST("4005", "The broadcast cannot be done, because the service status is partially provisioned."),
    INVALID_BROADCAST_TIME("4006", "The broadcast start time or stop time is invalid:%s"),
    INVALID_USI_FOR_BROADCAST("4007", "The USI names under the broadcast should be unique."),
    USER_SERVICE_IS_USED("4008", "One user service can not be used by the mutiple USIs."),
    USER_SERVICE_NOT_EXSIT("4009", "The user service does not exsit."),
    APPROVED_NOT_START_BROADCAST_CAN_NOT_BE_DELETED("4010", "The approved/not-started broadcast can not be deleted."),
    BROADCAST_NOT_ALLOWED_DELETE_BY_USI_OPR_STATUS("4011","The broadcast can not be deleted: because the USI(%s) operation state is DEACTIVATING."),
    APPROVED_IN_PROCESS_BROADCAST_CAN_NOT_BE_DELETED("4012", "Approved/In-process broadcast can not be deleted"),
    CALCELLED_IN_PROCESS_BROADCAST_CAN_NOT_BE_DELETED("4013", "Cancelled/In-process broadcast can not be deleted"),
    PARTIALLY_PROVISIONED_BROADCAST_CAN_NOT_BE_DELETED("4014", "Partially Provisioned broadcast can not be deleted"),
    BROADCAST_OVERLAP("4015","The broadcast overlaps another one."),
    INVALID_BROADCAST_MODIFY_TIME_BY_STATUS("4016","The start time or stop time of broadcast can not be modified, when it's state is not pending."),
    INVALID_BROADCAST_MODIFY_BY_CANCEL("4017","The broadcast can not be modified when the admin state is CANCLE."),
    INVALID_BROADCAST_WITH_FILESCHEDULE("4018","The start time or stop time of broadcast can not be modified because of the conflict with the existing file schedule."),
    INVALID_MODIFY_BROADCAST_START_TIME("4019","The broadcast start time can not be modified early than the current time."),
    INVALID_MODIFY_BROADCAST_TIME("4020","The broadcast start time must before broadcast stop time."),
    INVALID_MODIFY_BROADCAST_STOP_TIME("4021","The broadcast extension stop time must be later than the broadcast stop time."),
    INVALID_MODIFY_BROADCAST_ADMIN_STATUS("4022","The broadcast admin can't be modified with other parameters."),
    INVALID_MODIFY_BROADCAST_USI("4023","The broadcast can't apply new User Service Instance(USI)."),
    FEC_TEMPLATE_MODIFY_REJECT_BY_ADMIN("4024","The FEC template of a User Service Instance(USI) for the broadcast can't be modified when admin state is approved or cancelled."),
    INVALID_BROADCAST_MODIFY_BY_ARRROVE("4025","The broadcast can not be modified when the admin status is approved and the operation status is stopped."),
    INVALID_BROADCAST_MODIFY_BY_TEMEPLATE("4026","The broadcast can not be modified, because the FR/RR template can not be added if the broadcast is approved and no FR/RR template is configured before."),
    INVALID_BROADCAST_NAME("4027","The broadcast name can not be modified."),
    FAILED_TO_VALIDATE_BROADCAST_APPROVAL_WHEN_CANCEL("4028", "Failed to validate the broadcast approval, caused by: %s"),
    USER_SERVICE_INSTANCE_NOT_EXSIT("4029", "User Service Instance does not exist."),
    FAILED_TO_REACTIVE_BROADCAST_WHEN_APPROVED("4030", "The broadcast can not be reactived when the broadcast is not approved."),
    FAILED_TO_REACTIVE_BROADCAST_WHEN_NOT_START("4031", "The broadcast can not be reactived when the broadcast does not start."),
    FAILED_TO_REACTIVE_BROADCAST_WHEN_STOPPED("4032", "The broadcast can not be reactived when broadcast stopped."),
    FAILED_TO_REVERT_BROADCAST_WHEN_NOT_CANCELLED("4033", "The broadcast can not be reverted when broadcast is not cancelled."),
    FAILED_TO_REVERT_BROADCAST_WHEN_NOT_START("4034", "The broadcast can not be reverted when it already started."),
    FAILED_TO_REVERT_BROADCAST_WHEN_NOT_PROVISION("4035", "The broadcast can not be reverted when it does not provision successfully."),
    FAILED_TO_REVERT_BROADCAST_WHEN_DEACTIVE_USI("4036", "The broadcast can not be reverted when USI contains deactivating delivery session instance(s)."),
    FAILED_TO_REPROVISION_BROADCAST_WHEN_PENDING("4037", "The broadcast can not be re-provisioning when it is pending."),
    MAX_BITRATE_OR_TOTAL_MEDIABYTES_IS_INVALID("4100", "The FEC is invalid or the GBR bandwidth is too small for the USI mode, no media content can be delivered with this bandwidth."),
    ILLEGAL_CONTENT_MODE_TYPE("4101", "Illegal content mode type: %s for %s broadcast under the %s service."),
    ALL_BDCS_CREATE_EMBMS_SESSION_FAILED("4200", "All BDCs failed to create the eMBMS session."),
    ALL_BDCS_CREATE_DELIVERY_SESSION_INSTANCE_FAILED("4201", "All BDCs failed to add delivery session instance."),
    NOT_ALL_BDCS_REACTIVATE_EMBMS_SESSION_SUCCESSFULLY("4300","Not all BDCs succeed to re-activate the eMBMS session."),
    FAILED_TO_REVERT_BROADCAST("4301","Failed to revert the broadcast,reason: %s"),
    FAILED_TO_REPROVISION_BROADCAST("4302","Failed to reprovision the broadcast on the following BDC(s) %s, reason: %s"),
    BROADCAST_OVERRIDE_T_G_VALUE_MUST_BE_POSITIVE("4304", "The broadcast T&G override value must be positive"),
    FAILED_TO_VALIDATE_BROADCAST_DELETION("4305", "Failed to validate broadcast deletion, caused by: %s"),
    ILLEGAL_BROADCAST_OPERATION_REQUEST("4306", "Illegal broadcast operation request: %s"),
    FAILED_TO_VALIDATE_BROADCAST_APPROVAL("4307", "Failed to validate broadcast approval, caused by: %s"),
    FAILED_TO_VALIDATE_BROADCAST_CANCEL("4308", "Failed to validate broadcast cancel, caused by: %s"),
    BROADCAST_STOPTIME_MUST_LOCATE_AFTER_STARTTIME("4309", "The broadcast stopTime must later than the startTime."),
    BROADCAST_EXTENSION_STOPTIME_CAN_NOT_LOCATE_BEFORE_STOPTIME("4310", "The broadcast extension stopTime can not locate before the stopTime."),
    BROADCAST_STARTTIME_CAN_NOT_BE_EARLIER_THAN_CURRENT_TIME("4311", "The broadcast startTime can not be earlier than the current time."),
    CAN_NOT_CREATE_CONTINUOUS_USI_WITH_FR_TEMPLATE("4312", "Can not create continuous USI with FR template."),
    INVALID_DATE_TIME_FORMAT("4313", "Invalid dateTime format: '%s'."),
    INVALID_LIMIT_VALUE("4314", "Invalid limit value: '%s'."),
    INVALID_OFFSET_VALUE("4315", "Invalid offset value: '%s'."),
    INVALID_BROADCAST_OPERSTATE("4316", "Broadcast operState cannot be stopped when cancel."),
    BROADCAST_STARTTIME_STOPTIME_CAN_NOT_BE_NULL("4317", "Broadcast startTime/stopTime can not be null."),
    FAILED_TO_RESERVE_PIPE("4318", "Broadcast %s approve failed due to reserve Capacity-allocation/TMGI failed."),
    INVALID_PIPE_ASSIGMENT("4318", "Invalid capacity allocation assignment to USI, broadcast %s should assign capacity allocation first and ensure same capacity allocation under one bearer."),
    INVALID_STATUS_UPDATE_BROADCAST("4319", "Can't update the broadcast when the status is %s"),

    PIPE_IS_INVALID("4401", "Capacity allocation '%s' is invalid."),
    PIPE_IS_NOT_SAME_ALL_USI_ONE_BEARER("4402", "ALL user service instance of same bearer '%s' has different capacity allocation."),
    PIPE_HAS_NOT_ENOUGH_BANDWIDTH("4403", "Capacity allocation of broadcast area '%s' has no available bandwidth."),
    INVALID_MODIFY_BROADCAST_USI_PIPE("4404", "Capacity allocation of user service instance can not be updated when broadcast admin status is approved or cancelled."),
    PIPE_DOES_NOT_EXIST("4405", "Capacity allocation '%s' doesn't exist."),
    PIPE_DOES_NOT_BELONG_TO_BROADCASTAREA("4406", "Inconsistency of Capacity allocation id and braodcast area: %s"),
    //Content related
    ONREQUST_NOT_EXIST("5001", "The UserServiceInstance %s type is not onrequest."),
    CACHE_FILEURI_EXISTS("5002", "Cache file already exist %s under same UserServiceInstance."),
    CACHE_NOT_EXIST("5003", "%s"),
    USERSERVICEINSTANCE_NOT_EXIST("5100", "UserServiceInstance %s does not exist."),
    CONTINUOUS_NOT_EXIST("5101", "The UserServiceInstance %s type is not continuous."),
    REPRESENTATIONID_IS_VALID("5110", "The representationID value should be mpd or MPD."),
    USERSERVICEINSTANCE_NO_MPD_FILE("5111", "The request userServiceInstance does not contain mpd file."),
    CACHE_FILE_URL_DUPLICATED("5130", "There is duplicated file url list in cache request."),
    CACHE_CREATE_ONCE_OVER_MAX_CAPACITY("5131", "Cache number in request reach limitation: %s"),
    CACHE_OVER_MAXITEM_FOR_ONE_ONREQUEST("5132",  "In one request the cache maxItem is %s"),
    CACHE_ALREADY_USED("5133", "Cache has been used by content or fileschedule, can not be deleted."),


    // Content delivery error code 5150-5169
    INVALID_CONTENT_TIME("5150","The content delivery time is invaild:%s"),
    CONTENT_ONCE_NOT_AUTOREFRESHABLE("5151","Once content delivery cannot set autorefresh interval!"),
    CONTENT_COUNT_MAX("5152","The content delivery number exceed limitation."),
    //INVALID_CACHE_ID("5153","Content delivery's cache ID not exists."),
    INVALID_CACHE_STATUS("5154","The cache status is not ready:%s"),
    INVALID_CONTENT_MODIFY_TIME("5155","The content delivery can't be modified %s in 15min before content start time."),
    INVALID_CONTENT_TIME_FOR_ESTIMATE_TIME("5156","The content delivery stop time must be later than cache estimated transtime."),
    CONTENT_CONTINOUS_CREATE_FAIL("5157","Create continuous content failed."),

    CONTENT_DELIVERY_NOT_EXISTS("5158","The content delivery does not exist!"),
    CONTENT_DELIVERY_NOT_IN_PROCESS("5159","The content delivery status is not in-process!"),
    CONTENT_ONCE_NOT_REFRESHABLE("5160", "Cannot refresh ongoing once content delivery cache."),
    INVALID_CONTENT_STOP_TIME("5161","The content delivery stop time is not valid, it should before the stop time of the broadcast!"),
    INVALID_CONTENT_START_TIME("5162","The content delivery start time is not valid, it should after the start time of the broadcast!"),
    INVALID_REFRESH_OPERATION("5163","The request cache is in refreshing!"),
    CONTENT_REPROVISIONING_VALIDATION_FAILED("5165","Content re-provisioning validation failed !"),
    INVALID_MPD_REQUEST("5166", "Validate continuous request failed."),
    FILE_SCHEDULE_NOT_EXISTS("5167", "FileScheduleId not exists."),
    FILE_SCHEDULE_NUM_EXCEED_LIMIT("5168", "Fileschedule number exceeds the limit %d."),
    INVALID_FILE_SCHEDULE_TIME("5169", "Invalid fileschedule time, start/stop time should %s"),
    IN_PROCESS_CONTENT_DELIVERY_CAN_NOT_DELETE("5170", "In-process content delivery can not be deleted."),
    //EXCEED_MAX_ALLOW_DELETE_NUM("5171", "max num allowed to delete in one request is %d."),
    FAILED_TO_REPROVISION_CONTENT("5172","Failed to reprovision content on BDC %s"),
    INVALID_CONTENT_REFRESH_INTERVAL("5173","Content auto refresh interval should between 5 and 3600!"),



    //Setting related
    FREQUENCY_ALREADY_EXIST("6000", "Frequency %s is duplicated."),
    CDN_ALREADY_EXIST("6001", "CDN %s is duplicated."),
    FREQUENCY_ALREADY_INUSE("6002", "Frequency %s already explicitly in use."),
    //FREQUENCY_IS_THE_ONLY_DEFAULT_ONE("6003", "Frequency %s is the only default frequency."),
    //FREQUENCY_IS_THE_ONLY_ONE("6004", "Frequency %s is the only default frequency."),
    //CDN_IS_THE_ONLY_ONE("6005", "CDN %s is the only CDN."),
    EXCEED_SYSTEM_CAPACITY("6006", "%s exceeds system maximum capacity."),
    AT_LEAST_ONE_CDN("6007", "Require at least one CDN in BMC."),
    FREQUENCY_CAN_NOT_BE_ALL_DELETED("6008", "Frequencies can not be all deleted."),
    FEC_TEMPLATE_ENCODING_INVALID("6009", "FEC template %s encoding id can only be 1,6."),
    FEC_TEMPLATE_OVERHEAD_INVALID("6010", "FEC Template %s overhead is invalid. Overhead should be 0 if encodingId is 0."),
    SERVICE_TEMPLATE_ALREADY_EXIST("6011", "%s Service template %s already exist."),
    SERVICE_TEMPLATE_NOT_EXIST("6012", "%s Service template %s does not exist."),
    SERVICE_TEMPLATE_IS_USED_BY_SERVICE("6013", "%s Service template %s is used by service."),
    //SERVICE_TEMPLATE_NAME_SHOULD_NOT_BE_CHANGED("6014", "Service Template name %s should not be changed"),
    FAILED_TO_DELETE_SERVICE_TEMPLATES("6015", "Delete service templates failed, reason: %s"),
    FREQUENCY_MUST_HAVE_ONE_WHEN_SYSTEM_INITATE("6016", "At least one frequency should be configured."),
    //CDN_MUST_HAVE_ONE_WHEN_SYSTEM_INITATE("6017", "Should send one CDN when system inidate."),
    //FREQUENCY_ALREADY_IMPLICITLYINUSE("6018", "Frequency %s already implicitly in use."),
    SSMIP_IS_DIFFERENT_WITH_MULTICASTIP("6019", "Ssmip %s is not the same type as multicastIP."),
    FREQUENCY_MUST_HAVE_ONE_DEFAULT_WHEN_SERVICEAREA_IMPLICITLy_IN_USE("6020", "Cannot delete or update default frequency %s without given a new defalut frequency."),
    CDN_MUST_HAVE_OTHER_ATTRIBUTES_BESIDES_NAME("6021", "CDN  must have other attributes besides name."),
    INVALID_RR_TEMPLATE("6022", "Validate file reporting template failed."),
    INVALID_FR_TEMPLATE("6023", "Validate file repair template failed."),
    MWCONFIG_IS_EMPTY("6024", "MWConfig file is empty."),
    MWCONFIG_IS_NOT_MATCH_SCHEMA("6025", "MWConfig schema validation failed, detail error is '%s'."),
    FAILED_TO_CREATE_FEC_TEMPLATE("6026", "Failed to create FEC service template %s"),
    RR_TEMPLATE_DOES_NOT_EXIST("6027", "RRTemplate '%s' does not exist."),
    FR_TEMPLATE_DOES_NOT_EXIST("6028", "FRTemplate '%s' does not exist."),
    FEC_TEMPLATE_DOES_NOT_EXIST("6029", "FECTemplate '%s' does not exist."),
    NO_SAI_EXISTS_ASSOCIATED_WITH_BDC("6030", "No sai exists associated with bdc '%s'"),
    FAILED_TO_REPROVISIONING_BDC("6031", "Failed to reprovisioning bdc, %s"),

    //User related 6100
    USER_DOES_NOT_EXIST("6033", "User: %s doesn't exist."),
    PASSWORD_NOT_STRONG("6036","Password not strong, length must greater than or equal to 8 and contain at least one uppercase lowercase numeric and special characters."),
    USER_ROLE_NOT_SUPPORT("6037", "Given Role: %s is not one of: %s"),
    ADMIN_CAN_NOT_BE_DELETED("6038", "Admin can not be deleted."),
    ASP_CSP_ROLE_CAN_NOT_ASSIGN_BOTH_SERVICE_AND_SERVICE_CLASS_LIST("6039", "ASP and CSP role can't assign both service and service class lists."),
    OPERATOR_VIEWER_ROLE_CAN_NOT_ASSIGN_SERVICE_AND_SERVICE_CLASS_LIST("6040", "Operator and Viewer role can't assign service and service class lists."),
    USER_MANAGEMENT_VALIDATE_FAILS("6041", "Validation for user management fails, caused by: %s"),
    CAN_NOT_GET_ADMIN("6042", "Cannot get Admin account."),
    AAA_USER_NOT_AUTHORIZED("6050", "User is not authorized because %s"),
    AAA_USER_NOT_ALLOWED("6051", "Operation is not allowed, because %s"),
    AAA_USER_FORBIDDON("6052", "Operation is forbidden."),
    AAA_ASP_CSP_NOT_ALLOWED_LOGIN_FROM_GUI("6053", "ASP & CSP are not allowed to login from GUI."),
    AAA_USER_NOT_EXIST("6054", "User does not exist."),
    AAA_USER_LOCKED("6055", "Your user account has been locked. Please find you system administrator to unlock your user account."),
    AAA_WRONG_PASSWORD("6056", "Password is incorrect, you have entered a wrong password for %s times. Your user account will be locked if you enter a wrong password %s times consecutively."),
    AAA_ADMIN_USER_LOCKED("6057", "Your account is temporarily locked for about 5 minutes. Please try logging on later."),
    USER_INFO_CAN_NOT_BE_NULL("6062", "BMC User info %s should be provided."),
    USER_ALREADY_EXISTS("6063", "BMC User with name %s already exists."),
    ALL_USERS_DO_NOT_EXIST("6064", "All users do not exist."),
    USER_VALIDATE_DATE_SHOULD_AFTER_CURRENT_DATE("6053", "The user validate date should be after the current date."),

    //Alarm related
    ACTIVE_ALARM_NOT_EXIST("6066", "Active alarm %s does not exist."),
    USER_ALARM_SEARCH_CRITERIA_NOT_EXIST("6069", "Search criteria for user %s does not exist."),
    USER_ALARM_SEARCH_CRITERIA_EMPTY("6070", "Search criteria for saving is empty."),
    HISTORY_ALARM_NOT_EXIST("6071", "History alarm %s does not exist."),
    ACTIVE_ALARM_NOT_EXIST_IN_SET_STEP("6072", "Some active alarms are not existed."),
    ACTIVE_ALARM__MAX_HANDLING_CAPACITY("6073", "More than the active alarm handling capacity. System handling capacity is %s"),
    DOWNLOAD_EXPORTED_CSV_FAILED("6074", "Failed to download exported csv file %s"),
    MAX_ORIGINATINGSOURCE_CAPACITY("6075", "Originating Source search capacity more than 20."),
    MAX_RESOURCEID_CAPACITY("6076", "Resource ID search capacity more than 10."),
    MAX_MODULE_CAPACITY("6077", "Module search capacity more than 20."),

    //ADF
    ADF_ALREADY_EXIST("6078","ADF %s is duplicated."),
    ADF_NUM_EXCEED("6079","The total number of ADFs is more than %s"),
    ADF_NAME_LENGTH_EXCEED("6080", "ADF serverName %s is more than 127 characters."),
    ADF_NAME_DUPLICATED("6081","ADF serverName %s is duplicated."),

    CHARACTERS_LENGTH_EXCEED("6082", "%s is more than 127 characters."),
    ILLEGAL_CHARACTERS("6083", "%s contains illegal characters."),

    //TmgiRange
    AT_LEAST_ONE_TMGI_RANGE("6090", "Require at least one TMGI range in BMC."),
    TMGI_RANGE_OVER_LAP("6091", "TMGI ranges overlap."),
    INVALID_TMGI_RANGE("6092", "invalid TMGI ranges TmgiRange.from should less than TMGIRange.to."),
    MCC_MNC_NOT_CORRECT("6093", "MNC and MCC are not same in one TMGIRange"),
    INVALID_TMGI_RANGE_LENGTH("6094", "Length of TMGIRange from and to should be 12");





    private BmcErrorCode(String id, String errDesc) {
        this.id = id;
        this.description = errDesc;
    }

   /**
     * Returns the error id.
     *
     * @return The error id.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the error description.
     *
     * @return the error description.
     */
    public String getDescription(Object... arg) {
        if (arg == null || arg.length == 0) {
            return description;
        }
        try {
            return String.format(description, arg);
        } catch(Exception e) {
            return description;
        }
    }

    private final String id;
    private final String description;

}
