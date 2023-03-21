package com.hgbong.blogsearch.annotation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BlogSortCheckValidator implements ConstraintValidator<BlogSortCheck, String> {

    private static final String[] AVAILABLE_SORT_FIELDS = new String[]{"accuracy", "recency"};

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 값이 없을 경우 기본 정렬 적용
        if (!StringUtils.hasLength(value)) {
            return true;
        }

        for (String availableSortField : AVAILABLE_SORT_FIELDS) {
            if (availableSortField.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
