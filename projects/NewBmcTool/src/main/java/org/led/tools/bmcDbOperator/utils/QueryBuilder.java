package org.led.tools.bmcDbOperator.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.led.tools.bmcDbOperator.data.SearchObject;
import org.led.tools.bmcDbOperator.data.SearchQuery;
import org.led.tools.bmcDbOperator.data.SearchQuery.OperatorEnum;


/**
 * @author ebaoluo
 *
 */
public final class QueryBuilder {
    private QueryBuilder(){}

    public static TypedQuery getQueryForTotal(EntityManager em, org.led.tools.bmcDbOperator.data.SearchQuery restfulQuery, Class rootClass) {
        return buildQuery(em, restfulQuery, rootClass, true,Long.class);
    }

    public static TypedQuery getQueryForSearch(EntityManager em, SearchQuery restfulQuery, Class rootClass) {
        return buildQuery(em, restfulQuery, rootClass, false,null);
    }

    public static TypedQuery getQueryForGroupByAndTotal(EntityManager em, SearchQuery restfulQuery, Class rootClass , Class returnClass) {
        return buildQuery(em, restfulQuery, rootClass, true,returnClass);
    }

    private static <Z, X> TypedQuery buildQuery(EntityManager em, SearchQuery restfulQuery,
            Class rootClass, boolean isGetTotal,Class returnClass) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery critQuery = null;

        Root root = null;

        if (isGetTotal) {
            critQuery = criteriaBuilder.createQuery(returnClass);
            root = critQuery.from(rootClass);
            critQuery.select(criteriaBuilder.count(root));
        } else {
            critQuery = criteriaBuilder.createQuery(rootClass);
            root = critQuery.from(rootClass);
        }

        // store join table class
        Map<Class, From<Z, X>> fromMap = new HashMap<Class, From<Z, X>>();

        // operate search predicate
        Predicate predicate = null;
        if(restfulQuery != null ){
            predicate = buildPredicate(rootClass, restfulQuery, criteriaBuilder, root, fromMap, critQuery);
        }

