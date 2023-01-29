package com.example.board.repository;

import com.example.board.config.JpaConfig;
import com.example.board.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("testdb")
@DisplayName("JPA test")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                      @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select test")
    @Test
    void jpaSelectTest() {
        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        Assertions.assertThat(articles)
                .isNotNull()
                .hasSize(1);

    }

    @DisplayName("insert test")
    @Test
    void jpaInsertTest() {
        long previousCount = articleRepository.count();

        Article savedArtcle = articleRepository.saveAndFlush(Article.of("제목", "내용","해시태그"));

        Assertions.assertThat(articleRepository.count())
                .isEqualTo(previousCount+1);
    }


    @DisplayName("update test")
    @Test
    void jpaUpdateTest() {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";

        article.setHashtag(updatedHashtag);

        //when
        Article savedArticle = articleRepository.saveAndFlush(article);

        //then
        Assertions.assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag",updatedHashtag);

    }

    @DisplayName("delete test")
    @Test
    void jpaDeleteTest() {

        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousCount = articleRepository.count();
        long previousCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        //when
        articleRepository.delete(article);

        //then
        Assertions.assertThat(articleRepository.count()).isEqualTo(previousCount -1);
        Assertions.assertThat(articleCommentRepository.count()).isEqualTo(previousCommentCount - deletedCommentsSize);

    }
}