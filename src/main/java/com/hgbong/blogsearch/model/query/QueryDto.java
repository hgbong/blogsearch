package com.hgbong.blogsearch.model.query;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"query", "count"})
public class QueryDto {
    @ApiModelProperty(value = "검색어", example = "test")
    private String query;

    @ApiModelProperty(value = "검색 수", example = "100")
    private long count;
}
