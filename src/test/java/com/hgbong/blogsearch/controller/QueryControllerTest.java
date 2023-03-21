package com.hgbong.blogsearch.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgbong.blogsearch.entity.Query;
import com.hgbong.blogsearch.repository.QueryRepository;
import io.swagger.annotations.Authorization;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.system.SystemProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class QueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
}