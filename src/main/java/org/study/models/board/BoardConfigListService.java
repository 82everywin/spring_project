package org.study.models.board;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.study.controllers.admin.board.BoardSearch;
import org.study.entities.board.Board;
import org.study.entities.board.QBoard;
import org.study.repositories.board.BoardRepository;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class BoardConfigListService {

    private  final BoardRepository repository;

    public Page<Board> gets(BoardSearch boardSearch) {
        QBoard board = QBoard.board;
        BooleanBuilder builder = new BooleanBuilder();

        int page = boardSearch.getPage();
        int limit = boardSearch.getLimit();

        page = page < 1 ? 1 : page;
        limit = limit < 1 ? 20 : limit;

        /** 검색 조건 처리 S */
        String sopt = boardSearch.getSopt();
        String skey = boardSearch.getSkey();
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            sopt = sopt.trim(); // 공백 제거
            skey = skey.trim(); // 공백 제거

            if (sopt.equals("all")) {
                builder.and(board.bId.contains(skey))
                        .or(board.boardNm.contains(skey));
            } else if (sopt.equals("bId")) {
                builder.and(board.bId.contains(skey));
            } else if (sopt.equals("boardNm")) {
                builder.and(board.boardNm.contains(skey));
            }
        }
        /** 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<Board> data = repository.findAll(builder, pageable);

        return data;
    }
}
