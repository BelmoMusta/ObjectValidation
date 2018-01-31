package musta.belmo.validation.criteria;

import musta.belmo.validation.enumeration.Operator;
import musta.belmo.validation.utils.ArithmeticUtils;

/**
 * Created by DELL on 22/01/2018.
 */
public class Criterion {
    private String fieldName;
    private Operator operator;
    private Object expected;
    private boolean required;
    private Object found;

    public Criterion() {
        super();
    }

    public static Criterion of(String fieldName) {
        Criterion rCriteria = new Criterion();
        rCriteria.setFieldName(fieldName);
        return rCriteria;
    }

    public Criterion field(String field) {
        setFieldName(field);
        return this;
    }

    public Criterion operator(Operator operator) {
        setOperator(operator);
        return this;
    }

    public Criterion expected(Object value) {
        setExpected(value);
        return this;
    }

    public <T> Criterion matches(T value) {
        operator(Operator.REGEX).expected(value);
        return this;
    }

    public <T> Criterion length(T value) {
        operator(Operator.LENGTH).expected(value);
        return this;
    }

    public <T> Criterion lessThan(T value) {
        operator(Operator.LESS_THAN).expected(value);
        return this;
    }

    public <T> Criterion is(T value) {
        operator(Operator.EQUALS).expected(value);
        return this;
    }

    public <T> Criterion greatherThan(T value) {
        operator(Operator.GREATER_THAN).expected(value);
        return this;
    }

    public <T> Criterion lessThanOrEquals(T value) {
        operator(Operator.GREATER_OR_EQUALS).expected(value);
        return this;
    }

    public <T> Criterion greaterOrEquals(T value) {
        operator(Operator.GREATER_OR_EQUALS).expected(value);
        return this;
    }

    public Criterion notNull() {
        operator(Operator.NOT_NULL);
        return this;
    }

    public Criterion required() {
        setRequired(true);
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public Criterion found(Object found) {
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
