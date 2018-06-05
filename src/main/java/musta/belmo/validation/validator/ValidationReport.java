package musta.belmo.validation.validator;

import musta.belmo.validation.annotation.Validation;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.enumeration.Operator;
import musta.belmo.validation.utils.ArrayUtils;

/**
 * The validation Report as the result of the Validation process.
 */
public class ValidationReport {

    /**
     * TODO: provide a description for this field
     */
    private boolean valid;

    /**
     * TODO: provide a description for this field
     */
    private boolean required;

    /**
     * TODO: provide a description for this field
     */
    private Object found;

    /**
     * TODO: provide a description for this field
     */
    private Validation validation;

    /**
     * TODO: provide a description for this field
     */
    private Criterion criterion;

    /**
     * TODO: provide a description for this constructor
     */
    public ValidationReport() {
        valid = true;
    }

    /**
     * TODO: provide a description for this method
     *
     * @return boolean
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Setter of the field valid
     *
     * @param valid { @link boolean}
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * TODO: provide a description for this method
     *
     * @return boolean
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Setter of the field required
     *
     * @param required { @link boolean}
     */
    public void setRequired(boolean required) {
        this.required = required;
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
     * @param found { @link Object}
     */
    public void setFound(Object found) {
        this.found = found;
    }

    /**
     * TODO: provide a description for this method
     *
     * @return String
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
        stringBuilder.append('|').append("required=").append(required);
        if (required) {
            stringBuilder.append(", found=").append(stringify(foundVal)).append(", expected=");
            if (operator != null) {
                stringBuilder.append(operator.getLabel());
            }
            stringBuilder.append(':').append('[').append(value).append(']').append(", valid=").append(valid);
        }
        stringBuilder.append('|');
        return stringBuilder.toString();
    }

    /**
     * TODO: provide a description for this method
     *
     * @param foundVal { @link Object}
     * @return String
     */
    private String stringify(Object foundVal) {
        StringBuilder sb = new StringBuilder();
        if (foundVal == null) {
            sb.append("#null#");
        } else {
            sb.append(foundVal.getClass().getSimpleName()).append(':');
            if (foundVal.getClass().isArray())
                sb.append(ArrayUtils.toString(foundVal));
            else
                sb.append(foundVal);
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
     * @param validation { @link Validation}
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
     * @param criterion { @link Criterion}
     */
    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
        if (criterion != null) {
            setRequired(criterion.isRequired());
        }
    }
}
