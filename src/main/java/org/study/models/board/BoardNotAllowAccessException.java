package org.study.models.board;

import org.springframework.http.HttpStatus;
import org.study.commons.validators.CommonException;

public class BoardNotAllowAccessException extends CommonException {
    public BoardNotAllowAccessException() {
        super(bundleValidation.getString("Validation.board.NotAllowAccess"), HttpStatus.UNAUTHORIZED);
    }
}
