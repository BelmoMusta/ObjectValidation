package musta.belmo.validation.enumeration;

public enum ErrorMessage {

    NULL_OBJECT_MSG("Object is null, cannot validate its fields!"),
    REGEX_OVER_NON_STRING("REGEX validation cannot be applied on this field of type %s"),
    REGEX_OVER_NULL("REGEX validation cannot be applied on a null String"),
    NOT_A_NUMBER("Arithmethic operations cannot be applied on non number field"),
    ARITHMETIC_ON_NULL("Arithmethic operations cannot be applied on a null object"),
    NULL_FIELD_NAME("The field cannot be null");
    private String label;

    private ErrorMessage(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
