package org.study.admin.Board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.study.commons.constants.board.AfterWriteTarget;
import org.study.commons.constants.board.SkinType;
import org.study.commons.messageBundle.MessageBundle;
import org.study.commons.validators.BadRequestException;
import org.study.controllers.admin.board.BoardForm;
import org.study.models.board.BoardConfigSaveService;
import org.study.models.board.DuplicateBoardConfigException;
import org.study.repositories.board.BoardRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 * '게시판 관리 - 게시판 등록'에 해당하는 테스트 클래스 입니다.
 * 파일명 : "BoardConfigSaveService"
 * 메서드 : save()
 *
 */
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class PostFormTest {

    @Autowired
    private MockMvc mockMvc;

    private BoardForm boardForm;

    @Autowired
    private BoardRepository repository;

    @Autowired
    private BoardConfigSaveService service;

    @BeforeEach
    void config() {
        // 테스트 양식 데이터 추가
        boardForm = BoardForm.builder()
                .bId("QnA")
                .boardNm("게시판1")
                .isUse(true)
                .rowsPerPage(20)
                .useViewList(true)
                .category("QnA Notice")
                .useEditor(true)
                .afterWriteTarget(AfterWriteTarget.VIEW.toString())
                .useComment(true)
                .skin(SkinType.DEFAULT.toString())
                .isReview(true)
                .build();
    }

    @Test
    @DisplayName("예외 없이 게시판이 성공적으로 등록 (최종목적) ")
    void configSuccess(){
        assertDoesNotThrow(()->{
            service.save(boardForm);
        });
        System.out.println(boardForm);
    }

    @Test
    @DisplayName("BoardConfig- 전체 Null값일때 예외메세지발생")
    void boardConfig_Null_Exception(){
        BadRequestException thrown = assertThrows(BadRequestException.class,() -> {
           service.save(null);
        });

        /** "잘못된 접근입니다." 문구 포함여부 체크 */
        assertTrue(thrown.getMessage().contains("잘못된 접근"));

        /** HttpStatus가 400 - BadRequest로 설정되어 있는지 체크 */
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
    }

    /** 유효성 검사 S */
    @Test
    @DisplayName("필수 입력 값 체크 -예외메세지발생")
    void boardConfig_Essential(){
        // 발생 경우 수
        // bId , boardNm, rowsPerPage 에 대한 필수 입력 값 체크
        // null, isBlank

        /** 빈값 체크 S */
        String[] fields = {"bId", "boardNm" /* "rowsPerPage" */};
        String bId = boardForm.getBId();
        String boardNm = boardForm.getBoardNm();

        for (String field : fields) {
            String includedWord = null;

            if (field.equals("bId")) {
                boardForm.setBId("   ");
                boardForm.setBoardNm(boardNm);
                includedWord = "게시판 아이디";

            } else if (field.equals("boardNm")) {
                boardForm.setBId(bId);
                boardForm.setBoardNm("   ");
                includedWord = "게시판 이름";
            }
            BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
                service.save(boardForm);
            });
            System.out.println(field);
            System.out.println(thrown.getMessage());

            // 예외 메세지에 핵심 키워드가 포함되어 있는지 체크
            assertTrue(thrown.getMessage().contains(includedWord));

            // 상태 코드 검증(HttpStatus.BAD_REQUEST)
            assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());

        }
        /** 빈값 체크 E */

        /** NULL 체크 S */
        String[] fields2 = {"bId", "boardNm", "rowsPerPage"};
        int rowsPerPage = boardForm.getRowsPerPage();

        for (String field : fields2) {
            String includedWord = null;
            if (field.equals("bId")) {
                boardForm.setBId(null);
                boardForm.setBoardNm(boardNm);
                boardForm.setRowsPerPage(rowsPerPage);
                includedWord = "게시판 아이디";

            } else if (field.equals("boardNm")) {
                boardForm.setBId(bId);
                boardForm.setBoardNm(null);
                boardForm.setRowsPerPage(rowsPerPage);
                includedWord = "게시판 이름";

            BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
                service.save(boardForm);
            });
            System.out.println(field);
            System.out.println(thrown.getMessage());

            // 예외 메세지에 핵심 키워드가 포함되어 있는지 체크
            assertTrue(thrown.getMessage().contains(includedWord));
            // 상태 코드 검증(HttpStatus.BAD_REQUEST)
            assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        }
        /** NULL 체크 E */
    }

    //validator Test
    /**
     * 유효성 검사 추가 (오류메세지가 제대로 나오는지에 대한 체크는 통합테스트에서 진행)
     * 1. 게시판 아이디가 중복되는지 체크
     * 2. rowsPerPage : 최소 10개 부터 되는지 체크
     * 3. category: '\n' 줄바꿈울 기준으로 인식 되는지 
     */

    /** 1. 게시판 아이디가 중복되는지 체크 */
    @Test
    @DisplayName("bId 중복 등록 시 DuplicateCateBIdException 발생 여부")
    void DuplicateCateBIdTest() {
        // 테스트 전 분류 등록
        service.save(boardForm);

        assertThrows(DuplicateBoardConfigException.class, () -> {
           // 중복 분류로 등록
           service.save(boardForm);
        });
    }

    /** 2. rowsPerPage : 최소 10부터 되는지 체크 */
    @Test
    @DisplayName("rowsPerPage : 최소 10부터 되는지 체크")
    void rowsPerPageMinTest() {
        assertThrows(BadRequestException.class, () -> {
            boardForm.setRowsPerPage(9);
            service.save(boardForm);
        });
        System.out.println(boardForm);
    }

    /** 3. category: '\n' 줄바꿈울 기준으로 인식 되는지 */
    @Test
    @DisplayName("category: '\n' 줄바꿈울 기준으로 인식 되는지")
    void categoryEnterTest() {
        // textarea, split
        boardForm.setCategory("분류1\n분류2\n분류3\n");
        service.save(boardForm);
        System.out.println(boardForm);
    }

    /**
     * 통합 테스트
     */
    @Test
    @DisplayName("성공적으로 등록완료되면 /admin/board 이동")
    void configSuccessRedirectTest() throws Exception {
        mockMvc.perform(post("/admin/board")
                .param("bId", boardForm.getBId())
                .param("boardNm", boardForm.getBoardNm())
                .param("isUse", String.valueOf(boardForm.isUse()))
                .param("rowsPerPage", String.valueOf(boardForm.getRowsPerPage()))
                .param("useViewList", String.valueOf(boardForm.isUseViewList()))
                .param("category", boardForm.getCategory())
                .param("useEditor", String.valueOf(boardForm.isUseEditor()))
                .param("afterWriteTarget", boardForm.getAfterWriteTarget())
                .param("useComment", String.valueOf(boardForm.isUseComment()))
                .param("skin", boardForm.getSkin())
                .param("isReview", String.valueOf(boardForm.isReview())).with(csrf()))
                .andExpect(redirectedUrl("/admin/board"));

    }

    @Test
    @DisplayName("필수항목 누락 시 Bean Validation 검증 체크")
    void checkResponseTest() throws Exception {
        String body = mockMvc.perform(post("/admin/board").with(csrf()))
                .andReturn().getResponse().getContentAsString();

        // 게시판아이디 검증(bId)
        assertTrue(body.contains("게시판 아이디"));

        // 게시판이름 검증(boardNm)
        assertTrue(body.contains("게시판 이름"));

        // 페이지 당 게시글 수 검증(rowsPerPage)
        assertTrue(body.contains("페이지 당 게시글 수"));
    }

    @Test
    @DisplayName("중복 등록 Bean Validation 검증 체크")
    void duplicateBIdCheckResponseTest() throws Exception {
        service.save(boardForm);

        String body = mockMvc.perform(post("/admin/board")
                .param("bId", boardForm.getBId())
                .param("boardNm", boardForm.getBoardNm())
                .param("rowsPerPage", String.valueOf(boardForm.getRowsPerPage())).with(csrf()))
                .andReturn().getResponse().getContentAsString();

        String message = MessageBundle.getMessage("Duplicate.boardConfig.bId");
        assertTrue(body.contains(message));
    }
}
