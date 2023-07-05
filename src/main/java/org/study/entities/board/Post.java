package org.study.entities.board;

import jakarta.persistence.*;
import lombok.*;
import org.study.entities.BaseEntity;
import org.study.entities.User;

import java.util.UUID;

@Entity @Data
@Builder @NoArgsConstructor @AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_post_category", columnList = "category DESC"), // 카테고리 최신 기준으로 정렬
        @Index(name = "idx_post_createdAt", columnList = "createdAt DESC")
})
public class Post extends BaseEntity {

    @Id @GeneratedValue
    private Long id; // 게시글 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bId")
    private Board board; // 게시판 아이디

    @Column(length = 65, nullable = false)
    private String gid = UUID.randomUUID().toString(); // 그룹 아이디 ( 파일 전송시 필요 )

    @Column(length = 40, nullable = false)
    private String poster; // 작성자

    @Column(length = 60)
    private String category; // 게시판 분류

    @Column(nullable = false)
    private String subject; // 제목

    @Lob
    @Column(nullable = false)
    private String content; // 내용

    private int hit; // 조회수

    @Column(length = 125)
    private String ua; // User-Agent : 브라우저 정보

    @Column(length = 20)
    private String ip; // 작성자 IP 주소

    private int commentCnt; // 댓글 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userNo")
    private User user; // 작성 회원

}
