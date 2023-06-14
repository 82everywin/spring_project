package org.study.controllers.admin.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/study")
public class StudyController {



    /**
     * <스터디관리> 클릭시 나오는 페이지
     * ==스터디 목록
     *
     * @return
     */
    @GetMapping
    public String index(){



        return "admin/study/index";
    }

    /**
     * 스터디 개설 신청 관리
     *
     * @return
     */
    @GetMapping("/approvals")
    public String approvals(){

        return "admin/study/approvals";
    }

    /**
     * 관리자 승인/미승인 페이지
     * => 개설신청관리에서 '처리하기'버튼 클릭시 나오는 관리자 페이지
     * ( approve.html에서 th:action="@{/admin/study/approve}" 갈 예정
     *
     * @return
     */
    @GetMapping("/approve")
    public String approve(){
        return "admin/study/approve";
    }


    /**
     * 카테고리 관리
     *
     * @return
     */
    @GetMapping("/category")
    public String category(){
        return "admin/study/category";
    }


}
