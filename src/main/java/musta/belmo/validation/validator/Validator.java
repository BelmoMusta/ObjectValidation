package musta.belmo.validation.validator;

import musta.belmo.validation.annotation.Assertion;
import musta.belmo.validation.annotation.Validation;
import musta.belmo.validation.criteria.Criteria;
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

    private static Validator validator;

    public static Validator getInstance() {
        if (validator == null) {
            validator = new Validator();
        }
        return validator;
    }

    /**
     * Check the validity of a given object by annotations.
     *
     * @param object the instance to check.
     * @param <T>    the explicit type of the instance.
     * @return true if the object is valid, false otherwise.
     * @throws ValidationException when error
     */
    public <T> boolean check(T object) throws ValidationException {
        boolean valid = true;
        if (object == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        final List<Criteria> criteria = new ArrayList<>();
        final List<Field> annotatedFields = getAnnotatedFields(object.getClass());
        Iterator<Field> iterator = annotatedFields.iterator();
        while (iterator.hasNext() && valid) {
            final Object currentValue;
            Field field = iterator.next();
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
            final Criteria cr = Criteria
                    .of(field.getName())
                    .operator(operator)
                    .found(currentValue)
                    .expected(expected)
                    .required();
            criteria.add(cr);
            if (required) {
                valid = check(object, criteria);
            }
        }
        return valid;
    }

    /**
     * Checks the validity of the given object by criteria
     *
     * @param object   the object to validate
     * @param criteria the criteria to be respected
     * @param <T>      the generic type of the object
     * @return true if the obect meets the given criteria, false otherwise.
     * @throws ValidationException when error
     */
    public <T> boolean check(T object, List<Criteria> criteria) throws ValidationException {
        boolean valid = true;
        if (object == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        Iterator<Criteria> iterator = criteria.iterator();
        while (iterator.hasNext() && valid) {
            final Criteria criterion = iterator.next();
            final String fieldName = criterion.getFieldName();
            final Field declaredField;
            final Object currentValue;
            final String expected;

            try {
                if (fieldName != null) {
                    declaredField = object.getClass().getDeclaredField(fieldName);
                } else {
                    throw new NoSuchFieldException(ErrorMessage.NULL_FIELD_NAME.getLabel());
                }
                declaredField.setAccessible(true);
                currentValue = declaredField.get(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ValidationException(e);
            }

            expected = String.valueOf(criterion.getExpected());
            if (criterion.isRequired()) {
                valid = checkValidation(currentValue, criterion.getOperator(), expected);
            }
        }
        return valid;
    }

    /**
     * Checks the validity of a number against the given operator and expected.
     *
     * @param number   the number to check validity for.
     * @param operator The binary operator.
     * @param value    the expected to validate against
     * @return true if the number is valid, false otherwise.
     */
    private boolean checkNumber(Number number, Operator operator, String value) {
        boolean valid = true;
        switch (operator) {
            case NOT_NULL:
                break;
            case EQUALS:
                valid = number.doubleValue() == Double.parseDouble(value);
                break;
            case GREATER_THAN:
                valid = number.doubleValue() > Double.parseDouble(value);
                break;
            case LESS_THAN:
                valid = number.doubleValue() < Double.parseDouble(value);
                break;
            case GREATER_OR_EQUALS:
                valid = number.doubleValue() >= Double.parseDouble(value);
                break;
            case LESS_OR_EQUALS:
                valid = number.doubleValue() <= Double.parseDouble(value);
                break;
            case REGEX:
                break;
            case NONE:
                break;
            case LENGTH:
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
     * @throws ValidationException when error
     */
    public <T> Map<String, ValidationReport> getValidationReport(T object) throws ValidationException {

        if (Objects.isNull(object)) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        final List<Criteria> criteria = new ArrayList<>();
        final List<Field> annotatedFields = getAnnotatedFields(object.getClass());

        for (Field field : annotatedFields) {
            final Validation validation = field.getAnnotation(Validation.class);
            final Assertion assertion = validation.assertion();
            final Operator operator = assertion.operator();
            final String expected = assertion.value();
            final boolean required = validation.required();
            final Object currentValue;
            try {
                currentValue = field.get(object);
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
            final Criteria cr = Criteria
                    .of(field.getName())
                    .operator(operator)
                    .expected(expected)
                    .found(currentValue);
            cr.setRequired(required);
            criteria.add(cr);
        }
        return getValidationReport(object, criteria);
    }

    /**
     * Constructs a validation report of the object according to the criteria in params.
     *
     * @param object   the object to generate the report for.
     * @param <T>      the Type of the object
     * @param criteria the criteria to validate to object against
     * @return a validation report containing details for the object fields.
     * @throws ValidationException when error
     */
    public <T> Map<String, ValidationReport> getValidationReport(T object, List<Criteria> criteria) throws ValidationException {
        final Map<String, ValidationReport> validationReportMap = new LinkedHashMap<>();

        if (object == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        for (Criteria criterion : criteria) {
            String fieldName = criterion.getFieldName();
            Object currentValue;
            String value = String.valueOf(criterion.getExpected());
            final ValidationReport validationReport = new ValidationReport();
            validationReportMap.put(criterion.getFieldName(), validationReport);
            validationReport.setCriterion(criterion);
            validationReport.setRequired(criterion.isRequired());
            try {
                Field declaredField = object.getClass().getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                currentValue = declaredField.get(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ValidationException(e);
            }

            boolean valid = checkValidation(currentValue, criterion.getOperator(), value);
            criterion.setFound(currentValue);
            validationReport.setValid(valid);
        }
        return validationReportMap;
    }


    /**
     * Method to refactor the validation process.
     *
     * @param currentValue the current expected of the field.
     * @param operator     the wanted operator.
     * @param expected     the expected expected
     * @return true if the object is valid, false otherwise.
     */
    private boolean checkValidation(Object currentValue, Operator operator, String expected) throws ValidationException {
        boolean valid = true;
        switch (operator) {
            case NOT_NULL:
                valid = currentValue != null;
                break;

            case EQUALS:
                if (currentValue instanceof Number) {
                    valid = checkNumber((Number) currentValue, operator, expected);
                } else {
                    valid = Objects.equals(expected, currentValue);
                }
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

            case LENGTH:
                valid = checkLength(currentValue, expected);
                break;

            case GREATER_THAN:
            case LESS_THAN:
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
     * checks if the current object is of the expected length
     *
     * @param currentValue   the value to check length of
     * @param expectedLength the expected length
     * @return true if object is of length the expected length.
     * @throws ValidationException when error
     */
    private boolean checkLength(Object currentValue, String expectedLength) throws ValidationException {
        Integer length;
        if (currentValue == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        String strObject = String.valueOf(currentValue);
        try {
            length = Integer.parseInt(expectedLength);
        } catch (NumberFormatException ex) {
            throw new ValidationException(ErrorMessage.LENGTH_ERROR_MSG.getLabel() + expectedLength, ex);
        }
        return strObject.length() == length;
    }

    /**
     * @param aClass the class to  retrieve validation fields from
     * @param <T>    the generic type of the object
     * @return <tt> List&lt;Field&gt;</tt> a list of the annotated fields with
     * <tt> Validation annotation </tt> in the given class
     */
    private <T> List<Field> getAnnotatedFields(Class<? extends T> aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        List<Field> fields = new ArrayList<>();

        for (Field field : declaredFields) {
            /*
             * private fields need to be set to be accessible before processing
             */
            field.setAccessible(true);

            /*
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

