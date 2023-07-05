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
     * @param id 게시글 번호
     * @param isComplete : false - 소프트 삭제, true - 완전 삭제
     */
    public void delete(Long id, boolean isComplete) {
        Post post = repository.findById(id).orElseThrow(PostNotFoundException::new);

        if (isComplete) { // 완전 삭제
            repository.delete(post);
        } else { // 소프트 삭제
            post.setDeletedAt(LocalDateTime.now());
        }

        repository.flush();
    }

    public void delete(Long id) {
        delete(id, false);
    }
}
