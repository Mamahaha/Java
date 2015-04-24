package com.ericsson.bvps.mpdmonitor;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.log4j.Logger;

public class MpdDownloader {

    private static final Logger LOGGER = Logger.getLogger(MpdDownloader.class);

    private MessageDigest md = null;
    
    private int bufferSize = 1024;

    public MpdDownloader(){
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Initialize failed");
        }
    }

    /*
     * Fetch mpd file through mpdUrl and return its md5 digest.
     * If get 304 response(Not Modified) or other fail, return empty.
     * @param   mpdUrl  mpd file's url, e.g. "http://ericsson.com/manifest.mpd"
     * @return          md5 digest for fetched mpd content or empty for other cases
     */
    public MpdInfo downloadFile(String mpdUrl, long lastModifyTime) throws FileNotFoundException
    {
        BufferedInputStream in = null;
        byte fileBlob[] = new byte[0];
        long timestamp = new Date().getTime();
        try
        {
            HttpURLConnection uc = (HttpURLConnection)(new URL(mpdUrl).openConnection());
            uc.setIfModifiedSince(lastModifyTime);
            timestamp = uc.getDate();

            in = new BufferedInputStream(uc.getInputStream());

            byte data[] = new byte[bufferSize];
            int count;
            int totalCount = 0;
            while ((count = in.read(data, 0, bufferSize)) != -1)
            {
                if (LOGGER.isTraceEnabled())
                    LOGGER.trace("Downloaded " + count + " bytes.");
                byte[] temp = new byte[totalCount + count];
                System.arraycopy(fileBlob, 0, temp, 0, totalCount);
                System.arraycopy(data, 0, temp, totalCount, count);

                totalCount = totalCount + count;

                fileBlob = new byte[totalCount];
                System.arraycopy(temp, 0, fileBlob, 0, totalCount);

                data = new byte[bufferSize];
            }
            
            String retreivedFileString = new String(fileBlob, 0, fileBlob.length);
            if (data.length > 0) {
                if (! retreivedFileString.contains("MPD") && !retreivedFileString.contains("mpd")) {
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("Not a valid MPD file: " + retreivedFileString);
                    return null;
                }
            }
            
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Downloaded a total of " + totalCount + " bytes.");
        }
        catch (FileNotFoundException e)
        {
            throw new FileNotFoundException(); 
        }
        catch (ConnectException e)
        {
            LOGGER.warn("Failed to connect.", e);
        }
        catch (Exception e)
        {
            LOGGER.warn("Failed to read mpd.", e);
        }
        finally
        {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("Failed to close stream.", e);
                }
        }
        md.reset();

        if (0 == fileBlob.length) {
            return null;
        }
        byte[] hash = md.digest(fileBlob);
        StringBuilder sb = new StringBuilder(2 *hash.length);
        for (byte b : hash) {
            sb.append(String.format("%02x", b&0xff));
        }
        return new MpdInfo(timestamp, sb.toString());
    }
}
