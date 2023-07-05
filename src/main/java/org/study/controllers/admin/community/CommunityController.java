package org.study.controllers.admin.community;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.study.commons.Pagination;
import org.study.controllers.admin.board.BoardSearch;
import org.study.entities.board.Board;
import org.study.entities.board.Post;
import org.study.models.Community.PostListService;
import org.study.models.board.BoardConfigInfoService;
import org.study.repositories.board.PostRepository;

import java.util.ArrayList;
import java.util.List;

/**
 *  <게시판 관리> -> 게시판 등록 을 통해서 게시판을 등록할 예정입니다.
 *  이 페이지는 게시판 분류 없이 모든 게시글을 조회할 수 있는 페이지로 구성되어 있습니다.
 *
 */

@Controller
@RequestMapping("/admin/community")
@RequiredArgsConstructor
public class CommunityController {

    private final BoardConfigInfoService infoService;
    private final PostListService listService;
    private final HttpServletRequest request;
    private final BoardConfigInfoService configInfoService;
    private Board board; // 게시판 설정


    /**
     * 관리자 커뮤니티 게시글 목록
     *
     * @return
     */
    @GetMapping
    public String list(String bId, @RequestParam(value = "category", required = false) String categoryName,
                       Model model, @ModelAttribute BoardSearch boardSearch) {
        commonProcess(bId, "list", model);

        Board board = infoService.get(bId, "list");
        model.addAttribute("board", board);
        model.addAttribute("category", categoryName);

        // 카테고리별 조회하기
        Page<Post> items = listService.gets(boardSearch, bId, categoryName);
        model.addAttribute("postList", items.getContent());

        String url = request.getRequestURI();
        Pagination pagination = new Pagination(items, url);
        model.addAttribute("pagination", pagination);

        return "board/list";
    }


    /**
     *  게시글 상세 보기 -> user에서 보여지는 게시글로 이동 (getmapping x)
     */

    /**
     *  게시글 수정
     */

    private void commonProcess(String bId, String action, Model model) {
        /**
         * 1. bId 게시판 설정 조회
         * 2. action - write, update - 공통 스크립트, 공통 CSS
         *           - 에디터 사용 -> 에디터 스크립트 추가
         *           - 에디터 미사용 -> 에디터 스크립트 미추가
         *           - write, list, view -> 권한 체크
         *           - update - 본인이 게시글만 수정 가능
         *                    - 회원 - 회원번호
         *                    - 관리자는 다 가능
         */

        board = configInfoService.get(bId, action);
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        // 공통 스타일 CSS
        addCss.add("board/style");
        addCss.add(String.format("board/%s_style", board.getSkin()));

        // 글 작성, 수정시 필요한 자바스크립트
        if (action.equals("write") || action.equals("update")) {
            if (board.isUseEditor()) { // 에디터 사용 경우
                addScript.add("ckeditor/ckeditor");
            }
            addScript.add("fileManager");
            addScript.add("board/form");
        }

        // 공통 필요 속성 추가
        model.addAttribute("board", board); // 게시판 설정
        model.addAttribute("addCss", addCss); // CSS 설정
        model.addAttribute("addScript", addScript); // JS 설정
    }

}
