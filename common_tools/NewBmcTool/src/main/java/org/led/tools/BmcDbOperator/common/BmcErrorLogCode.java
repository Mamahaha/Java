
package org.led.tools.BmcDbOperator.common;

/**
 * all the error code enum used for print error log
 *
 */
public enum BmcErrorLogCode {
    /**
     * List of all error codes used by mdf-cp, ncm and adf-provisioning
     * the error code is a 6 digital number such as 010001
     * the first two numbers represent the application type
     *   17 for bmc
     * the last four numbers represent node specific error code
     */

    //DATABASE_CREATE_FAILED("010100", "Failed to create '%s' in the database %s, reason: %s."),
    //DATABASE_DELETE_FAILED("010101", "Failed to delete '%s' in the database %s, reason: %s."),
    //DATABASE_SEARCH_FAILED("010102", "Failed to search '%s' in the database %s, reason: %s."),
    //DATABASE_UPDATE_FAILED("010103", "Failed to update '%s' in the database %s, reason: %s."),
    //LOAD_SCHEMA_FAILED("020002", "Failed to load schema %s, reason: %s."),
    //RESOLVE_RESOURCE_FAILED("020003", "Failed to resolve the resource content %s, reason: %s."),
    XML_MARSHAL_ELEMENT_FAILED("020004", "Failed to marshal the XML element for the %s class."),
    XML_UNMARSHAL_TYPE_FAILED("020005", "Failed to unmarshal the XML type for the class %s."),
    //CONFIGURE_JAXB_MARSHALLER_FAILED("020006", "Failed to configure JAXB marshaller for class %s, reason: %s."),
    //UNEXPECTED_VARIABLE_TYPE("020007", "The '%s' with type '%s' is not an expected instance of '%s' when %s."),
    //CREATE_TIMER_FAILED("020008", "Failed to create a timer %s, reason: %s."),
    GENERATE_MIME_MESSAGE_FAILED("020011", "Failed to generate MIME Message, reason: %s."),
    //INIT_OAM_EVENT_QUEUE_FAILED("020100", "Failed to initialize the OAM event queue, reason: %s."),
    QUEUE_OAM_EVENT_FAILED("020101", "Failed to send OAM event '%s' to the OAM event queue, reason: %s."),
    SEND_EVENT_TO_ESA_FAILED("020102", "Failed to send an event to the ESA. Please check connection with ESA and see debug log for detail."),
    GET_ACTIVE_ALARM_FAILED("020103", "Failed to get the active alarm list from the ESA, reason: %s."),
    //REPORT_PM_TO_ESA_FAILED("020104", "Failed to report the PM counter to the ESA, reason %s."),
    //FAILED_TO_RAISE_ALARM_TO_ESA("020105", "Failed to raise an alarm to the ESA. Please check connection with ESA and see debug log for detail."),
    //FAILED_TO_SEND_EVENT_TO_ESA("020106", "Failed to send an event to the ESA. Please check connection with ESA and see debug log for detail."),
    //FAILED_TO_CLEAR_ALARM_TO_ESA("020107", "Failed to clear an alarm to the ESA. Please check connection with ESA and see debug log for detail."),
    SEND_MESSAGE_TO_ESA_FAILED("020108", "Failed to send an message to the ESA. Please check connection with the ESA and see debug log for detail."),
    USERMGT_INITIALIZEFAILURE("170001", "UserManagement initialization failed , Failed to init %s in DB."),
    USER_NOTEXIST("170002", "User %s not exist in BMC."),
    USER_NOTVALIDATE("170003", "User %s not pass authentication in BMC"),
    PROVISIONING_CONFLICT("170004", "Request on %s conflict" ),
    VALIDATION_FAILED("170005", "Request on %s validation failed with reason %s."),
    NO_BDC_FOUND("170006", "No BDC defined or found when handle %s."),
    //SEND_REQUEST_TO_BDC_FAILED("170007", "Failed to send %s request to BDC: %s when handle %s."),
    NO_TMGI_RESOURCE("171008", "No TMGI is available in the DB."),
    //NO_MULTICAST_IP_REOURCES("171009", "No MulticastIp is avaliable in DB"),
    NO_DESTINATION_PORT_RESOURCE("171010", "No DestPort resource is avaliable."),
    NO_TSI_RESOURCE("171011", "No TSI resource is avaliable."),
    FAILED_SDP_PROCESSING("171012", "Failed to process SDP text, reason: %s."),
    NO_DELIVERY_SESSION_ID_RESOURCE("171013", "No Delivery Session ID resource is available."),
    INVALID_IP_ADDRESS("171014", "Invalid IP address for %s."),
    FAILED_CONVERT_BDC_RESPONSE("171015", "Failed to convert BDC response to BMC responseWrapper, reason: %s."),
    GET_HOSTNAME_ERROR("171016", "Get system host error."),
    USERNAME_NOT_MATCH_BETWEEN_URL_AND_BODY("171017","BmcUser name is not match between url and body, URL: '%s' - Body: '%s'."),

