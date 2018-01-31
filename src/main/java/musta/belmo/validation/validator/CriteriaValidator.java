package musta.belmo.validation.validator;

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

}
