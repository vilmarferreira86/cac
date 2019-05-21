package br.org.cac.validation;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.expression.ParseException;



public class CustomDateValidator implements
  ConstraintValidator<CustomDateConstraint, Date> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public void initialize(CustomDateConstraint customDate) {
    }

    @Override
    public boolean isValid(Date customDateField,
      ConstraintValidatorContext cxt) {
          SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        try
        {
            sdf.setLenient(false);
			System.out.println(customDateField.toString());
            return true;
        }
        catch (ParseException e)
        {
            return false;
        }
    }

}