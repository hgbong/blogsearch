package com.hgbong.blogsearch.model.search;

import io.swagger.annotations.ApiParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BlogSearchCriteria {
    @ApiParam(value = "질의어", example = "test")
    @NotNull(message = "query should not be null")
    private String query;

    @ApiParam(value = "페이지", example = "1")
    @Size(min = 1, max = 50, message = "page should be 1 to 50")
    private int page = 1;

    @ApiParam(value = "사이즈", example = "10")
    @Size(min = 1, max = 50, message = "size should be 1 to 50")
    private int size = 10;

    // todo  validate BlogSortType
    @ApiParam(value = "정렬", example = "recency")
    private String sort = BlogSortType.ACCURACY;
}
