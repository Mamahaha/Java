package com.ericsson.bvps.announcement;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.tailf.ncs.NcsMain;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.InteractiveCallback;
import ch.ethz.ssh2.SFTPException;
import ch.ethz.ssh2.SFTPv3Client;

import com.ericsson.bvps.ncs.NcsUtil;
import com.ericsson.bvps.ns.BroadcastArchiveNs;
import com.ericsson.bvps.ns.BroadcastServiceNs;
import com.ericsson.bvps.ns.BvpsAlarmTypesNs;
import com.ericsson.bvps.ns.EventsNs;
import com.ericsson.bvps.ns.ServiceAnnouncementControlNs;
import com.ericsson.bvps.ns.ServiceClassNs;
import com.ericsson.bvps.util.Defs;
import com.tailf.conf.Conf;
import com.tailf.conf.ConfBool;
import com.tailf.conf.ConfBuf;
import com.tailf.conf.ConfDatetime;
import com.tailf.conf.ConfException;
import com.tailf.conf.ConfIdentityRef;
import com.tailf.conf.ConfObject;
import com.tailf.conf.ConfPath;
import com.tailf.conf.ConfTag;
import com.tailf.conf.ConfUInt16;
import com.tailf.conf.ConfUInt32;
import com.tailf.maapi.Maapi;
import com.tailf.navu.NavuCdbConfigDiffIterate;
import com.tailf.navu.NavuCdbConfigSubscriber;
import com.tailf.navu.NavuCdbSubscribers;
import com.tailf.navu.NavuCdbSubscriptionConfigContext;
import com.tailf.navu.NavuContainer;
import com.tailf.navu.NavuContext;
import com.tailf.navu.NavuException;
import com.tailf.navu.NavuLeaf;
import com.tailf.ncs.alarmman.common.AlarmId;
import com.tailf.ncs.alarmman.common.ManagedDevice;
import com.tailf.ncs.alarmman.common.ManagedObject;
import com.tailf.ncs.alarmman.common.PerceivedSeverity;
import com.tailf.ncs.alarmman.producer.AlarmSink;
import com.tailf.ncs.ns.Ncs;

/**
 * This class is a container for the EMBM settings. It 
 * also manages the SFTP communication to the CDN.
 * 
 * The class maintains up to date values of the
 * settings container.
 */
public class Settings implements Runnable, NavuCdbConfigDiffIterate
{
    private static Settings settings;

    private static final String PASSWORD = "password";
    private static final String KEYBOARD_INTERACTIVE = "keyboard-interactive";
    private final NavuCdbConfigSubscriber subscriber;

    private int configPort;
    private int configPollInterval=1;
    private int configMpdPollInterval = 5;
    private int configSafUploadInterval=300;

    private String configHost;
    private String configUsername;
    private String configPassword;
    private String configSafFilePath;
    private String configSafServiceClass;
    private String configMsdcService;
    private String configMsdcFileName;
    private String configMsdcFilePath;
    private String configBootStrapFileName;
    private String configBootStrapFilePath;
    private String configSafDeliveryService;
    private String configSafServiceBearer;
    private String configSafUserService;
    
    //add a new server config for SAF uploading -- DMF CR
    private String configHost2;
    private int configPort2;
    private String configUsername2;
    private String configPassword2;
    
    private String configMmwConfigFileName;
    private String configMmwConfigFilePath;
    private String configBootconfigFileName;
    private String configBootconfigFilePath;
    private boolean configBootconfigCombination = false;

    
    private AlarmSink alarmSink = new AlarmSink();

    private boolean bootStrapFailed=false;

    private boolean safFailed=false; // If true, SAF delivery is failing.
    private boolean cdnFailed=false;// If true, CDN delivery is
    private boolean msdcFailed=false;
    private boolean safServiceFailed=false;

    private boolean mmwcFailed = false;
    private boolean bootconfigFailed = false;
    
    private Integer configMaxEvent;
    
    private HashMap<SFTPv3Client, Connection> connectionTable = new HashMap<SFTPv3Client, Connection>();

    private Integer configMaxArchivedbroadcasts;


    /**
     * @return
     */
    public synchronized Integer getMaxEventSize()
    {
        return configMaxEvent;
    }

    /**
     * @return
     */
    public synchronized String getConfigSafDeliveryService()
    {
        return configSafDeliveryService;
    }

    /**
     * @return
     */
    public synchronized String getConfigBootStrapFileName()
    {
        return configBootStrapFileName;
    }

    /**
     * @return
     */
    public synchronized String getConfigSafServiceClass()
    {
        return configSafServiceClass;
    }
    /**
     * @return
     */
    public synchronized String getConfigSafServiceBearer()
    {
        return configSafServiceBearer;
    }
    /**
     * @return
     */
    public synchronized String getConfigSafUserService()
    {
        return configSafUserService;
    }
    /**
     * @return
     */
    public synchronized String getConfigBootStrapFilePath()
    {
        return configBootStrapFilePath;
    }

