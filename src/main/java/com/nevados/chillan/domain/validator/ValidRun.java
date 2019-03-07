package com.nevados.chillan.domain.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})

@Retention(RetentionPolicy.RUNTIME)
//clase que se va a hacer cargo de la validación
@Constraint(validatedBy = ValidRunValidator.class)

public @interface ValidRun {
	String message() default "RUN no Válido";
	Class<? extends Payload>[] payload() default{};
	Class<?>[] groups() default{}; 
}
