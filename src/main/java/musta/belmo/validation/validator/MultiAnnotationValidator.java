package musta.belmo.validation.validator;



/**
 * CriteriaValidator class to perform validation over objects.
 *
 * @author Belmokhtar
 */
public class MultiAnnotationValidator extends AnnotationValidator {

    private static MultiAnnotationValidator validator;

    public static MultiAnnotationValidator getInstance() {
        if (validator == null) {
            validator = new MultiAnnotationValidator();
        }
        return validator;
    }

}
