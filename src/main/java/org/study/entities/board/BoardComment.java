package org.study.entities.board;

import jakarta.persistence.*;
import lombok.*;
import org.study.entities.BaseEntity;
import org.study.entities.User;

/**
 * 댓글
 *
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; // 게시글 번호

    @Column(length=45, nullable = false)
    private String gid; // 그룹 아이디

    @Column(length=45, nullable = false)
    private String commenter; // 댓글 작성자

    @Column(length=65)
    private String guestPw; // 비회원 비밀번호

    @Lob
    @Column(nullable = false)
    private String content; // 댓글 내용

    private Long idParent; // 부모 댓글 번호

    @Column(length=30)
    private String ipAddr; // IP 주소

    private String userAgent; // 브라우저 정보(UserAgent)

    private boolean useEditor; // 에디터 사용여부

    private Integer depth; // 댓글 작성시 들여쓰기 정도

    private Long listOrder; // 댓글 1차 정렬 - 내림차순
    private Long listOrder2; // 댓글 2차 정렬  - 내림차순

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idBoardData")
    @ToString.Exclude
    private Post post; // 원 게시글

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userNo")
    private User user;
}
