package com.hgbong.blogsearch.model.search;

import com.hgbong.blogsearch.annotation.BlogSortCheck;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class BlogSearchCriteria {
    @ApiParam(value = "질의어", example = "test")
    @NotBlank // todo  message i18n
    private String query;

    @ApiParam(value = "페이지", example = "1")
    @Min(1)
    @Max(50)
    private int page = 1;

    @ApiParam(value = "사이즈", example = "10")
    @Min(1)
    @Max(50)
    private int size = 10;

    @BlogSortCheck
    @ApiParam(value = "정렬", example = "recency")
    private String sort = BlogSortType.ACCURACY;
}
