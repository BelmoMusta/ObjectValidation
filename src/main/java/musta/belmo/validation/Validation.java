package musta.belmo.validation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {
    boolean required() default true;
    Assertion assertion() default @Assertion();

}
