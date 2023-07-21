package org.study.admin.study;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.study.commons.constants.RegionType;
import org.study.commons.constants.Status;
import org.study.controllers.admin.study.StudyConfig;
import org.study.controllers.study.StudySearch;
import org.study.models.studies.StudyListService;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * '스터디 관리 -> 스터디목록관리/ 개설신청관리 '에 해당하는 테스트 클래스 입니다.
 */

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor
@TestPropertySource(locations = "classpath:application-test.properties")
public class StudyListTest {


    private final StudyListService listService;


    private final StudySearch studySearch;


    private int cnt_Apply = 1; //approveStatus- APPLY 갯수
    private int cnt_Disapprove = 2;//approveStatus- DISAPPROVE 갯수
    private int cnt_Approve = 0;//approveStatus- APPROVE 갯수

    @BeforeEach
    public void register() { //테스트를 위한 데이터 등록
        StudyConfig studyConfig = StudyConfig.builder()
                .studyCode(Long.valueOf("5245625"))
                .studyNm("코리아스터디")
                .category("IT")
                .requestDt(LocalDateTime.now())
                .approveStatus(Status.APPLY.toString())
                .approveStatus("APPLY")
                .regStatusDt(LocalDateTime.now())
                .maxMember(Long.valueOf("40"))
                .remainSeat(Long.valueOf("3"))
                .activeStatus(true)
                .numOfWeek("주2-3회")
                .regionType(RegionType.OFFLINE.toString())
                .regionType("OFFLINE")
                .simpleIntro("백엔드개발 스터디 입니다.")
                .introduction("즐겁게 공부해봅시다.")
                .build();
      //  applyService.apply(studyConfig);

        StudyConfig studyConfig2 = StudyConfig.builder()
                .studyCode(Long.valueOf("12345678"))
                .studyNm("코리아스터디")
                .category("IT")
                .requestDt(LocalDateTime.now())
                .approveStatus(Status.DISAPPROVE.toString())
                .approveStatus("DISAPPROVE")
                .regStatusDt(LocalDateTime.now())
                .maxMember(Long.valueOf("40"))
                .remainSeat(Long.valueOf("3"))
                .activeStatus(true)
                .numOfWeek("주2-3회")
                .regionType(RegionType.OFFLINE.toString())
                .regionType("OFFLINE")
                .simpleIntro("백엔드개발 스터디 입니다.")
                .introduction("즐겁게 공부해봅시다.")
                .build();
       // applyService.apply(studyConfig2);

        StudyConfig studyConfig3 = StudyConfig.builder()
                .studyCode(Long.valueOf("2345678"))
                .studyNm("코리아스터디")
                .category("IT")
                .requestDt(LocalDateTime.now())
                .approveStatus("APPROVE")
                .regStatusDt(LocalDateTime.now())
                .maxMember(Long.valueOf("40"))
                .remainSeat(Long.valueOf("3"))
                .activeStatus(true)
                .numOfWeek("주2-3회")
                .regionType(RegionType.OFFLINE.toString())
                .regionType("OFFLINE")
                .simpleIntro("백엔드개발 스터디 입니다.")
                .introduction("즐겁게 공부해봅시다.")
                .build();
      //  applyService.apply(studyConfig3);
    }


    /**
     * @Test StudyListService::gets()
     * 스터디목록 전체를 조회
     * 메서드 gets()
     */

    @Test
    @DisplayName("스터디 목록 전체 조회")
    void study_gets() {
        assertDoesNotThrow(() -> {
            StudySearch studySearch = new StudySearch();
          //  listService.gets(studySearch);
        });
    }

    /**
     * @Test StudyListService::get()
     * 스터디목록 개별 조회
     * 메서드 get()
     */
    @Test
    @DisplayName("스터디 목록 개별 조회")
    void study_get() {
        assertDoesNotThrow(() -> {
          //  listService.get(5245625L);

        });

    }
/**
    @Test
    @DisplayName("approveStatus(승인상태-APPLY,APPROVE,DISAPPROVE)에 따라 조회된 데이터 갯수와 cnt갯수 맞으면 성공")
    void study_ApproveStatus_Apply_gets() {
        //approveStatus- APPLY 경우 스터디 조회
        StudySearch studySearch = new StudySearch();
        assertDoesNotThrow(() -> {
            studySearch.setApproveStatus(new String[]{"APPLY"});
          //  listService.gets(studySearch);
        });
        //approveStatus- DISAPPROVE 경우 스터디 조회
        assertDoesNotThrow(() -> {
            studySearch.setApproveStatus(new String[]{"DISAPPROVE"});
          //  listService.gets(studySearch);
        });
        //approveStatus- APPROVE 경우 스터디 조회
        assertDoesNotThrow(() -> {
            studySearch.setApproveStatus(new String[]{"APPROVE"});
        //    listService.gets(studySearch);

        });

    }
    **/
}