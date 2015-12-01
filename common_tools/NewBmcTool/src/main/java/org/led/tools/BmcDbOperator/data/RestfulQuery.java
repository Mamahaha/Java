package org.led.tools.BmcDbOperator.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * The following is for restful query format: <URL>?search=column1:<value>,column2:<value>&offset=1&limit=5 <URL>/count?search=column1:<value>,column2:<value>
 * for example: broadcast?search=name:broadcast,service:service&offset=1&limit=5 Note: search should refer org.led.tools.BmcDbOperator.entity classes. If class have
 * sub-entity, the column should prefix with "." for example: Broadcast entity have Service sub-entity. If query Broadcast entity with Service name is
 * myService. broadcast?search=name:myBroadcast,.service.name:myService
 */
public class RestfulQuery {

    private static final int INDEX_OF_UPPER_RIGHT_LATITUDE = 3;
    private static final int INDEX_OF_UPPER_RIGHT_LONGITUDE = 2;
    private static final int INDEX_OF_LOWER_LEFT_LATITUDE = 1;
    private static final int INDEX_OF_LOWER_LEFT_LONGITUDE = 0;
    private static final int CURRENT_BOUND_ARRAY_LENGTH = 4;
    public static final String SEARCH_STR = "search";
    public static final String OFFSET_STR = "offset";
    public static final String LIMIT_STR = "limit";
    public static final String ORDERBY_STR = "orderby";
    public static final String ENCODE_SPACE_STR = "%20";
    public static final String ENCODE_SPACE_PLUS_STR = "+";
    public static final String DECODE_SPACE_STR = " ";
    public static final String ENCODE_COLON_STR = "%3A";
    public static final String DECODE_COLON_STR = ":";
    public static final String ENCODE_COMMA_STR = "%2C";
    public static final String DECODE_COMMA_STR = ",";
    public static final String ENCODE_LEFT_SQUARE_BRACKET_STR = "%5B";
    public static final String DECODE_LEFT_SQUARE_BRACKET_STR = "[";
    public static final String ENCODE_RIGHT_SQUARE_BRACKET_STR = "%5D";
    public static final String DECODE_RIGHT_SQUARE_BRACKET_STR = "]";
    public static final String ORDERBY_ASC = "asc";
    public static final String ORDERBY_DESC = "desc";
    public static final String SELECT_STR = "select";
    public static final String CURRENTBOUND = "currentBound";
    public static final String COMMA_STR = ",";

    private Map<String, String> searchs;
    private Map<String, BigDecimal> currentBound;
    private int offset = -1;
    private int limit = -1;
    private String orderby = "";
    private String sortType = ORDERBY_ASC;
    private String select = "";

    public Map<String, String> getSearchs() {
        return searchs;
    }

    public void setSearchs(Map<String, String> searchs) {
        this.searchs = searchs;
    }

    public Map<String, BigDecimal> getCurrentBound() {
        return currentBound;
    }

