package com.ericsson.bvps.mpdmonitor;

public class MpdServiceInfo
{
    private String serviceName;
    private String broadcastName;
    private String contentName;
    private String mpdISKey;

    public MpdServiceInfo(String serviceName, String broadcastName, String contentName, String mpdISKey)
    {
        this.serviceName = serviceName;
        this.broadcastName = broadcastName;
        this.contentName = contentName;
        this.mpdISKey = mpdISKey;
    }
    
    public MpdServiceInfo(String[] composedId)
    {
        this(composedId[0],composedId[1],composedId[2],composedId[3]);
    }
    
    public MpdServiceInfo(String composedId)
    {
        this(composedId.split(";"));
    }
    
    public String getServiceName()
    {
        return serviceName;
    }
    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }
    public String getBroadcastName()
    {
        return broadcastName;
    }
    public void setBroadcastName(String broadcastName)
    {
        this.broadcastName = broadcastName;
    }
    public String getContentName()
    {
        return contentName;
    }
    public void setContentName(String contentName)
    {
        this.contentName = contentName;
    }
    public String getMpdISKey()
    {
        return mpdISKey;
    }
    public void setMpdISKey(String mpdISKey)
    {
        this.mpdISKey = mpdISKey;
    }
    
    public String toString()
    {
        return this.serviceName + ";" + this.broadcastName + ";" + this.contentName + ";" + this.mpdISKey;
    }
    
    public boolean equals(MpdServiceInfo serviceInfo)
    {
        if (serviceInfo.getBroadcastName().equals(this.broadcastName)
                && serviceInfo.getContentName().equals(this.contentName)
                && serviceInfo.getServiceName().equals(this.serviceName)
                && serviceInfo.getMpdISKey().equals(this.mpdISKey))
            return true;
        return false;
    }
}
