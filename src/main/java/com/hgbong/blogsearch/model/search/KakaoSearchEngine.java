package com.hgbong.blogsearch.model.search;

import com.hgbong.blogsearch.model.common.PageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class KakaoSearchEngine implements SearchEngine {

    // todo  여러 데이터소스, webClient 멀티쓰레드에서 관리
    //  private WebClient webClient;

    // todo  설정 분리
    @Value(value = "${search-engine.kakao.url}")
    private String url;

    @Value(value = "${search-engine.kakao.path}")
    private String path;

    @Value(value = "${search-engine.kakao.token}")
    private String token;

    @Override
    public PageResponse<DocumentDto> search(BlogSearchCriteria blogSearchCriteria) {
        WebClient webClient = WebClient.builder()
            .baseUrl(url)
            .defaultHeaders(headers -> {
                headers.add("Authorization", "KakaoAK " + token);
            })
            .build()
            .mutate()
            .build();

        // fixme  block() 처리 수정
        Map<String, Object> response = webClient.get()
            .uri(uriBuilder ->
                uriBuilder.path(path)
                    .queryParam("query",blogSearchCriteria.getQuery())
                    .queryParam("sort",blogSearchCriteria.getSort())
                    .queryParam("page",blogSearchCriteria.getPage())
                    .queryParam("size",blogSearchCriteria.getSize())
                    .build())
            .retrieve()
            .bodyToMono(HashMap.class)
            .block();

        // todo  refactoring
        // 해당 API 에서 정의된 필드
        List<DocumentDto> documents = (List<DocumentDto>) response.get("documents");
        Map<String, Object> meta = (Map<String, Object>) response.get("meta");

        boolean is_end = (Boolean) meta.get("is_end");
        int pageable_count = (Integer) meta.get("pageable_count");
        int total_count = (Integer) meta.get("total_count");

        PageResponse<DocumentDto> pageResponse = new PageResponse<>();
        pageResponse.setContents(documents);
        pageResponse.setPage(blogSearchCriteria.getPage());
        pageResponse.setSize(blogSearchCriteria.getSize());
        pageResponse.setSort(blogSearchCriteria.getSort());
        pageResponse.setTotalCount(total_count);

        return pageResponse;
    }

}
