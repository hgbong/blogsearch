package com.hgbong.blogsearch.model.search;

import com.hgbong.blogsearch.model.common.PageResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class NaverSearchEngine implements SearchEngine {

    @Value(value = "${search-engine.naver.url}")
    private String url;

    @Value(value = "${search-engine.naver.path}")
    private String path;

    @Value(value = "${search-engine.naver.clientId}")
    private String clientId;

    @Value(value = "${search-engine.naver.clientSecret}")
    private String clientSecret;


    @Override
    public PageResponse<DocumentDto> search(BlogSearchCriteria blogSearchCriteria) {
        WebClient webClient = WebClient.builder()
            .baseUrl(url)
            .defaultHeaders(headers -> {
                headers.add("X-Naver-Client-Id", clientId);
                headers.add("X-Naver-Client-Secret", clientSecret);
            })
            .build();

        // fixme  block() 처리 수정
        NaverSearchResponse response = webClient.get()
            .uri(uriBuilder ->
                uriBuilder.path(path)
                    .queryParam("query",blogSearchCriteria.getQuery())
                    .queryParam("display",blogSearchCriteria.getSize())
                    .queryParam("start",blogSearchCriteria.getPage())
                    .queryParam("sort",blogSearchCriteria.getSort()) // fixme !!!! 필수!!!
                    .build())
            .retrieve()
            .bodyToMono(NaverSearchResponse.class)
            .block();

        List<Item> items = response.getItems();


        PageResponse<DocumentDto> pageResponse = new PageResponse<>();
        pageResponse.setContents(response.getItems().stream().map(item -> {
            DocumentDto build = DocumentDto.builder()
                .title(item.getTitle())
                .url(item.getLink())
                .contents(item.getDescription())
                //.datetime(new SimpleDateFormat("yyyy-MM-dd").parse(item.getPostdate()))
                .build();

            return build;
        }).collect(Collectors.toList()));

        pageResponse.setPage(blogSearchCriteria.getPage());
        pageResponse.setSize(blogSearchCriteria.getSize());
        pageResponse.setSort(blogSearchCriteria.getSort());
        pageResponse.setTotalCount(response.getTotal());

        return pageResponse;
    }

    @Setter
    @Getter
    static class NaverSearchResponse {
        private int total;
        private int start;
        private int display;
        private List<Item> items;
    }

    @Setter
    @Getter
    static class Item {
        private String title;
        private String link;
        private String description;
        private String bloggerlink;
        private String postdate;
    }
}
