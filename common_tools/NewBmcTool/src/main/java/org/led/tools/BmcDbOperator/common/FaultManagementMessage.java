package org.led.tools.BmcDbOperator.common;

public class FaultManagementMessage {
    private String moduleId = null;
    private int errorCode = -1;
    private String resourceId = null;
    private String originalSourceIp = null;
    private String description = null;
    private Action action;
    private String key = null;
    private static final int PRIME_NUM = 31;
    private static final int CAPACITY_NUM = 200;

    public FaultManagementMessage(String moduleId, int errorCode, String resourceId, String desc, Action action) {
        this.moduleId = moduleId;
        this.errorCode = errorCode;
        this.resourceId = resourceId;
        this.description = desc;
        this.action = action;
    }
    
    public FaultManagementMessage(String moduleId, int errorCode, String resourceId, String desc, Action action, String key) {
        this.moduleId = moduleId;
        this.errorCode = errorCode;
        this.resourceId = resourceId;
        this.description = desc;
        this.action = action;
        this.key = key;
    }

    public FaultManagementMessage(String moduleId, int errorCode, String resourceId, String originalSourceIp, String desc, Action action) {
        this.moduleId = moduleId;
        this.errorCode = errorCode;
        this.resourceId = resourceId;
        this.originalSourceIp = originalSourceIp;
        this.description = desc;
        this.action = action;
    }
    
    public FaultManagementMessage(String moduleId, int errorCode, String resourceId, String originalSourceIp, String desc, Action action, String key) {
        this.moduleId = moduleId;
        this.errorCode = errorCode;
        this.resourceId = resourceId;
        this.originalSourceIp = originalSourceIp;
        this.description = desc;
        this.action = action;
        this.key = key;
    }

    public Action getAction() {
        return this.action;
    }

    public String getModuleId() {
        return this.moduleId;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getDescription() {
        return this.description;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public String getOriginalSourceIp() {
        return originalSourceIp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((o == null) || (super.getClass() != o.getClass())) {
            return false;
        }

        FaultManagementMessage that = (FaultManagementMessage) o;

        if (this.errorCode != that.errorCode) {
            return false;
        }

        if (this.action != that.action) {
            return false;
        }

        if (!(this.moduleId.equals(that.moduleId))) {
            return false;
        }

        if (originalSourceIp != null && !(this.originalSourceIp.equals(that.originalSourceIp))) {
            return false;
        }

        return this.resourceId.equals(that.resourceId);
    }

    @Override
    public int hashCode() {
        int result = this.moduleId.hashCode();
        result = PRIME_NUM * result + this.errorCode;
        result = PRIME_NUM * result + this.resourceId.hashCode();
        if (this.originalSourceIp != null) {
            result = PRIME_NUM * result + this.originalSourceIp.hashCode();
        }
        result = PRIME_NUM * result + this.action.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return  new StringBuilder(CAPACITY_NUM).append("EventMessage {moduleId: ")
                                               .append(this.moduleId)
                                               .append(", ec: ")
                                               .append(this.errorCode)
                                               .append(", resourceId: ")
                                               .append(this.resourceId)
                                               .append(", orignalSourceId: ")
                                               .append(this.originalSourceIp)
                                               .append(", desc: ")
                                               .append(this.description)
                                               .append(", action: ")
                                               .append(this.action)
                                               .append('}')
                                               .toString();
    }

    public static enum Action {
        RAISE_ALARM, CLEAR_ALARM, RAISE_EVENT, UNDEFINED;
    }
}