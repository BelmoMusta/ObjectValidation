package musta.belmo.validation.criteria;

import musta.belmo.validation.enumeration.Operator;

/**
 * Created by DELL on 22/01/2018.
 */
public class Criteria {
    private String fieldName;
    private Operator operator;
    private Object expected;
    private boolean required;
    private Object found;

    public Criteria() {
        super();
    }

    public static Criteria of(String fieldName) {
        Criteria rCriteria = new Criteria();
        rCriteria.setFieldName(fieldName);
        return rCriteria;
    }

    public Criteria field(String field) {
        setFieldName(field);
        return this;
    }

    public Criteria operator(Operator operator) {
        setOperator(operator);
        return this;
    }

    public Criteria expected(Object value) {
        setExpected(value);
        return this;
    }

    public <T> Criteria matches(T value) {
        operator(Operator.REGEX).expected(value);
        return this;
    }

    public <T> Criteria lessThan(T value) {
        operator(Operator.LESS_THAN).expected(value);
        return this;
    }

    public <T> Criteria equal(T value) {
        operator(Operator.EQUALS).expected(value);
        return this;
    }

    public <T> Criteria greatherThan(T value) {
        operator(Operator.GREATER_THAN).expected(value);
        return this;
    }

    public <T> Criteria lessThanOrEquals(T value) {
        operator(Operator.GREATER_OR_EQUALS).expected(value);
        return this;
    }

    public <T> Criteria greaterOrEquals(T value) {
        operator(Operator.GREATER_OR_EQUALS).expected(value);
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

    public boolean isRequired() {
        return required;
    }

    public Criteria found(Object found) {
        setFound(found);
        return this;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setExpected(Object expected) {
        this.expected = expected;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setFound(Object found) {
        this.found = found;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFound() {
        return found;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getExpected() {
        return expected;
    }
}
