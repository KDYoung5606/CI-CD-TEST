package umc.study.validation.anotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import umc.study.validation.validator.CategoriesExistValidator;

import java.lang.annotation.*;

@Documented // 사용자정의 에노테이션
@Constraint(validatedBy = CategoriesExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER}) // 에노테이션 작동범위
@Retention(RetentionPolicy.RUNTIME) // 생명주기
public @interface ExistCategories {
    String message() default "해당 카테고리가 존재하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
