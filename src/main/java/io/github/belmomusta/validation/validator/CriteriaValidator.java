package io.github.belmomusta.validation.validator;

import io.github.belmomusta.validation.criteria.Criteria;
import io.github.belmomusta.validation.criteria.Criterion;
import io.github.belmomusta.validation.enumeration.ErrorMessage;
import io.github.belmomusta.validation.exception.ValidationException;
import io.github.belmomusta.validation.utils.ReflectUtils;
import java.util.Iterator;
import java.util.List;

/**
 * CriteriaValidator class to perform validation over objects.
 *
 * @author Belmokhtar
 * @since 0.0.0.SNAPSHOT
 * @version 0.0.0
 */
public class CriteriaValidator<R> extends AbstractValidator<R> {

    /**
     * The {@link #mCriteria} attribute.
     */
    protected final Criteria<R> mCriteria = new Criteria<>();

    /**
     * Adds a criterion to the validation process
     *
     * @param criterion the criterion to be added
     * @return
     */
    public CriteriaValidator<R> add(Criterion criterion) {
        mCriteria.add(criterion);
        return this;
    }

    /**
     * Checks the validity of the given object by criteria
     *
     * @param criteria the criteria to be respected
     * @return true if the object meets the given criteria, false otherwise.
     * @throws ValidationException when error
     */
    protected boolean check(Criteria<R> criteria) throws ValidationException {
        boolean valid = true;
        Iterator<Criterion> iterator = criteria.all().iterator();
        while (iterator.hasNext() && valid) {
            final Criterion criterion = iterator.next();
            final String fieldName = criterion.getFieldName();
            final Object currentValue;
            final String expected = String.valueOf(criterion.getExpected());
            try {
                currentValue = ReflectUtils.getFieldValue(criteria.getObject(), fieldName);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ValidationException(e);
            }
            valid = checkValidation(currentValue, criterion.getOperator(), expected);
        }
        return valid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean check(R object) throws ValidationException {
        mCriteria.setObject(object);
        return check(mCriteria);
    }

    /**
     * Constructs a validation report of the object according to the criteria in params.
     *
     * @param criteria the {@link Criteria} to validate to object against
     * @return a validation report containing details for the object fields.
     * @throws ValidationException when error
     */
    protected ValidationReport getValidationReport(Criteria<R> criteria) throws ValidationException {
        final ValidationReport validationReportMap = new ValidationReport();
        if (criteria.getObject() == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        List<Criterion> all = criteria.all();
        for (Criterion criterion : all) {
            String fieldName = criterion.getFieldName();
            Object currentValue;
            String value = String.valueOf(criterion.getExpected());
            final ValidationReportItem validationReportItem = new ValidationReportItem();
            validationReportItem.setCriterion(criterion);
            try {
                currentValue = ReflectUtils.getFieldValue(criteria.getObject(), fieldName);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ValidationException(e);
            }
            boolean valid = checkValidation(currentValue, criterion.getOperator(), value);
            validationReportItem.setFound(currentValue);
            validationReportItem.setValid(valid);
            validationReportMap.add(validationReportItem);
        }
        return validationReportMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationReport getValidationReport(R object) throws ValidationException {
        mCriteria.setObject(object);
        return getValidationReport(mCriteria);
    }
}
