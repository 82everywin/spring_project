package org.study.controllers.community;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.study.commons.Pagination;
import org.study.commons.UserUtils;
import org.study.controllers.admin.board.BoardSearch;
import org.study.controllers.admin.community.CommunitySearch;
import org.study.entities.User;
import org.study.entities.board.Board;
import org.study.entities.board.Post;
import org.study.models.Community.*;
import org.study.models.board.BoardConfigInfoService;
import org.study.models.board.BoardNotAllowAccessException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/community")
@RequiredArgsConstructor
public class PostController {

    private final BoardConfigInfoService boardConfigInfoService;
    private final PostInfoService infoService;
    private final PostSaveService saveService;
    private final PostListService listService;
    private final PostAllListService allListService;
    private final UserUtils userUtils;
    private final HttpServletRequest request;
    private final UpdateHitService updateHitService;
    private final PostDeleteService deleteService;
    private Board board; // 게시판 설정

    @GetMapping
    public String index(@ModelAttribute CommunitySearch communitySearch, Model model) {
        Page<Post> data = allListService.gets(communitySearch);
        model.addAttribute("items", data.getContent());

        return "front/community/community";
    }

    /**
     * 게시글 목록
     *
     * @param bId
     * @return
     */
    @GetMapping("/list/{bId}")
    public String list(@PathVariable String bId, @RequestParam(value = "category", required = false) String categoryName,
                       Model model, @ModelAttribute BoardSearch boardSearch) {
        commonProcess(bId, "list", model);

        Board board = boardConfigInfoService.get(bId, "list");
        model.addAttribute("board", board);
        model.addAttribute("category", categoryName);

        // 카테고리별 조회하기
        Page<Post> items = listService.gets(boardSearch, bId, categoryName);
        model.addAttribute("postList", items.getContent());

        String url = request.getRequestURI();
        Pagination pagination = new Pagination(items, url);
        model.addAttribute("pagination", pagination);

        return "front/community/list";
    }

    /**
     * 게시글 작성
     *
     * @param bId
     * @return
     */
    @GetMapping("/write/{bId}")
    public String write(@PathVariable String bId, @ModelAttribute PostForm postForm, Model model) {
        commonProcess(bId, "write", model);
        postForm.setBId(bId);
        if (userUtils.isLogin()) {
            postForm.setPoster(userUtils.getUser().getUserNickNm());
        }

        return "front/community/register";
    }

    /**
     * 게시글 수정
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/update")
    public String update(@PathVariable Long id, Model model) {
        Post post = infoService.get(id, "update");
        board = post.getBoard();
        commonProcess(board.getBId(), "update", model);

        // 수정 권한 체크
        updateDeletePossibleCheck(post);

        PostForm postForm = new ModelMapper().map(post, PostForm.class);
        model.addAttribute("postForm", postForm);

        return "front/community/update";
    }

    @PostMapping("/save")
    public String save(@Valid PostForm postForm, Errors errors, Model model) {
        Long id = postForm.getId();
        String mode = "write";
        if (id != null) {
            mode = "update";
            Post post = infoService.get(id);
            board = post.getBoard();
            postForm.setUserNo(post.getUser().getUserNo());

            updateDeletePossibleCheck(post);
        }
        commonProcess(postForm.getBId(), mode, model);

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(System.out::println);
            return "front/community/" + mode;
        }
        saveService.save(postForm);

        // 작성후 이동 설정 - 목록, 글보기
        String location = board.getAfterWriteTarget().toString();
        String url = "redirect:/user/community/";
        url += location.equals("view") ? "view/" + postForm.getId() : "list/" + postForm.getBId();

        return url;
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Post post = infoService.get(id);
        board = post.getBoard();

        commonProcess(board.getBId(), "view", model);

        model.addAttribute("post", post);
        model.addAttribute("board", board);

        updateHitService.update(id); // 게시글 조회수 업데이트

        return "front/community/view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Post post = infoService.get(id, "update");
        board = post.getBoard();
        String bid = board.getBId();
        commonProcess(bid, "update", model);

        // 삭제 권한 체크
        updateDeletePossibleCheck(post);

        // 삭제 처리
        deleteService.delete(id);

        // 삭제 완료시 게시글 목록으로 이동
        return "redirect:/user/community/list/" + bid;
    }

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

        board = boardConfigInfoService.get(bId, action);
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

    /**
     * 수정, 삭제 권한 체크
     * <p>
     * - 회원 : 작성한 회원
     * - 관리자 : 가능
     */
    public void updateDeletePossibleCheck(Post post) {
        if (userUtils.isAdmin()) { // 관리자는 무조건 가능
            return;
        }

        if (userUtils.isLogin() &&
                userUtils.getUser().getUserNo() != post.getUser().getUserNo()) {
            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
            System.out.println(userUtils.getUser().getUserNo() + post.getUser().getUserNo());
            throw new BoardNotAllowAccessException();
        }
    }

    public void updateDeletePossibleCheck(Long id) {
        Post post = infoService.get(id, "update");
        updateDeletePossibleCheck(post);
    }
}
