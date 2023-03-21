package com.hgbong.blogsearch.annotation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BlogSortCheckValidator.class)
public @interface BlogSortCheck {
    String message() default "입력된 정렬값이 유효하지 않습니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
