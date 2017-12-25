package musta.belmo.validation;

public class ValidationReport {


    private String className;
    private boolean valide;
    private boolean required;
    private Object found;
    private Object expected;
    private Assertion assertion;

    public ValidationReport() {
        valide = true;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
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

        stringBuilder.append('|')
                .append("required=")
                .append(required);

        if (required) {
            stringBuilder
                    .append(", operator=")
                    .append(assertion.operator())
                    .append(", found=");

            if (found != null)
                stringBuilder.append(found);
            else
                stringBuilder.append("{null}");

            stringBuilder.append(", expected=");

            if (assertion.value() != null) {

                if (Operator.operatorMap.containsKey(assertion.operator()))

                    stringBuilder.append(Operator.operatorMap.get(assertion.operator()));
                stringBuilder.append(assertion.value());

            } else
                stringBuilder.append("{null}");

            stringBuilder.append(", valide=")
                    .append(valide);

        }

        stringBuilder.append('|');
        return stringBuilder.toString();
    }

    public void setExpected(Object expected) {
        this.expected = expected;
    }

    public Object getExpected() {
        return expected;
    }

    public void setAssertion(Assertion assertion) {
        this.assertion = assertion;
    }

    public Assertion getAssertion() {
        return assertion;
    }
}
