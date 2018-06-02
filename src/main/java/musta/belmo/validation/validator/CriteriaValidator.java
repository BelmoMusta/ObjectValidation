package musta.belmo.validation.validator;

import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.exception.ValidationException;

import java.lang.reflect.Field;
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

    private static CriteriaValidator criteriaValidator;

    public static CriteriaValidator getInstance() {
        if (criteriaValidator == null) {
            criteriaValidator = new CriteriaValidator();
        }
        return criteriaValidator;
    }

    @Override
    public boolean check(Criteria criteria) throws ValidationException {
        boolean valid = true;
        Object object = criteria.getObject();
        Iterator<Criterion> iterator = criteria.all().iterator();
        while (iterator.hasNext() && valid) {
            final Criterion criterion = iterator.next();
            final String fieldName = criterion.getFieldName();
            final Field declaredField;
            final Object currentValue;
            final String expected;

            try {
                if (fieldName != null) {

                    declaredField = object.getClass().getDeclaredField(fieldName);
                } else {
                    throw new NoSuchFieldException(ErrorMessage.NULL_FIELD_NAME.getLabel());
                }
                declaredField.setAccessible(true);
                currentValue = declaredField.get(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ValidationException(e);
            }

            expected = String.valueOf(criterion.getExpected());
            if (criterion.isRequired()) {
                valid = checkValidation(currentValue, criterion.getOperator(), expected);
            }
        }
        return valid;
    }

    @Override
    public <T> boolean check(T object) throws ValidationException {
        if (object instanceof Criteria)
            return check((Criteria) object);
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, ValidationReport> getValidationReport(Criteria criteria) throws ValidationException {
        final Map<String, ValidationReport> validationReportMap = new LinkedHashMap<>();

        if (criteria.getObject() == null) {
            throw new ValidationException(ErrorMessage.NULL_OBJECT_MSG.getLabel());
        }
        List<Criterion> all = criteria.all();
        for (Criterion criterion : all) {
            String fieldName = criterion.getFieldName();
            Object currentValue;
            String value = String.valueOf(criterion.getExpected());
            final ValidationReport validationReport = new ValidationReport();
            validationReportMap.put(criterion.getFieldName(), validationReport);
            validationReport.setCriterion(criterion);
            try {
                Field declaredField = criteria.getObject().getClass().getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                currentValue = declaredField.get(criteria.getObject());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ValidationException(e);
            }

            boolean valid = checkValidation(currentValue, criterion.getOperator(), value);
            validationReport.setValid(valid);
        }
        return validationReportMap;
    }

    @Override
    public <T> Map<String, ValidationReport> getValidationReport(T object) throws ValidationException {
        throw new UnsupportedOperationException();
    }


}
