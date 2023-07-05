package org.study.models.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.study.commons.validators.BoardNotFoundException;
import org.study.entities.board.Board;
import org.study.repositories.board.BoardRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardConfigDeleteService {
    private final BoardRepository repository;

    /**
     * 게시판 삭제
     *
     * @param bId 게시판 아이디
     * @param isComplete : false - 소프트 삭제, true - 완전 삭제
     */
    public void delete(String bId, boolean isComplete) {
        Board board = repository.findById(bId).orElseThrow(BoardNotFoundException::new);

        if (isComplete) { // 완전 삭제
            repository.delete(board);
        } else { // 소프트 삭제
            board.setDeletedAt(LocalDateTime.now());
        }
        repository.flush();
    }

    public void delete(String bId) {
        delete(bId, false);
    }
}