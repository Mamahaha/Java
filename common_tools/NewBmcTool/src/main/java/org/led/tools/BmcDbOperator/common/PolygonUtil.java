package org.led.tools.BmcDbOperator.common;

import java.util.List;



public final class PolygonUtil {

    private PolygonUtil() {
    }

    /**
     * Gets lower left corner and upper right corner
     * 
     * @param List
     *            <PolygonPoint>
     * @return the PolygonInfo
     */
    public static PolygonInfo getPolygonInfo(final List<PolygonPoint> polygonPointList) {
        if (polygonPointList != null && polygonPointList.size() > 0) {
            PolygonPoint lowerLeftCorner = new PolygonPoint();
            PolygonPoint upperRightCorner = new PolygonPoint();
            for (PolygonPoint polygonPoint : polygonPointList) {
                setLowerLeftCorner(lowerLeftCorner, polygonPoint);
                setUpperRightCorner(upperRightCorner, polygonPoint);
            }
            return new PolygonInfo(lowerLeftCorner, upperRightCorner);
        } else {
            BmcLogger.eventWarn(BmcLogModule.COMMONUTIL, "Failed to generate polygon info, the point is null");
            return null;
        }
    }

    private static void setUpperRightCorner(PolygonPoint upperRightCorner, PolygonPoint polygonPoint) {
        if (upperRightCorner.getLatitude() == null && upperRightCorner.getLongitude() == null) {
            upperRightCorner.setLatitude(polygonPoint.getLatitude());
            upperRightCorner.setLongitude(polygonPoint.getLongitude());
        }
        if (polygonPoint.getLatitude().compareTo(upperRightCorner.getLatitude()) == 1) {
            upperRightCorner.setLatitude(polygonPoint.getLatitude());
        }
        if (polygonPoint.getLongitude().compareTo(upperRightCorner.getLongitude()) == 1) {
            upperRightCorner.setLongitude(polygonPoint.getLongitude());
        }
    }

    private static void setLowerLeftCorner(PolygonPoint lowerLeftCorner, PolygonPoint polygonPoint) {
        if (lowerLeftCorner.getLatitude() == null && lowerLeftCorner.getLongitude() == null) {
            lowerLeftCorner.setLatitude(polygonPoint.getLatitude());
            lowerLeftCorner.setLongitude(polygonPoint.getLongitude());
        }
        if (polygonPoint.getLatitude().compareTo(lowerLeftCorner.getLatitude()) == -1) {
            lowerLeftCorner.setLatitude(polygonPoint.getLatitude());
        }
        if (polygonPoint.getLongitude().compareTo(lowerLeftCorner.getLongitude()) == -1) {
            lowerLeftCorner.setLongitude(polygonPoint.getLongitude());
        }
    }
}
