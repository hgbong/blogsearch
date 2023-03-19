package com.hgbong.blogsearch.service;

import com.hgbong.blogsearch.entity.Query;
import com.hgbong.blogsearch.model.common.PageResponse;
import com.hgbong.blogsearch.model.query.QueryDto;
import com.hgbong.blogsearch.model.search.BlogSearchCriteria;
import com.hgbong.blogsearch.model.search.DocumentDto;
import com.hgbong.blogsearch.model.search.SearchEngine;
import com.hgbong.blogsearch.repository.QueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class QueryService {

    private final QueryRepository queryRepository;

    private final ModelMapper modelMapper;


    /**
     * 한번 이상 검색된 문자열은 카운트 1 증가시키고,
     * 처음 검색된 문자열은 새로 추가
     * @param queryStr
     */
    public void addQueryCount(String queryStr) {
        // todo  동시성 이슈 처리

        Optional<Query> opQuery = queryRepository.findById(queryStr);
        if (opQuery.isPresent()){
            Query query = opQuery.get();
            query.addCount();
            log.info("query count added. str: {}, count: {}", queryStr, query.getCount());
        } else {
            Query query = new Query(queryStr);
            queryRepository.save(query);
            log.info("query added. str: {}", queryStr);
        }
    }


    public List<QueryDto> listFavoriteQueries() {
        List<Query> queries = queryRepository.findTop10ByOrderByCountDesc();
        return queries.stream()
            .map(q -> modelMapper.map(q, QueryDto.class))
            .collect(Collectors.toList());
    }
}
