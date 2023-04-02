package org.study.controllers.users;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

// 사용자 관련 컨트롤러
@Controller
@RequestMapping("/user")
public class UserController {

    // 회원가입 양식 - GET /user
    @GetMapping
    public String join(Model model) {
        UserJoin userJoin = new UserJoin();
        model.addAttribute("userJoin", userJoin);

        return "front/user/join";
    }

    // 회원가입 처리 - POST /user
    @PostMapping
    public String joinPs(@Valid UserJoin userJoin, Errors errors) {

        if (errors.hasErrors()) {
            return "front/user/join";
        }

        return "redirect:/user/login";
    }

    // 회원정보 수정 - GET /user/사용자 ID
    @GetMapping("/{userId}")
    public String edit(@PathVariable String userId) {
        return "front/user/edit";
    }

    // 회원정보 수정 PATCH /user
    @PatchMapping
    public String editPs() {
        return "redirect:/mypage"; // 회원정보 수정 후 마이페이지 이동
    }

    // 로그인 양식 - GET /user/login
    @GetMapping("/login")
    public String login() {
        return "front/user/login";
    }
}