    /**
     * @return
     */
    public synchronized String getConfigHost()
    {
        return configHost;
    }

    /**
     * @return
     */
    public synchronized int getConfigPort()
    {
        return configPort;
    }

    /**
     * @return
     */
    public synchronized String getConfigUsername()
    {
        return configUsername;
    }

    /**
     * @return
     */
    public synchronized String getConfigPassword()
    {
        return configPassword;
    }

    /**
     * @return
     */
    public synchronized int getConfigPollInterval()
    {
        return configPollInterval;
    }

    /**
     * @return
     */
    public synchronized int getConfigMpdPollInterval()
    {
        return configMpdPollInterval;
    }
    
    /**
     * @return
     */
    public synchronized int getConfigSafUploadInterval()
    {
        return configSafUploadInterval;
    }

    //add a new server config for SAF uploading -- DMF CR
    public synchronized String getConfigHost2()
    {
        return configHost2;
    }

    public synchronized int getConfigPort2()
    {
        return configPort2;
    }

    public synchronized String getConfigUsername2()
    {
        return configUsername2;
    }

    public synchronized String getConfigPassword2()
    {
        return configPassword2;
    }
    
    /**
     * @return
     */
    public synchronized String getConfigSafFilePath()
    {
        return configSafFilePath;
    }

    private static final Logger LOGGER = Logger
            .getLogger(Settings.class);

    /**
     * @throws UnknownHostException
     * @throws IOException
     * @throws ConfException
     */
    private Settings() throws UnknownHostException, IOException, ConfException
    {
        subscriber = (NavuCdbConfigSubscriber)
                NavuCdbSubscribers.configSubscriber(
                NcsMain.getInstance().getNcsHost(),
                NcsMain.getInstance().getNcsPort(),
                Settings.class.getName());

        subscriber.register(this, new ConfPath("/services/properties/settings")); 
        updateConfig(null);
    }

    @Override
    public void run()
    {
        subscriber.run();
    }

    /**
     * @return
     */
    public static Settings getInstance()
    {
        synchronized (Settings.class){
            if (settings == null)
                try {
                    settings = new Settings();
                } catch (Exception e) {
                    LOGGER.error("Failed to create settings.");
                }
        }
        return settings;
    }


