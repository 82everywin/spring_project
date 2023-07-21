package org.study.models.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.study.commons.constants.UserRole;
import org.study.commons.constants.board.AfterWriteTarget;
import org.study.commons.constants.board.SkinType;
import org.study.controllers.admin.board.BoardForm;
import org.study.entities.board.Board;
import org.study.repositories.board.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {

    private final BoardRepository boardRepository;

    public void save(BoardForm boardForm) {
        save(boardForm, null);
    }

    public void save(BoardForm boardForm, Errors errors) { // Errors는 Bean Validation에서 검증( 필수 항목 등등 )하는 Error
        /**
         * 에러가 있으면 하단 코드 실행하지 않고, 없으면 save() 실행!
         * 테스트를 위해 errors != null 조건도 추가
         */
        if (errors != null && errors.hasErrors()) {
            return;
        }

        /**
         *  게시판 설정 있으면 조회 -> 없으면 엔티티 생성
         *  게시판 등록 모드인 경우 중복 여부 체크!
         */
        Board board = boardRepository.findById(boardForm.getBId()).orElseGet(Board::new); // 있으면 bId 조회, 없으면 생성

        String mode = boardForm.getMode();
        // 게시판 등록시, 중복이면서 mode가 update가 아니면 Exception 발생
        if ((mode == null || !mode.equals("update")) && board.getBId() != null) {
            throw new DuplicateBoardConfigException();
        }

        board = Board.builder()
                .bId(boardForm.getBId())
                .boardNm(boardForm.getBoardNm())
                .isUse(boardForm.isUse())
                .rowsPerPage(boardForm.getRowsPerPage())
                .useViewList(boardForm.isUseViewList())
                .category(boardForm.getCategory())

                .listAccessRole(UserRole.valueOf(boardForm.getListAccessRole()))
                .viewAccessRole(UserRole.valueOf(boardForm.getViewAccessRole()))
                .writeAccessRole(UserRole.valueOf(boardForm.getWriteAccessRole()))
                .replyAccessRole(UserRole.valueOf(boardForm.getReplyAccessRole()))
                .commentAccessRole(UserRole.valueOf(boardForm.getCommentAccessRole()))

                .useEditor(boardForm.isUseEditor())
                .useFileAttach(boardForm.isUseFileAttach())
                .useImageAttach(boardForm.isUseImageAttach())
                .afterWriteTarget(AfterWriteTarget.valueOf(boardForm.getAfterWriteTarget()))
                .useComment(boardForm.isUseComment())
                .skin(SkinType.valueOf(boardForm.getSkin()))
                .isReview(boardForm.isReview())
                .build();

        boardRepository.saveAndFlush(board);
    }
}
