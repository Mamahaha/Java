package org.led.tools.bmcDbOperator.data;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.led.tools.bmcDbOperator.common.BmcErrorCode;
import org.led.tools.bmcDbOperator.common.BmcValidationRunTimeException;



/**
 * The following is for restful query format: <URL>?search=column1:<value>,column2:<value>&offset=1&limit=5 <URL>/count?search=column1:<value>,column2:<value>
 * for example: broadcast?search=name:broadcast,service:service&offset=1&limit=5 Note: search should refer org.led.tools.BmcDbOperator.entity classes. If class have
 * sub-entity, the column should prefix with "." for example: Broadcast entity have Service sub-entity. If query Broadcast entity with Service name is
 * myService. broadcast?search=name:myBroadcast,.service.name:myService
 */
public class SearchQuery {

    public static final String SEARCH_STR = "search";
    public static final String OFFSET_STR = "offset";
    public static final String LIMIT_STR = "limit";
    public static final String ORDERBY_STR = "orderby";
    public static final String SPACE_STR = " ";
    public static final String ORDERBY_ASC = "asc";
    public static final String ORDERBY_DESC = "desc";
    public static final String SELECT_STR = "select";
    public static final String COMMA_STR = ",";
    public static final String COLON_STR = ":";

    public enum OperatorEnum {
        EQ, OR, LIKE, LikeIgnoreCase, GreaterThanOrEqualTo, LessThan, Max
    }

    private List<SearchObject> searchObjectList;
    private Map<String, BigDecimal> currentBound;
    private int offset = 0;
    private int limit = 0;
    private String orderby = "";
    private Class orderbyTable = null;
    private String sortType = ORDERBY_ASC;
    private String select = "";

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
     * @param queryStr
     * @return if the URL syntax is wrong
     * @throws UnsupportedEncodingException
     */
    public static SearchQuery createQuery(String queryStr) throws UnsupportedEncodingException {
        String remainingPart = queryStr;
        SearchQuery query = new SearchQuery();
        if (remainingPart == null) {
            return query;
        }
        remainingPart = java.net.URLDecoder.decode(remainingPart, "UTF-8");
        // delete the "?" character
        if (remainingPart.startsWith("?")) {
            remainingPart = remainingPart.substring(1);
        }
        String[] entries = remainingPart.split("&");

        for (String entry : entries) {
            initSearchQuery(entry, query, remainingPart);
        }

        return query;
    }

    public void setSearchObjectList(List<SearchObject> searchObjectList) {
        this.searchObjectList = searchObjectList;
    }

    public List<SearchObject> getSearchObjectList() {
        if (searchObjectList == null) {
            searchObjectList = new ArrayList<SearchObject>();
        }
        return searchObjectList;
    }

    private static void initSearchQuery(String entry, SearchQuery query, String remainingPart) {
        String[] items = entry.split("=");
        String name = items[0];
        String value = items[1];
        if (name.equalsIgnoreCase(SEARCH_STR)) {
            initSearchStr(value, query);
        } else if (name.equalsIgnoreCase(OFFSET_STR)) {
            int offset = 0;
            try {
                offset = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new BmcValidationRunTimeException(BmcErrorCode.INVALID_OFFSET_VALUE, value);
            }
            query.setOffset(offset);
        } else if (name.equalsIgnoreCase(LIMIT_STR)) {
            int count;
            try {
                count = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new BmcValidationRunTimeException(BmcErrorCode.INVALID_LIMIT_VALUE, value);
            }
            query.setLimit(count);
        } else if (name.equalsIgnoreCase(ORDERBY_STR)) {
            initOrderBy(value, query);
        } else if (name.equalsIgnoreCase(SELECT_STR)) {
            query.setSelect(value);
        }  else {
            throw new IllegalArgumentException("URL syntax is wrong, remaining part is " + remainingPart);
        }
    }

    private static void initSearchStr(String value, SearchQuery query){
        String[] searchItems = value.split(COMMA_STR);
        for (String searchItem : searchItems) {
            String[] searchKeyValue = searchItem.split(COLON_STR, 2);
            SearchObject searchType = new SearchObject();
            String searchValue = searchKeyValue[1];
            searchType.setName(searchKeyValue[0]);
            searchType.setValue(searchValue);
            query.getSearchObjectList().add(searchType);
        }
    }
    
    private static void initOrderBy(String value, SearchQuery query){
        if (value.indexOf(SPACE_STR) > 0) {
            String[] orderbyValue = value.split(SPACE_STR);
            query.setOrderby(orderbyValue[0]);
            if (orderbyValue[1].equalsIgnoreCase(ORDERBY_DESC)) {
                query.setSortType(ORDERBY_DESC);
            }
        } else {
            query.setOrderby(value);
        }
    }

    public Class getOrderbyTable() {
        return orderbyTable;
    }

    public void setOrderbyTable(Class orderbyTable) {
        this.orderbyTable = orderbyTable;
    }
}