    /**
     * Read the config from the settings container.
     * @param navuContainer 
     */
    private synchronized void updateConfig(NavuContainer root)
    {
        Maapi maapi=null;
        int th = 0;
        try {
            if (root == null){
                LOGGER.debug("Opening transaction");
                LOGGER.debug("Creating my own maapi socket.");
                maapi = NcsUtil.newSystemMaapi();
                th = maapi.startTrans(Conf.DB_RUNNING, Conf.MODE_READ);
                root = new NavuContainer(new NavuContext(maapi,th))
                .container(new Ncs().hash());
            }

            NavuContainer properties = root
                    .container(Ncs._services)
                    .container(Ncs._properties);
                
            NavuContainer settings = properties
                    .container(ServiceAnnouncementControlNs._settings);

            if(settings.exists())
            {
                LOGGER.debug("Reading updated CDN config.");
                NavuContainer cdn = settings.container(ServiceAnnouncementControlNs._cdn);

                NavuContainer sftpConfig =
                        cdn.container(ServiceAnnouncementControlNs._sftp_config);

                ConfBuf confHost =
                        (ConfBuf) sftpConfig.leaf(ServiceAnnouncementControlNs._host).value();
                ConfBuf confUsername =
                        (ConfBuf) sftpConfig.leaf(ServiceAnnouncementControlNs._username).value();
                ConfBuf confPassword =
                        (ConfBuf) sftpConfig.leaf(ServiceAnnouncementControlNs._password).value();
                ConfBuf confSafFilePath =
                        (ConfBuf) cdn.leaf(ServiceAnnouncementControlNs._saf_path).value();

                //add a new server config for SAF uploading -- DMF CR
                NavuContainer sftpConfig2 =
                        cdn.container(ServiceAnnouncementControlNs._sftp_config2);
                ConfBuf confHost2 =
                        (ConfBuf) sftpConfig2.leaf(ServiceAnnouncementControlNs._host).value();
                ConfBuf confUsername2 =
                        (ConfBuf) sftpConfig2.leaf(ServiceAnnouncementControlNs._username).value();
                ConfBuf confPassword2 =
                        (ConfBuf) sftpConfig2.leaf(ServiceAnnouncementControlNs._password).value();
                ConfUInt16 confPort2 = 
                        ((ConfUInt16) sftpConfig2.leaf(ServiceAnnouncementControlNs._port).value());
                
                ConfBuf confMsdcFileName =
                        (ConfBuf) cdn.leaf(ServiceAnnouncementControlNs._msdc_file_name_).value();

                ConfBuf confMsdcFilePath =
                        (ConfBuf) cdn.leaf(ServiceAnnouncementControlNs._msdc_path_).value();

                ConfBuf confBootStrapFilePath =
                        (ConfBuf) cdn.leaf(ServiceAnnouncementControlNs._boot_strap_path_).value();

                ConfBuf confBootStrapFileName =
                        (ConfBuf) cdn.leaf(ServiceAnnouncementControlNs._boot_strap_file_name_).value();

                ConfUInt16 confSafUploadInterval =
                        (ConfUInt16) cdn.leaf(ServiceAnnouncementControlNs._saf_upload_interval).value();
                
                if (confSafUploadInterval != null)
                    configSafUploadInterval = (int)confSafUploadInterval.longValue();
                
                ConfBuf confMmwFileName =
                    (ConfBuf) cdn.leaf(ServiceAnnouncementControlNs._mmwc_file_name_).value();
                
                ConfBuf confMmwFilePath =
                    (ConfBuf) cdn.leaf(ServiceAnnouncementControlNs._mmwc_path_).value();

                ConfBuf confBootconfigFileName =
                    (ConfBuf) cdn.leaf(ServiceAnnouncementControlNs._boot_config_file_name_).value();
                
                ConfBuf confBootconfigFilePath =
                    (ConfBuf) cdn.leaf(ServiceAnnouncementControlNs._boot_config_path_).value();
                
                ConfBool confBootconfigCombination = 
                    (ConfBool) cdn.leaf(ServiceAnnouncementControlNs._combination_).value();

                
                ConfBuf confSafDeliveryService = null;
                ConfBuf confSafSeviceBearer = null;
                ConfBuf confSafUserService = null;
                for (NavuContainer safDeliveryService:  settings.list(BroadcastServiceNs._service_announcement_service).elements())
                {
                    confSafDeliveryService =
                            (ConfBuf) safDeliveryService
                            .leaf(BroadcastServiceNs._broadcast_service).value();
                    confSafSeviceBearer =
                            (ConfBuf) safDeliveryService
                            .leaf(BroadcastServiceNs._service_bearer).value();
                    confSafUserService =
                            (ConfBuf) safDeliveryService
                            .leaf(BroadcastServiceNs._user_service).value();

                    //Maximum one element in this list
                }
                ConfBuf confMsdcService =
                        (ConfBuf) settings.leaf(ServiceAnnouncementControlNs._msdc_service_).value();

                ConfUInt16 confEventLogSize =
                        (ConfUInt16) settings.container(EventsNs._event_log_).leaf(EventsNs._max_events_).value();

                if (confEventLogSize != null) {
                    configMaxEvent = new Integer((int) confEventLogSize.longValue());
                }

                ConfUInt16 confMaxArchivedBroadcasts =
                        (ConfUInt16) settings.container(BroadcastArchiveNs._broadcast_archive_).leaf(BroadcastArchiveNs._max_broadcasts_).value();

                if (confMaxArchivedBroadcasts != null) {
                    configMaxArchivedbroadcasts = new Integer((int) confMaxArchivedBroadcasts.longValue());
                }

                NavuLeaf serviceClassKeyLeaf = settings
                        .leaf(BroadcastServiceNs._service_announcement_service_class_);
                ConfBuf confSafServiceClassRef =
                        (ConfBuf) serviceClassKeyLeaf.value();

                ConfBuf confSafServiceClass=null;

                if (confSafServiceClassRef != null){
                    /*
                     * Get service-class value.
                     */
                    confSafServiceClass=(ConfBuf) properties
                            .container(ServiceClassNs._service_classes_)
                            .list(ServiceClassNs._service_class_)
                            .elem(serviceClassKeyLeaf.toKey())
                            .leaf(ServiceClassNs._service_class_)
                            .value();
                }
                if (confHost != null)
                    configHost = confHost.toString();
                else
                    LOGGER.debug("CDN config missing hostname");

                if (confUsername != null)
                    configUsername = confUsername.toString();
                else
                    LOGGER.debug("CDN config missing username");

                if (confPassword != null)
                    configPassword = confPassword.toString();
                else
                    LOGGER.debug("CDN config missing password");

                //add a new server config for SAF uploading -- DMF CR
                if (confHost2 != null)
                    configHost2 = confHost2.toString();
                else
                    LOGGER.debug("CDN config2 missing hostname");

                if (confPort2 != null)
                    configPort2 = (int) confPort2.longValue();
                else
                    LOGGER.debug("CDN config2 missing port");
                
                if (confUsername2 != null)
                    configUsername2 = confUsername2.toString();
                else
                    LOGGER.debug("CDN config2 missing username");

                if (confPassword2 != null)
                    configPassword2 = confPassword2.toString();
                else
                    LOGGER.debug("CDN config2 missing password");
                
                handleSafDirectoryStatus(confSafFilePath);

                handleSafDeliveryServiceStatus(confSafDeliveryService, confSafServiceClass, confSafSeviceBearer, confSafUserService);

                /**
                 * CR029, Use MMW configuration replace the MSDC
                 * So, hide the MSDC related parameters update handle logic
                 *
                handleMsdcStatus(confMsdcFilePath, confMsdcFileName, confMsdcService);
                 */

                handleBootStrapFileStatus(confBootStrapFilePath, confBootStrapFileName);

                handleMmwConfigStatus(confMmwFilePath, confMmwFileName);

                handleBootconfigStatus(confBootconfigCombination, confBootconfigFilePath, confBootconfigFileName);
                
                
                configPollInterval =
                        (int) ((ConfUInt32) cdn
                                .leaf(ServiceAnnouncementControlNs._poll_interval)
                                .value()).longValue();
                
                configMpdPollInterval =
                        (int) ((ConfUInt32) cdn
                                .leaf(ServiceAnnouncementControlNs._mpd_poll_interval)
                                .value()).longValue();
                
                configPort =
                        (int) ((ConfUInt16) sftpConfig
                                .leaf(ServiceAnnouncementControlNs._port)
                                .value()).longValue();
                if (cdnFailed==true){
                    alarmSink.submitAlarm(new ManagedDevice(Defs.BVPS), 
                            new ManagedObject(Defs.CDN), 
                            new ConfIdentityRef(BvpsAlarmTypesNs.hash,
                                    BvpsAlarmTypesNs._cdn_unavailable_alarm), 
                                    new ConfBuf(""),
                                    PerceivedSeverity.CLEARED,
                                    "CDN settings has been restored.",
                                    new ArrayList<ManagedObject>(), 
                                    new ArrayList<AlarmId>(), 
                                    new ArrayList<ManagedObject>(),
                                    ConfDatetime.getConfDatetime());
                }
            } else {
                LOGGER.debug("No settings available. Will not be able to publish services.");
                cdnFailed=true;
                alarmSink.submitAlarm(new ManagedDevice(Defs.BVPS), 
                        new ManagedObject(Defs.CDN), 
                        new ConfIdentityRef(BvpsAlarmTypesNs.hash,
                                BvpsAlarmTypesNs._cdn_unavailable_alarm), 
                                new ConfBuf(""),
                                PerceivedSeverity.WARNING,
                                "No settings available. Will not be able to publish services.",
                                new ArrayList<ManagedObject>(), 
                                new ArrayList<AlarmId>(), 
                                new ArrayList<ManagedObject>(),
                                ConfDatetime.getConfDatetime());
            }
        } catch (NavuException e) {
            LOGGER.warn("Unexpected error reading CDN configuration.", e);
            cdnFailed=true;
            try {
                alarmSink.submitAlarm(new ManagedDevice(Defs.BVPS), 
                        new ManagedObject(Defs.CDN), 
                        new ConfIdentityRef(BvpsAlarmTypesNs.hash,
                                BvpsAlarmTypesNs._cdn_unavailable_alarm), 
                                new ConfBuf(""),
                                PerceivedSeverity.MAJOR,
                                "Cannot read CDN access settings. ",
                                new ArrayList<ManagedObject>(), 
                                new ArrayList<AlarmId>(), 
                                new ArrayList<ManagedObject>(),
                                ConfDatetime.getConfDatetime());
            } catch (Exception e2) {
                LOGGER.error("Failed to emit alarm.",e2);
            }

        } catch (Exception e) {
            LOGGER.error("",e);
        } finally {

            if (maapi  != null){
                LOGGER.debug("Closing transaction.");
                try {
                    maapi.finishTrans(th);
                    maapi.getSocket().close();
                } catch (Exception e) {
                    LOGGER.error("Failed to close transaction.",e);
                }
            }
        }


    }

