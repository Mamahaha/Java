package org.led.tools.BmcDbOperator.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class UserServiceInstance {

    private static final String USERSERVICEINSTANCE = "userServiceInstance";

    @Id
    @GeneratedValue
    private long id;

    @Index
    private String name;

    private String description;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "BCID")
    private Broadcast broadcast;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "USID")
    private UserService userService;

    @Index
    private long deliverSessionId;

    @OneToOne
    @ForeignKey
    @JoinColumn(name = "SdpInfoId")
    private SDPInfo sdpInfo;

    @Lob
    private String sdp;

    @Index
    private String tmgi;

    @Index
    private String pipename;

    private int gBR;

    private String contentIngestionPoint;

    private int usdVersion;

    private int sdpVersion;

    private int scheduleVersion;

    private int mpdVersion;

    private int adpdVersion;

    @Index
    private String operState;

    private String operStateDescription;

    @OneToOne(mappedBy = USERSERVICEINSTANCE, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.EAGER)
    private Continuous continuous;

    @OneToOne(mappedBy = USERSERVICEINSTANCE, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.EAGER)
    private OnRequest onRequest;

    @OneToMany(mappedBy = USERSERVICEINSTANCE, cascade = { CascadeType.REMOVE })
    private List<AdpdServiceUri> adpdServiceUris = new ArrayList<AdpdServiceUri>();

    @OneToMany(mappedBy = USERSERVICEINSTANCE, cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
    private List<DeviceDeliverySessionInstanceInfo> deviceDeliverySessionInstanceInfos = new ArrayList<DeviceDeliverySessionInstanceInfo>();

    public String getPipename() {
        return pipename;
    }

    public void setPipename(String pipename) {
        this.pipename = pipename;
    }

    public String getTmgi() {
        return tmgi;
    }

    public void setTmgi(String tmgi) {
        this.tmgi = tmgi;
    }

    public void addDeviceDeliverySessionInfo(DeviceDeliverySessionInstanceInfo info) {
        deviceDeliverySessionInstanceInfos.add(info);
    }

    public List<DeviceDeliverySessionInstanceInfo> getDeviceDeliverySessionInstanceInfos() {
        return deviceDeliverySessionInstanceInfos;
    }

    public List<AdpdServiceUri> getAdpdServiceUris() {
        return adpdServiceUris;
    }

    public void addAdpdServiceUri(AdpdServiceUri uri) {
        adpdServiceUris.add(uri);
    }

    public void setOnRequest(OnRequest onRequest) {
        this.onRequest = onRequest;
    }

    public OnRequest getOnRequest() {
        return onRequest;
    }

    public void setContinuous(Continuous c) {
        this.continuous = c;
    }

    public Continuous getContinuous() {
        return continuous;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Broadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public int getGBR() {
        return gBR;
    }

    public void setGBR(int gBR) {
        this.gBR = gBR;
    }

    public String getContentIngestionPoint() {
        return contentIngestionPoint;
    }

    public void setContentIngestionPoint(String contentIngestionPoint) {
        this.contentIngestionPoint = contentIngestionPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUsdVersion() {
        return usdVersion;
    }

    public void setUsdVersion(int usdVersion) {
        this.usdVersion = usdVersion;
    }

    public int getSdpVersion() {
        return sdpVersion;
    }

    public void setSdpVersion(int sdpVersion) {
        this.sdpVersion = sdpVersion;
    }

    public int getScheduleVersion() {
        return scheduleVersion;
    }

    public void setScheduleVersion(int scheduleVersion) {
        this.scheduleVersion = scheduleVersion;
    }

    public int getMpdVersion() {
        return mpdVersion;
    }

    public void setMpdVersion(int mpdVersion) {
        this.mpdVersion = mpdVersion;
    }

    public int getAdpdVersion() {
        return adpdVersion;
    }

    public void setAdpdVersion(int adpdVersion) {
        this.adpdVersion = adpdVersion;
    }

    public String getOperState() {
        return operState;
    }

    public void setOperState(String operState) {
        this.operState = operState;
    }

    public String getOperStateDescription() {
        return operStateDescription;
    }

    public void setOperStateDescription(String operStateDescription) {
        this.operStateDescription = operStateDescription;
    }

    public long getDeliverSessionId() {
        return deliverSessionId;
    }

    public void setDeliverSessionId(long deliverSessionId) {
        this.deliverSessionId = deliverSessionId;
    }

    public SDPInfo getSdpInfo() {
        return sdpInfo;
    }

    public void setSdpInfo(SDPInfo sdpInfo) {
        this.sdpInfo = sdpInfo;
    }

    public String getSdp() {
        return sdp;
    }

    public void setSdp(String sdp) {
        this.sdp = sdp;
    }

    @Override
    public String toString() {
        return String.format("[id: %s,operState: %s,BCID: %s]", id, operState, broadcast.getId());
    }

}
