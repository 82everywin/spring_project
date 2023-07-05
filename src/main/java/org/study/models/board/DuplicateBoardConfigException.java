package org.study.models.board;

import org.springframework.http.HttpStatus;
import org.study.commons.validators.CommonException;

import java.util.ResourceBundle;

/**
 * 게시판 중복 등록 시 발생 예외
 */
public class DuplicateBoardConfigException extends CommonException {
    public DuplicateBoardConfigException() {super("이미 등록된 게시판 입니다.", HttpStatus.BAD_REQUEST);}

}
