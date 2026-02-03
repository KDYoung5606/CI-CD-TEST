package umc.study.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import umc.study.validation.anotation.ExistStores;

public class StoresExistValidator implements ConstraintValidator<ExistStores, Long> {

    @Override
    public void initialize(ExistStores constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return true;
    }
}
