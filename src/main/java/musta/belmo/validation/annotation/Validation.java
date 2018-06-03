package musta.belmo.validation.annotation;

import musta.belmo.validation.enumeration.Operator;

import java.lang.annotation.*;

/**
 * The Validation Annotation
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {

    /**
     * Assign this expected when annotating the wanted field.
     *
     * @return true of false
     */
    boolean required() default true;

    /**
     * Assign this expected with the wanted operator to validate the field.
     *
     * @return Operator
     */
    Operator operator() default Operator.NOT_NULL;

    /**
     * Holds the expected value to validate the object against.
     *
     * @return String
     */
    String value() default "";
}