    /**
     * @param confBootStrapFilePath
     * @param confBootStrapFileName
     * @throws NavuException
     * @throws ConfException
     * @throws IOException
     */
    public synchronized void handleBootStrapFileStatus(
            ConfBuf confBootStrapFilePath,
            ConfBuf confBootStrapFileName) throws NavuException,
            ConfException,
            IOException
            {
        if (confBootStrapFileName != null &&
                confBootStrapFilePath != null) {
            configBootStrapFileName = confBootStrapFileName.toString();
            configBootStrapFilePath = confBootStrapFilePath.toString();
            if (bootStrapFailed == true){
                alarmSink.submitAlarm(new ManagedDevice(Defs.BVPS), 
                        new ManagedObject(Defs.BOOT_STRAP_FILE), 
                        new ConfIdentityRef(BvpsAlarmTypesNs.hash,
                                BvpsAlarmTypesNs._boot_strapfile_delivery_alarm), 
                                new ConfBuf(""),
                                PerceivedSeverity.CLEARED,
                                "CDN config missing boot strap directory",
                                new ArrayList<ManagedObject>(), 
                                new ArrayList<AlarmId>(), 
                                new ArrayList<ManagedObject>(),
                                ConfDatetime.getConfDatetime());

            }

            bootStrapFailed = false;
        } else {
            LOGGER.debug("CDN config missing BOOT STRAP FILE NAME and/or PATH");
            bootStrapFailed = true;
            alarmSink.submitAlarm(new ManagedDevice(Defs.BVPS), 
                    new ManagedObject(Defs.BOOT_STRAP_FILE), 
                    new ConfIdentityRef(BvpsAlarmTypesNs.hash,
                            BvpsAlarmTypesNs._boot_strapfile_delivery_alarm), 
                            new ConfBuf(""),
                            PerceivedSeverity.MAJOR,
                            "CDN config missing boot strap file name",
                            new ArrayList<ManagedObject>(), 
                            new ArrayList<AlarmId>(), 
                            new ArrayList<ManagedObject>(),
                            ConfDatetime.getConfDatetime());
        }
            }

