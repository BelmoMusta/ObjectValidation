package musta.belmo.validation.bean;

import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.annotation.Assertion;
import musta.belmo.validation.enumeration.Operator;

/**
 * The validation Report as the result of the Validation process.
 */
public class ValidationReport {

    private String className;
    private boolean valid;
    private boolean required;
    private Object found;
    private Assertion assertion;
    Criteria criteria;

    public ValidationReport() {
        valid = true;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
        } else {
            value = String.valueOf(criteria.getValue());
        }

        if (assertion != null) {
            operator = assertion.operator();
        } else {
            operator = criteria.getOperator();
        }

        if (found != null) {
            foundVal = found;
        } else if(criteria!=null){
            foundVal = criteria.getFound();
        } else  {
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

    public void setCriterion(Criteria criteria) {
        this.criteria = criteria;
    }

    public Criteria getCriteria() {
        return criteria;
    }
}
