package musta.belmo.validation.validator;

import musta.belmo.validation.annotation.Validation;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.enumeration.Operator;
import musta.belmo.validation.exception.ValidationException;
import musta.belmo.validation.utils.ReflectUtils;
import musta.belmo.validation.utils.ValidationUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * AbstractValidator class to perform validation over objects.
 *
 * @author Belmokhtar
 */
public abstract class AbstractValidator {
    /**
     * Checks the validity of the given object by criteria
     *
     * @param criteria the criteria to be respected
     * @return true if the object meets the given criteria, false otherwise.
     * @throws ValidationException when error
     */
    public abstract boolean check(Criteria criteria) throws ValidationException;

    /**
     * Checks the validity of the given object by criteria
     *
     * @param criteria the criteria to be respected
     * @return true if the object meets the given criteria, false otherwise.
     * @throws ValidationException when error
     */
    public abstract <T> boolean check(T criteria) throws ValidationException;

    /**
     * Constructs a validation report of the object according to the criteria in params.
     *
     * @param criteria the criteria to validate to object against
     * @return a validation report containing details for the object fields.
     * @throws ValidationException when error
     */
    public abstract Map<String, ValidationReport> getValidationReport(Criteria criteria) throws ValidationException;

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
        if (operator != null) {
            switch (operator) {
                case NOT_NULL:
                    valid = currentValue != null;
                    break;
                case EQUALS:
                    if (currentValue != null && currentValue instanceof Number) {
                        valid = ValidationUtils.checkNumber(currentValue, operator, expected);
                    } else {
                        valid = ValidationUtils.safeEquals(currentValue, expected);
                    }
                    break;
                case REGEX:
                    valid = ValidationUtils.checkRegex(currentValue, expected);
                    break;
                case LENGTH:
                    valid = ValidationUtils.checkLength(currentValue, expected);
                    break;
                case GREATER_THAN:
                case LESS_THAN:
                case LESS_OR_EQUALS:
                case GREATER_OR_EQUALS:
                    valid = ValidationUtils.checkNumber(currentValue, operator, expected);
                    break;
                default:
            }
        }
        return valid;
    }

    /**
     * Constructs a validation report over the annotated fields of the given object.
     *
     * @param object the object to generate the report for.
     * @param <T>    the Type of the object
     * @return a validation report containing details for the object fields.
     * @throws ValidationException when error
     */
    public abstract <T> Map<String, ValidationReport> getValidationReport(T object) throws ValidationException;

    /**
     * Creates a {@link Criteria } object from the object in parameters
     *
     * @param object
     * @param <T>
     * @return {@link Criteria}
     * @throws ValidationException
     */
    public <T> Criteria createCriteria(T object) throws ValidationException {
        if (object == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        final List<Field> annotatedFields = ReflectUtils.getAnnotatedFields(object.getClass(), Validation.class);
        Criteria criteria = new Criteria();
        criteria.setObject(object);
        for (Field field : annotatedFields) {
            final Object currentValue;
            try {
                currentValue = field.get(object);
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
            final Validation validation = field.getAnnotation(Validation.class);
            final Criterion cr = Criterion
                    .of(field.getName())
                    .operator(validation.operator())
                    .found(currentValue)
                    .expected(validation.value());
            cr.setRequired(validation.required());
            criteria.add(cr);
        }
        return criteria;
    }
}
