package org.study.models.Community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.commons.UserUtils;
import org.study.commons.validators.PostNotFoundException;
import org.study.entities.board.Post;
import org.study.models.board.BoardConfigInfoService;
import org.study.repositories.board.PostRepository;

@Service
public class PostInfoService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BoardConfigInfoService configInfoService;
    @Autowired
    private UserUtils userUtils;

    public Post get(Long id) {
        return get(id, "view");
    }
    public Post get(Long id, String location) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        // 게시판 설정 조회 + 접근 권한체크
        configInfoService.get(post.getBoard().getBId(), location);

        // 게시글 삭제 여부 체크(소프트 삭제)
        if (!userUtils.isAdmin() && post.getDeletedAt() != null) { // 삭제된 게시글
            throw new PostNotFoundException();
        }
        return post;
    }
}
