package com.hgbong.blogsearch.scheduler;

import com.hgbong.blogsearch.service.QueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class QueryScheduler {

    // 정해진 시간동안 검색된 검색어와 횟수를 저장하는 Map
    private final ConcurrentHashMap<String, Long> queryCountMap = new ConcurrentHashMap();

    private final QueryService queryService;

    /**
     * 특정 검색어가 검색된 경우 해당 검색어 카운트를 1 증가한다.
     *
     * @param query 검색어
     */
    public void querySearched(String query) {
        queryCountMap.put(query, queryCountMap.getOrDefault(query, 0l) + 1);
    }

    /**
     * 정해진 시간마다, 저장된 검색어 카운트를 DB에 저장한다.
     */
    @Scheduled(cron = "${query-cron:*/1 * * * * *}")
    public void saveQueryCount() {
        Set<Map.Entry<String, Long>> entries = queryCountMap.entrySet();
        for (Map.Entry<String, Long> entry : entries) {
            queryService.addQueryCount(entry.getKey(), entry.getValue())
                .exceptionally(throwable -> {
                    log.warn("insert or update query has problem : ", throwable);
                    return null;
                });
            queryCountMap.remove(entry.getKey());
        }
    }
}
