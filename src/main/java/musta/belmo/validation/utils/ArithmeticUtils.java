package musta.belmo.validation.utils;

import musta.belmo.validation.enumeration.Operator;

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

    public static boolean checkLength(Object expected, Object found) {

        return true;
    }
}
