package io.github.belmomusta.validation.validator;

import io.github.belmomusta.validation.criteria.Criteria;
import io.github.belmomusta.validation.criteria.Criterion;
import io.github.belmomusta.validation.exception.ValidationException;
import io.github.belmomusta.validation.utils.ReflectUtils;
import io.github.belmomusta.validation.enumeration.ErrorMessage;

import java.util.Iterator;
import java.util.List;

/**
 * CriteriaValidator class to perform validation over objects.
 *
 * @author Belmokhtar
 */
public class CriteriaValidator extends AbstractValidator {
    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean check(Criteria<T> criteria) throws ValidationException {
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
    public <T> boolean check(T object) throws ValidationException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> ValidationReport getValidationReport(Criteria<T> criteria) throws ValidationException {
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
    public <T> ValidationReport getValidationReport(T object) throws ValidationException {
        throw new UnsupportedOperationException();
    }
}
