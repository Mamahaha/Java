package org.led.tools.bmcDbOperator.common;

public class PolygonInfo {

    private PolygonPoint lowerLeftCorner;
    private PolygonPoint upperRightCorner;

    public PolygonInfo() {
    }

    /**
     * @param lowerLeftCorner
     * @param upperRightCorner
     */
    public PolygonInfo(PolygonPoint lowerLeftCorner, PolygonPoint upperRightCorner) {
        this.lowerLeftCorner = lowerLeftCorner;
        this.upperRightCorner = upperRightCorner;
    }

    public PolygonPoint getLowerLeftCorner() {
        return lowerLeftCorner;
    }

    public void setLowerLeftCorner(PolygonPoint lowerLeftCorner) {
        this.lowerLeftCorner = lowerLeftCorner;
    }

    public PolygonPoint getUpperRightCorner() {
        return upperRightCorner;
    }

    public void setUpperRightCorner(PolygonPoint upperRightCorner) {
        this.upperRightCorner = upperRightCorner;
    }
    
    
}
