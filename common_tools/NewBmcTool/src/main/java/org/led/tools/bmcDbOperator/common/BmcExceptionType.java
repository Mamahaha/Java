package org.led.tools.bmcDbOperator.common;

public enum BmcExceptionType {
    
    UNSPECIFIED(0,"Unspecified"),
    UNSUPPORTED_OPERATION(1,"The operation is unsupported"),
    AAA_SERVICE_UNAVAILABLE(2,"The AAA service is unvailable"),
    INVALID_REQUEST_PARAMETER(3,"Invalid request parameter or IO exception in southbound connection"),
    FAILED_CREATE_NEW_BEARER_DELIVERY_SESSION(4,"Creation of new bearer delivery session is failure"),
    CONNECTOR_UNAVAILABLE(5,"Southbound connector is unvailable"),
    CONVERTER_UNAVAILABLE(6,"Southbound converter is unvailable"),
    FAILED_CONVERT_CREATE_DELIVERY_SESSION_RESPONSE(7,"Failed to converter response of creating delivery session"),
    FAILED_SEND_MESSAGE_VIA_CONNECTOR(8,"Failed to send out the message via connector"),
    INVALID_VALUE_OF_SCHEDULEDJOBMONITOR_EXPECTEDRUNTIME(9,"scheduledjobmonitor's expectedRunTime must be a positive number"),
    FAILED_CREATE_DATA_TYPE_FACTORY(10,"Failed to create of new instance of datatype factory"),
    FAILED_JAXBCONTEXT_MARSHALL(11,"JAXBContext marshalling is failure"),
    FAILED_JAXBCONTEXT_UNMARSHALL(12,"JAXBContext unmarshalling is failure"),
    FAILED_MIMEMESSAGE_OPERATION(13,"MimeMessage operation is failure"),
    ALL_BDC_CREATE_EMBMS_SESSION_FAILED(14,"All BDCs created embms session failed"),
    NO_TMGI_RESOURCE(15,"No TMGI is available in the DB"),
    FAILED_PARSE_IP_ADDRESS(16, "Failed to parse ip address"),
    INVALID_IP_ADDRESS_TYPE(17, "Invalid IP address type"),
    NO_AVAILABLE_MULTICASTIP(18, "No MulticastIp is available in DB"),
    NO_AVAILABLE_PORT(19, "No DestPort resource is available"),
    NO_AVAILABLE_TSI(20, "No Tsi resource is available"),
    FAILED_SDP_PROCESSING(21, "Failed to process SDP text"),
    NO_AVAILABLE_DELIVERY_SESSION_ID(22, "No Delivery Session ID resource is available"),
    BDC_LIST_IS_EMPTY(23,"BDC list is empty for the broadcast"),
    FAILED_TO_PARSE_XML(24,"Failed to parse XML"),
    FAILED_TO_GENERATEAESKEY(25,"Failed to generate AES Key"),
    FAILED_TO_ENCRYPT_PWD(26,"Failed to encry password"),
    FAILED_TO_DENCRYPT_PWD(27,"Failed to dencry password"), 
    FAILED_DISPATCH_ADDCACHE(28,"Failed to dispatch add cache towards ALL BDCs."),
    INVALID_TIME_FORMAT(29, "Invalid time format"), 
    FAILED_DISPATCH_REMOVECACHE(30, "Failed to dispatch remove cache towards ALL BDCs."),
    FAILED_TO_FIND_CONTENT_HANDLER(31,"could not find corresponding content handler !"),
    FAILED_TO_UPDATE_FREQUENCY(32, "Failed to update frequency of Service Area."), 
    CACHE_FILEURI_EXISTS(33,"file uri already exists !"), 
    FAILED_DISPATCH_SEND_CONTROL_FRAGMENT(34,"Failed to dispatch send control fragment towards ALL BDCs."), 
    FAILED_DISPATCH_ADDCONTENT(35,"Failed to dispatch add content towards ALL BDCs."), 
    FAILED_DISPATCH_REFRESH_CONTENT(36,"Failed to dispatch refresh content cache towards ALL BDCs."), 
    FAILED_DISPATCH_MODIFY_CONTENT(37,"Failed to dispatch modify content towards ALL BDCs."), 
    FAILED_DISPATCH_REMOVE_CONTENT(38,"Failed to dispatch remove content towards ALL BDCs."),
    INVALID_SERVICE_AREA_ID(39, "Service Area ID is Invalid"),
    GENERATE_AES_KEY_FAILED(40, "Generate AES Key Failed."),
    NO_AVAILABLE_MPD_FR_RR(41, "No MPD or No FR/RR Template is available in DB for Control Fragment"),
    FAILED_TO_PARSE_MPD(42, "Failed to parse MPD"),
    FAILED_TO_GENERATE_BROADCAST_METADATA(43, "Failed to generate broadcast metadata"),
    FAILED_TO_CONNECT_REPORTING(44, "Reporting server connect failed"),
    FAILED_TO_LOAD_MPD(45, "Can not download Mpdis content, Content(media) bitrate exceed the max bitrate(GBR)");
    private int value;
    private String description;
    
    private BmcExceptionType(int value, String description){
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