    /**
     * @param confSafDirectory
     * @throws NavuException
     * @throws ConfException
     * @throws IOException
     */
    public synchronized void
    handleSafDirectoryStatus(ConfBuf confSafDirectory) throws NavuException,
    ConfException,
    IOException
    {
        if (confSafDirectory != null) {
            configSafFilePath = confSafDirectory.toString();
            if (safFailed == true){
                alarmSink.submitAlarm(new ManagedDevice(Defs.BVPS), 
                        new ManagedObject(Defs.SDCH), 
                        new ConfIdentityRef(BvpsAlarmTypesNs.hash,
                                BvpsAlarmTypesNs._sdch_delivery_alarm), 
                                new ConfBuf(""),
                                PerceivedSeverity.CLEARED,
                                "CDN config missing service announcement directory",
                                new ArrayList<ManagedObject>(), 
                                new ArrayList<AlarmId>(), 
                                new ArrayList<ManagedObject>(),
                                ConfDatetime.getConfDatetime());

            }

            safFailed = false;
        } else {
            LOGGER.debug("CDN config missing service announcement directory");
            safFailed = true;
            alarmSink.submitAlarm(new ManagedDevice(Defs.BVPS), 
                    new ManagedObject(Defs.SDCH), 
                    new ConfIdentityRef(BvpsAlarmTypesNs.hash,
                            BvpsAlarmTypesNs._sdch_delivery_alarm), 
                            new ConfBuf(""),
                            PerceivedSeverity.MAJOR,
                            "CDN config missing service announcement directory",
                            new ArrayList<ManagedObject>(), 
                            new ArrayList<AlarmId>(), 
                            new ArrayList<ManagedObject>(),
                            ConfDatetime.getConfDatetime());
        }
    }

    /**
     * @param confSafDeliveryService
     * @param confSafServiceClass
     * @param confSafSeviceBearer
     * @param confSafUserService
     * @throws NavuException
     * @throws ConfException
     * @throws IOException
     */
    public synchronized void
        handleSafDeliveryServiceStatus(
                ConfBuf confSafDeliveryService,
                ConfBuf confSafServiceClass,
                ConfBuf confSafSeviceBearer,
                ConfBuf confSafUserService) throws NavuException,
            ConfException,
            IOException
    {

        configSafDeliveryService = confSafDeliveryService==null ? null : confSafDeliveryService.toString();
        configSafServiceClass = confSafServiceClass==null ? null : confSafServiceClass.toString();
        configSafServiceBearer = confSafSeviceBearer==null ? null : confSafSeviceBearer.toString();
        configSafUserService = confSafUserService==null ? null : confSafUserService.toString();
        if (confSafDeliveryService != null &&
            confSafServiceClass != null) {
            if (safServiceFailed == true) {
                alarmSink
                    .submitAlarm(
                            new ManagedDevice(Defs.BVPS),
                            new ManagedObject(Defs.SDCH),
                            new ConfIdentityRef(
                                    BvpsAlarmTypesNs.hash,
                                    BvpsAlarmTypesNs._missing_saf_delivery_service_alarm),
                            new ConfBuf(""),
                            PerceivedSeverity.CLEARED,
                            "Settings is missing service announcement delivery service/class",
                            new ArrayList<ManagedObject>(),
                            new ArrayList<AlarmId>(),
                            new ArrayList<ManagedObject>(),
                            ConfDatetime.getConfDatetime());

            }

            safServiceFailed = false;
        } else {
            LOGGER
                .debug("Settings is missing service announcement delivery service/class");
            safServiceFailed = true;
            alarmSink
                .submitAlarm(
                        new ManagedDevice(Defs.BVPS),
                        new ManagedObject(Defs.SDCH),
                        new ConfIdentityRef(
                                BvpsAlarmTypesNs.hash,
                                BvpsAlarmTypesNs._missing_saf_delivery_service_alarm),
                        new ConfBuf(""),
                        PerceivedSeverity.MAJOR,
                        "Settings is missing service announcement delivery service/class",
                        new ArrayList<ManagedObject>(),
                        new ArrayList<AlarmId>(),
                        new ArrayList<ManagedObject>(),
                        ConfDatetime.getConfDatetime());
        }
    }

