package org.study.controllers.admin.board;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.study.commons.validators.CommonException;
import org.study.entities.board.Board;
import org.study.models.board.BoardConfigDeleteService;
import org.study.models.board.BoardConfigSaveService;
import org.study.models.board.BoardConfigInfoService;
import org.study.models.board.BoardConfigListService;

@Controller
@RequestMapping("admin/board")
public class BoardController {
    @Autowired
    private BoardConfigSaveService saveService;
    @Autowired
    private BoardConfigInfoService infoService;
    @Autowired
    private BoardConfigListService listService;
    @Autowired
    private BoardConfigDeleteService deleteService;

    /** <게시판관리> 클릭하면 나오는 페이지 == 게시판목록 */
    @GetMapping
    public String index(@ModelAttribute BoardSearch boardSearch, Model model) {
        Page<Board> data = listService.gets(boardSearch);
        model.addAttribute("items", data.getContent());

        return "admin/board/index";
    }

    /** 게시판 등록  (세부목록 클릭시 ) */
    @GetMapping("/register")
    public String register(@ModelAttribute BoardForm boardForm) {
        return "admin/board/register";
    }

    /** 게시판 상세 -> 게시판 수정 & 삭제 가능 */
    @GetMapping("/{bId}/update")
    public String update(@PathVariable String bId, Model model, HttpServletResponse response) {
        model.addAttribute("mode", "update");
//        try {
            Board board = infoService.get(bId, true);
            BoardForm boardForm = new ModelMapper().map(board, BoardForm.class);
            boardForm.setMode("update"); // 모드 update로 설정
            boardForm.setListAccessRole(board.getListAccessRole().toString());
            boardForm.setViewAccessRole(board.getViewAccessRole().toString());
            boardForm.setWriteAccessRole(board.getWriteAccessRole().toString());
            boardForm.setReplyAccessRole(board.getReplyAccessRole().toString());
            boardForm.setCommentAccessRole(board.getCommentAccessRole().toString());

            model.addAttribute("boardForm", boardForm);
//        } catch (CommonException e) {
//            response.setStatus(e.getStatus().value());
//            model.addAttribute("script", "alert('" + e.getMessage() + "');history.back();");
//            return "commons/execute_script";
//        }
        return "admin/board/update";
    }

    /** 게시판 삭제 */
    @GetMapping("/delete/{bId}")
    public String delete(@PathVariable String bId) {
        deleteService.delete(bId);

        // 삭제 완료시 게시글 목록으로 이동
        return "redirect:/admin/community";
    }

    /**
     * 게시판 목록 ->//(페이지이동)//-> 수정하기 버튼 => 수정 & 등록완료 => 목록페이지 이동
     *: 코드 302 로 페이지 임시이동 예정
     *
     * @param boardForm
     * @param errors
     *
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid BoardForm boardForm, Errors errors) {
        String mode = boardForm.getMode();

        try {
            saveService.save(boardForm, errors);
        } catch (CommonException e) {
            e.printStackTrace();
            errors.reject("BoardConfigError", e.getMessage());
        }

        if (errors.hasErrors()) {
            String tpl = mode == null ? "register" : "update";
            return "admin/board/" + tpl;
        }

        return "redirect:/admin/board"; // 게시판 등록&수정 후 목록으로 이동
    }
}
