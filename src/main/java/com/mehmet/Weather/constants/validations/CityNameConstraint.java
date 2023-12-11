package com.mehmet.Weather.constants.validations;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {CityNameValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD,  ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CityNameConstraint {
    String message() default "Invalid City Name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
