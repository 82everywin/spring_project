package org.study.controllers.admin.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/user")
@Controller
public class UserController {

    /**
     * <회원관리> 클릭시 나오는 페이지
     * == 회원 목록
     *
     * @return
     */
    @GetMapping
    public String index() {

        return "admin/user/index";
    }


    /**
     * 회원 정보 상세
     *
     * @return
     */
    @GetMapping("/detail")
    public String detail(){

        return "admin/user/detail";
    }

    /**
     * 회원 목록에서 '관리' 버튼 클릭시 나오는 페이지
     * 하단의 탈퇴/정지 버튼 클릭시 나오는 페이지
     * : 코드 302 로 페이지 임시이동 예정
     *
     * @param userNo
     * @return
     */
    @PostMapping("/manage")
    public String manage(@PathVariable Long userNo){

        // 이 부분 좀 더 생각해보고, 수정 바랍니다.
        return "redirect: /admin/user/detail";
    }
}