        if (!isGetTotal) {
            // set order by
            buildOrderBy(rootClass, restfulQuery, criteriaBuilder, root, fromMap, critQuery);
        }
        TypedQuery typeQuery = em.createQuery(predicate == null ? critQuery : critQuery
                .where(predicate));
        if (!isGetTotal) {
            // set offset
            typeQuery.setFirstResult(restfulQuery.getOffset());
            // set limit
            if(restfulQuery.getLimit() != 0 ){
                typeQuery.setMaxResults(restfulQuery.getLimit());
            }
        }
        return typeQuery;
    }

    private static <X, Z> Predicate buildPredicate(Class rootClass, org.led.tools.bmcDbOperator.data.SearchQuery restfulQuery, CriteriaBuilder criteriaBuilder, Root root,
            Map<Class, From<Z, X>> fromMap, CriteriaQuery critQuery) {
        Predicate predicate = null;
        From<Z, X> from = null;
        List<SearchObject> searchObjects = restfulQuery.getSearchObjectList();
        Field[] fs = rootClass.getDeclaredFields();
        for (SearchObject searchObject : searchObjects) {
            if (rootClass.equals(searchObject.getTableName())) {
                from = root;
            } else {
                if (fromMap.containsKey(searchObject.getTableName())) {
                    from = fromMap.get(searchObject.getTableName());
                } else {
                    String simpleName = searchObject.getTableName().getSimpleName();
                    from= bulidTableFrom(root,fs,simpleName, from);
                    fromMap.put(searchObject.getTableName(), from);
                }
            }
            if (predicate == null) { 
                predicate = addPredicate(criteriaBuilder, from, searchObject);
            } else {
                predicate = addPredicateWithOldPredicate(criteriaBuilder, predicate, from, searchObject);
            }
            buildGroupBy(searchObject,critQuery,criteriaBuilder,root);
        }
        return predicate;
    }

    private static <Z, X> void buildOrderBy(Class rootClass, SearchQuery restfulQuery,
            CriteriaBuilder criteriaBuilder, Root root, Map<Class, From<Z, X>> fromMap,
            CriteriaQuery critQuery) {
        String restfulOrdeby = restfulQuery.getOrderby();
        Path<String> orderBy = null;
        From<Z, X> from = null;
        Class orderByTable = restfulQuery.getOrderbyTable();
        Field[] fs = rootClass.getDeclaredFields();
        if (orderByTable.equals(rootClass)) {
            from = root;
        } else {
            if (fromMap.containsKey(orderByTable)) {
                from = fromMap.get(orderByTable);
            } else {
                String simpleName = orderByTable.getSimpleName();
                from = bulidTableFrom(root, fs, simpleName, from);
            }
        }

        orderBy = from.get(restfulOrdeby);
        critQuery.distinct(true);
        if (restfulQuery.getSortType().equalsIgnoreCase(SearchQuery.ORDERBY_ASC)) {
            critQuery.orderBy(criteriaBuilder.asc(orderBy));
        } else {
            critQuery.orderBy(criteriaBuilder.desc(orderBy));
        }
    }

    private static <Z, X> void buildGroupBy(SearchObject searchObject, CriteriaQuery critQuery, CriteriaBuilder criteriaBuilder, Root root) {
        if (StringUtils.isNotEmpty(searchObject.getGroupBy())) {
            String groupbyParameter = searchObject.getGroupBy();
            Path<String> groupBy = root.get(groupbyParameter);
            critQuery.groupBy(groupBy);
            buildAggregateFunction(searchObject, critQuery, criteriaBuilder, root, groupBy);
        }
    }

    private static void buildAggregateFunction(SearchObject searchObject, CriteriaQuery critQuery, CriteriaBuilder criteriaBuilder, Root root,
            Path<String> groupBy) {
        OperatorEnum oper = searchObject.getOperator();
        if (SearchQuery.OperatorEnum.Max.equals(oper)) {
            String select = searchObject.getSelectParameters();
            critQuery.multiselect(groupBy, critQuery.getSelection(), criteriaBuilder.max(root.get(select)));
        }
    }

    private static <Z, X> From bulidTableFrom(Root root, Field[] fs, String simpleName, From<Z, X> from) {
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            String type = f.getType().getSimpleName();
            if (f.getType().isAssignableFrom(List.class)) {
                Type genericType = f.getGenericType();
                ParameterizedType pt = (ParameterizedType) genericType;
                Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                String className = genericClazz.getSimpleName();
                if (className.equals(simpleName)) {
                    return root.join(f.getName());
                }
            } else {
                if (type.equals(simpleName)) {
                    return  root.join(f.getName());
                }
            }

        }

        return from;
    }

    @SuppressWarnings("unchecked")
    private static <Z, X> Predicate addPredicateWithOldPredicate(CriteriaBuilder criteriaBuilder,
            Predicate pre, From<Z, X> from, SearchObject searchObject) {
        Predicate predicate=pre;
        SearchQuery.OperatorEnum oper = searchObject.getOperator();
        if (SearchQuery.OperatorEnum.OR.equals(oper)) {
            predicate = criteriaBuilder.and(predicate,
                    from.get(searchObject.getName()).in(searchObject.getValue()));
        } else if (SearchQuery.OperatorEnum.LIKE.equals(oper)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(from.<String>get(searchObject.getName()),
                    escapeStr((String) searchObject.getValue())));
        } else if (SearchQuery.OperatorEnum.LikeIgnoreCase.equals(oper)) {
            String value = escapeStr(((String) searchObject.getValue()).toUpperCase());
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(from.<String>get(searchObject.getName())),value));
        } else if (SearchQuery.OperatorEnum.EQ.equals(oper)) {
            predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(from.get((String) searchObject.getName()),
                            searchObject.getValue()));
        } else if (SearchQuery.OperatorEnum.LessThan.equals(oper)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(
                    from.<Comparable> get((String) searchObject.getName()),
                    (Comparable) searchObject.getValue()));
        } else if (SearchQuery.OperatorEnum.GreaterThanOrEqualTo.equals(oper)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(
                    from.<Comparable> get((String) searchObject.getName()),
                    (Comparable) searchObject.getValue()));
        }
        return predicate;
    }

    @SuppressWarnings("unchecked")
    private static <Z, X> Predicate addPredicate(CriteriaBuilder criteriaBuilder, From<Z, X> from, SearchObject searchObject) {
        Predicate predicate=null;
        OperatorEnum oper = searchObject.getOperator();
        if (OperatorEnum.OR.equals(oper)) {
            predicate = criteriaBuilder.and(from.get(searchObject.getName()).in(searchObject.getValue()));
        } else if (OperatorEnum.LIKE.equals(oper)) {
            predicate = criteriaBuilder.and(criteriaBuilder.like(
                    from.<String> get(searchObject.getName()), escapeStr((String) searchObject.getValue())));
        } else if (SearchQuery.OperatorEnum.LikeIgnoreCase.equals(oper)) {
            String value = escapeStr(((String) searchObject.getValue()).toUpperCase());
            predicate = criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(from.<String>get(searchObject.getName())),value));
        } else if (OperatorEnum.EQ.equals(oper)) {
            predicate = criteriaBuilder.and(criteriaBuilder.equal(
                    from.get((String) searchObject.getName()), searchObject.getValue()));
        } else if (OperatorEnum.LessThan.equals(oper)) {
            predicate = criteriaBuilder.and(criteriaBuilder.lessThan(
                    from.<Comparable> get((String) searchObject.getName()),
                    (Comparable) searchObject.getValue()));
        } else if (OperatorEnum.GreaterThanOrEqualTo.equals(oper)) {
            predicate = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(
                    from.<Comparable> get((String) searchObject.getName()),
                    (Comparable) searchObject.getValue()));
        }
        return predicate;
    }
    // The string like "_" , "%" , "[]" are sql wildcard characters,
    // escape the characters by "#"
    private static String escapeStr(String str) {
        return "%"+str.replaceAll("#", "##").replaceAll("_", "#_").replaceAll("%", "#%")
                .replaceAll("\\[", "#[")+"%";
    }

}
