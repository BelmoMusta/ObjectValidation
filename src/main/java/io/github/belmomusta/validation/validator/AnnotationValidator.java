package io.github.belmomusta.validation.validator;

import io.github.belmomusta.validation.annotation.Validation;
import io.github.belmomusta.validation.criteria.Criteria;
import io.github.belmomusta.validation.criteria.Criterion;
import io.github.belmomusta.validation.enumeration.ErrorMessage;
import io.github.belmomusta.validation.exception.ValidationException;
import io.github.belmomusta.validation.utils.ReflectUtils;
import java.lang.reflect.Field;
import java.util.List;

/**
 * CriteriaValidator class to perform validation over objects.
 *
 * @author Belmokhtar
 * @since 0.0.0.SNAPSHOT
 * @version 0.0.0
 */
public class AnnotationValidator<T> extends CriteriaValidator<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean check(T object) throws ValidationException {
        Criteria<T> criteria = createCriteria(object);
        return super.check(criteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationReport getValidationReport(T object) throws ValidationException {
        Criteria<T> criteria = createCriteria(object);
        return super.getValidationReport(criteria);
    }

    /**
     * Creates a {@link Criteria } object from the object in parameters
     *
     * @param object
     * @return @link Criteria}
     * @throws ValidationException if validation is not successful
     */
    private Criteria<T> createCriteria(T object) throws ValidationException {
        if (object == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        final List<Field> annotatedFields = ReflectUtils.getAnnotatedFields(object.getClass(), Validation.class);
        Criteria<T> criteria = new Criteria<>();
        criteria.setObject(object);
        for (Field field : annotatedFields) {
            final Object currentValue;
            try {
                currentValue = field.get(object);
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
            final Validation validation = field.getAnnotation(Validation.class);
            final Criterion criterion = Criterion.of(field.getName()).operator(validation.operator()).found(currentValue).expected(validation.value());
            criteria.add(criterion);
        }
        return criteria;
    }
}
