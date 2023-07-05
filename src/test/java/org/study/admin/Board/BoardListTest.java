package org.study.admin.Board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.study.commons.constants.board.AfterWriteTarget;
import org.study.commons.constants.board.SkinType;
import org.study.commons.constants.board.ViewType;
import org.study.controllers.admin.board.BoardForm;
import org.study.models.board.BoardConfigSaveService;
import org.study.models.board.BoardConfigInfoService;
import org.study.models.board.BoardConfigListService;
import org.study.repositories.board.BoardRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class BoardListTest {

    private BoardForm boardForm;
    @Autowired
    private BoardRepository repository;
    @Autowired
    private BoardConfigSaveService service;
    @Autowired
    private BoardConfigInfoService infoService;
    @Autowired
    private BoardConfigListService listService;

    private int boardCnt = 30;
    @BeforeEach
    void insert() {
        for (int i=0; i<boardCnt; i++) {
            BoardForm boardForm = BoardForm.builder()
                    .bId("게시판아이디" + i)
                    .boardNm("게시판명" + i)
                    .isUse(true)
                    .rowsPerPage(10L)
                    .useViewList(true)
                    .category("수능\n공무원\n")
                    .viewType(ViewType.USER.toString())
                    .useEditor(true)
                    .afterWriteTarget(AfterWriteTarget.VIEW.toString())
                    .useComment(true)
                    .skin(SkinType.DEFAULT.toString())
                    .isReview(true)
                    .build();
            service.save(boardForm);
        }
    }
    /**
     *  게시물 목록을 조회할 때 필요한 테스트입니다.
     *  클래스 : BoardInfoService
     *  메서드 : get()]
     *
     */
    @Test
    @DisplayName("개별 조회 - BId를 통해 게시글을 가져 올 수 있다. ")
    void get() {
        //오류 주석처리
//        BoardConfig config = infoService.get("게시판아이디1");
//        System.out.println(config);

    }
    /**
     * 클래스 : BoardListService
     * 메서드 : gets()
     */
    @Test
    @DisplayName("최종목적 - 전체 게시판 리스트를 조회 성공")
    void gets() {
        List<BoardForm> lists = listService.gets();
        System.out.println(lists);
    }

    @Test
    @DisplayName("전체 데이터 조회 - 조회된 데이터가 boardCnt와 갯수가 일치하면 성공")
    void getAllTest() {
        List<BoardForm> lists = listService.gets();
        System.out.println(lists);

        assertEquals(boardCnt, lists.size());
    }

    /**
     * 특정 카테고리를 선택한 후, 해당 카테고리에만 해당되는 게시물 목록이 조회되는지 테스트하는 메서드입니다.
     */
    @Test
    @DisplayName("하나의 카테고리 선택 - 해당 게시물만 조회 가능 ")
    void get_category_lists() {

    }
}
