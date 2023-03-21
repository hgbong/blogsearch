package com.hgbong.blogsearch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Executor;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listBlogs_queryNotExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/blogs"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void listBlogs_queryExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/blogs")
                .queryParam("query","test"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.contents").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").isNumber());
    }

    @Test
    void listBlogs_queryWithSort() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/blogs")
                .queryParam("query","test")
                .queryParam("sort", "recency"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.contents").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").isNumber());
    }

    @Test
    void listBlogs_queryWithPaging() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/blogs")
                .queryParam("query","test")
                .queryParam("page", "10")
                .queryParam("size", "15"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.contents").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.page").value(10))
            .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(15));
    }
}