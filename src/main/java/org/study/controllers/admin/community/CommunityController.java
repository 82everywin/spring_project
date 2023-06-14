package org.study.controllers.admin.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/community")
public class CommunityController {


    /**
     * <커뮤니티관리> 클릭하면 나오는 페이지
     * == 카테고리 목록
     *
     * @return
     */
    @GetMapping
    public String index(){
        return "admin/community/index";
    }

    /**
     * 카테고리 등록  (세부목록 클릭시 )
     *
     * @return
     */
    @GetMapping("/register")
    public String register(){
        return "admin/community/register";
    }

    /**
     * 카테고리 목록 ->(페이지이동)-> 수정하기 버튼 => 수정 & 등록완료 => 목록페이지 이동
     *: 코드 302 로 페이지 임시이동 예정
     *
     * @return
     */
    @PostMapping("/save")
    public String save(){
         return "redirect:/admin/community";
    }

    /**
     * 게시글 목록
     *
     * @return
     */
    @GetMapping("/lists")
    public String lists(){
        return "admin/community/lists";
    }

    /**
     * 게시글 상세 -> 게시글 수정 & 삭제 가능
     * @return
     */
    @GetMapping("/update/{code}")
    public String update(@PathVariable Long code){
        return "admin/community/update";
    }
}
