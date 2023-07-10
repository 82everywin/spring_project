package org.study.models.Community;

import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.study.controllers.admin.board.BoardSearch;
import org.study.entities.board.Post;
import org.study.entities.board.QBoard;
import org.study.entities.board.QPost;
import org.study.repositories.board.PostRepository;

/**
 * 게시글 목록 조회 서비스
 */

@Service
public class PostListService {

    @Autowired
    private PostRepository postRepository;

    /**  */
    public Page<Post> gets(BoardSearch boardSearch, String bId, String category) {
        QPost post = QPost.post;
        QBoard board = QBoard.board;
        BooleanBuilder builder = new BooleanBuilder();

        int page = boardSearch.getPage();
        int pageSize = boardSearch.getLimit();

        page = page < 1 ? 1 : page;
        pageSize = pageSize < 1 ? 20 : pageSize;

        /** 분류 검색 S */
        if (bId != null && !bId.isBlank()){
            builder.and(board.bId.eq(bId));
        }

        if (category != null && !category.isBlank()) {
            builder.and(post.category.eq(category));
        }
        /** 분류 검색 E */

        String sopt = boardSearch.getSopt();
        String skey = boardSearch.getSkey();

        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            sopt = sopt.trim();
            skey = skey.trim();

            if (sopt.equals("subject_content")) {
                builder.and(post.subject.contains(skey))
                        .or(post.content.contains(skey));
            } else if (sopt.equals("subject")) {
                builder.and(post.subject.contains(skey));
            } else if (sopt.equals("content")) {
                builder.and(post.content.contains(skey));
            } else if (sopt.equals("poster")) {
                builder.and(post.poster.contains(skey));
            }
        }

        Pageable pageable = PageRequest.of(page -1, pageSize, Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> postList = postRepository.findAll(builder, pageable);

        return postList;
    }
}