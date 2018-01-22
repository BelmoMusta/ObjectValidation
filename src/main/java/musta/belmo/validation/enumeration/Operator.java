package musta.belmo.validation.enumeration;

public enum Operator {
    NOT_NULL("{!=null}"),
    EQUALS("{==}"),
    GREATER_THAN("{>}"),
    LESS_THAN("{<}"),
    GREATER_OR_EQUALS("{>=}"),
    LESS_OR_EQUALS("{<=}"),
    REGEX("{REGEX}");

    private String label;

    Operator(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
