package io.github.belmomusta.validation.validator;

import io.github.belmomusta.validation.annotation.Validation;
import io.github.belmomusta.validation.criteria.Criterion;
import io.github.belmomusta.validation.enumeration.Operator;
import io.github.belmomusta.validation.utils.ArrayUtils;
import java.util.Objects;

/**
 * The validation Report Item as the result of the Validation process.
 *
 * @since 0.0.0.SNAPSHOT
 * @author default author
 * @version 0.0.0
 */
public class ValidationReportItem {

    /**
     * The {@link #valid} attribute.
     */
    private boolean valid;

    /**
     * The {@link #found} attribute.
     */
    private Object found;

    /**
     * The {@link #validation} attribute.
     */
    private Validation validation;

    /**
     * The {@link #criterion} attribute.
     */
    private Criterion criterion;

    /**
     * The default constructor
     */
    public ValidationReportItem() {
        valid = true;
    }

    /**
     * Returns the value of the valid field
     *
     * @return boolean
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Setter of the field valid
     *
     * @param valid {@link boolean}
     */
    public void setValid(boolean valid) {
        this.valid = valid;
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
     * @param found {@link Object}
     */
    public void setFound(Object found) {
        this.found = found;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String value;
        Operator operator;
        Object foundVal;
        if (validation != null) {
            value = validation.value();
            operator = validation.operator();
        } else if (criterion != null) {
            value = String.valueOf(criterion.getExpected());
            operator = criterion.getOperator();
        } else {
            value = "";
            operator = Operator.NONE;
        }
        if (found != null) {
            foundVal = found;
        } else if (criterion != null) {
            foundVal = criterion.getFound();
        } else {
            foundVal = "{null}";
        }
        stringBuilder.append("field=").append(criterion.getFieldName()).append('|').append("found=").append(stringify(foundVal)).append(", expected=");
        if (operator != null) {
            stringBuilder.append(operator.getLabel());
        }
        stringBuilder.append(':').append('[').append(value).append(']').append(", valid=").append(valid).append('|');
        return stringBuilder.toString();
    }

    /**
     * Converts an object to its string format
     *
     * @param object {@link Object}
     * @return String
     */
    private String stringify(Object object) {
        StringBuilder sb = new StringBuilder();
        if (Objects.isNull(object)) {
            sb.append("#null#");
        } else {
            String simpleName = object.getClass().getSimpleName();
            sb.append(simpleName).append(':');
            if (object.getClass().isArray()) {
                sb.append(ArrayUtils.toString(object));
            } else if ("String".equals(simpleName)) {
                sb.append('"').append(object).append('"');
            } else {
                sb.append(object);
            }
        }
        return sb.toString();
    }

    /**
     * Getter of the field validation
     *
     * @return Validation
     */
    public Validation getValidation() {
        return validation;
    }

    /**
     * Setter of the field validation
     *
     * @param validation {@link Validation}
     */
    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    /**
     * Getter of the field criterion
     *
     * @return Criterion
     */
    public Criterion getCriterion() {
        return criterion;
    }

    /**
     * Setter of the field criterion
     *
     * @param criterion {@link Criterion}
     */
    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
        if (criterion != null) {
        // setRequired(criterion.isRequired());
        }
    }
}
