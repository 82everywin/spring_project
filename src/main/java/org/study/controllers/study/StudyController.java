package org.study.controllers.study;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.study.commons.Areas;
import org.study.commons.UserUtils;
import org.study.commons.constants.Status;
import org.study.commons.validators.CommonException;
import org.study.commons.validators.NotAllowedException;
import org.study.entities.Category;
import org.study.entities.Studies;

import org.study.models.studies.*;
import org.study.repositories.CategoryRepository;

import java.util.List;


@Controller("frontStudyController")
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {


    private final SaveService saveService;
    private final InfoService infoService;


    private final CategoryRepository categoryRepository;

    private final StudySaveValidator saveValidator;

    private final UserUtils userUtils;

    @GetMapping
    public String register(@ModelAttribute StudyForm studyForm, Model model) {
        commonProcess(model);

        return "front/study2/register";
    }

    @GetMapping("/{studyNo}/edit")
    public String update(@PathVariable Long studyNo, Model model) {
        commonProcess(model);

        Studies studies = infoService.get(studyNo, true);

        StudyForm studyForm = new ModelMapper().map(studies, StudyForm.class);
        if (studyForm.getMaxMember() > 0) studyForm.setSelMaxMember("each");
        model.addAttribute("studyForm", studyForm);

        if (studyForm.getSido() != null) {
            String[] siguguns = Areas.getSigugun(studyForm.getSido());
            model.addAttribute("siguguns", siguguns);
        }

        return "front/study2/update";
    }

    /**
     * 스터디 등록, 수정
     *
     * @return
     */
    @PostMapping
    public String save(@Valid StudyForm studyForm, Errors errors, Model model) {
        Long studyNo = studyForm.getStudyNo();
        commonProcess(model);

        saveValidator.validate(studyForm, errors);

        if (errors.hasErrors()) {
            String tpl = studyNo == null ? "register" : "update";
            return "front/study2/" + tpl;
        }

        saveService.save(studyForm);

        return "redirect:/study/view/" + studyForm.getStudyNo();
    }


    /**
     * 스터디 신청 내역 조회
     * - 본인이 작성한 내역은 보이나 다른 회원이나 비회원인 경우는 승인 여부 검증
     *
     * @param studyNo
     * @param model
     * @return
     */
    @GetMapping("/view/{studyNo}")
    public String view(@PathVariable Long studyNo, Model model) {
        Studies studies = infoService.get(studyNo);

        /** 관리자 및 본인 게시글 외에는 미승인 시에는 미노출 처리 */
        if (!userUtils.isAdmin() && !(userUtils.isLogin() && userUtils.isMine(userUtils.getUser().getUserEmail())) && studies.getStatus() != Status.APPROVE) {
            throw new NotAllowedException();
        }

        model.addAttribute("study", studies);
        model.addAttribute("pageTitle", studies.getSimpleIntro());
        model.addAttribute("addCss", new String[] { "study/view" });
        model.addAttribute("addScript", new String[] { "study/view" });

        return "front/study2/view";
    }

    private void commonProcess(Model model) {
        String[] sidos = Areas.sido;
        String[] addScript = { "ckeditor/ckeditor", "fileManager", "study/form" };
        String[] addCss = {"study/form"};
            
        model.addAttribute("sidos", sidos);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
        model.addAttribute("pageTitle", "스터디 만들기");
        model.addAttribute("menuCode", "study");

        /** 스터디 분류 */
        List<Category> categories = categoryRepository.getCategories("study");
        model.addAttribute("categories", categories);
    }

    /** 컨트롤러 예외 처리 - 자바스크립트로 메세지 처리 */
    @ExceptionHandler(CommonException.class)
    public String errorHandler(CommonException e, Model model, HttpServletResponse response) {

        response.setStatus(e.getStatus().value());

        String script = String.format("alert('%s');history.back();", e.getMessage());
        e.printStackTrace();

        model.addAttribute("script", script);

        return "commons/execute_script";
    }
}
