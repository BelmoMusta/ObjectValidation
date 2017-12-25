package musta.belmo.validation;


import java.lang.reflect.Field;
import java.util.*;

public class Validator {
    public <T> boolean validate(T object) throws IllegalAccessException {

        boolean retVal = true;
        Map<String, ValidationReport> validationReport = getValidationReport(object);
        Iterator<Map.Entry<String, ValidationReport>> iterator = validationReport.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, ValidationReport> item = iterator.next();
            boolean valide = item.getValue().isValide();
            if (!valide) {
                return false;
            }
        }


        return retVal;
    }

    private boolean checkNumber(Number number, Operator type, String value) {
        boolean retVal = true;
        switch (type) {
            case GREATER:
                retVal = number.doubleValue() > Double.parseDouble(value);
                break;
            case LESS:
                retVal = number.doubleValue() < Double.parseDouble(value);
                break;
            case GREATER_OR_EQUALS:
                retVal = number.doubleValue() >= Double.parseDouble(value);
                break;
            case LESS_OR_EQUALS:
                retVal = number.doubleValue() <= Double.parseDouble(value);
                break;

            default:
                break;
        }
        return retVal;
    }

    public <T> Map<String, ValidationReport> getValidationReport(T object) throws IllegalAccessException {
        Map<String, ValidationReport> validationReportMap = new LinkedHashMap<String, ValidationReport>();

        if (object == null)
            throw new IllegalArgumentException("object is null, cannot validate its fields!");

        List<Field> annotatedFields = getAnnotatedFields(object);

        for (Field field : annotatedFields) {

            ValidationReport validationReport = new ValidationReport();
            validationReport.setClassName(field.getType().getCanonicalName());
            Object currentValue = field.get(object);
            validationReport.setFound(currentValue);

            final Validation validation = field.getAnnotation(Validation.class);
            final Assertion assertion = validation.assertion();
            validationReport.setAssertion(assertion);

            final Operator operator = assertion.operator();
            final String expected = assertion.value();

            final boolean required = validation.required();

            validationReport.setRequired(required);
            if (required) {
                switch (operator) {
                    case NOT_NULL:
                        if (currentValue == null) {
                            validationReport.setValide(false);
                        }
                        break;

                    case EQUALS:
                        boolean valide = expected.equals(currentValue);
                        validationReport.setValide(valide);
                        break;

                    case REGEX:
                        if (!object.toString().matches(expected))
                            validationReport.setValide(false);
                        break;

                    case GREATER:
                    case LESS:
                    case LESS_OR_EQUALS:
                    case GREATER_OR_EQUALS:


                        if (currentValue instanceof Number) {

                            boolean retVal = checkNumber((Number) currentValue, operator, expected);
                            validationReport.setValide(retVal);
                        } else
                            throw new IllegalArgumentException("Cannot apply this validation to this type of Object, field "
                                    + field.getName() +
                                    " of type :" + currentValue.getClass() + ", validation:" + operator);
                        break;

                    default:
                        break;
                }
            }
            validationReportMap.put(/*object.getClass().getName() + "#" + */field.getName(), validationReport);


        }
        return validationReportMap;
    }


    /**
     * @param t   the object retrieve validation fields from
     * @param <T> the generic type of the object
     * @return <tt> List&lt;Field&gt;</tt> a list of the annotated fields with
     * <tt> Validation annotation </tt> in the given class
     */
    private <T> List<Field> getAnnotatedFields(T t) {
        Class cls = t.getClass();
        Field[] fs = cls.getDeclaredFields();
        List<Field> fields = new ArrayList<Field>();

        for (Field field : fs) {
            /**
             * private fields need to be set to be accessible before processing
             */
            field.setAccessible(true);

            /**
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

