
package musta.belmo.validation.validator;

import musta.belmo.validation.annotation.Assertion;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.enumeration.Operator;

/**
 * The validation Report as the result of the Validation process.
 */
public class ValidationReport {
    private boolean valid;
    private boolean required;
    private Object found;

    private Assertion assertion;
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

        if (assertion != null) {
            value = assertion.value();
        } else if (criterion != null) {
            value = String.valueOf(criterion.getExpected());
        } else {
            value = "";
        }

        if (assertion != null) {
            operator = assertion.operator();
        } else if (criterion != null) {
            operator = criterion.getOperator();
        } else {
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
                    .append(foundVal)
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

    public void setAssertion(Assertion assertion) {
        this.assertion = assertion;
    }

    public Assertion getAssertion() {
        return assertion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    public Criterion getCriterion() {
        return criterion;
    }
}
