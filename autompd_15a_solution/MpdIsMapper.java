package com.ericsson.bvps.servicemapper.metadata;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ericsson.bvps.ns.BroadcastServiceNs;
import com.ericsson.bvps.ns.ServiceAnnouncementNs;
import com.ericsson.bvps.servicemapper.BroadcastServiceScheduleSubscriber;
import com.ericsson.bvps.servicemapper.BroadcastTime;
import com.ericsson.bvps.servicemapper.SdchSettings;
import com.ericsson.bvps.servicemapper.util.Util;
import com.ericsson.bvps.servicemapper.util.VersionUtil;
import com.ericsson.bvps.util.MaapiUtils;
import com.tailf.cdb.Cdb;
import com.tailf.conf.ConfBool;
import com.tailf.conf.ConfBuf;
import com.tailf.conf.ConfDatetime;
import com.tailf.conf.ConfEnumeration;
import com.tailf.conf.ConfException;
import com.tailf.conf.ConfKey;
import com.tailf.conf.ConfObject;
import com.tailf.conf.ConfUInt32;
import com.tailf.conf.ConfValue;
import com.tailf.navu.NavuContainer;
import com.tailf.navu.NavuContext;
import com.tailf.navu.NavuException;
import com.tailf.navu.NavuList;
import com.tailf.ncs.NcsMain;

public class MpdIsMapper
{
    private static final Logger LOGGER = Logger
            .getLogger(MpdIsMapper.class);
    private SdchSettings settings;
    private String serviceName;
    private MessageDigest digestor;

    public MpdIsMapper(
            SdchSettings settings, String serviceName, MessageDigest digestor)
    {
        this.settings=settings;
        this.serviceName=serviceName;
        this.digestor=digestor;
    }

    /**
     * Creates MPD and IS fragments in the service announcement model. 
     * Precondition: The MPD list element must be created -
     * 
     * @param serviceAnn
     * @param bcService
     * @throws NavuException
     * @throws UnsupportedEncodingException
     */
    public void createMpdAndIs(
            NavuContainer serviceAnn,
            NavuContainer bcService,
            ConfDatetime validUntilOverride,
            boolean fastMap) throws NavuException, UnsupportedEncodingException
            {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("start to create MPD and IS for service " + bcService.getName());
        }
        NavuList mpdList =
                serviceAnn
                .list(ServiceAnnouncementNs._media_presentation_description);

        ConfBool announce = (ConfBool) bcService.leaf(BroadcastServiceNs._announce_).value();
        Boolean announceBool=false;
        if(announce==null)
            announceBool = ((ConfBool)bcService.leaf(BroadcastServiceNs._announce_)
                    .getInfo()
                    .getCsNode()
                    .getNodeInfo()
                    .getDefval()).booleanValue();
        else
            announceBool=announce.booleanValue();

        /*
         * Create time stamped MPD and IS
         * for all continuous content.
         */

        HashMap<ConfKey, BroadcastTime> content2Broadcast = new HashMap<ConfKey, BroadcastTime>();

