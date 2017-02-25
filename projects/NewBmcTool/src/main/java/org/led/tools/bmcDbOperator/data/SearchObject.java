package org.led.tools.bmcDbOperator.data;

import org.led.tools.bmcDbOperator.data.SearchQuery.OperatorEnum;

public class SearchObject {

    private String name;
    private Object value;
    private OperatorEnum operator;
    private Class type;
    private Class tableName;
    private String groupBy;
    private String selectParameters;
    private static final int PRIME_NUM = 31;

    public OperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(OperatorEnum operator) {
        this.operator = operator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getTableName() {
        return tableName;
    }

    public void setTableName(Class tableName) {
        this.tableName = tableName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SearchObject that = (SearchObject) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (operator != that.operator) {
            return false;
        }
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = PRIME_NUM * result + (operator != null ? operator.hashCode() : 0);
        result = PRIME_NUM * result + (tableName != null ? tableName.hashCode() : 0);
        return result;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getSelectParameters() {
        return selectParameters;
    }

    public void setSelectParameters(String selectParameters) {
        this.selectParameters = selectParameters;
    }

}
