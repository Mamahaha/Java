
package org.led.tools.bmcDbOperator.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.led.tools.bmcDbOperator.common.GenericBusinessLogicObject.BusinessLogicSubObjectType;
import org.led.tools.bmcDbOperator.data.RestfulQuery;


/**
 * <pre>
 * Transform the parameters, which are in RestfulQuery, to standard field in entity.class for the JPA. 
 * 
 * For example,if you want get a list of ServiceArea.class,in this case "maxBitrate=xx"，
 * The field in searchs in RestfulQuery may be "maxbitrate",so you could use {@link #getParameterProcess(BusinessLogicSubObjectType)} to get a ParameterProcess to deal with the field.
 * </pre>
 * 
 * @author EXYBBCK
 */
public final class ParameterProcessFactory {
    private ParameterProcessFactory(){}

    private static Map<BusinessLogicSubObjectType, Map<String, String>> entityFieldMap = new HashMap<BusinessLogicSubObjectType, Map<String, String>>();

    private static List<BusinessLogicSubObjectType> supportBusinessLogicSubObjectTypes = new ArrayList<BusinessLogicSubObjectType>();

    private static Map<BusinessLogicSubObjectType, ParameterProcess> parameterProcessInstanceMap = 
            new HashMap<BusinessLogicSubObjectType, ParameterProcessFactory.ParameterProcess>();


    static {
        initServiceAreaFields();
        initServiceClassFields();
        initBroadcastAreaFields();

        supportBusinessLogicSubObjectTypes.add(BusinessLogicSubObjectType.ServiceArea);
        supportBusinessLogicSubObjectTypes.add(BusinessLogicSubObjectType.ServiceClass);
        supportBusinessLogicSubObjectTypes.add(BusinessLogicSubObjectType.BroadcastArea);

        for (BusinessLogicSubObjectType businessLogicSubObjectType : supportBusinessLogicSubObjectTypes) {
            parameterProcessInstanceMap.put(businessLogicSubObjectType, new ParameterProcess(businessLogicSubObjectType));
        }
    }

    private static void initServiceAreaFields() {
        // surveyState;maxBitrate;fecOverhead;networkState;bdcList
        Map<String, String> serviceAreaFields = new HashMap<String, String>();
        serviceAreaFields.put("survey-state".toLowerCase(), "surveyState");
        serviceAreaFields.put("maxBitrate".toLowerCase(), "maxBitrate");
        serviceAreaFields.put("fecOverhead".toLowerCase(), "fecOverhead");
        serviceAreaFields.put("network-state".toLowerCase(), "networkState");
        serviceAreaFields.put("sai".toLowerCase(), "sai_long");
        serviceAreaFields.put("frequencies".toLowerCase(), "frequencies");

        entityFieldMap.put(BusinessLogicSubObjectType.ServiceArea, serviceAreaFields);

    }

    private static void initServiceClassFields() {
        // name
        Map<String, String> serviceClassFields = new HashMap<String, String>();
        String nameStr = "name";
        serviceClassFields.put(nameStr.toLowerCase(), nameStr);
        entityFieldMap.put(BusinessLogicSubObjectType.ServiceClass, serviceClassFields);

    }

    private static void initBroadcastAreaFields() {
        // name
        Map<String, String> broadcastAreaFields = new HashMap<String, String>();
        broadcastAreaFields.put("name".toLowerCase(), "name");
        broadcastAreaFields.put("Region-type".toLowerCase(), "regionType");
        broadcastAreaFields.put("Admin-state".toLowerCase(), "adminState");
        entityFieldMap.put(BusinessLogicSubObjectType.BroadcastArea, broadcastAreaFields);

    }

    /**
     * Recommend to call {@link #checkIfSupport(BusinessLogicSubObjectType)} method to make sure there is a ParameterProcess type to use.
     * 
     * @param businessLogicSubObjectType
     * @return
     */
    public static ParameterProcess getParameterProcess(BusinessLogicSubObjectType businessLogicSubObjectType) {
        if (!checkIfSupport(businessLogicSubObjectType)) {
            throw new IllegalArgumentException("unsupport ParameterProcess type :" + businessLogicSubObjectType.toString());
        }
        return parameterProcessInstanceMap.get(businessLogicSubObjectType);
    }

    public static ParameterProcess createServiceAreaParameterProcess() {
        return new ParameterProcess(BusinessLogicSubObjectType.ServiceArea);
    }

    public static ParameterProcess createBroadcasetAreaParameterProcess() {
        // TODO
        return null;
    }

    public static class ParameterProcess {
        private BusinessLogicSubObjectType businessLogicSubObjectType;

        public ParameterProcess(BusinessLogicSubObjectType businessLogicSubObjectType) {
            this.businessLogicSubObjectType = businessLogicSubObjectType;
        }

        public Map<String, String> processParameters(RestfulQuery query) {
            Map<String, String> parameterMap = new HashMap<String, String>();
            Map<String, String> fieldInfo = entityFieldMap.get(businessLogicSubObjectType);

            if (query.getSearchs() != null) {
                for (Entry<String, String> search : query.getSearchs().entrySet()) {
                    if (fieldInfo.containsKey(search.getKey().toLowerCase())) {
                        parameterMap.put(fieldInfo.get(search.getKey().toLowerCase()), search.getValue());
                    } else {
                        parameterMap.put(search.getKey(), search.getValue());
                    }
                }
            }

            if ((!query.getOrderby().isEmpty()) && (fieldInfo.containsKey(query.getOrderby().toLowerCase()))) {
                String[] orderbyStr = fieldInfo.get(query.getOrderby().toLowerCase()).split("_");
                query.setOrderby(orderbyStr[0]);
            }
            return parameterMap;
        }
    }

    /**
     * @param businessLogicSubObjectType
     * @return
     */
    public static boolean checkIfSupport(BusinessLogicSubObjectType businessLogicSubObjectType) {
        return supportBusinessLogicSubObjectTypes.contains(businessLogicSubObjectType);
    }

    // public static void main(String[] args) throws InstantiationException, IllegalAccessException {
    // RestfulQuery restfulQuery = new RestfulQuery();
    // Map<String, String> searchs = new HashMap<String, String>();
    // searchs.put("maxBitRATe", "testA");
    // searchs.put("sURVeyState", "testB");
    // restfulQuery.setSearchs(searchs);
    // restfulQuery.setOrderby("netWOrkStATe");
    // Map parameterMap = getParameterProcess(BusinessLogicSubObjectType.ServiceArea).processParameters(restfulQuery);
    //
    // System.out.println(GsonBuilder.class.newInstance().disableInnerClassSerialization().create().toJson(restfulQuery));
    // System.out.println(GsonBuilder.class.newInstance().disableInnerClassSerialization().create().toJson(parameterMap));
    // }
}