        String scheduleUri = null;
        NavuList broadcassts = bcService.list(BroadcastServiceNs._broadcast_);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("The broadcast number is " + broadcassts.size() + " under service " + bcService.getName());
        }
        for (NavuContainer broadcast : bcService.list(
                BroadcastServiceNs._broadcast_).elements()) {
            
            ConfKey broadcastKey = broadcast
                    .leaf(BroadcastServiceNs._name_)
                    .toKey();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Processing broadcast " + broadcastKey);
            }
            ConfEnumeration admState = (ConfEnumeration)broadcast
                    .leaf(BroadcastServiceNs._admin_state_)
                    .value();

            if (admState.getOrdinalValue() != BroadcastServiceNs._pending && announceBool){

                ConfDatetime startTime = (ConfDatetime)broadcast
                        .container(BroadcastServiceNs._schedule_)
                        .container(BroadcastServiceNs._start_time_)
                        .leaf(BroadcastServiceNs._actual_)
                        .value();

                ConfDatetime stopTime = (ConfDatetime)broadcast
                        .container(BroadcastServiceNs._schedule_)
                        .container(BroadcastServiceNs._stop_time_)
                        .leaf(BroadcastServiceNs._actual_)
                        .value();
                for (NavuContainer content :broadcast.list(BroadcastServiceNs._content_).elements()) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Processing content " + content.getName() + " under broadcast " + broadcastKey);
                    }

                    if (content
                            .choice(BroadcastServiceNs._instance_)
                            .getSelectedCase().getTag()
                            .equals(BroadcastServiceNs._continuous_)) {

                        ConfValue userServiceValue = content
                                .leaf(BroadcastServiceNs._user_service_)
                                .value();

                        ConfValue serviceBearerValue = content
                                .leaf(BroadcastServiceNs._service_bearer_)
                                .value();

                        ConfKey contentKey = new ConfKey(new ConfObject[]{
                            serviceBearerValue,
                            userServiceValue
                        });
                        //Only announce if mpd exists
                        NavuContainer metadata = content.container(BroadcastServiceNs._continuous_)
                                .container(BroadcastServiceNs._metadata_);

                        if (metadata != null &&
                                metadata.leaf(BroadcastServiceNs._mpd_).exists()
                                && metadata.leaf(BroadcastServiceNs._mpd_).value() != null){
                            BroadcastTime broadcastTime = new BroadcastTime(broadcastKey,
                                    contentKey,
                                    startTime,
                                    stopTime);
    
                            BroadcastTime currentBest = content2Broadcast.get(contentKey);
    
                            if (currentBest == null){
                                content2Broadcast.put(contentKey, broadcastTime);
                            } else if (broadcastTime.isBetterFitThan(currentBest)){
                                content2Broadcast.put(contentKey, broadcastTime);
                                if(LOGGER.isTraceEnabled())
                                {
                                    LOGGER.trace("Better fit!: " + broadcastTime.toString());
                                    LOGGER.trace("Better fit!: " + broadcastTime.getBroadcastKey());
                                }
                            }

                            //stop "wait for mpd" timer
                            BroadcastServiceScheduleSubscriber bSSSubscriber = BroadcastServiceScheduleSubscriber.getInstance();
                            bSSSubscriber.stopMpdTimers(broadcast);
                            //Clear active alarm and remove from not announce list
                            ConfKey notAnnounceKey = new ConfKey(new ConfObject[] {new ConfBuf(serviceName), 
                                new ConfBuf(broadcastTime.getBroadcastKey().toString().substring(1, broadcastTime.getBroadcastKey().toString().length()-1))});
                            if(LOGGER.isDebugEnabled())
                                LOGGER.debug("notAnnounceKey: " + notAnnounceKey);
                            if( serviceAnn.list(ServiceAnnouncementNs._not_announce_services_).containsNode(notAnnounceKey))
                            {
                                NavuContainer notAnnounce = serviceAnn.list(ServiceAnnouncementNs._not_announce_services_).elem(notAnnounceKey);
                                if(notAnnounce != null)
                                {
                                    ConfBool alarmed = (ConfBool) notAnnounce.leaf(ServiceAnnouncementNs._alarmed_).value();
                                    if(alarmed.booleanValue())
                                    {
                                        //Clear active alarm
                                        LOGGER.debug("Clearing alarm");
                                        bSSSubscriber.clearAlarm(broadcast.getKeyPath());
                                    }
                                    if(LOGGER.isDebugEnabled())
                                        LOGGER.debug("notAnnounceKey: " + notAnnounceKey);

                                    String userServiceName = userServiceValue
                                            .toString();
                                    String serviceBearerName = serviceBearerValue
                                            .toString();

                                    scheduleUri =
                                            Util.buildScheduleUri(settings.getBaseUrl(), 
                                                    serviceName,
                                                    serviceBearerName, 
                                                    userServiceName);
                                }
                                LOGGER.debug("Delete notAnnounceKey!");
                                serviceAnn.list(ServiceAnnouncementNs._not_announce_services_).delete(notAnnounceKey);
                            }
                        }
                    }
                }                
            }
        }
        LOGGER.debug("Interate all broadcasts done");
        //update schedule version!
        if ( scheduleUri != null)
        {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("Updating version for: " + scheduleUri);
            int version = VersionUtil.forceVersionUpdate(serviceAnn, scheduleUri);
            if( version != -1)
            {
                NavuContainer sched = serviceAnn
                        .list(ServiceAnnouncementNs._schedule)
                        .create(MaapiUtils.toKey(scheduleUri));
                sched.leaf(ServiceAnnouncementNs._version).set(new ConfUInt32( version));
            }
        }


        for (BroadcastTime broadcastTime: content2Broadcast.values()){
            NavuContainer content = bcService.list(
                    BroadcastServiceNs._broadcast_)
                    .elem(broadcastTime.getBroadcastKey())
                    .list(BroadcastServiceNs._content_)
                    .elem(broadcastTime.getContentKey());
            NavuContainer metadata = content
                    .container(BroadcastServiceNs._continuous_)
                    .container(BroadcastServiceNs._metadata_);

            ConfValue userServiceValue = content
                    .leaf(BroadcastServiceNs._user_service_)
                    .value();
            String userServiceName = userServiceValue
                    .toString();

            ConfValue serviceBearerValue = content
                    .leaf(BroadcastServiceNs._service_bearer_)
                    .value();

            String serviceBearerName = serviceBearerValue
                    .toString();

            if (metadata
                    .leaf(BroadcastServiceNs._mpd_).value() != null
                && !metadata
                    .leaf(BroadcastServiceNs._mpd_).valueAsString().equals("")){
                LOGGER.debug("mpd found");
                
                String mpdUri =
                        Util.buildMpdUri(settings.getBaseUrl(), 
                                serviceName,
                                serviceBearerName, 
                                userServiceName);

                NavuContainer saMpd = mpdList
                        .elem(MaapiUtils.toKey(mpdUri));

                saMpd
                .leaf(ServiceAnnouncementNs._broadcast_name_)
                .set(broadcastTime.getBroadcastName());

                saMpd
                .leaf(ServiceAnnouncementNs._start_time_)
                .set(broadcastTime.getStartTime());

                saMpd
                .leaf(ServiceAnnouncementNs._stop_time_)
                .set(broadcastTime.getStopTime());


                String  mpdBlob = Util
                        .buildBlobFromNode(metadata);

                byte[] hash =
                        digestor
                        .digest((mpdBlob).getBytes("UTF-8"));

                int version = VersionUtil.updateAndGetVersion(serviceAnn, mpdUri, hash);
                if(LOGGER.isDebugEnabled())
                {
                    LOGGER.debug("mpdUri: " + mpdUri);
                    LOGGER.debug("new version: " + version);
                }

                ConfUInt32 confVersion = new ConfUInt32(version);
                saMpd.leaf(ServiceAnnouncementNs._version).set(
                        confVersion);

                if (fastMap)
                {
                    for (NavuContainer is : saMpd
                            .list(ServiceAnnouncementNs._is_).elements())
                    {
                        LOGGER.trace("Setting is version");
                        is.leaf(ServiceAnnouncementNs._version_).set(confVersion);
                        LOGGER.trace("Is version set");
                        if( validUntilOverride != null)
                        {
                            is
                            .leaf(ServiceAnnouncementNs._valid_until_override_)
                            .set(validUntilOverride);
                        }
                    }
                }
                else
                {
                   updateIsInCdb(mpdUri, validUntilOverride, confVersion);
                }
                if( validUntilOverride != null)
                {
                    saMpd
                    .leaf(ServiceAnnouncementNs._valid_until_override_)
                    .set(validUntilOverride);
                }
            }
        }
    }
    
    private void updateIsInCdb( String mpdUri, ConfDatetime validUntilOverride, ConfUInt32 confVersion)
    {
        Socket sock = null;
        try {
            sock = new Socket(NcsMain.getInstance().getNcsHost(), NcsMain.getInstance().getNcsPort());
            Cdb cdb;
            cdb = new Cdb(getClass().getName(),sock);
            final NavuContext context = new NavuContext(cdb);
            try {
                context.setLockingMode(true);
                final NavuContainer root = new NavuContainer(context);
                final NavuContainer serviceAnn = root.container(ServiceAnnouncementNs.hash)
                        .container(ServiceAnnouncementNs._service_announcement_file_);

                NavuList mpdList =
                        serviceAnn
                        .list(ServiceAnnouncementNs._media_presentation_description);

                NavuContainer saMpd = mpdList.elem(MaapiUtils.toKey(mpdUri));
                for (NavuContainer is : saMpd
                        .list(ServiceAnnouncementNs._is_).elements())
                {
                    LOGGER.trace("Setting is version in cdb");
                    is.leaf(ServiceAnnouncementNs._version_).set(confVersion);
                    LOGGER.trace("Is version in cdb set");
                    if( validUntilOverride != null)
                    {
                        is
                        .leaf(ServiceAnnouncementNs._valid_until_override_)
                        .set(validUntilOverride);
                    }
                }
            } finally {
                context.removeCdbSessions();
                if ( sock != null) {
                    try {
                        sock.close();
                    } catch (IOException e) {
                        LOGGER.error("Failed to close socket", e);
                    }
                }
            }

        } catch (UnknownHostException e1) {
            LOGGER.error("Failed to create CDB socket",e1);
        } catch (IOException e1) {
            LOGGER.error("Failed to create CDB socket",e1);
        } catch (ConfException e1) {
            LOGGER.error("Failed to create CDB socket",e1);
        }
    }
}

