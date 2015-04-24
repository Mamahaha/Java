package com.ericsson.bvps.mpdmonitor;

import java.util.HashSet;
import java.util.Set;

public class MpdInfo {
    
    private Set<MpdServiceInfo> serviceInfo;
    private long lastModifyTime;
    private String md5;

    public MpdInfo(long lastModifyTime, String md5)
    {
        this.lastModifyTime = lastModifyTime;
        this.md5 = md5;
        this.serviceInfo = new HashSet<MpdServiceInfo>();
    }
    
    public MpdInfo(long lastModifyTime, String md5, MpdServiceInfo serviceInfo)
    {
        this(lastModifyTime,md5);
        this.serviceInfo.add(serviceInfo);
    }
    
    public MpdInfo(long lastModifyTime, String md5, Set<MpdServiceInfo> serviceInfo)
    {
        this(lastModifyTime,md5);
        this.serviceInfo.addAll(serviceInfo);
    }

    public MpdInfo(MpdInfo mpdInfo)
    {
        this(mpdInfo.getLastModifyTime(), mpdInfo.getMd5());
    }

    public void update(MpdInfo mpdInfo)
    {
        this.lastModifyTime = mpdInfo.getLastModifyTime();
        this.md5 = mpdInfo.getMd5();
    }

    public Set<MpdServiceInfo> getServiceInfo()
    {
        return serviceInfo;
    }

    public void AddServiceInfo(MpdServiceInfo serviceInfo)
    {
        this.serviceInfo.add(serviceInfo);
    }

    public void removeServiceInfo(MpdServiceInfo serviceInfo)
    {
        this.serviceInfo.remove(serviceInfo);
    }

    public long getLastModifyTime()
    {
        return lastModifyTime;
    }

    public String getMd5()
    {
        return md5;
    }
}