    USER_SERVICE_INSTANCE_IS_INCOMPLETE("174000", "The %s User Service Instance (USI) of the %s broadcast for the %s service is incomplete."),
    MAX_BITRATE_OR_TOTAL_MEDIABYTES_IS_INVALID("174100", "Invalid FECTemplate for the USI mode, no media content can be delivered with the bandwidth."),
    ALL_BDC_CREATE_EMBMS_SESSION_FAILED("174200", "All BDCs failed to create the eMBMS session for %s broadcast."),
    ALL_BDC_ADD_DELIVERY_SESSION_FAILED("174201", "All BDCs failed to create the delivery session for %s broadcast."),
    ALL_BDC_PROVISION_FAILED("174202", "All BDCs failed to provision for %s service."),
    //BDC_NUMBER_EXCEED("174203", "The total number of BDC in the DB is more than %s."),
    FAILED_TO_OPERATE_DELIVERY_SESSION("174204", "Operation %s of delivery session failed for user service %s on %s."),
    FAILED_TO_CREATE_SDCH_SERVICE("174205", "Create SDCH Service %s failed, reason: Create %s failed. %s."),
    ROLLBACK_FAILED_WHEN_CREATE_SDCH_SERVICE_FAILED("174206", "Create SDCH Service %s failed, reason: Create %s failed. %s and rollback failed, reason: Delete %s failed. %s."),
    BEARER_UNEXPECT_STOP("174207","Raise a Bearer Unexpect Stop Alarm for %s, resourceId: %s."),
    BEARER_START_PERMANENT_FAILED("174208","Raise a Bearer Start Permanent Failed Alarm for %s, resourceId: %s."),
    BEARER_START_TEMPORARY_FAILED("174209","Raise a Bearer Start Temporary Failed Alarm for %s, resourceId: %s."),
    BEARER_STOP_PERMANENT_FAILED("174210","Raise a Bearer Stop Permanent Failed Alarm for %s, resourceId: %s."),
    RETRIEVE_LICENSE_INFORMATION_FAILED("174300", "Failed to retrieve the license '%s' to host %s with the return code '%s'."),
    RENEW_LICENSE_TO_HOST_FAILED("174301", "Failed to renew the license '%s' to host %s with the return code '%s'."),
    RELEASE_LICENSE_TO_HOST_FAILED("174302", "Failed to release the license to host %s with the return code '%s'."),
    REQUEST_LICENSE_TO_HOST_FAILED("174303", "Failed to request license '%s' to host %s with the return code '%s'."),
    SESSION_UNEXPECT_STOP("174304","Raise a Session Unexpect Stop Alarm for %s, resourceId: %s."),
    BEARER_STOP_TEMPORARY_FAILED("174211","Raise a Bearer Stop Temporary Failed Alarm for %s, resourceId: %s."),
    PDU_MESSAGE_INVALID("174212","Pdu message received from ESA is invalid, drop this message."),
    MWCONFIG_NOT_MATCH_SCHEMA("174213","MWConfig file is not match the schema, detail error is '%s'."),
    //LOCK_FAILED_WHEN_CREATECONTENT("4202", "lock failed when createContent"),
    CREATE_CONTINUOUS_CONTENT("174214", "Create continuous content: %s."),
    FAILED_TO_VALIDATE_REQUEST_CONTENT("174215", "failed_to_validate_request_content %s."),
    MSG_BDC_NOT_FOUND("174216", "msg_bdc_not_found."),
    //FAILED_TO_ADD_CACHE("4206", "failed_to_add_cache"),
    //FAILED_TO_ADD_CONTENT("4207", "failed_to_add_content"),
    //FAILED_TO_SEND_CACHEADD_REQUEST_TO_BDCS("4208", "failed_to_send_cacheadd_request_to_bdcs"),
    //FAILED_TO_GET_CONTENTS("4209", "failed_to_get_contents"),
    FAILED_TO_VALIDATE_REQUEST_FILESCHEDULE("174226", "failed_to_validate_request_fileschedule."),
    //CACHE_FILEURI_EXISTS("4211","file uri already exists !"),
    //CONVERT_NBI_REQUST_FAILED("4212","Convert North Bound request failed"),
    CONVERT_NBI_EVENT_FAILED("175001","Convert North Bound event failed."),
    FAILED_TO_GET_BDC_CONVERTER("175002","No matched BDC Converter loaded."),
    FAILED_TO_GET_BDC_CONNECTOR("175003","No matched BDC Connector loaded."),
    FAILED_TO_DISPATCH_UNKNOWN_EXCEPTION("175004","Critical error occur when send request via connection mgr see debug log for detail."),
    RESTSERVER_NOT_START("175005", "Rest http server can not start correctly."),
    RESTSERVER_NOT_STOP("175006", "Rest http server can not stop correctly."),
    FAILED_TO_VALIDATE_REQUEST_BROADCAST_CANCEL("175007","Failed to validate the request of BroadcastCancelTriggeredByBroadcastBL, "),
    //need improve
    FAILED_TO_CANCEL_BROADCAST("175008","Failed to handle the request of broadcast cancel triggered by broadcast bl"),
    //need improve
    RE_PROVISIONING_ERROR("175009","re-provisioning error"),
    UNSUPPORTED_ENCODING_ERROR("175010","Unsupported Encoding Exception"),
    CDN_ABSOLUTE_PATH_NOT_EXIST_ERROR("175011", "The CDN absolute path does not exist, CDN:%s."),
    FAILED_SEND_SAFILE_TO_CDN("175012", "Failed to send file %s to SFTP Server due to: %s."),
    FAILED_UPLOAD_FILE_TO_SFTP_SERVER("175013", "Failed to upload file to SFTP server: %s."),
    NOT_CONNECTED_ESTABLISHED_TO_SFTP_SERVER("175014", "Connection could not be established with the SFTP server: %s."),
    FAILED_TO_CREATE_SERVICE_ANNOUNCEMENT_RELATIVE_PATH("175015", "Failed to create Service Annoucement relative path."),
    ONLY_SUPPORT_PASSWORD_OR_KEY_BOAR_INTERACTIVE_AUTHENTICATION("175016", "BMC can only support password or keyboard-interactive authentication method currently."),
    AUTHENTICATION_WITH_SFTP_FAILED("175017", "Authentication with SFTP failed."),
    //SFTP_EXCEPTION_ERROR("175018", "SFTPException: "),
    UNABLE_TO_CREATE_SFTP_CONNECTION("175019", "Unable to create SFTP connection to SFTP server."),
    FAILED_TO_SEND_ALARM("175020", "Unable to send alarm for %s."),
    CDN_IS_NULL("175021", "%s."),
    KEYSTORE_FILE_NOT_EXIST("175022", "Failed to find keystore file %s for HTTPS server."),
    TRUST_KEYSTORE_FILE_NOT_EXIST("175023", "Failed to find trust keystore file %s for HTTPS client."),
    TRUST_MANAGER_INIT_ERROR("175024", "Failed to initialize Trust Manager, %s."),
    SSL_CONTEXT_INIT_ERROR("175025", "Failed to initialize the SSL context, %s."),
    SNMP_RECEIVER_START_FAILED("175026", "Failed to start snmp receiver, reason: %s."),
    INVALID_PARAMETERS_FOR_REPORTING_GUI("175027","Found invalid parameters of the ReportingGUI,detail: %s.So the default value is applied."),
    PUSH_BROADCAST_METADATA_FAILED("175028","Failed to push metadata of broadcast %s under service %s to reporting reason: %s."),
    SCHEDULE_STARTUP_JOB_START_FAILED("175029", "BMC BL startup schedule job failed, no broadcast and content will be covered after startup.Please restart the schedule service bundle."),
    FAILED_TO_RESERVE_PIPE("175030", "Failed to reserve pipe/TMGI for %s broadcast. reason: %s"),
    INVALID_PIPE_ASSIGMENT("175031", "Invalid pipe assignment to USI for broadcast %s."),
    FAILED_IMPORT_PIPE_FILE("175032", "Failed to import pipe configuration file %s, reason: %s."),
    FAILED_VALIDATE_PIPE_FILE("175033", "Failed to validate pipe configuration file %s, reason: %s."),

    UNKNOWN("179000", "Unknown Error."),
    UNSUPPORTOPERATION("179001", "Get unknow support."),
    ERROR_OCCURED_WHEN_RECORD_OPERATION_HISTORY("179002", "Error occured when record operation history: %s");


    private BmcErrorLogCode(String id, String errDesc) {
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
