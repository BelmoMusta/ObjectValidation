package musta.belmo.validation.validator;

import musta.belmo.validation.annotation.Assertion;
import musta.belmo.validation.annotation.Validation;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.exception.ValidationException;
import musta.belmo.validation.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * CriteriaValidator class to perform validation over objects.
 *
 * @author Belmokhtar
 */
public class AnnotationValidator extends CriteriaValidator {
    private static AnnotationValidator annotationValidator;

    public static AnnotationValidator getInstance() {
        if (annotationValidator == null) {
            annotationValidator = new AnnotationValidator();
        }
        return annotationValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean check(T object) throws ValidationException {
        if (object == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }

        final List<Field> annotatedFields = ReflectUtils.getAnnotatedFields(object.getClass(), Validation.class);
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
            final Criterion cr = Criterion
                    .of(field.getName())
                    .operator(assertion.operator())
                    .found(currentValue)
                    .expected(assertion.value());
            cr.setRequired(validation.required());
            cri.setObject(object);
            cri.add(cr);
        }
        return super.check(cri);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Map<String, ValidationReport> getValidationReport(T object) throws ValidationException {
        if (Objects.isNull(object)) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        Criteria criteria = new Criteria();
        criteria.setObject(object);
        final List<Field> annotatedFields = ReflectUtils.getAnnotatedFields(object.getClass(), Validation.class);

        for (Field field : annotatedFields) {
            final Validation validation = field.getAnnotation(Validation.class);
            final Assertion assertion = validation.assertion();
            final Criterion cr = Criterion
                    .of(field.getName())
                    .operator(assertion.operator())
                    .expected(assertion.value());
            cr.setRequired(validation.required());
            criteria.add(cr);
            try {
                cr.found(field.get(object));
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
        }
        return super.getValidationReport(criteria);
    }
}
