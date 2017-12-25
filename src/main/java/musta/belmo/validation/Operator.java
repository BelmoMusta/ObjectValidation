package musta.belmo.validation;

import java.util.HashMap;
import java.util.Map;

public enum Operator {
    NOT_NULL,
    EQUALS,
    GREATER,
    LESS,
    GREATER_OR_EQUALS,
    LESS_OR_EQUALS,
    REGEX;

    public static Map<Operator, String> operatorMap;

    static {

        operatorMap = new HashMap<Operator, String>();

        operatorMap.put(NOT_NULL, "{!=null}");
        operatorMap.put(EQUALS, "{==}");
        operatorMap.put(GREATER, "{>}");
        operatorMap.put(GREATER_OR_EQUALS, "{>=}");
        operatorMap.put(LESS, "{<}");
        operatorMap.put(LESS_OR_EQUALS, "{<=}");
        operatorMap.put(REGEX, "{REGEX}");

    }

}
