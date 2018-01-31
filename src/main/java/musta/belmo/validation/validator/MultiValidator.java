package musta.belmo.validation.validator;

import musta.belmo.validation.annotation.*;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.enumeration.Operator;
import musta.belmo.validation.exception.ValidationException;
import musta.belmo.validation.utils.ArithmeticUtils;
import musta.belmo.validation.utils.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;


/**
 * Validator class to perform validation over objects.
 *
 * @author Belmokhtar
 */
public class MultiValidator {

    private static final List<Class<? extends Annotation>> ANNOTATION_CLASSES = Arrays.asList(Equals.class, Length.class, NotNull.class, Regex.class);

    private static MultiValidator validator;

    public static MultiValidator getInstance() {
        if (validator == null) {
            validator = new MultiValidator();
        }
        return validator;
    }


    public <T> boolean check(T object, Criteria criteria) throws ValidationException {
        boolean valid = true;

        if (object == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }

        Iterator<Map.Entry<String, List<Criterion>>> iterator = criteria.map.entrySet().iterator();
        {
            Object valueFromField;
            while (valid && iterator.hasNext()) {
                Map.Entry<String, List<Criterion>> next = iterator.next();
                String fieldName = next.getKey();
                List<Criterion> criterionList = next.getValue();

                for (Criterion criterion : criterionList) {
                    try {
                        valueFromField = ReflectUtils.getValueFromField(object, fieldName);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new ValidationException("TODO", e);

                    }
                    criterion.setFound(valueFromField);
                    valid = isMet(criterion);
                }
            }

            return valid;
        }
    }

    private boolean isMet(Criterion criterion) {
        boolean isMet = true;
        Object expected = criterion.getExpected();
        Object found = criterion.getFound();
        Operator operator = criterion.getOperator();
        switch (criterion.getOperator()) {
            case NOT_NULL:
                isMet = found != null;
                break;
            case EQUALS:
                if (expected instanceof Number) {
                    ArithmeticUtils.checkNumber((Number) expected, Operator.EQUALS, found.toString());
                } else {
                    isMet = expected.equals(found);
                }
                break;

            case GREATER_THAN:
            case LESS_THAN:
            case GREATER_OR_EQUALS:
            case LESS_OR_EQUALS:
                isMet = ArithmeticUtils.checkNumber((Number) expected, operator, found.toString());
                break;
            case REGEX:
                isMet = ArithmeticUtils.checkRegex(expected, found);
                break;
            case NONE:
                break;
            case LENGTH:
                isMet = ArithmeticUtils.checkLength(expected, found);
                break;
        }

        return isMet;

    }
}
