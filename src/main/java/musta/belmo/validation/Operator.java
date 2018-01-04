package musta.belmo.validation;

public enum Operator {
    NOT_NULL("{!=null}"),
    EQUALS("{==}"),
    GREATER("{>}"),
    LESS("{<}"),
    GREATER_OR_EQUALS("{>=}"),
    LESS_OR_EQUALS("{<=}"),
    REGEX("{REGEX}");

    private String operatorLabel;


    Operator(String operatorLabel) {
        this.operatorLabel = operatorLabel;
    }

    public String getOperatorLabel() {
        return operatorLabel;
    }
}
