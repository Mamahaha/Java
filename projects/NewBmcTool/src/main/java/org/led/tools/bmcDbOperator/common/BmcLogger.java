package org.led.tools.bmcDbOperator.common;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public final class BmcLogger {

    private BmcLogger() {

    }

    private static final String bmcLog = "BmcLog";
    private static final String bmcCarouselLog = "BmcCarouselLog";
    private static final String bmcPipeImportLog = "ImportLog";
    private static final String bmcPipeValidateLog = "ValidateLog";
    private static final String newlineStr = "\r\n";

    /**
     * Record the request message from BMC to external node. *
     * 
     * @param co
     *            the request info
     * @param response
     *            the response info
     * @see ConnectionObject ConnectionResponse
     */
    public static void accessLog(ConnectionObject co, ConnectionResponse response) {

        Logger accessLogger = LoggerFactory.getLogger("AccessLog");

        if (!accessLogger.isInfoEnabled()) {
            return;
        }

        BmcAccessMessage msg = new BmcAccessMessage();

        msg.addAccessRequest(co);
        msg.addAccessResponse(response);

        // log content
        String msgContent = msg.format();

        accessLogger.info(msgContent);
    }

    

    /**
     * Record the audit message of user login logout
     * 
     * @param msg
     *            FaultManagementMessage
     * @see FaultManagementMessage
     */
    public static void snmpLog(FaultManagementMessage msg) {
        System.out.println("snmpLog");
    }

    /**
     * Record the bdc event message received by BMC (debug level)
     * 
     * @param BDCEvent
     *            msg
     * @see BDCEvent
     */
    public static void bdcEventLogDebug(String event) {
        Logger bdcEventLogger = LoggerFactory.getLogger("BDCEventLog");

        if (!bdcEventLogger.isDebugEnabled()) {
            return;
        }

        // log content
        String msgContent = event;

        bdcEventLogger.info(msgContent);
    }

    /**
     * Record the bdc event message received by BMC
     * 
     * @param BDCEvent
     *            msg
     * @see BDCEvent
     */
    public static void bdcEventLogInfo(String eventContent) {
        Logger bdcEventLogger = LoggerFactory.getLogger("BDCEventLog");

        if (!bdcEventLogger.isInfoEnabled()) {
            return;
        }

        // log content
        String msgContent = eventContent;

        bdcEventLogger.info(msgContent);
    }

    /**
     * Record the observing object info for performance troubleshooting
     * 
     * @param module
     *            module name where the observing object located
     * @param counter
     *            the observing object
     * @param value
     *            the value of the observing object
     * 
     */
    public static void performRecord(String module, String counter, String value) {
        Logger performLogger = LoggerFactory.getLogger("PerformLog");

        performLogger.debug(BmcEventMessage.formatPerform(new Object[] { module, counter, value }));
    }

    /**
     * Method eventError record error event log.
     * 
     * @param domainOrComponent
     *            domain or component name
     * @param msgContent
     *            message content
     */
    public static void eventError(String domainOrComponent, BmcErrorLogCode error, Object... arg) {
        Logger eventLogger = LoggerFactory.getLogger(bmcLog);

        if (!eventLogger.isErrorEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(domainOrComponent);
        eventLog.setErrorCode(error.getId());
        eventLog.setMsgContent(error.getDescription(arg));

        // record error log
        eventLogger.error(eventLog.formatCommon());
    }

    /**
     * Method eventWarn record warn event log.
     * 
     * @param domainOrComponent
     *            domain or component name
     * @param msgContent
     *            message content
     */
    public static void eventWarn(String domainOrComponent, String msgContent, Object... arg) {
        Logger eventLogger = LoggerFactory.getLogger(bmcLog);

        if (!eventLogger.isWarnEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(domainOrComponent);
        eventLog.setMsgContent(msgContent, arg);

        // record warn log
        eventLogger.warn(eventLog.formatCommon());
    }

    public static void eventWarn(BmcLogModule module, Exception exception) {
        Logger eventLogger = LoggerFactory.getLogger(bmcLog);

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(module.getModuleDisplayName());
        eventLog.setMsgContent(getExceptionStack(exception));

        // record warn log
        eventLogger.warn(eventLog.formatDebug());

    }

    /**
     * Method eventInfo record info event log.
     * 
     * @param domainOrComponent
     *            domain or component name
     * @param msgContent
     *            message content
     */
    public static void eventInfo(String domainOrComponent, String msgContent, Object... arg) {

        Logger eventLogger = LoggerFactory.getLogger(bmcLog);

        if (!eventLogger.isInfoEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(domainOrComponent);
        eventLog.setMsgContent(msgContent, arg);

        // record info log
        eventLogger.info(eventLog.formatCommon());
    }

    /**
     * Method eventDebug record debug event log.
     * 
     * @param domainOrComponent
     *            domain or component name
     * @param classInfo
     *            class information
     * @param msgContent
     *            message content
     */
    public static void eventDebug(String domainOrComponent, String classInfo, String msgContent, Object... arg) {
        Logger eventLogger = LoggerFactory.getLogger(bmcLog);

        if (isCarouselLog(domainOrComponent)) {
            eventLogger = LoggerFactory.getLogger(bmcCarouselLog);
        }

        if (!eventLogger.isDebugEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(domainOrComponent);
        eventLog.setClassInfo(classInfo);
        eventLog.setMsgContent(msgContent, arg);

        // record debug log
        eventLogger.debug(eventLog.formatDebug());
    }

    /**
     * Method eventError record error event log.
     * 
     * @param domainOrComponent
     *            domain or component name
     * @param classInfo
     *            class information
     * @param e
     *            exception
     */
    public static void eventDebug(String domainOrComponent, String classInfo, Exception e) {
        Logger eventLogger = LoggerFactory.getLogger(bmcLog);

        if (isCarouselLog(domainOrComponent)) {
            eventLogger = LoggerFactory.getLogger(bmcCarouselLog);
        }

        if (!eventLogger.isDebugEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(domainOrComponent);
        eventLog.setClassInfo(classInfo);
        eventLog.setMsgContent(getExceptionStack(e));

        // record debug log
        eventLogger.debug(eventLog.formatDebug());
    }

    /**
     * Method eventError record error event log.
     * 
     * @param domainOrComponent
     *            domain or component name
     * @param classInfo
     *            class information
     * @param e
     *            exception
     */
    public static void eventDebug(String domainOrComponent, String classInfo, Throwable e) {
        Logger eventLogger = LoggerFactory.getLogger(bmcLog);

        if (isCarouselLog(domainOrComponent)) {
            eventLogger = LoggerFactory.getLogger(bmcCarouselLog);
        }

        if (!eventLogger.isDebugEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(domainOrComponent);
        eventLog.setClassInfo(classInfo);
        eventLog.setMsgContent(getThrowableStack(e));

        // record debug log
        eventLogger.debug(eventLog.formatDebug());
    }

    private static boolean isCarouselLog(String domainOrComponent) {
        return (domainOrComponent.equals(BmcLogModule.SVCCAROUSEL.getModuleDisplayName())
                || domainOrComponent.equals(BmcLogModule.BLBDCCHECKER.getModuleDisplayName()) || domainOrComponent.equals(BmcLogModule.BLBDCLEGACYREFRESH
                .getModuleDisplayName()));
    }

    /**
     * Method eventGetExceptionStack output exception stack.
     * 
     * @param Exception
     *            e
     */
    private static String getExceptionStack(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return newlineStr + sw.toString() + newlineStr;
        } catch (Exception e2) {
            return "";
        }
    }

    /**
     * Method eventGetThrowableStack output throwable stack.
     * 
     * @param Throwable
     *            e
     */
    private static String getThrowableStack(Throwable e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return newlineStr + sw.toString() + newlineStr;
        } catch (Exception e2) {
            return "";
        }
    }

    /**
     * Method eventError record error event log.
     * 
     * @param module
     *            BmcLogModule
     * @param classInfo
     *            class information
     * @param e
     *            exception
     * @See BmcLogModule
     */
    public static void eventError(BmcLogModule module, BmcErrorLogCode error, Object... arg) {
        eventError(module.getModuleDisplayName(), error, arg);
    }

    /**
     * Method eventError record warn event log.
     * 
     * @param module
     *            BmcLogModule
     * @param classInfo
     *            class information
     * @param e
     *            exception
     * @See BmcLogModule
     */
    public static void eventWarn(BmcLogModule module, String msgContent, Object... arg) {
        eventWarn(module.getModuleDisplayName(), msgContent, arg);

    }

    /**
     * Method eventError record info event log.
     * 
     * @param module
     *            BmcLogModule
     * @param classInfo
     *            class information
     * @param e
     *            exception
     * @See BmcLogModule
     */
    public static void eventInfo(BmcLogModule module, String msgContent, Object... arg) {
        eventInfo(module.getModuleDisplayName(), msgContent, arg);

    }

    /**
     * Method eventError record eventDebug log.
     * 
     * @param module
     *            BmcLogModule
     * @param classInfo
     *            class information
     * @param Exception
     *            e
     * @see BmcLogModule
     */
    public static void eventDebug(BmcLogModule module, String classInfo, Exception e) {
        eventDebug(module.getModuleDisplayName(), classInfo, e);
    }

    /**
     * Method eventError record eventDebug log.
     * 
     * @param module
     *            BmcLogModule
     * @param classInfo
     *            class information
     * @param e
     *            exception
     * @See BmcLogModule
     */
    public static void eventDebug(BmcLogModule module, String classInfo, String msgContent, Object... arg) {
        eventDebug(module.getModuleDisplayName(), classInfo, msgContent, arg);
    }

    /**
     * Method eventError record eventDebug og.
     * 
     * @param module
     *            BmcLogModule
     * @param classInfo
     *            class information
     * @param e
     *            Throwable
     * @See BmcLogModule
     */
    public static void eventDebug(BmcLogModule module, String classInfo, Throwable e) {
        eventDebug(module.getModuleDisplayName(), classInfo, e);
    }

    /**
     * Method pipeImportLogDebug record debug log for pipe import.
     * 
     * @param module
     *            BmcLogModule
     * @param classInfo
     *            class information
     * @param e
     *            Throwable
     */
    public static void pipeImportLogDebug(BmcLogModule module, String classInfo, Throwable e) {
        Logger eventLogger = LoggerFactory.getLogger(bmcPipeImportLog);

        if (!eventLogger.isDebugEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(module.getModuleDisplayName());
        eventLog.setClassInfo(classInfo);
        eventLog.setMsgContent(getThrowableStack(e));

        // record debug log
        eventLogger.debug(eventLog.formatDebug());
    }

    /**
     * Method pipeImportLogDebug record debug log for pipe import.
     * 
     * @param module
     *            BmcLogModule
     * @param classInfo
     *            class information
     * @param msgContent
     *            message content
     * @param arg
     *            variable argument list
     */
    public static void pipeImportLogDebug(BmcLogModule module, String classInfo, String msgContent, Object... arg) {
        Logger eventLogger = LoggerFactory.getLogger(bmcPipeImportLog);

        if (!eventLogger.isDebugEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(module.getModuleDisplayName());
        eventLog.setClassInfo(classInfo);
        eventLog.setMsgContent(msgContent, arg);

        // record info log
        eventLogger.debug(eventLog.formatDebug());
    }

    /**
     * Method pipeImportLogInfo record info log for pipe import.
     * 
     * @param module
     *            BmcLogModule
     * @param msgContent
     *            message content
     * @param arg
     *            variable argument list
     */
    public static void pipeImportLogInfo(BmcLogModule module, String msgContent, Object... arg) {
        Logger eventLogger = LoggerFactory.getLogger(bmcPipeImportLog);

        if (!eventLogger.isInfoEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(module.getModuleDisplayName());
        eventLog.setMsgContent(msgContent, arg);

        // record info log
        eventLogger.info(eventLog.formatCommon());
    }

    /**
     * Method pipeImportLogWarn record warn log for pipe import.
     * 
     * @param module
     *            BmcLogModule
     * @param msgContent
     *            message content
     * @param arg
     *            variable argument list
     */
    public static void pipeImportLogWarn(BmcLogModule module, String msgContent, Object... arg) {
        Logger eventLogger = LoggerFactory.getLogger(bmcPipeImportLog);

        if (!eventLogger.isWarnEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(module.getModuleDisplayName());
        eventLog.setMsgContent(msgContent, arg);

        // record warn log
        eventLogger.warn(eventLog.formatCommon());
    }

    /**
     * Method pipeImportLogError record error log for pipe import.
     * 
     * @param module
     *            BmcLogModule
     * @param error
     *            BmcErrorLogCode
     * @param arg
     *            variable argument list
     */
    public static void pipeImportLogError(BmcLogModule module, BmcErrorLogCode error, Object... arg) {
        Logger eventLogger = LoggerFactory.getLogger(bmcPipeImportLog);

        if (!eventLogger.isErrorEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(module.getModuleDisplayName());
        eventLog.setErrorCode(error.getId());
        eventLog.setMsgContent(error.getDescription(arg));

        // record error log
        eventLogger.error(eventLog.formatCommon());
    }

    /**
     * Method pipeImportLogDebug record debug log for pipe validate.
     * 
     * @param module
     *            BmcLogModule
     * @param classInfo
     *            class information
     * @param e
     *            Throwable
     */
    public static void pipeValidateLogDebug(BmcLogModule module, String classInfo, Throwable e) {
        Logger eventLogger = LoggerFactory.getLogger(bmcPipeValidateLog);

        if (!eventLogger.isDebugEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(module.getModuleDisplayName());
        eventLog.setClassInfo(classInfo);
        eventLog.setMsgContent(getThrowableStack(e));

        // record debug log
        eventLogger.debug(eventLog.formatDebug());
    }

    /**
     * Method pipeImportLogDebug record debug log for pipe validate.
     * 
     * @param module
     *            BmcLogModule
     * @param classInfo
     *            class information
     * @param msgContent
     *            message content
     * @param arg
     *            variable argument list
     */
    public static void pipeValidateLogDebug(BmcLogModule module, String classInfo, String msgContent, Object... arg) {
        Logger eventLogger = LoggerFactory.getLogger(bmcPipeValidateLog);

        if (!eventLogger.isDebugEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(module.getModuleDisplayName());
        eventLog.setClassInfo(classInfo);
        eventLog.setMsgContent(msgContent, arg);

        // record info log
        eventLogger.debug(eventLog.formatDebug());
    }

    /**
     * Method pipeImportLogInfo record info log for pipe validate.
     * 
     * @param module
     *            BmcLogModule
     * @param msgContent
     *            message content
     * @param arg
     *            variable argument list
     */
    public static void pipeValidateLogInfo(BmcLogModule module, String msgContent, Object... arg) {
        Logger eventLogger = LoggerFactory.getLogger(bmcPipeValidateLog);

        if (!eventLogger.isInfoEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(module.getModuleDisplayName());
        eventLog.setMsgContent(msgContent, arg);

        // record info log
        eventLogger.info(eventLog.formatCommon());
    }

    /**
     * Method pipeImportLogWarn record warn log for pipe validate.
     * 
     * @param module
     *            BmcLogModule
     * @param msgContent
     *            message content
     * @param arg
     *            variable argument list
     */
    public static void pipeValidateLogWarn(BmcLogModule module, String msgContent, Object... arg) {
        Logger eventLogger = LoggerFactory.getLogger(bmcPipeValidateLog);

        if (!eventLogger.isWarnEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(module.getModuleDisplayName());
        eventLog.setMsgContent(msgContent, arg);

        // record warn log
        eventLogger.warn(eventLog.formatCommon());
    }

    /**
     * Method pipeImportLogError record error log for pipe validate.
     * 
     * @param module
     *            BmcLogModule
     * @param error
     *            BmcErrorLogCode
     * @param arg
     *            variable argument list
     */
    public static void pipeValidateLogError(BmcLogModule module, BmcErrorLogCode error, Object... arg) {
        Logger eventLogger = LoggerFactory.getLogger(bmcPipeValidateLog);

        if (!eventLogger.isErrorEnabled()) {
            return;
        }

        // create event object
        BmcEventMessage eventLog = new BmcEventMessage();
        eventLog.setDomainOrComponent(module.getModuleDisplayName());
        eventLog.setErrorCode(error.getId());
        eventLog.setMsgContent(error.getDescription(arg));

        // record error log
        eventLogger.error(eventLog.formatCommon());
    }

}
