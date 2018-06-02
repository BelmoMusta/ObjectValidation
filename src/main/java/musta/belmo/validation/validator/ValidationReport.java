
package musta.belmo.validation.validator;

import musta.belmo.validation.annotation.Validation;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.enumeration.Operator;
import musta.belmo.validation.utils.ArrayUtils;

/**
 * The validation Report as the result of the Validation process.
 */
public class ValidationReport {
    private boolean valid;
    private boolean required;
    private Object found;
    private Validation validation;
    private Criterion criterion;

    public ValidationReport() {
        valid = true;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Object getFound() {
        return found;
    }

    public void setFound(Object found) {
        this.found = found;
    }

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

        stringBuilder.append('|')
                .append("required=")
                .append(required);
        if (required) {
            stringBuilder
                    .append(", found=")
                    .append(stringify(foundVal))
                    .append(", expected=")
                    .append(operator.getLabel())
                    .append(':')
                    .append('[')
                    .append(value)
                    .append(']')
                    .append(", valid=")
                    .append(valid);
        }
        stringBuilder.append('|');
        return stringBuilder.toString();
    }

    private String stringify(Object foundVal) {
        StringBuilder sb = new StringBuilder();
        if (foundVal == null) {
            sb.append("#null#");
        } else {
            sb.append(foundVal.getClass().getSimpleName())
                    .append(':');
            if (foundVal.getClass().isArray())
                sb.append(ArrayUtils.toString(foundVal));
            else sb.append(foundVal);
        }
        return sb.toString();

    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
        if (criterion != null) {
            setRequired(criterion.isRequired());
        }
    }

    public Criterion getCriterion() {
        return criterion;
    }
}