    /**
     * @param confMsdcDirectory
     * @param confMsdcFileName
     * @param confMsdcService
     * @throws NavuException
     * @throws ConfException
     * @throws IOException
     */
    public synchronized void
    handleMsdcStatus(ConfBuf confMsdcDirectory, ConfBuf confMsdcFileName, ConfBuf confMsdcService) throws NavuException,
    ConfException,
    IOException
    {
        configMsdcFilePath = confMsdcDirectory == null ? null : confMsdcDirectory.toString();
        configMsdcService = confMsdcService == null ? null : confMsdcService.toString();
        configMsdcFileName = confMsdcFileName == null ? null : confMsdcFileName.toString();
        if(confMsdcDirectory != null && confMsdcFileName != null)
        {
            if (msdcFailed == true){
                alarmSink.submitAlarm(new ManagedDevice(Defs.BVPS), 
                        new ManagedObject(Defs.MSDC), 
                        new ConfIdentityRef(BvpsAlarmTypesNs.hash,
                                BvpsAlarmTypesNs._msdc_delivery_alarm), 
                                new ConfBuf(""),
                                PerceivedSeverity.CLEARED,
                                "MSDC settings are incomplete",
                                new ArrayList<ManagedObject>(), 
                                new ArrayList<AlarmId>(), 
                                new ArrayList<ManagedObject>(),
                                ConfDatetime.getConfDatetime());

            }

            msdcFailed = false;
        } else {
            LOGGER.debug("MSDC settings are incomplete");
            msdcFailed = true;
            alarmSink.submitAlarm(new ManagedDevice(Defs.BVPS), 
                    new ManagedObject(Defs.MSDC), 
                    new ConfIdentityRef(BvpsAlarmTypesNs.hash,
                            BvpsAlarmTypesNs._msdc_delivery_alarm), 
                            new ConfBuf(""),
                            PerceivedSeverity.MAJOR,
                            "MSDC settings are incomplete",
                            new ArrayList<ManagedObject>(), 
                            new ArrayList<AlarmId>(), 
                            new ArrayList<ManagedObject>(),
                            ConfDatetime.getConfDatetime());
        }
    }

    /**
     * Update MMW configuration parameters
     * 
     * @param confMmwFilePath
     * @param confMmwFileName
     * @throws NavuException
     * @throws ConfException
     * @throws IOException
     */
    public synchronized void 
    handleMmwConfigStatus(ConfBuf confMmwFilePath, ConfBuf confMmwFileName) throws NavuException, ConfException, IOException
    {
        configMmwConfigFilePath = confMmwFilePath == null ? null : confMmwFilePath.toString();
        configMmwConfigFileName = confMmwFileName == null ? null : confMmwFileName.toString();
        
        if (configMmwConfigFilePath != null && configMmwConfigFileName != null) {
            if (mmwcFailed) {
                alarmSink.submitAlarm(
                        new ManagedDevice(Defs.BVPS), 
                        new ManagedObject(Defs.MMWC), 
                        new ConfIdentityRef(BvpsAlarmTypesNs.hash, BvpsAlarmTypesNs._mmwc_delivery_alarm), 
                        new ConfBuf(""),
                        PerceivedSeverity.CLEARED,
                        "MMW configuration settings are incomplete",
                        new ArrayList<ManagedObject>(), 
                        new ArrayList<AlarmId>(), 
                        new ArrayList<ManagedObject>(),
                        ConfDatetime.getConfDatetime());
            }
            mmwcFailed = false;
            
        } else {
            LOGGER.debug("MMW configuration settings are incomplete");
            mmwcFailed = true;
            alarmSink.submitAlarm(
                    new ManagedDevice(Defs.BVPS), 
                    new ManagedObject(Defs.MMWC), 
                    new ConfIdentityRef(BvpsAlarmTypesNs.hash, BvpsAlarmTypesNs._mmwc_delivery_alarm), 
                    new ConfBuf(""),
                    PerceivedSeverity.MAJOR,
                    "MMW configuration settings are incomplete",
                    new ArrayList<ManagedObject>(), 
                    new ArrayList<AlarmId>(), 
                    new ArrayList<ManagedObject>(),
                    ConfDatetime.getConfDatetime());
        }
    }
    
