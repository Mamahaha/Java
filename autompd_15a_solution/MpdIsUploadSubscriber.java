package com.ericsson.bvps.servicemapper.preprocessor;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.restlet.engine.util.Base64;

import com.ericsson.bvps.ha.HaBlockedFlag;
import com.ericsson.bvps.io.CloseUtil;
import com.ericsson.bvps.loglevels.CheckpointLevel;
import com.ericsson.bvps.mpdmonitor.IMpdCache;
import com.ericsson.bvps.mpdmonitor.MpdCache;
import com.ericsson.bvps.mpdmonitor.MpdCache.Operation;
import com.ericsson.bvps.mpdmonitor.MpdServiceInfo;
import com.ericsson.bvps.ncs.NcsUtil;
import com.ericsson.bvps.ns.BroadcastServiceNs;
import com.ericsson.bvps.ns.BvpsAlarmTypesNs;
import com.ericsson.bvps.util.Defs;
import com.ericsson.bvps.util.MaapiUtils;
import com.tailf.cdb.CdbNotificationType;
import com.tailf.conf.Conf;
import com.tailf.conf.ConfBuf;
import com.tailf.conf.ConfDatetime;
import com.tailf.conf.ConfEnumeration;
import com.tailf.conf.ConfException;
import com.tailf.conf.ConfIdentityRef;
import com.tailf.conf.ConfKey;
import com.tailf.conf.ConfObject;
import com.tailf.conf.ConfPath;
import com.tailf.conf.DiffIterateOperFlag;
import com.tailf.maapi.Maapi;
import com.tailf.navu.NavuCdbConfigDiffIterate;
import com.tailf.navu.NavuCdbSubscriber;
import com.tailf.navu.NavuCdbSubscribers;
import com.tailf.navu.NavuCdbSubscriptionConfigContext;
import com.tailf.navu.NavuContainer;
import com.tailf.navu.NavuContext;
import com.tailf.navu.NavuException;
import com.tailf.ncs.NcsMain;
import com.tailf.ncs.alarmman.common.AlarmId;
import com.tailf.ncs.alarmman.common.ManagedDevice;
import com.tailf.ncs.alarmman.common.ManagedObject;
import com.tailf.ncs.alarmman.common.PerceivedSeverity;
import com.tailf.ncs.alarmman.producer.AlarmSink;
import com.tailf.ncs.ns.Ncs;

