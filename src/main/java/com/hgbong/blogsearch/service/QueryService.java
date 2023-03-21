package com.hgbong.blogsearch.service;

import com.hgbong.blogsearch.entity.Query;
import com.hgbong.blogsearch.model.query.QueryDto;
import com.hgbong.blogsearch.repository.QueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class QueryService {

    private final QueryRepository queryRepository;

    private final ModelMapper modelMapper;

    /**
     * 인기검색어 10개를 조회한다.
     * @return 검색어 10개
     */
    public List<QueryDto> listFavoriteQueries() {
        List<Query> queries = queryRepository.findTop10ByOrderByCountDesc();
        return queries.stream()
            .map(q -> modelMapper.map(q, QueryDto.class))
            .collect(Collectors.toList());
    }
    
    /**
     * 특정 검색어에 대해 검색된 횟수만큼 DB에 추가한다.
     * @param query 검색어
     * @param count 검색횟수
     */
    @Async("queryThreadPoolTaskExecutor")
    public CompletableFuture<Void> addQueryCount(String query, long count) {
        Optional<Query> opQuery = queryRepository.findById(query);
        if (opQuery.isPresent()){
            opQuery.get().addCount(count);
        } else {
            queryRepository.save(new Query(query, count));
        }

        return CompletableFuture.completedFuture(null);
    }
}
