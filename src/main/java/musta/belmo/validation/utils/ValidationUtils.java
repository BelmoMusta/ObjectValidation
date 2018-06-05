package musta.belmo.validation.utils;

import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.enumeration.Operator;
import musta.belmo.validation.exception.ValidationException;

import java.util.Collection;
import java.util.List;

public class ValidationUtils {
    private ValidationUtils() {
    }

    /**
     * Checks the validity of a number against the given operator and expected.
     *
     * @param currentValue the number to check validity for.
     * @param operator     The binary operator.
     * @param value        the expected to validate against
     * @return true if the number is valid, false otherwise.
     */
    public static boolean checkNumber(Object currentValue, Operator operator, String value) throws ValidationException {
        boolean valid = true;
        if (currentValue != null && currentValue instanceof Number) {
            Number number = (Number) currentValue;
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
        } else if (currentValue == null) {
            throw new ValidationException(ErrorMessage.ARITHMETIC_ON_NULL.getLabel());
        } else {
            throw new ValidationException(ErrorMessage.NOT_A_NUMBER.getLabel());
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
    public static boolean checkLength(Object currentValue, String expectedLength) throws ValidationException {
        int length;
        boolean isValid;
        try {
            length = Integer.parseInt(expectedLength);
        } catch (NumberFormatException ex) {
            throw new ValidationException(ErrorMessage.LENGTH_ERROR_MSG.getLabel() + expectedLength, ex);
        }
        if (currentValue == null) {
            isValid = false;
        } else if (currentValue instanceof Collection) {
            Collection collection = (Collection) currentValue;
            isValid = collection.size() == length;
        } else if (isArray(currentValue)) {
            int tempLength = 0;

            List list = ArrayUtils.castArrayToList(currentValue);
            if (list != null)
                tempLength = list.size();

            isValid = length == tempLength;
        } else {
            String strObject = String.valueOf(currentValue);
            isValid = strObject.length() == length;
        }
        return isValid;
    }

    private static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    public static <T, R> boolean safeEquals(T t, R r) {
        if (r == null || t == null) {
            return r == t;
        }
        return r.getClass() == t.getClass() && r.equals(t);
    }

    public static boolean checkRegex(Object currentValue, String expected) throws ValidationException {
        boolean valid;
        if (currentValue instanceof CharSequence) {
            valid = currentValue.toString().matches(expected);
        } else if (currentValue != null) {
            final String message = String.format(ErrorMessage.REGEX_OVER_NON_STRING.getLabel(), currentValue.getClass().getCanonicalName());
            throw new ValidationException(message);
        } else {
            throw new ValidationException(ErrorMessage.REGEX_OVER_NULL.getLabel());
        }
        return valid;
    }
}
