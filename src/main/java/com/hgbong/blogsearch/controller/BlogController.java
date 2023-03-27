package com.hgbong.blogsearch.controller;

import com.hgbong.blogsearch.event.QueryEvent;
import com.hgbong.blogsearch.model.common.PageResponse;
import com.hgbong.blogsearch.model.search.BlogSearchCriteria;
import com.hgbong.blogsearch.model.search.DocumentDto;
import com.hgbong.blogsearch.service.BlogSearchService;
import com.hgbong.blogsearch.scheduler.QueryScheduler;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    private final BlogSearchService blogSearchService;

    private final QueryScheduler queryScheduler;

    @ApiOperation(value = "블로그 검색", notes = "키워드를 입력하여 블로그를 페이지 구조로 조회할 수 있다.\n" +
        "sort 기능을 사용해 정확도(accuracy)순 또는 최신순(recency)으로 정렬하여 조회 가능하다. (기본정렬: accuracy)")
    @GetMapping
    public PageResponse<DocumentDto> pageBlogs(@Valid BlogSearchCriteria blogSearch) {

        publisher.publishEvent(new QueryEvent(this, blogSearch.getQuery()));

        queryScheduler.querySearched(blogSearch.getQuery());

        return blogSearchService.pageBlogs(blogSearch);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}
