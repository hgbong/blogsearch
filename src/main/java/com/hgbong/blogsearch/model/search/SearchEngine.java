package com.hgbong.blogsearch.model.search;

import com.hgbong.blogsearch.model.common.PageResponse;

public interface SearchEngine {
    PageResponse<DocumentDto> search(BlogSearchCriteria blogSearchCriteria);
}
