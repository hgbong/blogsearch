package com.hgbong.blogsearch.model.search;

import com.hgbong.blogsearch.model.common.PageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class NaverSearchEngine implements SearchEngine {

    @Override
    public PageResponse<DocumentDto> search(BlogSearchCriteria blogSearchCriteria) {
        throw new UnsupportedOperationException("not implemented yet.");
    }
}
