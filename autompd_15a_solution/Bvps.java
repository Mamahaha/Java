package com.ericsson.bvps;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.restlet.data.Parameter;

import com.ericsson.bvps.ac.AdmissionControl;
import com.ericsson.bvps.ac.BroadcastAreaValidator;
import com.ericsson.bvps.ac.ValidateFrequencies;
import com.ericsson.bvps.alarmstatus.AlarmCollector;
import com.ericsson.bvps.announcement.BaseUrlValidator;
import com.ericsson.bvps.announcement.BootStrapFileSubscriber;
import com.ericsson.bvps.announcement.ControlFragmentGenerator;
import com.ericsson.bvps.announcement.LastKnownStatusSubscriber;
import com.ericsson.bvps.announcement.SafOperDataCb;
import com.ericsson.bvps.announcement.ServiceAnnouncementSubscriber;
import com.ericsson.bvps.announcement.Settings;
import com.ericsson.bvps.announcement.ValidUntilGenerator;
import com.ericsson.bvps.broacastarea.SaiDb;
import com.ericsson.bvps.carousel.Carousel;
import com.ericsson.bvps.event.BmscEventService;
import com.ericsson.bvps.event.snmp.BmscSnmpEventService;
import com.ericsson.bvps.ha.HaControl;
import com.ericsson.bvps.license.LicenseManagerImpl;
import com.ericsson.bvps.mpdmonitor.MpdMonitor;
import com.ericsson.bvps.ncm.NcmValidator;
import com.ericsson.bvps.ncmned.NcmNedModule;
import com.ericsson.bvps.ncs.NcsUtil;
import com.ericsson.bvps.ned.PassEncryptValidator;
import com.ericsson.bvps.ned.NedModule;
import com.ericsson.bvps.reporting.ReportingBroadcastStopListener;
import com.ericsson.bvps.service_area_management.MbmsGwMap;
import com.ericsson.bvps.service_area_management.SaiBdcMapping;
import com.ericsson.bvps.servicemapper.BcsOperDataCb;
import com.ericsson.bvps.servicemapper.BroadcastServiceScheduleSubscriber;
import com.ericsson.bvps.servicemapper.OperStateSubscriber;
import com.ericsson.bvps.servicemapper.archive.BroadcastArchiver;
import com.ericsson.bvps.servicemapper.cancel.ActionCancelBroadcast;
import com.ericsson.bvps.servicemapper.device.BdcDeviceSyncSubscriber;
import com.ericsson.bvps.servicemapper.metadata.ActionSetControlFragment;
import com.ericsson.bvps.servicemapper.preprocessor.MpdIsUploadSubscriber;
import com.ericsson.bvps.servicemapper.refreshcontent.ActionRefreshContent;
import com.ericsson.bvps.servicemapper.status.ResourceStatusSubscriber;
import com.ericsson.bvps.servicemapper.validator.AnnounceValidator;
import com.ericsson.bvps.servicemapper.validator.BroadcastValidator;
import com.ericsson.bvps.servicemapper.validator.FileScheduleValidator;
import com.ericsson.bvps.ue_mw_config.ActionGenerateUeConfigFile;
import com.ericsson.bvps.user_admin.BroadcastServiceCallback;
import com.ericsson.bvps.user_admin.ServiceClassCallback;
import com.ericsson.bvps.ue_mw_config.MmwConfigFileSubscriber;
import com.ericsson.bvps.ue_mw_config.MmwConfigValidator;
import com.ericsson.bvps.ue_mw_config.MmwDefaultConfigLoadUtil;
import com.ericsson.bvps.user_admin.UserRegistryCallback;
import com.tailf.maapi.Maapi;
import com.tailf.ncs.ApplicationComponent;
import com.tailf.ncs.NcsMain;

public class Bvps implements ApplicationComponent
{
    private static final Logger LOGGER = Logger.getLogger(Bvps.class);

    public static Semaphore dbExclusivity = new Semaphore(1);

    private ServiceMonitor monitor;

    @Override
    public void init()
    {
        try {
            monitor = new ServiceMonitor();
        } catch (Exception e) {
            // Work-around for Tail-f TR #11414 Java API: Exceptions from
            // ApplicationComponent ignored
            LOGGER.fatal("Error during init", e);
            System.exit(1);
        }
    }

