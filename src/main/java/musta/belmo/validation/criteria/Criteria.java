package musta.belmo.validation.criteria;

import musta.belmo.validation.enumeration.Operator;

/**
 * Created by DELL on 22/01/2018.
 */
public class Criteria {
    String fieldName;
    Operator operator;
    Object value;

    public Criteria() {

    }

    public static Criteria of(String fieldName) {
        Criteria rCriteria = new Criteria();
        rCriteria.setFieldName(fieldName);
        return rCriteria;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Criteria field(String field) {
        setFieldName(field);
        return this;
    }

    public Criteria operator(Operator operator) {
        setOperator(operator);
        return this;
    }

    public Criteria value(Object value) {
        setValue(value);
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
