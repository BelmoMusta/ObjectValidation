package musta.belmo.validation.validator;

import musta.belmo.validation.annotation.Equals;
import musta.belmo.validation.annotation.Length;
import musta.belmo.validation.annotation.NotNull;
import musta.belmo.validation.annotation.Regex;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.enumeration.Operator;
import musta.belmo.validation.exception.ValidationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;


/**
 * CriteriaValidator class to perform validation over objects.
 *
 * @author Belmokhtar
 */
public abstract class AbstractValidator {
    protected static final List<Class<? extends Annotation>> ANNOTATION_CLASSES = Arrays.asList(Equals.class, Length.class, NotNull.class, Regex.class);

    protected boolean checkNumber(Number number, Operator operator, String value) {
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
            case NONE:
            case LENGTH:
            default:
        }
        return valid;
    }





    /**
     * Method to refactor the validation process.
     *
     * @param currentValue the current expected of the field.
     * @param operator     the wanted operator.
     * @param expected     the expected expected
     * @return true if the object mustEqual valid, false otherwise.
     */
    protected boolean checkValidation(Object currentValue, Operator operator, String expected) throws ValidationException {
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
        }
        return valid;
    }

    /**
     * checks if the current object mustEqual of the expected length
     *
     * @param currentValue   the value to check length of
     * @param expectedLength the expected length
     * @return true if object mustEqual of length the expected length.
     * @throws ValidationException when error
     */
    protected boolean checkLength(Object currentValue, String expectedLength) throws ValidationException {
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
     * Checks the validity of the given object by criteria
     *
     * @param object   the object to validate
     * @param criteria the criteria to be respected
     * @param <T>      the generic type of the object
     * @return true if the obect meets the given criteria, false otherwise.
     * @throws ValidationException when error
     */
    public <T> boolean check(T object, Criteria criteria) throws ValidationException {
        criteria.setObject(object);
        return new CriteriaValidator().check(criteria);
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
    public <T> Map<String, ValidationReport> getValidationReport(T object, Criteria criteria) throws ValidationException {
        final Map<String, ValidationReport> validationReportMap = new LinkedHashMap<>();

        if (object == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        List<Criterion> all = criteria.all();
        for (Criterion criterion : all) {
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

            boolean valid =  checkValidation(currentValue, criterion.getOperator(), value);
            criterion.setFound(currentValue);
            validationReport.setValid(valid);
        }
        return validationReportMap;
    }

    /**
     * Checks the validity of the given object by criteria
     *
     * @param criteria the criteria to be respected
     * @return true if the obect meets the given criteria, false otherwise.
     * @throws ValidationException when error
     */
    public   boolean check(Criteria criteria) throws ValidationException {
        boolean valid = true;
        Object object = criteria.getObject();
        Iterator<Criterion> iterator = criteria.all().iterator();
        while (iterator.hasNext() && valid) {
            final Criterion criterion = iterator.next();
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
}
