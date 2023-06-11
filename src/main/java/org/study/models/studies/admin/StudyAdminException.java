package org.study.models.studies.admin;

import org.springframework.http.HttpStatus;
import org.study.commons.validators.CommonException;

public class StudyAdminException extends CommonException {
    public StudyAdminException(String message) {

        super(message, HttpStatus.BAD_REQUEST);
    }
}
