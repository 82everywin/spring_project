package org.study.controllers.commons;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorsController {

    /**
     * 권한없음 상태 오류 페이지
     * 
     * @return
     */
    @GetMapping("/401")
    public String status401(HttpServletResponse response, Model model) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        model.addAttribute("status",401);
        return "error/401";
    }


    /**
     * 페이지 없음 오류 페이지
     *
     * @return
     */
    @GetMapping("/404")
    public String status404(HttpServletResponse response,Model model){
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("status",404);
        return "error/404";
    }
}
