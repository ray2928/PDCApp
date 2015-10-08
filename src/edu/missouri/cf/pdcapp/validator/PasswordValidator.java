package edu.missouri.cf.pdcapp.validator;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.PasswordField;

@SuppressWarnings("serial")
public class PasswordValidator extends AbstractValidator<String> {
	 public PasswordValidator() {
         super("The password provided is not valid");
     }

     @Override
     protected boolean isValidValue(String value) {
         //
         // Password must be at least 8 characters long and contain at least
         // one number
         //
         if (value != null
                 && (value.length() < 8 || !value.matches(".*\\d.*"))) {
             return false;
         }
         return true;
     }

     @Override
     public Class<String> getType() {
         return String.class;
     }
	
}

