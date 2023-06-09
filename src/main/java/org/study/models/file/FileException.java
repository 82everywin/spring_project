package org.study.models.file;

import org.springframework.http.HttpStatus;
import org.study.commons.validators.CommonException;

import java.util.ResourceBundle;

public class FileException extends CommonException {
    private static ResourceBundle bundle = ResourceBundle.getBundle("messages.errors");
    public FileException(String code) {
        super(bundle.getString(code));
    }

    public FileException(String code, HttpStatus status) {
        super(bundle.getString(code), status);
    }
}
