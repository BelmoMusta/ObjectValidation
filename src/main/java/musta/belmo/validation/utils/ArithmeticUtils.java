package musta.belmo.validation.utils;

import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.enumeration.Operator;
import musta.belmo.validation.exception.ValidationException;

import java.util.Arrays;
import java.util.Collection;

public class ArithmeticUtils {


    /**
     * Checks the validity of a number against the given operator and expected.
     *
     * @param number   the number to check validity for.
     * @param operator The binary operator.
     * @param value    the expected to validate against
     * @return true if the number is valid, false otherwise.
     */
    public static boolean checkNumber(Number number, Operator operator, String value) {
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

    public static boolean checkRegex(Object expected, Object found) {
        return String.valueOf(found).matches(String.valueOf(expected));
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
            //  throw new ValidationException(ErrorMessage.NULL_FIELD_NAME.getLabel());
        } else if (currentValue instanceof Collection) {
            Collection collection = (Collection) currentValue;
            isValid = collection.size() == length;
        } else if (isArray(currentValue)) {

            int tempLength = 0;
            if (currentValue instanceof int[]) {
                tempLength = ArrayUtils.toBoxedArray((int[]) currentValue).length;
            } else if (currentValue instanceof long[]) {
                tempLength = ArrayUtils.toBoxedArray((long[]) currentValue).length;
            } else if (currentValue instanceof byte[]) {
                tempLength = ArrayUtils.toBoxedArray((byte[]) currentValue).length;
            } else if (currentValue instanceof boolean[]) {
                tempLength = ArrayUtils.toBoxedArray((boolean[]) currentValue).length;
            } else if (currentValue instanceof float[]) {
                tempLength = ArrayUtils.toBoxedArray((float[]) currentValue).length;
            } else if (currentValue instanceof short[]) {
                tempLength = ArrayUtils.toBoxedArray((short[]) currentValue).length;
            } else if (currentValue instanceof double[]) {
                tempLength = ArrayUtils.toBoxedArray((double[]) currentValue).length;
            } else if (currentValue instanceof char[]) {
                tempLength = ArrayUtils.toBoxedArray((char[]) currentValue).length;
            }

                /*
                TODO arrays.asList(int[]) returns a single object referenced by int[]
                 */

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

}
