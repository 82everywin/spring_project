package org.study.controllers.admin.study;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.commons.Areas;
import org.study.commons.Pagination;
import org.study.commons.constants.Status;
import org.study.commons.validators.CommonException;
import org.study.commons.validators.StudyNotFoundException;
import org.study.entities.Study;
import org.study.models.study.StudyListService;
import org.study.repositories.StudyRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/study")
public class StudyController {



    @Autowired
    private StudyListService listService;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private HttpServletRequest request;

    /**
     * <스터디관리> 클릭시 나오는 페이지
     * ==스터디 목록
     *
     * @return
     */
    @GetMapping
    public String index(Model model,StudyConfig studyConfig,StudySearch studySearch){
        String url = request.getContextPath() + "/admin/study";
        studySearch.setApproveStatus(new String[] {"APPROVE","DISAPPROVE"});
        Page<Study> data = listService.gets(studySearch); // 조회
        Pagination<Study> pagination = new Pagination<>(data, url);
        model.addAttribute("studies", data.getContent()); //조회된 엔티티 클래스를 직접 커맨드로 사용
        model.addAttribute("studySearch",studySearch);
        model.addAttribute("pagination", pagination);

        return "admin/study/index";
    }
    /**
     * 스터디 수정
     */
    @GetMapping("/update/{studyCode}")
    public String update(@PathVariable Long studyCode, Model model, HttpServletResponse response){
        model.addAttribute("mode", "update");
        try {
            StudyConfig studyConfig = listService.get(studyCode);

            model.addAttribute("studyConfig", studyConfig);
        }catch (CommonException e){
            response.setStatus(e.getStatus().value());
            model.addAttribute( "script", "alert('" + e.getMessage() + "');history.back();");
            return "common/execute_script";
        }

        return "admin/study/index";

    }

    /**
     * 스터디 개설 신청 관리
     *
     * @return
     */
    @GetMapping("/approvals")
    public String approvals(Model model, StudySearch studySearch){
        String url = request.getContextPath() + "/admin/study/approvals";
        studySearch.setApproveStatus(new String[] {"APPLY"});
        Page<Study> data = studyRepository.getStudyAdminP(studySearch);
        Pagination<Study> pagination = new Pagination<>(data, url);
        model.addAttribute("studies", data.getContent());
        model.addAttribute("pagination", pagination);

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
    public String approve(Model model, StudySearch studySearch){
        model.addAttribute("sidoList", Areas.sido);
        return "admin/study/approve";
    }






}
