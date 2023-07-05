package org.study.models.Community;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.commons.UserUtils;
import org.study.commons.validators.PostNotFoundException;
import org.study.controllers.community.PostForm;
import org.study.entities.board.Board;
import org.study.entities.board.Post;
import org.study.models.board.BoardConfigInfoService;
import org.study.repositories.board.PostRepository;

@Service
public class PostSaveService {
    @Autowired
    private BoardConfigInfoService configInfoService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserUtils userUtil;
    @Autowired
    private PostRepository repository;

    public void save(PostForm postForm) {
        Long id = postForm.getId();
        Board board = configInfoService.get(postForm.getBId(), id == null ? "write":"update");

        Post post = null;
        if (id == null) { // 게시글 추가
            String ip = request.getRemoteAddr();
            String ua = request.getHeader("User-Agent"); // 모바일인지 PC인지 ( 현재 사용하는 브라우저 정보 )
            post = Post.builder()
                    .gid(postForm.getGid())
                    .board(board)
                    .category(postForm.getCategory())
                    .poster(postForm.getPoster())
                    .subject(postForm.getSubject())
                    .content(postForm.getContent())
                    .ip(ip)
                    .ua(ua)
                    .build();
            if (userUtil.isLogin()) { // 로그인 시 - 회원 데이터
                post.setUser(userUtil.getEntity());
            } else {
                // 게시글 작성 금지
            }
        } else { // 게시글 수정
            post = repository.findById(postForm.getId()).orElseThrow(PostNotFoundException::new);
            post.setPoster(postForm.getPoster());
            post.setSubject(postForm.getSubject());
            post.setContent(postForm.getContent());
            post.setCategory(postForm.getCategory());
            }
        post = repository.saveAndFlush(post);
        post.setId(post.getId());
    }
}
