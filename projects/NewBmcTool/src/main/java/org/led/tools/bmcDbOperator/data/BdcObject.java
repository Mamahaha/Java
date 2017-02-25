package org.led.tools.bmcDbOperator.data;

import java.util.ArrayList;
import java.util.List;

import org.led.tools.bmcDbOperator.common.GenericBusinessLogicObject;
import org.led.tools.bmcDbOperator.entity.BDC;



public class BdcObject extends GenericBusinessLogicObject {

    private String bdcName;

    private BDC bdcEntity;

    private Boolean isSslUpdateNeeded;

    private List<BDC> bdcEntityList = new ArrayList<BDC>();

    private BDCConnectionDetail bdcConnectionDetail;

    private SearchQuery searchQuery;

    public String getBdcName() {
        return bdcName;
    }

    public void setBdcName(String bdcName) {
        this.bdcName = bdcName;
    }

    public BDC getBdcEntity() {
        return bdcEntity;
    }

    public void setBdcEntity(BDC bdcEntity) {
        this.bdcEntity = bdcEntity;
    }

    public List<BDC> getBdcEntityList() {
        return bdcEntityList;
    }

    public void setBdcEntityList(List<BDC> bdcEntityList) {
        this.bdcEntityList = bdcEntityList;
    }

    public Boolean isSslUpdateNeeded() {
        return isSslUpdateNeeded;
    }

    public void setSslUpdateNeeded(Boolean isSslUpdateNeeded) {
        this.isSslUpdateNeeded = isSslUpdateNeeded;
    }

    public BDCConnectionDetail getBdcConnectionDetail() {
        return bdcConnectionDetail;
    }

    public void setBdcConnectionDetail(BDCConnectionDetail bdcConnectionDetail) {
        this.bdcConnectionDetail = bdcConnectionDetail;
    }

    public SearchQuery getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(SearchQuery searchQuery) {
        this.searchQuery = searchQuery;
    }

    public static class BDCConnectionDetail {
        private String address;
        private Integer port;
        private boolean ssl;
        private String keyFolder;
        private Integer connectionTimeout;
        private Integer responseTimeout;
        private String basePath;
        private String origin;
        private String webDavMountPoint;
        private Integer end2EndNetworkDelay;

        public String getAddress() {
            return address;
        }

        public BDCConnectionDetail setAddress(String address) {
            this.address = address;
            return this;
        }

        public Integer getPort() {
            return port;
        }

        public BDCConnectionDetail setPort(Integer port) {
            this.port = port;
            return this;
        }

        public boolean isSsl() {
            return ssl;
        }

        public BDCConnectionDetail setSsl(boolean ssl) {
            this.ssl = ssl;
            return this;
        }

        public String getKeyFolder() {
            return keyFolder;
        }

        public BDCConnectionDetail setKeyFolder(String keyFolder) {
            this.keyFolder = keyFolder;
            return this;
        }

        public Integer getConnectionTimeout() {
            return connectionTimeout;
        }

        public BDCConnectionDetail setConnectionTimeout(Integer connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public Integer getResponseTimeout() {
            return responseTimeout;
        }

        public BDCConnectionDetail setResponseTimeout(Integer responseTimeout) {
            this.responseTimeout = responseTimeout;
            return this;
        }

        public String getBasePath() {
            return basePath;
        }

        public BDCConnectionDetail setBasePath(String basePath) {
            this.basePath = basePath;
            return this;
        }

        public String getOrigin() {
            return origin;
        }

        public BDCConnectionDetail setOrigin(String origin) {
            this.origin = origin;
            return this;
        }

        public String getWebDavMountPoint() {
            return webDavMountPoint;
        }

        public BDCConnectionDetail setWebDavMountPoint(String webDavMountPoint) {
            this.webDavMountPoint = webDavMountPoint;
            return this;
        }

        public Integer getEnd2EndNetworkDelay() {
            return end2EndNetworkDelay;
        }

        public BDCConnectionDetail setEnd2EndNetworkDelay(Integer end2EndNetworkDelay) {
            this.end2EndNetworkDelay = end2EndNetworkDelay;
            return this;
        }

    }

}
