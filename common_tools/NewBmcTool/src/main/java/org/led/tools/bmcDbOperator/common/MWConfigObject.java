package org.led.tools.bmcDbOperator.common;

import java.util.ArrayList;
import java.util.List;

import org.led.tools.bmcDbOperator.entity.MWConfigSchema;



public class MWConfigObject extends GenericBusinessLogicObject {

    private String mwConfigContent;

    List<MWConfigSchema> mwConfigSchemaList = new ArrayList<MWConfigSchema>();

    public String getMwConfigContent() {
        return mwConfigContent;
    }

    public void setMwConfigContent(String mwConfigContent) {
        this.mwConfigContent = mwConfigContent;
    }

    public List<MWConfigSchema> getMwConfigSchemaList() {
        return mwConfigSchemaList;
    }

    public void setMwConfigSchemaList(List<MWConfigSchema> mwConfigSchemaList) {
        this.mwConfigSchemaList = mwConfigSchemaList;
    }

}
