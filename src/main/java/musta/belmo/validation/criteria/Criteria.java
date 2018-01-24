package musta.belmo.validation.criteria;

import musta.belmo.validation.enumeration.Operator;

/**
 * Created by DELL on 22/01/2018.
 */
public class Criteria {
    private String fieldName;
    private Operator operator;
    private Object value;
    private boolean required;
    private Object found;

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


    public <T> Criteria matches(T value) {
        operator(Operator.REGEX).value(value);
        return this;
    }

    public <T> Criteria lessThan(T value) {
        operator(Operator.LESS_THAN).value(value);
        return this;
    }

    public <T> Criteria equal(T value) {
        operator(Operator.EQUALS).value(value);
        return this;
    }

    public <T> Criteria greatherThan(T value) {
        operator(Operator.GREATER_THAN).value(value);
        return this;
    }

    public <T> Criteria lessThanOrEquals(T value) {
        operator(Operator.GREATER_OR_EQUALS).value(value);
        return this;
    }

    public <T> Criteria greaterOrEquals(T value) {
        operator(Operator.GREATER_OR_EQUALS).value(value);
        return this;
    }

    public Criteria notNull() {
        operator(Operator.NOT_NULL);
        return this;
    }

    public Criteria required(boolean required) {
        setRequired(required);
        return this;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }


    public Criteria found(Object found) {
        setFound(found);
        return this;
    }

    public Object getFound() {
        return found;
    }


    public void setFound(Object found) {
        this.found = found;
    }
}
