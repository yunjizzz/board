package com.example.board.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 클래스 설명
 *
 * @author : yunji
 * @version : 1.0.0
 * @date : 2023/01/31
 */
@DisplayName("View controller - 게시글")
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;
    public ArticleControllerTest(@Autowired MockMvc mvc){
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 게시글 리스트 - 정상 호출")
    @Test
    void getArticles() throws Exception {
        //Given

        // When & Then
        mvc.perform(MockMvcRequestBuilders.get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));
    }

    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    @Disabled("구현중")
    void getArticleById() throws Exception {
        //Given

        // When & Then
        mvc.perform(MockMvcRequestBuilders.get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/datail"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("articleComments"));
    }

    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    @Disabled("구현중")
    void getArticleSearch() throws Exception {
        //Given

        // When & Then
        mvc.perform(MockMvcRequestBuilders.get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles"))
                .andExpect(view().name("articles/search"));
    }


    @DisplayName("[view][GET] 게시글 해시태그 검색 전용 페이지 - 정상 호출")
    @Test
    @Disabled("구현중")
    void getArticleHashTagSearch() throws Exception {
        //Given

        // When & Then
        mvc.perform(MockMvcRequestBuilders.get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles"))
                .andExpect(view().name("articles/search-hashtag"));
    }
}