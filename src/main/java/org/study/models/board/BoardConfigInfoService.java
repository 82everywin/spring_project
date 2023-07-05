package org.study.models.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.commons.UserUtils;
import org.study.commons.constants.UserRole;
import org.study.commons.validators.BoardNotFoundException;
import org.study.entities.board.Board;
import org.study.repositories.board.BoardRepository;

@Service
public class BoardConfigInfoService { // 조회

    @Autowired
    private BoardRepository repository;
    @Autowired
    private UserUtils userUtils;

    public Board get(String bId, String location) { // 프론트, 접근 권한 체크
        return get(bId, false, location);
    }

    /**
     * 게시판 설정 조회
     *
     * @param bId
     * @param isAdmin : true - 권한 체크 X
     *                : false - 권한 체크, location으로 목록, 보기, 글쓰기, 답글, 댓글
     *
     * @param location : 기능 위치(list, view, write, reply, comment)
     *
     * @return
     */
    public Board get(String bId, boolean isAdmin, String location) {

        Board board = repository.findById(bId).orElseThrow(BoardNotFoundException::new);

        if (!isAdmin) { // 권한 체크
            accessCheck(board, location);
        }

        return board;
    }
    public Board get(String bId, boolean isAdmin) {
        return get(bId, isAdmin, null);
    }

    /**
     * 접근 권한 체크
     *
     * @param board
     */
    private void accessCheck(Board board, String location) {

        /**
         * use - false : 모든 항목 접근 불가, 단 관리자만 가능
         */
        if (!board.isUse() && !userUtils.isAdmin()) {
            throw new BoardNotAllowAccessException();
        }

        UserRole role = UserRole.ALL;
        if (location.equals("list")) { // 목록 접근 권한
            role = board.getListAccessRole();

        } else if (location.equals("view")) { // 게시글 접근 권한
            role = board.getViewAccessRole();

        } else if (location.equals("write")) { // 글쓰기 권한
            role = board.getWriteAccessRole();

        } else if (location.equals("reply")) { // 답글 권한
            role = board.getReplyAccessRole();

        } else if (location.equals("comment")) { // 댓글 권한
            role = board.getCommentAccessRole();

        }

        if ((role == UserRole.USER && !userUtils.isLogin())
                || (role == UserRole.ADMIN && !userUtils.isAdmin())) {
            throw new BoardNotAllowAccessException();
        }


    }
}
