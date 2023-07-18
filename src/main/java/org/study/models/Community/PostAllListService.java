package org.study.models.Community;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.study.controllers.admin.community.CommunitySearch;
import org.study.entities.board.Board;
import org.study.entities.board.Post;
import org.study.entities.board.QPost;
import org.study.repositories.board.PostRepository;
import com.querydsl.core.BooleanBuilder;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class PostAllListService {
    private final PostRepository postRepository;

    public Page<Post> gets(CommunitySearch communitySearch) {
        QPost post = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();

        int page = communitySearch.getPage();
        int limit = communitySearch.getLimit();

        page = page < 1 ? 1 : page;
        limit = limit < 1 ? 20 : limit;

        /** 검색 조건 처리 S */
        String skey = communitySearch.getSkey();

        if (skey != null && !skey.isBlank()) {
            skey = skey.trim();
            builder.and(post.subject.contains(skey))
                    .or(post.content.contains(skey))
                    .or(post.poster.contains(skey));
        }
        /** 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<Post> data = postRepository.findAll(builder, pageable);

        return data;
    }
}
