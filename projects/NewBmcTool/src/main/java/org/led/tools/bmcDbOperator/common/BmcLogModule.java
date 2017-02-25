package org.led.tools.bmcDbOperator.common;

public enum BmcLogModule {

    COMMONUTIL("CommonUtil"),  
    NORTHBOUND("NorthBound"),
    BLDISPATCH("DispatchBL"), 
    BLNETWORKPLANNING("NetworkPlBL"), 
    BLSERVICE("ServiceBL"),
    BLBROADCAST("BroadcastBL"), 
    BLADMIN("ADMINBL"), 
    BLCONTENT("ContentBL"), 
    BLBDCMANAGER("BdcManagerBL"),
    SVCUSERMGT("UserManagement"), 
    SVCCAROUSEL("Carousel"), 
    SVCANNOUNCEMENT("Announcement"),
    SVCCONNECTMGT("ConnectionMgr"), 
    SVCEVENT("EventService"), 
    SVCPERSISTENCE("Persistence"),
    SVCSCHEDULE("Schedule"),
    SVCCONFIGURE("ConfigureMgr"),
    SVCLOCK("LockService"),
    SVCFAULTMGR("FaultManagement"),
    SVCSNMPRECV("SnmpReceiver"),
    SVCREPORTINGPUSHER("ReportingPusher"),
    SBOUNDEVENTRECEIVER("EventReceiver"),
    SBOUNDCDNCONN("CDNConnection"),
    SBOUNDBDCCONN("BDCConnection"),
    SBOUNDREPORTINGCONN("ReportingConnection"),
    SBOUNDCDNCONVERTER("CDNConverter"),
    SBOUNDBDCCONVERTER("BDCConverter"), 
    SBOUNDNCMCONVERTER("NCMConverter"),
    SBOUNDREPORTINGCONVERTER("ReportingConverter"),
    SBOUNDREPORTINGCONNECTOR("ReportingConnector"),
    BLBDCLEGACY("BDC1XSPECIALBL"), 
    BLBDCLEGACYREFRESH("Refresh"),
    BLBDCCHECKER("BDCChecker"),
    PRESTORE("PreStore"),
    BLALARM("AlarmBL");

    private String displayname;

    BmcLogModule(String displayname) {
        this.displayname = displayname;
    }

    public String getModuleDisplayName() {
        return displayname;

    }

}
