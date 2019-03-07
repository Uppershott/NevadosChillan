package com.nevados.chillan.domain.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidRunValidator implements ConstraintValidator<ValidRun, String> {
	@Override
	public boolean isValid(String run, ConstraintValidatorContext arg1) {
		if(!run.matches("\\d{1,2}(\\.|\\s| |)\\d{3}(\\.|\\s| |)\\d{3}(\\.|-| |)(\\d{1}|[kK])")) return false;
		return lunhAlgorithm(run);
	}
	
	public boolean lunhAlgorithm(String run) {
		boolean validacion = false;
		try {
			run =  run.toUpperCase();
			run = run.replace(".", "");
			run = run.replace("-", "");
			int rutAux = Integer.parseInt(run.substring(0, run.length() - 1));
			 
			char dv = run.charAt(run.length() - 1);
			 
			int m = 0, s = 1;
			for (; rutAux != 0; rutAux /= 10) {
				s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
			}
			if (dv == (char) (s != 0 ? s + 47 : 75)) {
				validacion = true;
			}
		} catch (Exception e) {
			System.out.println("Excepción al validar rut.");
		}
		return validacion;
	}
}
