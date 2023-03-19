package com.hgbong.blogsearch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BlogSearchControllerTest {

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