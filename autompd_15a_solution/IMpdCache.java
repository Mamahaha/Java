package com.ericsson.bvps.mpdmonitor;

import java.util.Map;
import java.util.Set;

import com.ericsson.bvps.mpdmonitor.MpdCache.Operation;

public interface IMpdCache {
    /*
     * Description: Used to initialize MPD memory cache and CDB cache.
     * Usage      : It is called each time the whole BMC system starts up.
     */
    boolean initCache();
    
    /*
     * Description: Used to check if MPD memory cache is synchronized with CDB cache.
     * Usage      : It is called each time HA failover occurs.
     */
    boolean memoryCacheReady();
    
    /*
     * Description: Used to check if MPD CDB cache is synchronized with CDB services.
     */
    boolean cdbCacheReady();
    
    /*
     * Description: Used to synchronize MPD memory cache with CDB cache.
     * Usage      : It is called each time HA failover occurs.
     */
    boolean reloadMemoryCache();
    
    /*
     * Description: Used to clear MPD memory cache.
     * Usage      : It is called each time the MpdMonitorTimerTask is triggered on BMC slave node.
     */
    void clearMemoryCache();
    
    /*
     * Description: Used to clear MPD CDB cache.
     * Usage      : It is called for the first time when BMC master node starts up.
     */
    boolean clearCdbCache();
    
    /*
     * Description: Used to get all changed mpd_url and related information from MPD memory cache.
     */
    Map<String, MpdInfo> getChangedMpd();
    
    /*
     * Description: Used to update MPD memory cache and CDB cache when changed MPD files are detected on CDN/DMF.
     */
    boolean updateCacheFromRemote(Map<String, MpdInfo> changedMpd);
    
    /*
     * Description: Used to update MPD memory cache and CDB cache when the status of a live broadcast is changed.
     * Usage      : It is called when a live broadcast is created/modified/stopped/cancelled.
     */
    boolean updateCacheFromLocal(String mpdUrl, MpdServiceInfo serviceInfo, long date, String md5, Operation op);
    
    /*
     * Description: Used to get all broadcasts which are using changed mpd_url.
     */
    Set<MpdServiceInfo> getBroadcastsByMpd(Set<String> mpdUrls);
}
