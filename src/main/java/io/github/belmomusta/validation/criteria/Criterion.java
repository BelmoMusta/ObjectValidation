package io.github.belmomusta.validation.criteria;

import io.github.belmomusta.validation.enumeration.Operator;

/**
 * Criterion class that contains validation attributes
 *
 * @since 0.0.0.SNAPSHOT
 * @author default author
 * @version 0.0.0
 */
public class Criterion {

    /**
     * The {@link #fieldName} attribute.
     */
    private String fieldName;

    /**
     * The {@link #operator} attribute.
     */
    private Operator operator;

    /**
     * The {@link #expected} attribute.
     */
    private Object expected;

    /**
     * The {@link #found} attribute.
     */
    private Object found;

    /**
     * the default constructor
     */
    public Criterion() {
        super();
    }

    /**
     * Creates an instance of criterion of the field name
     *
     * @param fieldName {@link String}
     * @return Criterion
     */
    public static Criterion of(String fieldName) {
        Criterion rCriteria = new Criterion();
        rCriteria.setFieldName(fieldName);
        return rCriteria;
    }

    /**
     * Sets the operator value
     *
     * @param operator {@link Operator}
     * @return Criterion
     */
    public Criterion operator(Operator operator) {
        setOperator(operator);
        return this;
    }

    /**
     * Sets the expected value
     *
     * @param value {@link Object}
     * @return Criterion
     */
    public Criterion expected(Object value) {
        setExpected(value);
        return this;
    }

    /**
     * Sets the regex value
     *
     * @param value {@link T}
     * @param <T> The Generic type
     * @return Criterion
     */
    public <T> Criterion matches(T value) {
        operator(Operator.REGEX).expected(value);
        return this;
    }

    /**
     * Sets the length value
     *
     * @param value {@link T}
     * @param <T> The Generic type
     * @return Criterion
     */
    public <T> Criterion length(T value) {
        operator(Operator.LENGTH).expected(value);
        return this;
    }

    /**
     * Sets the operator value
     *
     * @param value {@link T}
     * @param <T> The Generic type
     * @return Criterion
     */
    public <T> Criterion lessThan(T value) {
        operator(Operator.LESS_THAN).expected(value);
        return this;
    }

    /**
     * Sets the operator value
     *
     * @param value {@link T}
     * @param <T> The Generic type
     * @return Criterion
     */
    public <T> Criterion is(T value) {
        operator(Operator.EQUALS).expected(value);
        return this;
    }

    /**
     * Sets the operator value
     *
     * @param value {@link T}
     * @param <T> The Generic type
     * @return Criterion
     */
    public <T> Criterion greaterThan(T value) {
        operator(Operator.GREATER_THAN).expected(value);
        return this;
    }

    /**
     * Sets the operator value
     *
     * @param value  {@link T}
     * @param <T> The Generic type
     * @return Criterion
     */
    public <T> Criterion lessOrEquals(T value) {
        operator(Operator.LESS_OR_EQUALS).expected(value);
        return this;
    }

    /**
     * Sets the operator value
     *
     * @param value  {@link T}
     * @param <T> The Generic type
     * @return Criterion
     */
    public <T> Criterion greaterOrEquals(T value) {
        operator(Operator.GREATER_OR_EQUALS).expected(value);
        return this;
    }

    /**
     * Sets the operator value
     *
     * @return Criterion
     */
    public Criterion notNull() {
        operator(Operator.NOT_NULL);
        return this;
    }

    /**
     * Sets the operator value
     *
     * @param found  {@link Object}
     * @return Criterion
     */
    public Criterion found(Object found) {
        setFound(found);
        return this;
    }

    /**
     * Getter of the field fieldname
     *
     * @return String
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Setter of the field fieldname
     *
     * @param fieldName  {@link String}
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Getter of the field found
     *
     * @return Object
     */
    public Object getFound() {
        return found;
    }

    /**
     * Setter of the field found
     *
     * @param found  {@link Object}
     */
    public void setFound(Object found) {
        this.found = found;
    }

    /**
     * Getter of the field operator
     *
     * @return Operator
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Setter of the field operator
     *
     * @param operator  {@link Operator}
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    /**
     * Getter of the field expected
     *
     * @return Object
     */
    public Object getExpected() {
        return expected;
    }

    /**
     * Setter of the field expected
     *
     * @param expected  {@link Object}
     */
    public void setExpected(Object expected) {
        this.expected = expected;
    }
}
