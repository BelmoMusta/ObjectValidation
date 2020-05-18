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
public abstract class AbstractValidator<R> {

    /**
     * Checks the validity of the given object by criteria
     *
     * @param object the to be validated
     * @return true if the object meets the given criteria, false otherwise.
     * @throws ValidationException when error
     */
    public abstract boolean check(R object) throws ValidationException;

    /**
     * Method to refactor the validation process.
     *
     * @param currentValue the current expected of the field.
     * @param operator     the wanted operator.
     * @param expected     the expected value
     * @throws ValidationException if validation is not successful
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
     * @return a validation report containing details for the object fields.
     * @throws ValidationException when error
     */
    public abstract ValidationReport getValidationReport(R object) throws ValidationException;

}