    /**
     * Update bootconfig parameters
     * 
     * @param confBootconfigCombination
     * @param confBootconfigFilePath
     * @param confBootconfigFileName
     * @throws NavuException
     * @throws ConfException
     * @throws IOException
     */
    public synchronized void
        handleBootconfigStatus(ConfBool confBootconfigCombination, ConfBuf confBootconfigFilePath, ConfBuf confBootconfigFileName)
            throws NavuException, ConfException, IOException
    {
        if (confBootconfigCombination != null) {
            configBootconfigCombination = confBootconfigCombination.booleanValue();
        }
        
        configBootconfigFilePath = confBootconfigFilePath == null ? null : confBootconfigFilePath.toString();
        configBootconfigFileName = confBootconfigFileName == null ? null : confBootconfigFileName.toString();
        
        // only when combination flag is true, 
        // we need to check the bootconfig path and filename
        if (configBootconfigCombination) {
            if (configBootconfigFilePath != null && configBootconfigFileName != null) {
                if (bootconfigFailed) {
                    alarmSink.submitAlarm(
                            new ManagedDevice(Defs.BVPS), 
                            new ManagedObject(Defs.BOOT_CONFIG_FILE), 
                            new ConfIdentityRef(BvpsAlarmTypesNs.hash, BvpsAlarmTypesNs._boot_config_file_delivery_alarm), 
                            new ConfBuf(""),
                            PerceivedSeverity.CLEARED,
                            "Bootconfig settings are incomplete",
                            new ArrayList<ManagedObject>(), 
                            new ArrayList<AlarmId>(), 
                            new ArrayList<ManagedObject>(),
                            ConfDatetime.getConfDatetime());
                }
                bootconfigFailed = false;
                
            } else {
                LOGGER.debug("Bootconfig settings are incomplete");
                bootconfigFailed = true;
                alarmSink.submitAlarm(
                        new ManagedDevice(Defs.BVPS), 
                        new ManagedObject(Defs.BOOT_CONFIG_FILE), 
                        new ConfIdentityRef(BvpsAlarmTypesNs.hash, BvpsAlarmTypesNs._boot_config_file_delivery_alarm), 
                        new ConfBuf(""),
                        PerceivedSeverity.MAJOR,
                        "Bootconfig settings are incomplete",
                        new ArrayList<ManagedObject>(), 
                        new ArrayList<AlarmId>(), 
                        new ArrayList<ManagedObject>(),
                        ConfDatetime.getConfDatetime());
            }
        } else {
            // disable the bootconfig
            if (bootconfigFailed) {
                alarmSink.submitAlarm(
                        new ManagedDevice(Defs.BVPS), 
                        new ManagedObject(Defs.BOOT_CONFIG_FILE), 
                        new ConfIdentityRef(BvpsAlarmTypesNs.hash, BvpsAlarmTypesNs._boot_config_file_delivery_alarm), 
                        new ConfBuf(""),
                        PerceivedSeverity.CLEARED,
                        "Bootconfig settings are incomplete",
                        new ArrayList<ManagedObject>(), 
                        new ArrayList<AlarmId>(), 
                        new ArrayList<ManagedObject>(),
                        ConfDatetime.getConfDatetime());
            }
            bootconfigFailed = false;
        }
    }
    
    
    /**
     * @return
     */
    public SFTPv3Client getSftpConnection()
    {
        if (getConfigHost() == null ||
                getConfigHost().equals("nohost")) {
            return null;
        }
        Connection conn = new Connection(getConfigHost(), getConfigPort());
        ConnectionInfo ci = null;
        try {
            ci = conn.connect();
            String[] methods = conn.getRemainingAuthMethods(getConfigUsername());
            
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("Opening: " + conn + ", allow authentication methods are " + Arrays.toString(methods));
            boolean isAuth = false;
            
            if(conn.isAuthMethodAvailable(getConfigUsername(), PASSWORD)) {
                isAuth = conn.authenticateWithPassword(
                        getConfigUsername(),
                        getConfigPassword());
            } else if(conn.isAuthMethodAvailable(getConfigUsername(), KEYBOARD_INTERACTIVE)) {
                isAuth = conn.authenticateWithKeyboardInteractive(getConfigUsername(), new InteractiveCallback() {

                    @Override
                    public String[] replyToChallenge(String name, String instruction, int numPrompts, String[] prompt,
                            boolean[] echo) throws Exception {
                        if(LOGGER.isTraceEnabled())
                            LOGGER.trace("replyToChallenge: name is " + name + ", instruction is " + instruction
                                + ", numPrompts is " + numPrompts + ", prompt is " + Arrays.toString(prompt) + ", echo is " + Arrays.toString(echo));
                        if(Arrays.toString(prompt).toLowerCase().contains(PASSWORD)) {
                            String[] response = new String[1];
                            response[0] = getConfigPassword();
                            return response;
                        } else {
                            return new String[0];
                        }
                    }
                    
                });
            } else {
                LOGGER.error("BMC can only support password or keyboard-interactive authentication method currently");
                return null;
            }

            if (!isAuth)
            {
                LOGGER.error("Authentication with CDN failed");
            }
            else
            {
                SFTPv3Client sftpClient = new SFTPv3Client(conn);
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug("Opening sftp: "+ sftpClient);
                connectionTable.put(sftpClient, conn);
                return sftpClient;
            }
        } catch (SFTPException e)
        {
            LOGGER.warn("SFTPException: ", e);
        } catch (IOException e)
        {
            LOGGER.warn("Unable to create SFTP connection to CDN.", e);

        }
        return null;
    }
    
