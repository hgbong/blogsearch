package com.hgbong.blogsearch.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
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
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].query", Matchers.containsString("test")));
    }


    @Test
    @DisplayName("15개의 검색어에 대해, 각각 1~15번 호출하고 결과의 처음과 마지막 원소 확인")
    void listFavoriteQueries_queryMoreThan10() throws Exception {
        for (int i = 1; i <= 15; i++) {
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
        Assertions.assertEquals(first.path("query").asText(), "test15");
        Assertions.assertEquals(first.path("count").numberValue(), 15);

        JsonNode last = jsonNode.get(9);
        Assertions.assertEquals(last.path("query").asText(), "test6");
        Assertions.assertEquals(last.path("count").numberValue(), 6);
    }
}