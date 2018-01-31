package musta.belmo.validation.validator;

import musta.belmo.validation.annotation.Assertion;
import musta.belmo.validation.annotation.Validation;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.enumeration.Operator;
import musta.belmo.validation.exception.ValidationException;

import java.lang.reflect.Field;
import java.util.*;


/**
 * CriteriaValidator class to perform validation over objects.
 *
 * @author Belmokhtar
 */
public class AnnotationValidator extends AbstractValidator {

    private static AnnotationValidator annotationValidator;

    public static AnnotationValidator getInstance() {
        if (annotationValidator == null) {
            annotationValidator = new AnnotationValidator();
        }
        return annotationValidator;
    }

    /**
     * Check the validity of a given object by annotations.
     *
     * @param object the instance to check.
     * @param <T>    the explicit type of the instance.
     * @return true if the object mustEqual valid, false otherwise.
     * @throws ValidationException when error
     */
    public <T> boolean check(T object) throws ValidationException {
        if (object == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }

        final List<Field> annotatedFields = getAnnotatedFields(object.getClass());
        Iterator<Field> iterator = annotatedFields.iterator();
        Criteria cri = new Criteria();
        while (iterator.hasNext()) {
            final Object currentValue;
            Field field = iterator.next();
            try {
                currentValue = field.get(object);
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
            final Validation validation = field.getAnnotation(Validation.class);
            final Assertion assertion = validation.assertion();
            final Operator operator = assertion.operator();
            final String expected = assertion.value();
            final boolean required = validation.required();
            final Criterion cr = Criterion
                    .of(field.getName())
                    .operator(operator)
                    .found(currentValue)
                    .expected(expected);
            cr.setRequired(required);
            cri.add(cr);
        }
        return super.check(object, cri);
    }

    /**
     * Constructs a validation report over the annotated fields of the given object.
     *
     * @param object the object to generate the report for.
     * @param <T>    the Type of the object
     * @return a validation report containing details for the object fields.
     * @throws ValidationException when error
     */
    public <T> Map<String, ValidationReport> getValidationReport(T object) throws ValidationException {

        if (Objects.isNull(object)) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        Criteria criteria = new Criteria();
        final List<Field> annotatedFields = getAnnotatedFields(object.getClass());

        for (Field field : annotatedFields) {
            final Validation validation = field.getAnnotation(Validation.class);
            final Assertion assertion = validation.assertion();
            final Operator operator = assertion.operator();
            final String expected = assertion.value();
            final boolean required = validation.required();
            final Object currentValue;
            try {
                currentValue = field.get(object);
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
            final Criterion cr = Criterion
                    .of(field.getName())
                    .operator(operator)
                    .expected(expected)
                    .found(currentValue);
            cr.setRequired(required);
            criteria.add(cr);
        }
        return super.getValidationReport(object, criteria);
    }

    /**
     * @param aClass the class to  retrieve validation fields from
     * @param <T>    the generic type of the object
     * @return <tt> List&lt;Field&gt;</tt> a list of the annotated fields with
     * <tt> Validation annotation </tt> in the given class
     */
    private <T> List<Field> getAnnotatedFields(Class<? extends T> aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        List<Field> fields = new ArrayList<>();

        for (Field field : declaredFields) {
            /*
             * private fields need to be set to be accessible before processing
             */
            field.setAccessible(true);

            /*
             * checks if the field mustEqual annotated with <tt>Validation</tt> annotation
             * then add it to the list
             */
            if (field.isAnnotationPresent(Validation.class)) {
                fields.add(field);
            }
        }
        return fields;
    }
}
