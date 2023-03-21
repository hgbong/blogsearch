package com.hgbong.blogsearch.controller;

import com.hgbong.blogsearch.model.common.PageResponse;
import com.hgbong.blogsearch.model.search.BlogSearchCriteria;
import com.hgbong.blogsearch.model.search.DocumentDto;
import com.hgbong.blogsearch.service.BlogSearchService;
import com.hgbong.blogsearch.service.QueryService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogSearchService blogSearchService;

    private final QueryService queryService;

    @ApiOperation(value = "블로그 검색", notes = "키워드를 입력하여 블로그를 페이지 구조로 조회할 수 있다.\n" +
        "sort 기능을 사용해 정확도(accuracy)순 또는 최신순(recency)으로 정렬하여 조회 가능하다. (기본정렬: accuracy)")
    @GetMapping
    public PageResponse<DocumentDto> pageBlogs(@Valid BlogSearchCriteria blogSearch) {

        // todo 검색 기능과 분리 - insert 실패해도 API 동작 , 조회 성능문제 되지 않도록 처리 등

        CompletableFuture<Void> queryCountAsync = queryService.increaseQueryCountAsync(blogSearch.getQuery());
        queryCountAsync.exceptionally(throwable -> {
            log.warn("insert or update query has problem : ", throwable);
            return null;
        });

        return blogSearchService.pageBlogs(blogSearch);
    }
}
