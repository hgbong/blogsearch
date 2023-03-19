package com.hgbong.blogsearch.controller;

import com.hgbong.blogsearch.model.query.QueryDto;
import com.hgbong.blogsearch.service.QueryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/querys")
@RequiredArgsConstructor
public class QueryController {

    private final QueryService queryService;

    @ApiOperation(value = "인기 검색어 목록 10개 조회", notes = "검색 횟수가 많은 순서대로 검색어 10개를 조회한다.")
    @GetMapping("/favorite")
    public List<QueryDto> listFavoriteQueries() {
        return queryService.listFavoriteQueries();
    }
}