    /**
     * @return
     */
    public SFTPv3Client getSftpConnection(String sftpHost, int sftpPort, String sftpUser, final String sftpPassword)
    {
        if (sftpHost == null ||
                sftpHost.equals("nohost")) {
            return null;
        }
        Connection conn = new Connection(sftpHost, sftpPort);
        ConnectionInfo ci = null;
        try {
            ci = conn.connect();
            String[] methods = conn.getRemainingAuthMethods(sftpUser);
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("Opening: " + conn + ", allow authentication methods are " + Arrays.toString(methods));
            boolean isAuth = false;
            
            if(conn.isAuthMethodAvailable(sftpUser, PASSWORD)) {
                isAuth = conn.authenticateWithPassword(
                        sftpUser,
                        sftpPassword);
            } else if(conn.isAuthMethodAvailable(sftpUser, KEYBOARD_INTERACTIVE)) {
                isAuth = conn.authenticateWithKeyboardInteractive(sftpUser, new InteractiveCallback() {

                    @Override
                    public String[] replyToChallenge(String name, String instruction, int numPrompts, String[] prompt,
                            boolean[] echo) throws Exception {
                        if(LOGGER.isTraceEnabled())
                            LOGGER.trace("replyToChallenge: name is " + name + ", instruction is " + instruction
                                + ", numPrompts is " + numPrompts + ", prompt is " + Arrays.toString(prompt) + ", echo is " + Arrays.toString(echo));
                        if(Arrays.toString(prompt).toLowerCase().contains(PASSWORD)) {
                            String[] response = new String[1];
                            response[0] = sftpPassword;
                            return response;
                        } else {
                            return new String[0];
                        }
                    }
                    
                });
            } else {
                LOGGER.error("BMC can only support password or keyboard-interactive authentication method currently");
                return null;
            }

            if (!isAuth)
            {
                LOGGER.error("Authentication with CDN failed");
            }
            else
            {
                SFTPv3Client sftpClient = new SFTPv3Client(conn);
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug("Opening sftp: "+ sftpClient);
                connectionTable.put(sftpClient, conn);
                return sftpClient;
            }
        } catch (SFTPException e)
        {
            LOGGER.warn("SFTPException: ", e);
        } catch (IOException e)
        {
            LOGGER.warn("Unable to create SFTP connection to CDN.", e);

        }
        return null;
    }
    
    
    /**
     * 
     * @param sftpClient
     */
    public void endSftpConnection(SFTPv3Client sftpClient)
    {
        try {
            Connection conn = connectionTable.remove(sftpClient);
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("Closing sftp: " + sftpClient);
            sftpClient.close();
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("Closing: " + conn);
            conn.close();
        }
        catch (Exception e)
        {
            LOGGER.warn("Failed to close sftp connection.", e);
        }
    }

    /**
     * @return
     */
    public synchronized String getConfigMsdcFilePath()
    {
        return configMsdcFilePath;
    }


    /**
     * @return
     */
    public synchronized String getConfigMsdcServiceName()
    {
        return configMsdcService;
    }

    /**
     * @return
     */
    public synchronized String getConfigMsdcFileName()
    {
        return configMsdcFileName;
    }

    
    /**
     *  Get MMW configuration file name
     */
    public synchronized String getConfigMmwConfigFileName()
    {
        return configMmwConfigFileName;
    }
    
    /**
     *  Get MMW configuration file path
     */
    public synchronized String getConfigMmwConfigFilePath()
    {
        return configMmwConfigFilePath;
    }
    
    /**
     *  Get Bootconfig file name
     */
    public synchronized String getConfigBootconfigFileName()
    {
        return configBootconfigFileName;
    }
    
    /**
     *  Get Bootconfig file path
     */
    public synchronized String getConfigBootconfigFilePath()
    {
        return configBootconfigFilePath;
    }

    /**
     * Get Bootconfig combination status
     */
    public synchronized boolean getConfigBootconfigCombination()
    {
        return configBootconfigCombination;
    }

    
    /* (non-Javadoc)
     * @see com.tailf.navu.NavuCdbConfigDiffIterate#iterate(com.tailf.navu.NavuCdbSubscriptionConfigContext)
     */
    @Override
    public void iterate(NavuCdbSubscriptionConfigContext ctx)
    {
        ConfObject[] kp = ctx.keyPath();

        if (kp[0] instanceof ConfTag && 
                ((((ConfTag)kp[0]).getTagHash() == ServiceAnnouncementControlNs._valid_until_frequency) || 
                        (((ConfTag)kp[0]).getTagHash() == ServiceAnnouncementControlNs._valid_until_timer)))
        {
            /*
             * Ignore valid until settings.
             */
            ctx.iterRecurse();           
        } else {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("Updating: "+Arrays.toString(kp));
            updateConfig(ctx.root());
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("Updated: "+Arrays.toString(kp));
            ctx.iterStop();
        }

    }

    public Integer getMaxArchivedBroadcasts()
    {
        return configMaxArchivedbroadcasts;
    }


}
