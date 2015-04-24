package com.ericsson.bvps.mpdmonitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ericsson.bvps.io.CloseUtil;
import com.ericsson.bvps.ncs.NcsUtil;
import com.ericsson.bvps.ns.BroadcastServiceNs;
import com.ericsson.bvps.ns.BvpsMpdCacheNs;
import com.ericsson.bvps.util.MaapiUtils;
import com.tailf.conf.Conf;
import com.tailf.conf.ConfBool;
import com.tailf.conf.ConfEnumeration;
import com.tailf.maapi.Maapi;
import com.tailf.navu.NavuContainer;
import com.tailf.navu.NavuContext;
import com.tailf.navu.NavuException;
import com.tailf.navu.NavuList;
import com.tailf.ncs.ns.Ncs;

public class MpdCache implements IMpdCache {
    private static final Logger LOGGER = Logger.getLogger(MpdCache.class);

    private static IMpdCache cacheInstance = new MpdCache();
    // memory cache : <mpd_url, mpd_info>
    private static Map<String, MpdInfo> mpdCache;

    MpdDownloader mpdDownloader = new MpdDownloader();

    public enum Operation {
        CREATE, UPDATE, DELETE
    }

    private MpdCache() {
    }

    public static IMpdCache getInstance() {
        return cacheInstance;
    }

