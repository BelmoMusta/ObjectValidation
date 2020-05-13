package io.github.belmomusta.validation.validator;

import io.github.belmomusta.validation.criteria.Criteria;
import io.github.belmomusta.validation.enumeration.Operator;
import io.github.belmomusta.validation.exception.ValidationException;
import io.github.belmomusta.validation.utils.ValidationUtils;

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
    public abstract <T> boolean check(Criteria<T> criteria) throws ValidationException;

    /**
     * Checks the validity of the given object by criteria
     *
     * @param object the to be validated
     * @return true if the object meets the given criteria, false otherwise.
     * @throws ValidationException when error
     */
    public abstract <T> boolean check(T object) throws ValidationException;

    /**
     * Constructs a validation report of the object according to the criteria in params.
     *
     * @param criteria the {@link Criteria} to validate to object against
     * @return a validation report containing details for the object fields.
     * @throws ValidationException when error
     */
    public abstract <T>  ValidationReport getValidationReport(Criteria<T> criteria) throws ValidationException;

    /**
     * Method to refactor the validation process.
     *
     * @param currentValue the current expected of the field.
     * @param operator     the wanted operator.
     * @param expected     the expected value
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
                    if (currentValue instanceof Number) {
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
    public abstract <T> ValidationReport getValidationReport(T object) throws ValidationException;

}