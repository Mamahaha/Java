package com.ericsson.bvps.mpdmonitor;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ericsson.bvps.announcement.Settings;
import com.ericsson.bvps.ha.HaBlockedFlag;

public class MpdMonitor {
    private Timer timer = new Timer();
    private static final Logger LOGGER = Logger.getLogger(MpdMonitor.class);
    private HaBlockedFlag haBlockedFlag = HaBlockedFlag.getHaBlockedFlag();

    public MpdMonitor() {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("MpdMonitor is up");
        if (haBlockedFlag.isHaBlocked()) {
            LOGGER.debug("MpdMonitorTimerTask expired, but this is a slave node.");
        }
    }

    public void run() {
        IMpdCache mpdCache = MpdCache.getInstance();
        if (mpdCache.cdbCacheReady()) {
            mpdCache.clearCdbCache();
        }
        
        int interval = Settings.getInstance().getConfigMpdPollInterval();
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Next MpdMonitorTimerTask will start after " + interval + " seconds");
        timer.schedule(new MpdMonitorTimerTask(), interval * 1000);
    }
    
    class MpdMonitorTimerTask extends TimerTask {
        public void run() {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("MpdMonitorTimerTask expired.");

            try {
                long now = System.currentTimeMillis();
                
                IMpdCache mpdCache = MpdCache.getInstance();
                if (haBlockedFlag.isHaBlocked()) {
                    LOGGER.debug("MpdMonitorTimerTask expired, but this is a slave node.");
                    mpdCache.clearMemoryCache();
                } else {
                    if (!mpdCache.cdbCacheReady()) {
                        mpdCache.initCache();
                    }
                    
                    if (!mpdCache.memoryCacheReady()) {
                        mpdCache.reloadMemoryCache();
                    }
                    startWorker();
                } 

                int cost =  (int) ((System.currentTimeMillis() - now) / 1000);
                setTimer(cost);
            } catch (Exception exception) {
                LOGGER.error("Failed to run MPD Monitor task: " + exception);
            }
        }

        /**
         * 1. Retrieve MPD info from DMF/CDN 
         * 2. Check if changes exist 
         * 3. Download changed MPD files 
         * 4. Pick out related broadcasts 
         * 5. Refresh content 
         * 6. Trigger SAF and control fragment update
         */
        private void startWorker() {
            MpdUpdater updater = new MpdUpdater();
            updater.updateMpd();
        }

        private void setTimer(int cost) {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("The time cost of last MpdMonitorTimerTask is " + cost + " seconds");
            if (timer != null) {
                timer.cancel();
            }
            timer = new Timer();

            int interval = Settings.getInstance().getConfigMpdPollInterval();
            if (interval <= cost) {
                interval = 0;
            } else {
                interval -= cost;
            }
            timer.schedule(new MpdMonitorTimerTask(), interval * 1000);
        }
    }
}
