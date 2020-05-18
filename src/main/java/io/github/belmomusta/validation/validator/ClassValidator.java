package io.github.belmomusta.validation.validator;

import io.github.belmomusta.validation.annotation.Validation;
import io.github.belmomusta.validation.criteria.Criteria;
import io.github.belmomusta.validation.criteria.Criterion;
import io.github.belmomusta.validation.exception.ValidationException;
import io.github.belmomusta.validation.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * ClassValidator class to perform validation over objects using the class name.
 *
 * @author Belmokhtar
 */
public class ClassValidator<C> extends CriteriaValidator<C> {
    private Class<? extends C> theClass;

    public ClassValidator(Class<? extends C> theClass) {
        this.theClass = theClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public  boolean check(C object) throws ValidationException {
        Criteria<C> criteria = createCriteria(object);
        return super.check(criteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public  ValidationReport getValidationReport(C object) throws ValidationException {
        Criteria<C> criteria = createCriteria(object);
        return super.getValidationReport(criteria);
    }


    /**
     * Creates a {@link Criteria } object from the object in parameters
     *
     * @param <T> the generic type
     * @return {@link Criteria}
     * @throws ValidationException if validation is not successful
     */

    private <T> Criteria<T> createCriteria(T object) throws ValidationException {

        final List<Field> annotatedFields = getFields();
        Criteria<T> criteria = new Criteria<>();
        for (Field field : annotatedFields) {

            final Validation validation = field.getAnnotation(Validation.class);
            final Criterion criterion = Criterion
                    .of(field.getName())
                    .operator(validation.operator())
                    .expected(validation.value());
            criteria.add(criterion);
        }
        return criteria;
    }

    private <T> List<Field> getFields() {
        return ReflectUtils.getFieldsFromAClass(theClass);
    }
}
