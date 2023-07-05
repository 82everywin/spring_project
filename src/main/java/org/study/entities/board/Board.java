package org.study.entities.board;


import jakarta.persistence.*;
import lombok.*;
import org.study.commons.constants.UserRole;
import org.study.commons.constants.board.AfterWriteTarget;
import org.study.commons.constants.board.SkinType;
import org.study.entities.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 설정
 *
 */
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board extends BaseEntity {
    @Id
    @Column(length=20, nullable = false)
    private String bId; // 게시판 아이디

    @Column(length=60, nullable = false)
    private String boardNm; // 게시판명

    private boolean isUse; // 사용 여부

    @Column(length = 100, nullable = false)
    private int rowsPerPage = 20; // 1페이지 게시글 수

    private boolean useViewList; // 게시글 하단에 목록 노출

    @Lob
    private String category; // 분류

    // 목록 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private UserRole listAccessRole = UserRole.ALL;

    // 글보기 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private UserRole viewAccessRole = UserRole.ALL;

    // 글쓰기 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private UserRole writeAccessRole = UserRole.ALL;

    // 답글 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private UserRole replyAccessRole = UserRole.ALL;

    // 댓글 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable=false)
    private UserRole commentAccessRole = UserRole.ALL;

    private boolean useEditor; // 에디터 사용여부
    private boolean useFileAttach; // 파일 첨부 사용여부
    private boolean useImageAttach; // 이미지 첨부(에디터 추가) 사용 여부

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private AfterWriteTarget afterWriteTarget = AfterWriteTarget.VIEW; // 글 작성 후 이동 경로

    private boolean useComment; // 댓글 사용여부
    @Enumerated(EnumType.STRING)
    @Column(length=60)
    private SkinType skin = SkinType.DEFAULT; // 스킨명
    private boolean isReview; // 후기 게시판 여부

    @OneToMany(mappedBy = "board")
    @ToString.Exclude
    private List<Post> postList = new ArrayList<>();

    /**
     * 게시판 분류 목록 ( category )
     * @return
     */
    public String[] getCategories() {

        if (category == null) {
            return null;
        }
        // 리눅스 사용 시 \\r로 제거 후 \\n기준으로 나눔
        String[] categories = category.replaceAll("\\r","").trim().split("\\n");
        return categories;
    }
}
