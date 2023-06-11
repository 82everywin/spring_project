package org.study.controllers.admin.study;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.commons.Pagination;
import org.study.commons.validators.CommonException;
import org.study.controllers.study.StudySearch;
import org.study.entities.Category;
import org.study.entities.Studies;
import org.study.models.studies.ListService;
import org.study.models.studies.admin.StudyListUpdateService;
import org.study.repositories.CategoryRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/study")
@RequiredArgsConstructor
public class StudyController {

    private final ListService listService;
    private final HttpServletRequest request;
    private final CategoryRepository categoryRepository;

    private final StudyListUpdateService listUpdateService;

    @GetMapping
    public String index(@ModelAttribute StudySearch search, Model model) {
        String url = request.getContextPath() + "/admin/study";
        String status = search.getStatus();
        status = status == null || status.isBlank() ? "ALL" : status;
        search.setStatus(status);

        Page<Studies> data = listService.gets(search);
        Pagination pagination = new Pagination(data, url);

        List<Studies> items = data.getContent();

        model.addAttribute("items", items);
        model.addAttribute("pagination", pagination);

        /** 스터디 분류 */
        List<Category> categories = categoryRepository.getCategories("study");
        model.addAttribute("categories", categories);

        return "admin/study2/index";
    }

    @PostMapping
    public String listPs(StudyListForm listForm, Model model) {;
        String script = null;
        try {
            listUpdateService.update(listForm);

            // 업데이트 성공시 새로고침
            script = "parent.location.reload();";
        } catch (CommonException e) {
            script = String.format("alert('%s');", e.getMessage());

        }

        model.addAttribute("script", script);
        return "commons/execute_script";

    }

}
