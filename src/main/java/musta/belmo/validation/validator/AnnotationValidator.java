package musta.belmo.validation.validator;

import musta.belmo.validation.annotation.Validation;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.exception.ValidationException;
import musta.belmo.validation.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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
        Criteria criteria = createCriteria(object);
        return super.check(criteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Map<String, ValidationReport> getValidationReport(T object) throws ValidationException {
        Criteria criteria = createCriteria(object);
        return super.getValidationReport(criteria);
    }


    /**
     * Creates a {@link Criteria } object from the object in parameters
     *
     * @param object
     * @param <T>
     * @return {@link Criteria}
     * @throws ValidationException
     */

    private <T> Criteria createCriteria(T object) throws ValidationException {
        if (object == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        final List<Field> annotatedFields = ReflectUtils.getAnnotatedFields(object.getClass(), Validation.class);
        Criteria criteria = new Criteria();
        criteria.setObject(object);
        for (Field field : annotatedFields) {
            final Object currentValue;
            try {
                currentValue = field.get(object);
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
            final Validation validation = field.getAnnotation(Validation.class);
            final Criterion cr = Criterion
                    .of(field.getName())
                    .operator(validation.operator())
                    .found(currentValue)
                    .expected(validation.value());
            cr.setRequired(validation.required());
            criteria.add(cr);
        }
        return criteria;
    }
}
