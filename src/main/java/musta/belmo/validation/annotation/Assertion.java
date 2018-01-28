package musta.belmo.validation.annotation;

import musta.belmo.validation.enumeration.Operator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Assertion Annotation
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Assertion {
    /**
     * Assign this expected with the wanted operator to validate the field.
     *
     * @return Operator
     */
    Operator operator() default Operator.NOT_NULL;

    /**
     * Holds the expected to validate the object against.
     *
     * @return String
     */
    String value() default "";
}
