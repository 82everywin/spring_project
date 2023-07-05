package org.study.repositories.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.study.entities.board.BoardView;
import org.study.entities.board.QBoardView;

public interface BoardViewRepository extends JpaRepository<BoardView, Long>, QuerydslPredicateExecutor {
    default long getHit(Long id) {
        QBoardView boardView = QBoardView.boardView;
        return count(boardView.idBoardData.eq(id));
    }
}
