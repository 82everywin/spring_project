package org.study.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes={
        @Index(name="idx_user_createdAt", columnList = "createdAt DESC")
})
public class CommunityBoard extends BaseEntity {

    // 게시판 목록 & 등록 Entity
    @Id
    @Column(length = 40, nullable = false, unique = true)
    private String boardId; // 게시판ID

    @Column(length = 40, nullable = false, unique = true)
    private String boardNm; // 게시판명

    // 생성일자는 베이스 엔티티에서 사용

    private String manager; // 처리자

    @Column(name="is_use")
    private boolean use; // 사용여부

    @Column(nullable = false)
    private int noOfRows; // 페이지 당 게시글 수

    private boolean editor; // 에디터

    @Column(length = 40)
    private boolean file; // 파일첨부

    private boolean reply; // 댓글사용

    private boolean movement; // 글 작성 후 이동

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;
}