    @Override
    public boolean initCache() {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Start initializing MPD cache");
        boolean result = false;
        if (initMemoryCache()) {
            if (initCdbCache()) {
                result = true;
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Initialize MPD cache successfully");
            }
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("End of initializing MPD cache with result: " + result);
        return result;
    }

    @Override
    public boolean reloadMemoryCache() {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Start reloading MPD memory cache from CDB");
        if (mpdCache != null) {
            mpdCache.clear();
        }
        
        mpdCache = new ConcurrentHashMap<String, MpdInfo>();
        Maapi maapi = null;
        int th = 0;

        try {
            maapi = NcsUtil.newSystemMaapi();
            th = maapi.startTrans(Conf.DB_RUNNING, Conf.MODE_READ);
            NavuContext context = new NavuContext(maapi, th);
            NavuContainer root = new NavuContainer(context)
                    .container(BvpsMpdCacheNs.hash);

            if (null == root)
                LOGGER.error("Failed to access CDB: /bmch is null");

            NavuContainer mpdCdb = root.container(BvpsMpdCacheNs._mpd_cache_);
            NavuList mpdInfoList = mpdCdb.list(BvpsMpdCacheNs._mpd_info_);

            for (NavuContainer item : mpdInfoList) {
                String url = item.leaf(BvpsMpdCacheNs._mpd_url_)
                        .valueAsString();
                long lmt = Integer.parseInt(item.leaf(
                        BvpsMpdCacheNs._last_modify_time_).valueAsString());
                String md5 = item.leaf(BvpsMpdCacheNs._md5_).valueAsString();
                Set<MpdServiceInfo> bcSet = new HashSet<MpdServiceInfo>();
                for (NavuContainer subItem : item
                        .list(BvpsMpdCacheNs._broadcasts)) {

                    bcSet.add(new MpdServiceInfo(subItem.leaf(
                            BvpsMpdCacheNs._broadcast).valueAsString()));
                }
                MpdInfo mpdInfo = new MpdInfo(lmt, md5, bcSet);
                mpdCache.put(url, mpdInfo);
            }

            //maapi.applyTrans(th, false);
        } catch (Exception e) {
            LOGGER.error("Failed to reload memory cache from CDB cache: ", e);
        } finally {
            try {
                maapi.finishTrans(th);
            } catch (Exception e1) {
                LOGGER.error("Failed to close CDB transaction: " + e1);
            }
            CloseUtil.close(maapi.getSocket());
        }
        return true;
    }

    @Override
    public void clearMemoryCache() {
        if (mpdCache != null) {
            mpdCache.clear();
        }
        mpdCache = null;
    }

    @Override
    public boolean memoryCacheReady() {
        if (mpdCache != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean cdbCacheReady() {
        boolean result = false;
        Maapi maapi = null;
        int th = 0;
        try {
            maapi = NcsUtil.newSystemMaapi();
            th = maapi.startTrans(Conf.DB_RUNNING, Conf.MODE_READ);
            NavuContext context = new NavuContext(maapi, th);
            NavuContainer root = new NavuContainer(context)
                    .container(BvpsMpdCacheNs.hash);

            if (null == root) {
                LOGGER.error("Failed to access CDB: /bmch is null");
            }
            NavuContainer mpdCdb = root.container(BvpsMpdCacheNs._mpd_cache_);
            
            String strResult = mpdCdb.leaf(BvpsMpdCacheNs._init_flag).valueAsString();
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("CDB cache status is: " + strResult);
            
            result = Boolean.parseBoolean(strResult);
            
            //maapi.applyTrans(th, false);
        } catch (Exception e) {
            LOGGER.error("Failed to get CDB cache status: " + e);
        } finally {
            try {
                maapi.finishTrans(th);
            } catch (Exception e1) {
                LOGGER.error("Failed to close CDB transaction: " + e1);
            }
            CloseUtil.close(maapi.getSocket());
        }

        return result;
    }

    @Override
    public Set<MpdServiceInfo> getBroadcastsByMpd(Set<String> mpdUrls) {
        Set<MpdServiceInfo> bcServices = new HashSet<MpdServiceInfo>();
        for (String mpdUrl : mpdUrls) {
            for (MpdServiceInfo serviceInfo : mpdCache.get(mpdUrl)
                    .getServiceInfo()) {
                bcServices.add(serviceInfo);
            }
        }
        return bcServices;
    }

    @Override
    public boolean updateCacheFromRemote(Map<String, MpdInfo> changedMpd) {
        boolean result = true;
        if (updateCdbFromRemote(changedMpd)) {
            if (updateMemoryFromRemote(changedMpd)) {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Update MPD cache successfully.");
            } else {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Failed to update MPD cache in memory.");
                result = false;
            }
        } else {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Failed to update MPD cache in CDB.");
            result = false;
        }

        return result;
    }

    @Override
    public boolean updateCacheFromLocal(String mpdUrl,
            MpdServiceInfo serviceInfo, long date, String md5, Operation op) {
        if (updateCdbFromLocal(mpdUrl, serviceInfo, date, md5, op)) {
            updateMemoryFromLocal(mpdUrl, serviceInfo, date, md5, op);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, MpdInfo> getChangedMpd() {
        Map<String, MpdInfo> changedMpd = new HashMap<String, MpdInfo>();
        for (Map.Entry<String, MpdInfo> entry : mpdCache.entrySet()) {
            String mpdUrl = entry.getKey();
            MpdInfo originalMpdInfo = entry.getValue();
            try {
                MpdInfo remoteMpdInfo = mpdDownloader.downloadFile(mpdUrl,
                        entry.getValue().getLastModifyTime());
                if (null != remoteMpdInfo
                        && !remoteMpdInfo.getMd5().equals(
                                originalMpdInfo.getMd5())) {
                    MpdInfo newMpdInfo = new MpdInfo(
                            remoteMpdInfo.getLastModifyTime(),
                            remoteMpdInfo.getMd5(), entry.getValue()
                                    .getServiceInfo());
                    changedMpd.put(mpdUrl, newMpdInfo);
                }
            } catch (Exception e) {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Download MPD failed: " + e);
            }
        }

        return changedMpd;
    }

    @Override
    public boolean clearCdbCache() {
        LOGGER.info("Start clearing CDB cache");
        boolean result = false;
        Maapi maapi = null;
        int th = 0;
        try {
            maapi = NcsUtil.newSystemMaapi();
            th = maapi.startTrans(Conf.DB_RUNNING, Conf.MODE_READ_WRITE);
            NavuContext context = new NavuContext(maapi, th);
            NavuContainer root = new NavuContainer(context)
                    .container(BvpsMpdCacheNs.hash);
            if (null == root) {
                LOGGER.error("Failed to access CDB: /bmch is null");
            }
            NavuContainer mpdCdb = root.container(BvpsMpdCacheNs._mpd_cache_);

            NavuList mpdInfoList = mpdCdb.list(BvpsMpdCacheNs._mpd_info_);
            if (mpdInfoList.size() > 0) {
                for (NavuContainer item : mpdInfoList) {
                    item.delete();
                }
            }
            mpdCdb.leaf(BvpsMpdCacheNs._init_flag).set(new ConfBool(false));
            maapi.applyTrans(th, false);
            LOGGER.info("Clear CDB cache successfully");
            result = true;
        } catch (Exception e) {
            LOGGER.error("Failed to clear CDB cache: " + e);
        } finally {
            try {
                maapi.finishTrans(th);
            } catch (Exception e1) {
                LOGGER.error("Failed to close CDB transaction: " + e1);
            }
            CloseUtil.close(maapi.getSocket());
        }

        return result;
    }
    
    private boolean updateMemoryFromRemote(Map<String, MpdInfo> changedMpd) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Update MPD memory cache due to PMD files have changed");
        for (Map.Entry<String, MpdInfo> entry : changedMpd.entrySet()) {
            try {
                String mpdUrl = entry.getKey();
                MpdInfo mpdInfo = entry.getValue();
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Update MPD memory cache for mpd_url:<"
                            + mpdUrl + ">");

                MpdInfo newMpdInfo = new MpdInfo(mpdInfo.getLastModifyTime(),
                        mpdInfo.getMd5(), mpdInfo.getServiceInfo());

                mpdCache.put(mpdUrl, newMpdInfo);
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    private boolean updateCdbFromRemote(Map<String, MpdInfo> changedMpd) {
        boolean result = false;
        Maapi maapi = null;
        int th = 0;
        try {
            maapi = NcsUtil.newSystemMaapi();
            th = maapi.startTrans(Conf.DB_RUNNING, Conf.MODE_READ_WRITE);
            NavuContext context = new NavuContext(maapi, th);
            NavuContainer root = new NavuContainer(context)
                    .container(BvpsMpdCacheNs.hash);

            if (null == root) {
                LOGGER.error("Failed to access CDB: /bmch is null");
            }
            NavuContainer mpdCdb = root.container(BvpsMpdCacheNs._mpd_cache_);
            NavuList mpdInfoList = mpdCdb.list(BvpsMpdCacheNs._mpd_info_);

            for (Map.Entry<String, MpdInfo> entry : changedMpd.entrySet()) {
                String mpdUrl = entry.getKey();
                MpdInfo mpdInfo = entry.getValue();

                if (mpdCdb.list(BvpsMpdCacheNs._mpd_info_).containsNode(mpdUrl)) {
                    NavuContainer cacheNode = mpdInfoList.elem(MaapiUtils
                            .toKey(mpdUrl));
                    cacheNode.leaf(BvpsMpdCacheNs._last_modify_time_).set(
                            String.valueOf(mpdInfo.getLastModifyTime()));
                    cacheNode.leaf(BvpsMpdCacheNs._md5_).set(mpdInfo.getMd5());
                } else {
                    LOGGER.info("No such mpdUrl is found in CDB cache: "
                            + mpdUrl);
                }
            }

            maapi.applyTrans(th, false);
            result = true;
        } catch (Exception e) {
            LOGGER.error("Failed to update CDB cache from remote: " + e);
        } finally {
            try {
                maapi.finishTrans(th);
            } catch (Exception e1) {
                LOGGER.error("Failed to close CDB transaction: " + e1);
            }
            CloseUtil.close(maapi.getSocket());
        }
        
        return result;
    }

    private boolean updateCdbFromLocal(String mpdUrl,
            MpdServiceInfo mpdService, long lastModifyTime, String md5,
            Operation op) {
        Maapi maapi = null;
        int th = 0;
        try {
            maapi = NcsUtil.newSystemMaapi();
            th = maapi.startTrans(Conf.DB_RUNNING, Conf.MODE_READ_WRITE);
            NavuContext context = new NavuContext(maapi, th);
            NavuContainer root = new NavuContainer(context)
                    .container(BvpsMpdCacheNs.hash);

            if (null == root) {
                LOGGER.error("Failed to access CDB: /bmch is null");
            }
            NavuContainer mpdCdb = root.container(BvpsMpdCacheNs._mpd_cache_);

            switch (op) {
            case CREATE:
                addMpdInfoInCdb(mpdCdb, mpdUrl, mpdService, lastModifyTime, md5);
                break;
            case UPDATE:
                addMpdInfoInCdb(mpdCdb, mpdUrl, mpdService, lastModifyTime, md5);
                break;
            case DELETE:
                removeMpdInfoFromCdb(mpdCdb, mpdUrl, mpdService);
                break;
            default:
                LOGGER.error("Unsupported operation for MPD CDB cache");
                break;
            }
            maapi.applyTrans(th, false);
        } catch (Exception e) {
            LOGGER.error("Failed to update CDB cache from local: " + e);
        } finally {
            try {
                maapi.finishTrans(th);
            } catch (Exception e1) {
                LOGGER.error("Failed to close CDB transaction: " + e1);
            }
            CloseUtil.close(maapi.getSocket());
        }
        return true;
    }

    private void addMpdInfoInCdb(NavuContainer mpdCdb, String mpdUrl,
            MpdServiceInfo mpdService, long lastModifyTime, String md5)
            throws NavuException {
        NavuList mpdInfoList = mpdCdb.list(BvpsMpdCacheNs._mpd_info_);
        if (!mpdCdb.list(BvpsMpdCacheNs._mpd_info_).containsNode(mpdUrl)) {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Add a new mpdUrl in CDB cache: " + mpdUrl);

            mpdInfoList.create(mpdUrl);
            NavuContainer cacheNode = mpdInfoList
                    .elem(MaapiUtils.toKey(mpdUrl));
            cacheNode.leaf(BvpsMpdCacheNs._last_modify_time_).set(
                    String.valueOf(lastModifyTime));
            cacheNode.leaf(BvpsMpdCacheNs._md5_).set(md5);
            cacheNode.list(BvpsMpdCacheNs._broadcasts).create(
                    mpdService.toString());
        } else {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("mpdUrl already exists in CDB cache: " + mpdUrl);

            NavuContainer cacheNode = mpdInfoList
                    .elem(MaapiUtils.toKey(mpdUrl));
            cacheNode.leaf(BvpsMpdCacheNs._last_modify_time_).set(
                    String.valueOf(lastModifyTime));
            cacheNode.leaf(BvpsMpdCacheNs._md5_).set(md5);
            if (!cacheNode.list(BvpsMpdCacheNs._broadcasts).containsNode(
                    mpdService.toString())) {
                cacheNode.list(BvpsMpdCacheNs._broadcasts).create(
                        mpdService.toString());
            } else {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Failed to add the broadcast into CDB cache because it already exists: "
                            + mpdService.toString());
            }
        }
    }

    private void removeMpdInfoFromCdb(NavuContainer mpdCdb, String mpdUrl,
            MpdServiceInfo mpdService) throws NavuException {
        if (mpdCdb.list(BvpsMpdCacheNs._mpd_info_).containsNode(mpdUrl)) {
            NavuList mpdInfoList = mpdCdb.list(BvpsMpdCacheNs._mpd_info_);
            NavuContainer cacheNode = mpdInfoList
                    .elem(MaapiUtils.toKey(mpdUrl));
            if (cacheNode.list(BvpsMpdCacheNs._broadcasts).containsNode(
                    mpdService.toString())) {
                cacheNode.list(BvpsMpdCacheNs._broadcasts).delete(
                        mpdService.toString());

                if (cacheNode.list(BvpsMpdCacheNs._broadcasts).size() == 0) {
                    mpdInfoList.delete(mpdUrl);
                }
            } else {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Failed to remove the broadcast from CDB cache because it does not exist: "
                            + mpdService.toString());
            }
        } else {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("No such mpdUrl exists in CDB cache: " + mpdUrl);
        }
    }

    private void updateMemoryFromLocal(String mpdUrl,
            MpdServiceInfo mpdService, long lastModifyTime, String md5,
            Operation op) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Update MPD memory cache due to broadcast status has changed: mpd_url:<"
                    + mpdUrl
                    + "> service_info:<"
                    + mpdService.toString()
                    + "> lastModifyTime:<"
                    + lastModifyTime
                    + "> md5:<"
                    + md5
                    + ">");
        switch (op) {
        case CREATE:
            addMpdInfoInMemory(mpdUrl, mpdService, lastModifyTime, md5);
            break;
        case UPDATE:
            addMpdInfoInMemory(mpdUrl, mpdService, lastModifyTime, md5);
            break;
        case DELETE:
            removeMpdInfoFromMemory(mpdUrl, mpdService);
            break;
        default:
            LOGGER.error("Unsupported operation for MPD memory cache");
            break;
        }
    }

    private void addMpdInfoInMemory(String mpdUrl, MpdServiceInfo mpdService,
            long lastModifyTime, String md5) {
        MpdInfo newMpdInfo = null;
        if (mpdCache.containsKey(mpdUrl)) {
            MpdInfo originalMpdInfo = mpdCache.get(mpdUrl);
            // originalMpdInfo.getBroadcastIds().add(bcId);
            newMpdInfo = new MpdInfo(lastModifyTime, md5,
                    originalMpdInfo.getServiceInfo());
            newMpdInfo.getServiceInfo().add(mpdService);
        } else {
            newMpdInfo = new MpdInfo(lastModifyTime, md5, mpdService);
        }

        mpdCache.put(mpdUrl, newMpdInfo);
    }

    private void removeMpdInfoFromMemory(String mpdUrl,
            MpdServiceInfo mpdService) {
        if (mpdCache.containsKey(mpdUrl)) {
            MpdInfo originalMpdInfo = mpdCache.get(mpdUrl);
            if (originalMpdInfo.getServiceInfo().contains(mpdService)) {
                originalMpdInfo.getServiceInfo().remove(mpdService);
            }

            if (originalMpdInfo.getServiceInfo().size() == 0) {
                mpdCache.remove(mpdUrl);
            }
        } else {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("No such mpdUrl exists in memory cache: " + mpdUrl);
        }
    }

    private boolean initCdbCache() {
        LOGGER.info("Start initializing CDB cache");
        if (!clearCdbCache()) {
            return false;
        }

        boolean result = false;
        Maapi maapi = null;
        int th = 0;
        try {
            maapi = NcsUtil.newSystemMaapi();
            th = maapi.startTrans(Conf.DB_RUNNING, Conf.MODE_READ_WRITE);
            NavuContext context = new NavuContext(maapi, th);
            NavuContainer root = new NavuContainer(context)
                    .container(BvpsMpdCacheNs.hash);
            if (null == root) {
                LOGGER.error("Failed to access CDB: /bmch is null");
            }
            NavuContainer mpdCdb = root.container(BvpsMpdCacheNs._mpd_cache_);

            for (Map.Entry<String, MpdInfo> entry : mpdCache.entrySet()) {
                String mpdUrl = entry.getKey();
                MpdInfo mpdInfo = entry.getValue();
                for (MpdServiceInfo serviceInfo : mpdInfo.getServiceInfo()) {
                    addMpdInfoInCdb(mpdCdb, mpdUrl, serviceInfo,
                            mpdInfo.getLastModifyTime(), mpdInfo.getMd5());
                }
            }
            mpdCdb.leaf(BvpsMpdCacheNs._init_flag).set(new ConfBool(true));

            maapi.applyTrans(th, false);
            LOGGER.info("Initialize CDB cache successfully");
            result = true;
        } catch (Exception e) {
            LOGGER.error("Failed to initialize CDB cache: " + e);
        } finally {
            try {
                maapi.finishTrans(th);
            } catch (Exception e1) {
                LOGGER.error("Failed to close CDB transaction: " + e1);
            }
            CloseUtil.close(maapi.getSocket());
        }

        return result;
    }

    private boolean initMemoryCache() {
        LOGGER.info("Start initializing memory cache");
        boolean result = false;
        if (mpdCache != null) {
            mpdCache.clear();
        }
        mpdCache = new ConcurrentHashMap<String, MpdInfo>();

        Maapi maapi = null;
        int th = 0;

        try {
            maapi = NcsUtil.newSystemMaapi();
            th = maapi.startTrans(Conf.DB_RUNNING, Conf.MODE_READ);
            NavuContext context = new NavuContext(maapi, th);
            NavuContainer root = new NavuContainer(context).container(Ncs.hash);

            if (null == root) {
                LOGGER.error("Failed to access CDB: /ncs:services is null");
            }
            // =========================details===================
            NavuList services = root.container(Ncs._services_).list(
                    Ncs._service);

            for (NavuContainer service : services) {
                String serviceName = service.leaf(Ncs._object_id)
                        .valueAsString();
                NavuList broadcasts = service.container(Ncs._type_)
                        .container(BroadcastServiceNs._broadcast_service)
                        .list(BroadcastServiceNs._broadcast);
                for (NavuContainer broadcast : broadcasts) {
                    ConfEnumeration admState = (ConfEnumeration) broadcast
                            .leaf(BroadcastServiceNs._admin_state_).value();
                    if (admState.getOrdinalValue() == BroadcastServiceNs._pending) {
                        continue;
                    }
                    String broadcastName = broadcast.leaf(
                            BroadcastServiceNs._name).valueAsString();
                    NavuList contents = broadcast
                            .list(BroadcastServiceNs._content_);
                    for (NavuContainer content : contents) {
                        String serviceBearer = content.leaf(
                                BroadcastServiceNs._service_bearer)
                                .valueAsString();
                        String userService = content.leaf(
                                BroadcastServiceNs._user_service)
                                .valueAsString();
                        String contentName = serviceBearer + " " + userService;
                        NavuList mpdIsFiles = content
                                .container(BroadcastServiceNs._continuous_)
                                .container(BroadcastServiceNs._metadata_)
                                .list(BroadcastServiceNs._mpd_and_is_reference_);
                        for (NavuContainer mpdIsFile : mpdIsFiles) {
                            String mpdIsKey = mpdIsFile.leaf(
                                    BroadcastServiceNs._representation_id)
                                    .valueAsString();
                            String mpdIsUrl = mpdIsFile.leaf(
                                    BroadcastServiceNs._fragment_url_)
                                    .valueAsString();
                            MpdInfo mpdInfo = mpdDownloader.downloadFile(
                                    mpdIsUrl, -1);
                            if (mpdInfo != null) {
                                mpdInfo.AddServiceInfo(new MpdServiceInfo(
                                        serviceName, broadcastName,
                                        contentName, mpdIsKey));
                                insertMpdInfo(mpdIsUrl, mpdInfo);
                            }
                        }
                    }
                }
            }
            // =========================details===================
            //maapi.applyTrans(th, false);
            LOGGER.info("Initialize memory cache successfully");
            result = true;
        } catch (Exception e) {
            LOGGER.error("Failed to initialize memory cache: " + e);
        } finally {
            try {
                maapi.finishTrans(th);
            } catch (Exception e1) {
                LOGGER.error("Failed to close CDB transaction: " + e1);
            }
            CloseUtil.close(maapi.getSocket());
        }

        return result;
    }

    private void insertMpdInfo(String mpdUrl, MpdInfo mpdInfo) {
        if (mpdCache == null) {
            LOGGER.error("Failed to insert mpdInfo into memory cache due to mpdCache is null");
            return;
        }
        if (mpdCache.containsKey(mpdUrl)) {
            mpdCache.get(mpdUrl).getServiceInfo()
                    .addAll(mpdInfo.getServiceInfo());
        } else {
            mpdCache.put(mpdUrl, mpdInfo);
        }
    }
}
