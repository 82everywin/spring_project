package org.study.commons.validators;

import org.springframework.http.HttpStatus;

public class PostValidationException extends CommonException{
    public PostValidationException(String code) {
        super(bundleValidation.getString(code), HttpStatus.BAD_REQUEST);
    }
}