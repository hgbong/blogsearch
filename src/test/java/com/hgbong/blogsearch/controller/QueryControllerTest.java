package com.hgbong.blogsearch.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgbong.blogsearch.entity.Query;
import com.hgbong.blogsearch.repository.QueryRepository;
import com.hgbong.blogsearch.util.QueryStorage;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class QueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private QueryStorage queryStorage;

    @Autowired
    private QueryRepository queryRepository;


    // fixme  해당 내용 수정시까지 테스트 보류!!  - 스케줄러 관련 테스트 작성
    //  AS-IS Async job을 profile="test" 에서 동작하지 않도록 수정 -> Async job을 main 쓰레드에서 실행 -> rollback O
    //  TO-BE scheduler 에서 주기적으로 DB 요청 -> 별도 쓰레드에서 tx 관리 -> rollback X

    /*
    @Test
    void listFavoriteQueries_queryNotExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/querys/favorite"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.empty()));
    }

    @Test
    void listFavoriteQueries_queryLessThan10() throws Exception {
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(MockMvcRequestBuilders.get("/blogs")
                .queryParam("query","test" + i));
        }

        await().atMost(Duration.ofSeconds(30))
                .untilAsserted(() -> verify(queryStorage, atLeast(2)).saveQueryCount());

        mockMvc.perform(MockMvcRequestBuilders.get("/querys/favorite"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    @DisplayName("i (i>10)개의 검색어에 대해, 각각 i번 호출하고 결과의 처음과 마지막 원소 확인")
    void listFavoriteQueries_queryMoreThan10() throws Exception {
        int queryCnt = 11;
        for (int i = 1; i <= queryCnt; i++) {
            for (int j = 0; j < i; j++) {
                mockMvc.perform(MockMvcRequestBuilders.get("/blogs")
                    .queryParam("query","test" + i));
            }
        }

        // fixme  위의 TC와 동일한 이슈

        String response = mockMvc.perform(MockMvcRequestBuilders.get("/querys/favorite"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn().getResponse().getContentAsString();

        JsonNode jsonNode = new ObjectMapper().readTree(response);

        JsonNode first = jsonNode.get(0);
        Assertions.assertEquals(first.path("query").asText(), "test" + queryCnt);
        Assertions.assertEquals(first.path("count").numberValue(), queryCnt);

        JsonNode last = jsonNode.get(9);
        Assertions.assertEquals(last.path("query").asText(), "test" + (queryCnt - 9));
        Assertions.assertEquals(last.path("count").numberValue(), queryCnt - 9);
    }
    */
}