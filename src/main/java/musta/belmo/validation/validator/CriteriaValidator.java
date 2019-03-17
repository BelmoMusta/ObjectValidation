package musta.belmo.validation.validator;

import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.exception.ValidationException;
import musta.belmo.validation.utils.ReflectUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
