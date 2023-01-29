package com.example.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes={
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title; // 제목
    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 내용
    @Setter
    @Column(nullable = false)
    private String hashtag; // 해시태그


    @ToString.Exclude // 순환참조 문제 때문에 제외
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // 실무에서는 너무 강한 결합때문에 cascade 사용 X
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    @CreatedDate @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable = false, length = 100)
    private String createdBy; // 생성자
    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자

    protected Article() {} // 기본 생성자가 필요하지만, 외부에서 사용하지 못하도록 막음

    public Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    public static Article of(String title, String content, String hashtag) {
        return new Article(title,content,hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
