package musta.belmo.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validation annotation
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {
    /**
     * Assign this value when annotating the wanted field.
     *
     * @return true of false
     */
    boolean required() default true;

    /**
     * Assign this assertion to indicate the wanted predicate to validate the annotated field with.
     *
     * @return Assertion
     */
    Assertion assertion() default @Assertion();

}
