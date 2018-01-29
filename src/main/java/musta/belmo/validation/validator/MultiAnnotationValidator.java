package musta.belmo.validation.validator;

import musta.belmo.validation.annotation.Equals;
import musta.belmo.validation.annotation.Length;
import musta.belmo.validation.annotation.NotNull;
import musta.belmo.validation.annotation.Regex;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;


/**
 * CriteriaValidator class to perform validation over objects.
 *
 * @author Belmokhtar
 */
public class MultiAnnotationValidator extends AbstractValidator {

    private static final List<Class<? extends Annotation>> ANNOTATION_CLASSES = Arrays.asList(Equals.class, Length.class, NotNull.class, Regex.class);

    private static MultiAnnotationValidator validator;

    public static MultiAnnotationValidator getInstance() {
        if (validator == null) {
            validator = new MultiAnnotationValidator();
        }
        return validator;
    }

}
