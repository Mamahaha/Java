package com.ericsson.bvps.mpdmonitor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ericsson.bvps.ncs.NcsUtil;
import com.ericsson.bvps.ns.BroadcastServiceNs;
import com.ericsson.bvps.io.CloseUtil;
import com.tailf.conf.Conf;
import com.tailf.conf.ConfBuf;
import com.tailf.conf.ConfEnumeration;
import com.tailf.conf.ConfException;
import com.tailf.conf.ConfKey;
import com.tailf.conf.ConfObject;
import com.tailf.maapi.Maapi;
import com.tailf.navu.NavuContainer;
import com.tailf.navu.NavuContext;
import com.tailf.navu.NavuException;
import com.tailf.ncs.ns.Ncs;

public class MpdUpdater {

    private static final Logger LOGGER = Logger.getLogger(MpdUpdater.class);
    private static IMpdCache mpdCache = MpdCache.getInstance();


    public MpdUpdater() {
    }

    public void updateMpd() {
        Map<String, MpdInfo> changedMpd = mpdCache.getChangedMpd();
        if (changedMpd.size() == 0) {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("No MPD file is updated");
            return;
        }
        if (mpdCache.updateCacheFromRemote(changedMpd)) {
            for (Map.Entry<String, MpdInfo> entry : changedMpd.entrySet()) {
                String mpdUrl = entry.getKey();
                MpdInfo originalMpdInfo = entry.getValue();
                refreshBroadcasts(mpdUrl, originalMpdInfo.getServiceInfo());
            }

        }
        
    }

    private void refreshBroadcasts(String mpdUrl, Set<MpdServiceInfo> broadcastInfoSet) {

        if (broadcastInfoSet.isEmpty()) {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("No broadcast needs refresh");
            return;
        }

        for (MpdServiceInfo broadcastInfo : broadcastInfoSet) {
            String[] info = broadcastInfo.toString().split(";");
            refreshBroadcast(info[0], info[1], info[2], info[3], mpdUrl);
        }
    }
    
    private void refreshBroadcast(String serviceName, String broadcastName, String contentName, String mpdIsKey, String mpdurl) {
        // Perform refresh
        Maapi maapi = null;
        int th=0;
        NavuContainer broadcast = null;
        try {
            maapi = NcsUtil.newSystemMaapi();
            th = maapi.startTrans(Conf.DB_RUNNING, Conf.MODE_READ_WRITE);
            final NavuContext context = new NavuContext(maapi, th);
            NavuContainer root = new NavuContainer(context);
            NavuContainer ncs = root.container(new Ncs().hash());
            broadcast = ncs.container(Ncs._services_)
                    .list(Ncs._service)
                    .elem(serviceName)
                    .container(Ncs._type_)
                    .container(BroadcastServiceNs._broadcast_service)
                    .list(BroadcastServiceNs._broadcast)
                    .elem(broadcastName);
            ConfEnumeration admState = (ConfEnumeration) broadcast.leaf(BroadcastServiceNs._admin_state_).value();
            if (admState.getOrdinalValue() != BroadcastServiceNs._pending) { 
                NavuContainer metadata = broadcast
                        .list(BroadcastServiceNs._content_)
                        .elem(new ConfKey(
                                new ConfObject[]{
                                    new ConfBuf(contentName.split(" ")[0]),
                                    new ConfBuf(contentName.split(" ")[1])
                                }))
                        .container(BroadcastServiceNs._continuous_)
                        .container(BroadcastServiceNs._metadata_);
                
                NavuContainer mpdIsUpload = metadata
                        .list(BroadcastServiceNs._mpd_and_is_reference_)
                        .elem(new ConfKey(
                                new ConfObject[]{
                                    new ConfBuf(mpdIsKey),
                                    new ConfBuf(mpdurl)
                                }));
                doUpdateMpd(mpdIsUpload);
                
                if(LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Deleting _mpd_modification_parameters_");
                    LOGGER.debug(metadata.getKeyPath());
                }
                metadata.container(BroadcastServiceNs._mpd_modification_parameters_).delete();
            }
            maapi.applyTrans(th, false);
        } catch (Exception e) {
            LOGGER.error("Failed to trigger action_refresh_content: ", e);
        } finally {
            try {
                maapi.finishTrans(th);
            } catch (IOException | ConfException e) {
                LOGGER.error("Failed to close CDB transaction: ", e);
            }
            CloseUtil.close(maapi.getSocket());
        }
    }
    
    private boolean doUpdateMpd( NavuContainer mpdIsUpload) throws NavuException, MalformedURLException, IOException
    {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Uploading: "+mpdIsUpload.getKey());
        }
        String url = mpdIsUpload.leaf(BroadcastServiceNs._fragment_url_).value().toString();

        NavuContainer metadata = (NavuContainer) mpdIsUpload.getParent().getParent();
        /*
         * Try to read file
         */

        byte[] retrievedFile = null;
        retrievedFile = saveFileFromUrl(url);
        boolean modified = false;
        if(LOGGER.isDebugEnabled())
            LOGGER.debug("Mpd retreived, url:" + url);
        String retreivedFileString = new String(retrievedFile, 0, retrievedFile.length);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(retreivedFileString);
            //LOGGER.debug("length: " + retrievedFile.length);
        }

        if (retrievedFile.length > 0) {
            try {
                String newFileString = adjustAST(retreivedFileString);
                metadata.leaf(BroadcastServiceNs._mpd_).set(newFileString);
                modified = true;
            } catch (ConfException e) {
                LOGGER.error("Unable to set MPD",
                        e);
                throw new RuntimeException(
                        "Unable to set MPD",
                        e);
            }
        }
        
        return modified;
    }
    
    /***
     * 
     * @param filename
     * @param urlString
     * @return Returns retrieved file. Empty string if no file is retrieved.
     * @throws MalformedURLException
     * @throws IOException
     */
    private byte[] saveFileFromUrl(String urlString) throws MalformedURLException, IOException
    {
        BufferedInputStream in = null;
        byte fileBlob[] = new byte[0];
        try {
                in = new BufferedInputStream(new URL(urlString).openStream());

                byte data[] = new byte[1024];
                int count;
                int totalCount = 0;
                while ((count = in.read(data, 0, 1024)) != -1) {
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
        finally {
            if (in != null)
                in.close();
        }
        return fileBlob;
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
}
