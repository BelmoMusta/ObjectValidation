package musta.belmo.validation.enumeration;

/**
 * The enumeration of the error messages
 */
public enum ErrorMessage {
    NULL_OBJECT_MSG("Object is null, cannot validate its fields!"),
    REGEX_OVER_NON_STRING("REGEX validation cannot be applied on this field of type %s"),
    REGEX_OVER_NULL("REGEX validation cannot be applied on a null String"),
    NOT_A_NUMBER("Arithmetic operations cannot be applied on non number field"),
    ARITHMETIC_ON_NULL("Arithmetic operations cannot be applied on a null object"),
    ARRAY_IS_NULL("The array is null "),
    LENGTH_ERROR_MSG("length error: ");

    /**
     * the label field
     */
    private String label;

    /**
     * Constructor of the error message
     *
     * @param label String
     */
    ErrorMessage(String label) {
        this.label = label;
    }

    /**
     * getter of the label
     *
     * @return String
     */
    public String getLabel() {
        return label;
    }
}