    public void setCurrentBound(Map<String, BigDecimal> currentBound) {
        this.currentBound = currentBound;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int count) {
        this.limit = count;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    /**
     * create RestfulQuery by URL
     * 
     * @param remainingPart
     * @return if the URL syntax is wrong
     */
    public static RestfulQuery createQuery(String remainingPart) {

        // delete the "?" character
        String queryStr = remainingPart;
        if (remainingPart.startsWith("?")) {
            queryStr = remainingPart.substring(1);
        }
        // TODO: should use org.apache.httpcomponents:httpclient to parse the
        // URL
        return parseItemsToQuery(queryStr);
    }

    private static RestfulQuery parseItemsToQuery(String queryStr) {
        String[] entries = queryStr.split("&");
        RestfulQuery query = new RestfulQuery();

        for (String entry : entries) {
            String[] items = entry.split("=");
            String name = items[0];
            String value = items[1];

            if (name.equalsIgnoreCase(SEARCH_STR)) {
                Map<String, String> searchMap = parseSearchItems(value);
                query.setSearchs(searchMap);
            } else if (name.equalsIgnoreCase(OFFSET_STR)) {
                int offset = Integer.parseInt(value);
                query.setOffset(offset);
            } else if (name.equalsIgnoreCase(LIMIT_STR)) {
                int count = Integer.parseInt(value);
                query.setLimit(count);
            } else if (name.equalsIgnoreCase(ORDERBY_STR)) {
                if (value.indexOf(ENCODE_SPACE_PLUS_STR) > 0) {
                    value = value.replaceAll("\\" + ENCODE_SPACE_PLUS_STR, ENCODE_SPACE_STR);
                } 
                parseOrderByAndSortType(query, value);

            } else if (name.equalsIgnoreCase(SELECT_STR)) {
                query.setSelect(value);
            } else if (name.equalsIgnoreCase(CURRENTBOUND)) {
                Map<String, BigDecimal> cBMap = parseCurrentBound(queryStr, value);
                query.setCurrentBound(cBMap);
            } else {
                // ignore, do nothing
                throw new IllegalArgumentException("URL syntax is wrong, remaining part is " + queryStr);
            }
        }
        
        return query;
    }

    private static Map<String, BigDecimal> parseCurrentBound(String queryStr, String value) {
        String currentBoundStr = value;
        if (value.indexOf(ENCODE_LEFT_SQUARE_BRACKET_STR) >= 0) {
            currentBoundStr = currentBoundStr.replaceAll(ENCODE_LEFT_SQUARE_BRACKET_STR, DECODE_LEFT_SQUARE_BRACKET_STR);
        }
        if (currentBoundStr.indexOf(ENCODE_COMMA_STR) > 0) {
            currentBoundStr = currentBoundStr.replaceAll(ENCODE_COMMA_STR, DECODE_COMMA_STR);
        }
        if (currentBoundStr.indexOf(ENCODE_RIGHT_SQUARE_BRACKET_STR) > 0) {
            currentBoundStr = currentBoundStr.replaceAll(ENCODE_RIGHT_SQUARE_BRACKET_STR, DECODE_RIGHT_SQUARE_BRACKET_STR);
        }
        currentBoundStr = currentBoundStr.substring(1, currentBoundStr.length() - 1);
        if (currentBoundStr.indexOf(COMMA_STR) <= 0) {
            throw new IllegalArgumentException("URL syntax is wrong, remaining part is " + queryStr);
        }
        String[] currentBoundArray = currentBoundStr.split(COMMA_STR);
        if (currentBoundArray.length != CURRENT_BOUND_ARRAY_LENGTH) {
            throw new IllegalArgumentException("URL syntax is wrong, remaining part is " + queryStr);
        }
        BigDecimal lowerleftlongitude = new BigDecimal(currentBoundArray[INDEX_OF_LOWER_LEFT_LONGITUDE]);
        BigDecimal lowerleftlatitude = new BigDecimal(currentBoundArray[INDEX_OF_LOWER_LEFT_LATITUDE]);
        BigDecimal upperrightlongitude = new BigDecimal(currentBoundArray[INDEX_OF_UPPER_RIGHT_LONGITUDE]);
        BigDecimal upperrightlatitude = new BigDecimal(currentBoundArray[INDEX_OF_UPPER_RIGHT_LATITUDE]);

        Map<String, BigDecimal> cBMap = new HashMap<String, BigDecimal>();
        cBMap.put("lowerleftlatitude", lowerleftlatitude);
        cBMap.put("lowerleftlongitude", lowerleftlongitude);
        cBMap.put("upperrightlatitude", upperrightlatitude);
        cBMap.put("upperrightlongitude", upperrightlongitude);
        return cBMap;
    }

    private static void parseOrderByAndSortType(RestfulQuery query, String value) {
        if (value.indexOf(ENCODE_SPACE_STR) > 0) {
            String[] orderbyValue = value.split(ENCODE_SPACE_STR);
            query.setOrderby(orderbyValue[0]);
            if (orderbyValue[1].equalsIgnoreCase(ORDERBY_DESC)) {
                query.setSortType(ORDERBY_DESC);
            }
        } else {
            query.setOrderby(value);
        }
    }

    private static Map<String, String> parseSearchItems(String value) {
        String searchStr = value;
        if (value.indexOf(ENCODE_COMMA_STR) > 0) {
            searchStr = value.replaceAll(ENCODE_COMMA_STR, DECODE_COMMA_STR);
        }
        String[] searchItems = searchStr.split(DECODE_COMMA_STR);
        Map<String, String> searchMap = new HashMap<String, String>();
        for (String searchItem : searchItems) {
            if (searchItem.indexOf(ENCODE_COLON_STR) > 0) {
                searchItem = searchItem.replaceAll(ENCODE_COLON_STR, DECODE_COLON_STR);
            }
            String[] searchKeyValue = searchItem.split(DECODE_COLON_STR);
            String searchKey = searchKeyValue[0];
            String searchValue = searchKeyValue[1];
            if (searchStr.indexOf(ENCODE_SPACE_STR) > 0) {
                searchValue = searchValue.replaceAll(ENCODE_SPACE_STR, DECODE_SPACE_STR);
            }
            if (searchStr.indexOf(ENCODE_SPACE_PLUS_STR) > 0) {
                searchValue = searchValue.replaceAll("\\" + ENCODE_SPACE_PLUS_STR, DECODE_SPACE_STR);
            }
            searchMap.put(searchKey, searchValue);
        }
        return searchMap;
    }

}
