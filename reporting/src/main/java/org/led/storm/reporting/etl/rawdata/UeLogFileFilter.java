package org.led.storm.reporting.etl.rawdata;

import java.io.FilenameFilter;
import java.io.File;

public class UeLogFileFilter implements FilenameFilter {
    private String mPrefix;
    private String mType;

    public UeLogFileFilter(int sessionId, String type) {
        mPrefix = "" + sessionId;
        mType = type;
    }

    public boolean accept(File dir, String name) {
        if (name.startsWith(mPrefix) && name.endsWith(mType)) {
            return true;
        }

        return false;
    }
}