public class MpdIsUploadSubscriber implements
    Runnable,
    NavuCdbConfigDiffIterate
{
    private static final Logger LOGGER = Logger
            .getLogger(MpdIsUploadSubscriber.class);
    private final NavuCdbSubscriber subscriber;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private static MpdIsUploadSubscriber instance;
    private HaBlockedFlag haBlockedFlag = HaBlockedFlag.getHaBlockedFlag();
    private boolean deleteMpdModParam;
    private MessageDigest md = null;

    public static MpdIsUploadSubscriber getInstance()
    {
        synchronized (MpdIsUploadSubscriber.class){
            if (instance == null)
                try {
                    instance = new MpdIsUploadSubscriber();
                } catch (Exception e) {
                    LOGGER.error("Failed to create MpdIsUploadSubscriber. ",e);
                }
        }
        return instance;
    }
    
    private MpdIsUploadSubscriber() throws IOException, ConfException
    {
        subscriber = NavuCdbSubscribers.configSubscriber(
                NcsMain.getInstance().getNcsHost(), NcsMain.getInstance().getNcsPort(),
                MpdIsUploadSubscriber.class.getName());
        subscriber.register(this, new ConfPath(
                "/ncs:services/ncs:service/ncs:type/bcs:broadcast-service/bcs:broadcast/bcs:content/bcs:continuous/bcs:metadata/bcs:mpd-and-is-reference"));
        subscriber.register(this, new ConfPath(
                "/ncs:services/ncs:service/ncs:type/bcs:broadcast-service/bcs:broadcast/bcs:admin-state"));

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Failed to initialize MessageDigest instance. ", e);
        }
    }
    
    @Override
    public void iterate(NavuCdbSubscriptionConfigContext ctx)
    {
        if(haBlockedFlag.isHaBlocked())
        {
            ctx.iterContinue();
            return;
        }
        final ConfObject[] keyPath = ctx.keyPath();

        if(LOGGER.isTraceEnabled())
        {
            LOGGER.trace("path = " + Arrays.toString(keyPath));
        }
        if (ctx.getNotifType() != CdbNotificationType.SUB_COMMIT
           || ctx.getOperFlag() == DiffIterateOperFlag.MOP_DELETED) {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("Phase: "+ctx.getNotifType() + ", OperFalg: " + ctx.getOperFlag());
            ctx.iterContinue();
            return;
        }

        try {
            executor.submit(new Runnable() {
                @Override
                public void run()
                {
                    LOGGER.log(CheckpointLevel.CHECKPOINT, "MpdIsUploadSubscriber updateMpd start.");
                    Maapi maapi = null;
                    int th = 0;
                    NavuContainer broadcast = null;
                    try {
                        maapi = NcsUtil.newSystemMaapi();

                        th = maapi.startTrans(Conf.DB_RUNNING, Conf.MODE_READ_WRITE);
                        NavuContext context = new NavuContext(maapi, th);
                        NavuContainer root = new NavuContainer(context);
                        NavuContainer ncs = root.container(new Ncs().hash());
                        deleteMpdModParam = false;
                        boolean modified = false;
                        if(keyPath.length >=9)
                        {
                            ConfKey mpdIsKey = (ConfKey) keyPath[0];
                            ConfKey serviceName = (ConfKey) keyPath[10];
                            ConfKey broadcastName = (ConfKey) keyPath[6];
                            ConfKey contentName = (ConfKey) keyPath[4];
                            broadcast = ncs.container(Ncs._services_)
                                    .list(Ncs._service)
                                    .elem(serviceName)
                                    .container(Ncs._type_)
                                    .container(BroadcastServiceNs._broadcast_service)
                                    .list(BroadcastServiceNs._broadcast)
                                    .elem(broadcastName);
                            ConfEnumeration admState = (ConfEnumeration) broadcast.leaf(BroadcastServiceNs._admin_state_)
                                    .value();
                            
                            NavuContainer metadata = broadcast
                                    .list(BroadcastServiceNs._content_)
                                    .elem(contentName)
                                    .container(BroadcastServiceNs._continuous_)
                                    .container(BroadcastServiceNs._metadata_);
                            NavuContainer mpdIsUpload = metadata
                                    .list(BroadcastServiceNs._mpd_and_is_reference_)
                                    .elem(mpdIsKey);

                            String serviceNameStr = MaapiUtils.toString(serviceName, false);
                            String broadcastNameStr = MaapiUtils.toString(broadcastName, false);
                            String contentNameStr = MaapiUtils.toString(contentName, false);
                            String mpdIsKeyStr = mpdIsUpload.leaf(BroadcastServiceNs._representation_id).valueAsString();
                            MpdServiceInfo mpdServiceInfo = new MpdServiceInfo(serviceNameStr,broadcastNameStr,contentNameStr,mpdIsKeyStr);
                            
                            if (admState.getOrdinalValue() != BroadcastServiceNs._pending)
                            { 
                                modified = doUpdateMpd(mpdIsUpload, mpdServiceInfo);

                                if(deleteMpdModParam)
                                {
                                    if(LOGGER.isDebugEnabled())
                                    {
                                        LOGGER.debug("Deleting _mpd_modification_parameters_");
                                        LOGGER.debug(metadata.getKeyPath());
                                    }
                                    metadata.container(BroadcastServiceNs._mpd_modification_parameters_).delete();
                                }
                            } else {
                                String url = mpdIsUpload.leaf(BroadcastServiceNs._fragment_url_).value().toString();
                                
                                IMpdCache mpdCache = MpdCache.getInstance();
                                //mpdCache.updateCacheFromLocal(url, mpdServiceInfo, 0, null, Operation.DELETE);
                            }
                        }
                        else
                        {
                            
                            ConfKey serviceName = (ConfKey) keyPath[5];
                            ConfKey broadcastName = (ConfKey) keyPath[1];
                            broadcast = ncs.container(Ncs._services_)
                                    .list(Ncs._service)
                                    .elem(serviceName)
                                    .container(Ncs._type_)
                                    .container(BroadcastServiceNs._broadcast_service)
                                    .list(BroadcastServiceNs._broadcast)
                                    .elem(broadcastName);
                            String serviceNameStr = MaapiUtils.toString(serviceName,false);
                            String broadcastNameStr = MaapiUtils.toString(broadcastName, false);
                            modified = updateMpd(broadcast, serviceNameStr, broadcastNameStr);
                        }
                        if (modified) {
                            maapi.applyTrans(th, false);
                        }
                    } catch (ConfException | IOException e) {
                        LOGGER.error("Failed to read mpd/is.", e);
                        sendAlarm(broadcast, getErrorMsg(e));
                    } finally {
                        if (maapi!=null) {
                            try {
                                maapi.finishTrans(th);
                            } catch (IOException | ConfException e) {
                                LOGGER.error("Failed to read mpd/is.", e);
                            }
                            CloseUtil.close(maapi.getSocket());
                        }                            
                    }

                    LOGGER.log(CheckpointLevel.CHECKPOINT, "MpdIsUploadSubscriber updateMpd done.");
                }
            });

        }  finally {
            ctx.iterContinue();
        }
    }

    @Override
    public void run()
    {
        ((Runnable) subscriber).run();
    }

    private boolean updateMpd(NavuContainer broadcast, String serviceName, String broadcastName) throws NavuException, MalformedURLException, IOException
    {
        LOGGER.debug("Checking broadcast: " + broadcast.getKey());
        ConfEnumeration admState = (ConfEnumeration) broadcast.leaf(BroadcastServiceNs._admin_state_)
                .value();
        boolean modified = false;
        if (admState.getOrdinalValue() != BroadcastServiceNs._pending)
        {
            for (NavuContainer content :broadcast.list(BroadcastServiceNs._content_).elements()) {
                if (content
                        .choice(BroadcastServiceNs._instance_)
                        .getSelectedCase().getTag()
                        .equals(BroadcastServiceNs._continuous_)) 
                {
                    NavuContainer metadata = content
                            .container(BroadcastServiceNs._continuous_)
                            .container(BroadcastServiceNs._metadata_);
                    NavuContainer mpdModParam = metadata.container(BroadcastServiceNs._mpd_modification_parameters_);
                    ConfDatetime orgAST = getOriginalAST(mpdModParam.leaf(BroadcastServiceNs._original_availability_start_time_).getKeyPath());
                    if(orgAST != null)
                    {
                        //copy!
                        mpdModParam.leaf(BroadcastServiceNs._original_availability_start_time_).set(orgAST);
                        modified = true;
                    }
                    deleteMpdModParam = false;
                    for (NavuContainer mpdIsUpload : metadata.list(BroadcastServiceNs._mpd_and_is_reference_).elements())
                    {
                        String contentNameStr = MaapiUtils.toString(content.getKey(), false);
                        String mpdIsUploadStr = mpdIsUpload.leaf(BroadcastServiceNs._representation_id).valueAsString();
                        MpdServiceInfo mpdserviceInfo = new MpdServiceInfo(serviceName, broadcastName, contentNameStr, mpdIsUploadStr);
                        boolean bRet = doUpdateMpd(mpdIsUpload, mpdserviceInfo);
                        if (!modified) {
                            modified = bRet;
                        }
                    }
                    if(deleteMpdModParam)
                    {
                        if(LOGGER.isDebugEnabled())
                        {
                            LOGGER.debug("Deleting _mpd_modification_parameters_");
                            LOGGER.debug(metadata.getKeyPath());
                        }
                        // in WPST test for NCS 2.1.3.1, if we delete the MPD reference, it will
                        // trigger this subscriber again and leak socket, check via ncs --status 
                        metadata.container(BroadcastServiceNs._mpd_modification_parameters_).delete();
                    }
                }
            }
        } else {
            IMpdCache mpdCache = MpdCache.getInstance();
            
            for (NavuContainer content :broadcast.list(BroadcastServiceNs._content_).elements()) {
                if (content
                        .choice(BroadcastServiceNs._instance_)
                        .getSelectedCase().getTag()
                        .equals(BroadcastServiceNs._continuous_)) 
                {
                    NavuContainer metadata = content
                            .container(BroadcastServiceNs._continuous_)
                            .container(BroadcastServiceNs._metadata_);
                    for (NavuContainer mpdIsUpload : metadata.list(BroadcastServiceNs._mpd_and_is_reference_).elements())
                    {
                        String contentNameStr = MaapiUtils.toString(content.getKey(), false);
                        String mpdIsUploadStr = mpdIsUpload.leaf(BroadcastServiceNs._representation_id).valueAsString();
                        MpdServiceInfo mpdServiceInfo = new MpdServiceInfo(serviceName, broadcastName, contentNameStr, mpdIsUploadStr);
                        String url = mpdIsUpload.leaf(BroadcastServiceNs._fragment_url_).value().toString();
                        LOGGER.info("Removing from MPD cache: " + url + "; " + mpdServiceInfo.toString());
                        mpdCache.updateCacheFromLocal(url, mpdServiceInfo, 0, null, Operation.DELETE);
                    }
                }
            }
        }
        return modified;
    }

    /*
     * Add 'Z' at the end of AST in MPD if it does not end with 'Z'.
     * Used to resolve HT38696
     */
    private String getMatchedString(String srcString, String patternString) {
        if (srcString == null || patternString == null)
            return null;
        try {
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(srcString);

            if (matcher.find()) {
                return matcher.group(0);
            }
        } catch (Exception e) {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("An unexpected error is found when matching string :\n" + srcString + "\n" + patternString);
        }

        return null;
    }

    private String adjustAST(String mpdString) {
        String outStr = mpdString;
        if (mpdString == null) {
          return null;
        }

        String pattern1 = "availabilityStartTime=\"\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+\"";
        String pattern2 = "availabilityStartTime=\"\\d+-\\d+-\\d+T\\d+:\\d+:\\d+\"";

        String oldStr = getMatchedString(mpdString, pattern1);
        if (oldStr == null) {
            oldStr = getMatchedString(mpdString, pattern2);
        }

        String newStr = null;
        try {
            if (oldStr != null) {
              newStr = oldStr.substring(0, oldStr.length()-1) + "Z\"";
              outStr = mpdString.replaceFirst(oldStr, newStr);
            }
        } catch(Exception e) {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("An unexpected error is found when updating Mpd :\n" + mpdString);
        }

        return outStr;
    }

    private class HttpResult {
        public byte[] retrievedFile;
        public long date;
        
        public HttpResult()
        {
            retrievedFile = null;
            date = 0;
        }
        
        public HttpResult(byte[] retrievedFile, long date)
        {
            this.retrievedFile = retrievedFile;
            this.date = date;
        }
    }
    
    private boolean doUpdateMpd( NavuContainer mpdIsUpload, MpdServiceInfo serviceInfo) throws NavuException, MalformedURLException, IOException
    {
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Uploading: "+mpdIsUpload.getKey());
        }
        String url = mpdIsUpload.leaf(BroadcastServiceNs._fragment_url_).value().toString();

        String representationId = mpdIsUpload.leaf(BroadcastServiceNs._representation_id_).value().toString();

        NavuContainer metadata = (NavuContainer) mpdIsUpload.getParent().getParent();
        /*
         * Try to read file
         */

        HttpResult result = saveFileFromUrl( url);
        byte[] retrievedFile = result.retrievedFile;
        boolean modified = false;
        if(LOGGER.isDebugEnabled())
            LOGGER.debug("Mpd/Is retreived, url:" + url);
        String retreivedFileString = new String(retrievedFile, 0, retrievedFile.length);
        if(LOGGER.isDebugEnabled())
        {
            //LOGGER.debug(retreivedFileString);
            LOGGER.debug("length: " + retrievedFile.length);
        }
        if (retrievedFile.length > 0) 
        {
            if ( retreivedFileString.contains("MPD") || retreivedFileString.contains("mpd") )
            {
                deleteMpdModParam = true;
                try {
                    String newFileString = adjustAST(retreivedFileString);
                    metadata.leaf(BroadcastServiceNs._mpd_).set(newFileString);
                    modified = true;
                } catch (ConfException e) {
                    LOGGER.error("Unable to set MPD", e);
                    throw new RuntimeException("Unable to set MPD", e);
                }
                
                //Update MpdInfo cache for AutoMPD feature
                byte[] hash = md.digest(retrievedFile);
                StringBuilder sb = new StringBuilder(2 *hash.length);
                for (byte b : hash) {
                    sb.append(String.format("%02x", b&0xff));
                }
                IMpdCache cache = MpdCache.getInstance();
                cache.updateCacheFromLocal(url, serviceInfo, result.date, sb.toString(), Operation.CREATE);
            }
            else
            {
                /*
                 * treat the file as an initialization segment.
                 */
                
                boolean encoded = true;
                String encodedIsFile = null;
                try {
                    String decodedFile = new String( Base64.decode(retreivedFileString));
                    if(LOGGER.isDebugEnabled())
                        LOGGER.debug("Decoded file: " + decodedFile);
                    encodedIsFile = retreivedFileString;
                } catch (IllegalArgumentException e){
                    LOGGER.debug("Is file not decoded");
                    encoded = false;
                }
                if (!encoded)
                {
                    encodedIsFile = Base64.encode(retrievedFile, false);  //Base64 parameters not important, handled by mime generation.
                }
                try {
                    int last = url.lastIndexOf("/");

                    String isName = url.substring(last > -1?last+1:0, url.length());

                    ConfKey key = new ConfKey(new ConfObject[]{
                        new ConfBuf(representationId),
                        new ConfBuf(isName)
                    });
                    if(metadata.list(BroadcastServiceNs._is_).containsNode(key))
                    {
                        LOGGER.error("Duplicate init segments found: " + key);
                        //throw new RuntimeException("Duplicate initialization segments: " + key);
                    }
                    NavuContainer is = metadata.list(BroadcastServiceNs._is_).create(key);
                    is.leaf(BroadcastServiceNs._base_64_).set(encodedIsFile);
                    modified = true;
                } catch (ConfException e) {
                    LOGGER.error("Unable to set IS",
                            e);
                    throw new RuntimeException(
                            "Unable to set IS",
                            e);
                }
            }
            // in WPST test for NCS 2.1.3.1, if we delete the MPD reference, it will
            // trigger this subscriber again and leak socket, check via ncs --status 
//            if(LOGGER.isDebugEnabled())
//                LOGGER.debug("Deleting mpd/is reference: "+mpdIsUpload.getKey());
//            mpdIsUpload.delete();

        }
        
        return modified;

    }

    /***
     * 
     * @param filename
     * @param urlString
     * @return Returns retrieved file and date value from server response. Empty string if no file is retrieved.
     * @throws MalformedURLException
     * @throws IOException
     */
    private HttpResult saveFileFromUrl(String urlString) throws MalformedURLException, IOException
    {
        BufferedInputStream in = null;
        byte fileBlob[] = new byte[0];
        long date = 0l;
        try
        {
                URLConnection uc = new URL(urlString).openConnection();
                date = uc.getDate();
                in = new BufferedInputStream(uc.getInputStream());

                byte data[] = new byte[1024];
                int count;
                int totalCount = 0;
                while ((count = in.read(data, 0, 1024)) != -1)
                {
                    if(LOGGER.isTraceEnabled())
                        LOGGER.trace("Downloaded " + count + " bytes.");
                    byte[] temp = new byte[totalCount + count];
                    System.arraycopy(fileBlob, 0, temp, 0, totalCount);
                    System.arraycopy(data, 0, temp, totalCount, count);

                    totalCount = totalCount + count;
                    
                    fileBlob = new byte[totalCount];
                    System.arraycopy(temp, 0, fileBlob, 0, totalCount);
                    
                    data = new byte[1024];
                }
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug("Downloaded a total of " + totalCount + " bytes.");
        }
        finally
        {
            if (in != null)
                in.close();
        }
        return new HttpResult(fileBlob, date);
    }
    
    private ConfDatetime getOriginalAST(String orgASTKeyPath)
    {
        try {
            Maapi maapi = NcsUtil.newSystemMaapi();
            try {
                int th =
                        maapi.startTrans(Conf.DB_RUNNING, Conf.MODE_READ);
                try {
                    ConfDatetime orgAST = (ConfDatetime) maapi.getElem(th, orgASTKeyPath);
                    if(LOGGER.isDebugEnabled())
                        LOGGER.debug("Original AST: "+ orgAST);
                    return orgAST;
                } catch (ConfException e) {
                    if(LOGGER.isDebugEnabled())
                        LOGGER
                            .debug("Unable to read mpd modification enabled");
                } finally {
                    maapi.finishTrans(th);
                }
            } finally {
                maapi.getSocket().close();

            }
        } catch (Exception e) {
            LOGGER.warn("Unable to read mpd modification enabled.");
        }
        return null;
    }
    
    private void sendAlarm(NavuContainer metadata, String errorMsg)
    {
        AlarmSink alarmSink = new AlarmSink();
        try {
            alarmSink.submitAlarm(
                    new ManagedDevice(Defs.BVPS),
                    new ManagedObject(metadata.getKeyPath()),
                    new ConfIdentityRef(new BvpsAlarmTypesNs().hash(),
                            BvpsAlarmTypesNs._file_download_failed_alarm),
                    new ConfBuf(""),
                    PerceivedSeverity.MAJOR,
                    new String("Failed to upload mpd/is: "+errorMsg),
                    new ArrayList<ManagedObject>(),
                    new ArrayList<AlarmId>(),
                    new ArrayList<ManagedObject>(),
                    ConfDatetime.getConfDatetime());
            LOGGER.warn("Failed to upload mpd/is: "+errorMsg);
        } catch (ConfException | IOException e) {
            LOGGER.error("Failed to send alarm.", e);
        }
    } 

    private String getErrorMsg(Exception e)
    {
        String errorMsg = null;
        if (e.getClass() == FileNotFoundException.class) 
        {
            errorMsg = "File not found: ("+e.getMessage()+").";
        }
        else if(e.getClass() == UnknownHostException.class)
        {
            errorMsg = "Unknown host: ("+e.getMessage()+").";
        }
        else if(e.getClass() == MalformedURLException.class)
        {
            errorMsg = "Malformed url: ("+e.getMessage()+").";
        }
        else
        {
            errorMsg = e.getMessage();
        }
            
        return errorMsg;
    }
}

