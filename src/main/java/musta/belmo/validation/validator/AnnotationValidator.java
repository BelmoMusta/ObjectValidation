package musta.belmo.validation.validator;

import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.exception.ValidationException;

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
        Criteria criteria = super.createCriteria(object);
        return super.check(criteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Map<String, ValidationReport> getValidationReport(T object) throws ValidationException {
        Criteria criteria = super.createCriteria(object);
        return super.getValidationReport(criteria);
    }
}
