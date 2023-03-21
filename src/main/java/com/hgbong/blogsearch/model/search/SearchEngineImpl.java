package com.hgbong.blogsearch.model.search;

import com.hgbong.blogsearch.model.common.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class SearchEngineImpl implements SearchEngine {

    private final KakaoSearchEngine kakaoSearchEngine;
    private final NaverSearchEngine naverSearchEngine;

    private List<SearchEngine> searchEngines;


    @PostConstruct
    public void init() {
        searchEngines = new ArrayList<>();
        searchEngines.add(kakaoSearchEngine);
        searchEngines.add(naverSearchEngine);
    }

    @Override
    public PageResponse<DocumentDto> search(BlogSearchCriteria blogSearchCriteria) {
        for (SearchEngine searchEngine : searchEngines) {
            try {
                return searchEngine.search(blogSearchCriteria);
            } catch (Exception e) {
                log.warn("Search open API has problem. search engine: {}, reason: ", searchEngine.getClass(), e);
            }
        }

        // todo  에러코드 추가해서 관리 고려
        throw new RuntimeException("search engines not available.");
    }
}
