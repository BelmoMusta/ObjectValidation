package musta.belmo.validation;


import java.lang.reflect.Field;
import java.util.*;

/**
 * Validator class to perform validation over objects.
 *
 * @author Belmokhtar
 */
public class Validator {

    public static final String EXCEPTION_MESSAGE = "Cannot apply this validation to this type of Object, field %s of type: %s, validation: %s";
    public static final String NULL_OBJECT_MSG = "object is null, cannot validate its fields!";

    /**
     * Check the validity of a given object.
     *
     * @param object the instance to check.
     * @param <T>    the explicit type of the instance.
     * @return true if the object is valid, false otherwise.
     * @throws IllegalAccessException
     */
    public <T> boolean validate(T object) throws IllegalAccessException {
        Map<String, ValidationReport> validationReport = getValidationReport(object);
        Iterator<Map.Entry<String, ValidationReport>> iterator = validationReport.entrySet().iterator();
        boolean isValide = true;
        while (iterator.hasNext() && isValide) {
            Map.Entry<String, ValidationReport> item = iterator.next();
            isValide = item.getValue().isValide();
        }
        return isValide;
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
        boolean retVal = true;
        switch (operator) {
            case GREATER:
                retVal = number.doubleValue() > Double.parseDouble(value);
                break;
            case LESS:
                retVal = number.doubleValue() < Double.parseDouble(value);
                break;
            case GREATER_OR_EQUALS:
                retVal = number.doubleValue() >= Double.parseDouble(value);
                break;
            case LESS_OR_EQUALS:
                retVal = number.doubleValue() <= Double.parseDouble(value);
                break;
            default:
                break;
        }
        return retVal;
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
        Map<String, ValidationReport> validationReportMap = new LinkedHashMap<String, ValidationReport>();

        if (object == null) {
            throw new IllegalArgumentException(NULL_OBJECT_MSG);
        }
        List<Field> annotatedFields = getAnnotatedFields(object);

        for (Field field : annotatedFields) {

            ValidationReport validationReport = new ValidationReport();
            validationReport.setClassName(field.getType().getCanonicalName());
            Object currentValue = field.get(object);
            validationReport.setFound(currentValue);

            final Validation validation = field.getAnnotation(Validation.class);
            final Assertion assertion = validation.assertion();
            validationReport.setAssertion(assertion);
            final Operator operator = assertion.operator();
            final String expected = assertion.value();
            final boolean required = validation.required();

            boolean valid = true;
            validationReport.setRequired(required);
            if (required) {
                switch (operator) {
                    case NOT_NULL:
                        if (currentValue == null) {
                            valid = false;
                        }
                        break;

                    case EQUALS:
                        valid = expected.equals(currentValue);
                        break;

                    case REGEX:
                        if (!object.toString().matches(expected)) {
                            valid = false;
                        }

                        break;

                    case GREATER:
                    case LESS:
                    case LESS_OR_EQUALS:
                    case GREATER_OR_EQUALS:
                        if (currentValue instanceof Number) {
                            valid = checkNumber((Number) currentValue, operator, expected);
                        } else
                            throw new IllegalArgumentException(String.format(EXCEPTION_MESSAGE, field.getName(), currentValue.getClass(), operator));
                        break;
                    default:
                        break;
                }
                validationReport.setValide(valid);
            }
            validationReportMap.put(field.getName(), validationReport);
        }
        return validationReportMap;
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

