package org.study.repositories.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.study.entities.board.Post;
import org.study.entities.board.QPost;

public interface PostRepository extends JpaRepository<Post, Long>, QuerydslPredicateExecutor {

    /** 게시글 등록 여부 체크 */
    default boolean exists(String gid) {
        QPost post = QPost.post;
        return exists(post.gid.eq(gid));
    }

    /** 카테고리명이 질문과 답변일 때 해당 게시글 조회 */
}
