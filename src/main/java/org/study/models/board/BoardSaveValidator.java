package org.study.models.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.study.commons.validators.BadRequestException;
import org.study.commons.validators.RequiredCheckValidator;
import org.study.commons.validators.ServiceValidator;
import org.study.controllers.admin.board.BoardForm;
import org.study.repositories.board.BoardRepository;

/**
 * 게시판 등록 Validator
 *
 */
@Component
public class BoardSaveValidator implements ServiceValidator<BoardForm>, RequiredCheckValidator {

    @Autowired
    private BoardRepository repository;

    @Override
    public void check(BoardForm boardForm) {
        /** BoardConfig가 null 값일 경우 예외 발생 */
        nullCheck(boardForm, new BadRequestException("잘못된 접근입니다."));

//        /** 필수 항목 체크 */
//        requiredCheck(boardConfig.getBId(),new BadRequestException("게시판 아이디를 입력하세요."));
//        requiredCheck(boardConfig.getBoardNm(),new BadRequestException("게시판 이름을 입력하세요."));
//        nullCheck(boardConfig.getRowsPerPage(),new BadRequestException("페이지 당 게시글 수를 입력하세요."));
    }

    @Override
    public void check(BoardForm boardForm, Errors errors) {
        /** bId 중복 여부 체크 */
//        if (repository.exists(boardConfig.getBId())) {
//            // 이미 등록된 게시판 아이디인 경우
//            throw new DuplicateCateBIdException();
//        }
        if (errors.hasErrors()) {
            errors.rejectValue("bId", "NotBlank.config.bId");
            errors.rejectValue("boardNm", "NotBlank.config.boardNm");
            errors.rejectValue("rowsPerPage", "NotBlank.config.rowsPerPage");
        }

        /** rowsPerPage : 최소 10부터 되는지 체크 */
        if (boardForm.getRowsPerPage() < 10L) {
            errors.rejectValue("rowsPerPage", "minimum.value.rowsPerPage");
        }
    }
}
