package com.lokendra.teamcollab.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AtLeastOneFieldValidator implements ConstraintValidator<AtLeastOneField, Object> {

    private String[] fields;

    @Override
    public void initialize(AtLeastOneField constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        List<String> emptyFields = new ArrayList<>();

        try {
            for (String fieldName : fields) {
                Field field = value.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(value);

                if (fieldValue != null) {
                    if (fieldValue instanceof String) {
                        if (!((String) fieldValue).isBlank()) {
                            return true; // Valid: non-blank string
                        }
                    } else {
                        return true; // Valid: non-null non-string value
                    }
                }
                emptyFields.add(fieldName);
            }
        } catch (Exception e) {
            return false; // Fail validation if reflection fails
        }

        // Build dynamic message with missing field names
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                "At least one of these fields must be provided: " + String.join(", ", emptyFields))
                .addConstraintViolation();

        return false;
    }
}
