package musta.belmo.validation;

import musta.belmo.validation.annotation.Assertion;
import musta.belmo.validation.annotation.Validation;
import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.enumeration.Operator;
import musta.belmo.validation.exception.ValidationException;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Validator class to perform validation over objects.
 *
 * @author Belmokhtar
 */
public class Validator {
    /**
     * Check the validity of a given object.
     *
     * @param object the instance to check.
     * @param <T>    the explicit type of the instance.
     * @return true if the object is valid, false otherwise.
     * @throws ValidationException
     */
    public <T> boolean checkValidation(T object) throws ValidationException {
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
     * @throws ValidationException
     */
    public <T> Map<String, ValidationReport> getValidationReport(T object) throws ValidationException {
        final Map<String, ValidationReport> validationReportMap = new LinkedHashMap<>();

        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }

        List<Field> annotatedFields = getAnnotatedFields(object);
        for (Field field : annotatedFields) {
            final ValidationReport validationReport = new ValidationReport();
            Object currentValue = null;
            try {
                currentValue = field.get(object);
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
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
    private boolean checkValidation(Object currentValue, Operator operator, String expected) throws ValidationException {
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
                if (currentValue != null && currentValue.getClass() == String.class) {
                    valid = currentValue.toString().matches(expected);

                } else if (currentValue != null) {
                    final String message = String.format(ErrorMessage.REGEX_OVER_NON_STRING.getLabel(), currentValue.getClass().getCanonicalName());
                    throw new ValidationException(message);
                } else {
                    throw new ValidationException(ErrorMessage.REGEX_OVER_NULL.getLabel());

                }
                break;

            case GREATER:
            case LESS:
            case LESS_OR_EQUALS:
            case GREATER_OR_EQUALS:
                if (currentValue != null && currentValue instanceof Number) {
                    valid = checkNumber((Number) currentValue, operator, expected);
                } else if (currentValue == null) {
                    throw new ValidationException(ErrorMessage.ARITHMETIC_ON_NULL.getLabel());
                } else {
                    throw new ValidationException(ErrorMessage.NOT_A_NUMBER.getLabel());
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
        List<Field> fields = new ArrayList<>();

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

