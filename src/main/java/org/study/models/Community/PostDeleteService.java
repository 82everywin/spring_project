package org.study.models.Community;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.study.commons.validators.PostNotFoundException;
import org.study.entities.board.Post;
import org.study.repositories.board.PostRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostDeleteService {
    private final PostRepository repository;

    /**
     * 게시글 삭제
     *
     * @param id         게시글 번호
     */
    public void delete(Long id) {
        Post post = repository.findById(id).orElseThrow(PostNotFoundException::new);
        repository.delete(post);

        repository.flush();
    }
}
