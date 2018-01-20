package musta.belmo.validation;


import java.lang.reflect.Field;
import java.util.*;

/**
 * Validator class to perform validation over objects.
 *
 * @author Belmokhtar
 */
public class Validator {
    public static final String NULL_OBJECT_MSG = "Object is null, cannot validate its fields!";

    /**
     * Check the validity of a given object.
     *
     * @param object the instance to check.
     * @param <T>    the explicit type of the instance.
     * @return true if the object is valid, false otherwise.
     * @throws IllegalAccessException
     */
    public <T> boolean checkValidation(T object) throws IllegalAccessException {
        Map<String, ValidationReport> validationReport = getValidationReport(object);
        Iterator<Map.Entry<String, ValidationReport>> iterator = validationReport.entrySet().iterator();
        boolean isValid = true;
        while (iterator.hasNext() && isValid) {
            Map.Entry<String, ValidationReport> item = iterator.next();
            isValid = item.getValue().isValide();
        }
        return isValid;
    }

    /**
     * Checks the validity of a number against the given operator and value.
     *
     * @param number   the number to check validity for.
     * @param operator The binary operator.
     * @param value    the value to validate against
     * @return true if the number is valid, false otherwise.
     */
    private boolean checkNumber(Number number, Operator operator, String value) {
        boolean valid = true;
        switch (operator) {
            case GREATER:
                valid = number.doubleValue() > Double.parseDouble(value);
                break;
            case LESS:
                valid = number.doubleValue() < Double.parseDouble(value);
                break;
            case GREATER_OR_EQUALS:
                valid = number.doubleValue() >= Double.parseDouble(value);
                break;
            case LESS_OR_EQUALS:
                valid = number.doubleValue() <= Double.parseDouble(value);
                break;
            default:
                break;
        }
        return valid;
    }

    /**
     * Constructs a validation report over the annonated fields of the given object.
     *
     * @param object the object to generate the report for.
     * @param <T>    the Type of the object
     * @return a validation report containing details for the object fields.
     * @throws IllegalAccessException
     */
    public <T> Map<String, ValidationReport> getValidationReport(T object) throws IllegalAccessException {
        final Map<String, ValidationReport> validationReportMap = new LinkedHashMap<String, ValidationReport>();

        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(NULL_OBJECT_MSG);
        }

        List<Field> annotatedFields = getAnnotatedFields(object);
        for (Field field : annotatedFields) {
            final ValidationReport validationReport = new ValidationReport();
            final Object currentValue = field.get(object);
            final Validation validation = field.getAnnotation(Validation.class);
            final Assertion assertion = validation.assertion();
            final Operator operator = assertion.operator();
            final String expected = assertion.value();
            final boolean required = validation.required();

            validationReport.setAssertion(assertion);
            validationReport.setFound(currentValue);
            validationReport.setClassName(field.getType().getCanonicalName());
            validationReport.setRequired(required);

            boolean valid = true;
            if (required) {
                valid = checkValidation(currentValue, operator, expected);
            }
            validationReport.setValide(valid);
            validationReportMap.put(field.getName(), validationReport);
        }
        return validationReportMap;
    }

    /**
     * Method to refactor the validation process.
     *
     * @param currentValue the current value of the field.
     * @param operator     the wanted operator.
     * @param expected     the expected value
     * @return true if the object is valid, false otherwise.
     */
    private  boolean checkValidation(Object currentValue, Operator operator, String expected) {
        boolean valid = true;
        switch (operator) {
            case NOT_NULL:
                if (Objects.isNull(currentValue)) {
                    valid = false;
                }
                break;

            case EQUALS:
                valid = expected.equals(currentValue);
                break;

            case REGEX:
                if (!currentValue.toString().matches(expected)) {
                    valid = false;
                }
                break;

            case GREATER:
            case LESS:
            case LESS_OR_EQUALS:
            case GREATER_OR_EQUALS:
                if (currentValue instanceof Number) {
                    valid = checkNumber((Number) currentValue, operator, expected);
                }
                break;
            default:
                break;
        }
        return valid;
    }

    /**
     * @param t   the object retrieve validation fields from
     * @param <T> the generic type of the object
     * @return <tt> List&lt;Field&gt;</tt> a list of the annotated fields with
     * <tt> Validation annotation </tt> in the given class
     */
    private <T> List<Field> getAnnotatedFields(T t) {
        Class cls = t.getClass();
        Field[] fs = cls.getDeclaredFields();
        List<Field> fields = new ArrayList<Field>();

        for (Field field : fs) {
            /**
             * private fields need to be set to be accessible before processing
             */
            field.setAccessible(true);

            /**
             * checks if the field is annotated with <tt>Validation</tt> annotation
             * then add it to the list
             */
            if (field.isAnnotationPresent(Validation.class)) {
                fields.add(field);
            }
        }
        return fields;
    }
}

