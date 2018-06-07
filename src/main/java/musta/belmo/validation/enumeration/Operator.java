package musta.belmo.validation.enumeration;

/**
 * Enumeration of the operators
 */
public enum Operator {
    NOT_NULL("{!=null}"),
    EQUALS("{==}"),
    GREATER_THAN("{>}"),
    LESS_THAN("{<}"),
    GREATER_OR_EQUALS("{>=}"),
    LESS_OR_EQUALS("{<=}"),
    REGEX("{REGEX}"),
    NONE("NONE"),
    LENGTH("{length}");
    /**
     * The label field
     */
    private String label;

    /**
     * Constructor of the operator
     *
     * @param label The label value
     */
    Operator(String label) {
        this.label = label;
    }

    /**
     * Get the value of the label
     *
     * @return String
     */
    public String getLabel() {
        return label;
    }
}
