package io.github.belmomusta.validation.annotation;

import io.github.belmomusta.validation.enumeration.Operator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Validation Annotation
 *
 * @since 0.0.0.SNAPSHOT
 * @author default author
 * @version 0.0.0
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {

    /**
     * Assign this expected with the wanted operator to validate the field.
     *
     * @return Operator
     */
    Operator operator() default Operator.NONE;

    /**
     * Holds the expected value to validate the object against.
     *
     * @return String
     */
    String value() default "";
}
