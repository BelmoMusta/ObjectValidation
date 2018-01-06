package musta.belmo.validation;

import java.lang.annotation.*;

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