    @Override
    public void finish()
    {
        LicenseManagerImpl.getInstance().destroy();
        monitor.shutdownNow();
    }

    public void run()
    {
        try {
		
            LOGGER.info("Start\n" + getVersionInfo());
            // start license control, require license first, and then renew
            // license repeatly.
            // if require license fail, will invoke System.exit(1) exit.
            startLicenseControl();

            doRun();
        } catch (Exception e) {
            LOGGER.fatal("Uncaught error", e);
            NcsMain.getInstance().shutdown();
        }
    }

    private void startLicenseControl()
    {
        LicenseManagerImpl licenseManager = LicenseManagerImpl.getInstance();
        licenseManager.start();
    }

    private void doRun()
    {
        
        try {
            Maapi maapi = NcsUtil.newSystemMaapi();
            try {
                maapi.loadSchemas();
            } finally {
                maapi.getSocket().close();
            }
        } catch (Exception e) {
            LOGGER.error("Unable to load schema", e);
        }
        // this is make the classloader the load this class 
        Parameter p = new Parameter();
        p.setName("For ClassLoader issue");
        
        HaControl haControl = HaControl.getHaControl();
        AlarmCollector alarmCollector = new AlarmCollector();
        ServiceAnnouncementSubscriber saSubscr = null;
        BootStrapFileSubscriber bootstrapSubscr = null;
        MmwConfigFileSubscriber mmwConfigFileSubscr = null;
        AdmissionControl admissionControl = new AdmissionControl();
        BroadcastAreaValidator broadcastAreaValidator =
            new BroadcastAreaValidator();
        BroadcastValidator broadcastValidator = new BroadcastValidator();
        FileScheduleValidator fileScheduleValidator =
            new FileScheduleValidator();
        NcmValidator ncmValidator = new NcmValidator();
        LastKnownStatusSubscriber lastKnownSubscr = null;
        ControlFragmentGenerator fragGen = null;
        BroadcastServiceScheduleSubscriber bSSSubscriber = null;
        SaiDb saiDb = null;
        BroadcastArchiver archiver = null;
        ReportingBroadcastStopListener reportingBroadcastStopListener = null;
        OperStateSubscriber operStateSubscriber = null;
        BdcDeviceSyncSubscriber bdcDevSubscriber = null;

        com.ericsson.bvps.servicemapper.metadata.ActionSetControlFragment aHandler =
            new ActionSetControlFragment();
        ActionCancelBroadcast cancelHandler = new ActionCancelBroadcast();
        ActionRefreshContent refreshHandler = new ActionRefreshContent();

        BcsOperDataCb bcsCb = new BcsOperDataCb();
        SafOperDataCb safCb = new SafOperDataCb();

        ResourceStatusSubscriber resourceStatusSub = null;
        BaseUrlValidator baseUrlValidator = new BaseUrlValidator();
        AnnounceValidator announceValidator = new AnnounceValidator();
        UserRegistryCallback userRegistrySubscriber =
            new UserRegistryCallback();
        ServiceClassCallback serviceClassCallback = new ServiceClassCallback();
        BroadcastServiceCallback broadcastServiceCallback = new BroadcastServiceCallback();
        ActionGenerateUeConfigFile actionGenerateUeConfigFile = new ActionGenerateUeConfigFile();
        MpdIsUploadSubscriber mpdIsUploadSubscriber = MpdIsUploadSubscriber.getInstance();

        MbmsGwMap mbmsgwMap = MbmsGwMap.getInstance();
        SaiBdcMapping saiBdcMapping = SaiBdcMapping.getInstance();

        MmwConfigValidator mmwConfigValidator = new MmwConfigValidator();


        try {
            saSubscr = new ServiceAnnouncementSubscriber();
            bootstrapSubscr = new BootStrapFileSubscriber();
            mmwConfigFileSubscr = new MmwConfigFileSubscriber();
            fragGen = new ControlFragmentGenerator();
            lastKnownSubscr = new LastKnownStatusSubscriber();
            bSSSubscriber = BroadcastServiceScheduleSubscriber.getInstance();
            operStateSubscriber = new OperStateSubscriber();
            bdcDevSubscriber = new BdcDeviceSyncSubscriber();
            resourceStatusSub = new ResourceStatusSubscriber();
            archiver = new BroadcastArchiver();
            reportingBroadcastStopListener = new ReportingBroadcastStopListener();
            saiDb = new SaiDb();
            
        } catch (Exception e) {
            LOGGER.fatal("Subscribers could not be created.", e);
        }

        Settings settings = Settings.getInstance();
        ValidUntilGenerator vaGen = ValidUntilGenerator.getInstance();
        ValidateFrequencies validateFrequencies = new ValidateFrequencies();
        PassEncryptValidator passValidator = new PassEncryptValidator();

        Callable<?> ned = NedModule.newBmscNedService();
        Callable<?> ncmNed = NcmNedModule.newNcmNedService();
        BmscEventService event = new BmscEventService();
        BmscSnmpEventService trap = null;
        try {
            trap = new BmscSnmpEventService();
        } catch (Exception e) {
            LOGGER.info("Failed to start trap dispatcher", e);
        }

        Carousel carousel = Carousel.getInstance();
        DpReg dpReg = new DpReg();
        haControl.subscribe(trap);
        haControl.subscribe(carousel);
        dpReg.carousel = carousel;
        monitor.submit(haControl);
        monitor.submit(dpReg);
        monitor.submit(alarmCollector);
        monitor.submit(ned);
        monitor.submit(ncmNed);
        monitor.submit(saSubscr);
        monitor.submit(bootstrapSubscr);
        monitor.submit(settings);
        monitor.submit(vaGen);
        monitor.submit(admissionControl);
        monitor.submit(broadcastAreaValidator);
        monitor.submit(broadcastValidator);
        monitor.submit(fileScheduleValidator);
        monitor.submit(ncmValidator);
        monitor.submit(bcsCb);
        monitor.submit(safCb);
        monitor.submit(aHandler);
        monitor.submit(cancelHandler);
        monitor.submit(refreshHandler);
        monitor.submit(fragGen);
        monitor.submit(lastKnownSubscr);
        monitor.submit(bSSSubscriber);
        monitor.submit(carousel);
        monitor.submit(event);
        monitor.submit(operStateSubscriber);
        monitor.submit(bdcDevSubscriber);
        monitor.submit(resourceStatusSub);
        monitor.submit(archiver);
        monitor.submit(reportingBroadcastStopListener);
        monitor.submit(baseUrlValidator);
        monitor.submit(announceValidator);
        monitor.submit(userRegistrySubscriber);
        monitor.submit(serviceClassCallback);
        monitor.submit(broadcastServiceCallback);
        monitor.submit(saiDb);
        monitor.submit(actionGenerateUeConfigFile);
        monitor.submit(validateFrequencies);
        monitor.submit(mpdIsUploadSubscriber);

        monitor.submit(mbmsgwMap);
        monitor.submit(saiBdcMapping);
        monitor.submit(passValidator);


        monitor.submit(mmwConfigValidator);
        monitor.submit(mmwConfigFileSubscr);

        if (trap !=null){

            monitor.submit(trap);
        }

        // load the default MMW configuration and schema
        MmwDefaultConfigLoadUtil.loadMmwDefaultConfig();
        
        MpdMonitor mpdMonitor = new MpdMonitor();
        mpdMonitor.run();
        
        monitor.run();
        LOGGER.info("Stop");
    }

    private String getVersionInfo()
    {
        Properties ps = new Properties();
        try {
            InputStream s =
                getClass().getResourceAsStream("version.properties");
            if (s == null)
                s = new FileInputStream(new File("version.properties"));
            ps.load(s);
        } catch (IOException e) {
            LOGGER.error("Loading version info");
        }
        return String.format(
                "BVPS Version: %s\n" +
                    "BVPS Git Branch: %s\n" +
                    "BVPS Git Revision: %s\n" +
                    "NCS Version: %s",
                ps.getProperty("BVPS_VERSION", "Unknown"),
                ps.getProperty("BVPS_GIT_BRANCH", "Unknown"),
                ps.getProperty("BVPS_GIT_REV", "Unknown"),
                ps.getProperty("NCS_VERSION", "Unknown"));
    }

    public static void main(String[] args)
    {
        new Bvps().run();
    }
}
