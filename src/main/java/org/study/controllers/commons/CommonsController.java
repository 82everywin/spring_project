package org.study.controllers.commons;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("org.study.controllers")
public class CommonsController {

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, Model model){
        e.printStackTrace();
        model.addAttribute("message",e.getMessage());

        return "admin/";
    }
}
