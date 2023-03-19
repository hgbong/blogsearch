package com.hgbong.blogsearch.service;

import com.hgbong.blogsearch.model.common.PageResponse;
import com.hgbong.blogsearch.model.search.BlogSearchCriteria;
import com.hgbong.blogsearch.model.search.DocumentDto;
import com.hgbong.blogsearch.model.search.DocumentPageDto;
import com.hgbong.blogsearch.model.search.SearchEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogSearchService {

    private final SearchEngine searchEngine;


    public PageResponse<DocumentDto> pageBlogs(BlogSearchCriteria blogSearchCriteria) {

        // fixme  multi thread
        PageResponse<DocumentDto> search = searchEngine.search(blogSearchCriteria);
        return search;
    }
}
