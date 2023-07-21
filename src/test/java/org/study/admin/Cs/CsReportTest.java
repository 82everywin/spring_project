package org.study.admin.Cs;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.study.commons.constants.ReportStatus;
import org.study.controllers.admin.cs.CsConfig;
import org.study.controllers.user.user.UserJoin;
import org.study.models.cs.CsRegisterService;
import org.study.models.cs.ReportListService;
import org.study.repositories.cs.ReportRepository;
import org.study.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Cs는 가입된 회원을 전제로 신고합니다.
 * 유입 구분은 스터디 인지 , 커뮤니티 인지 구분합니다.
 * 파일명 : "CsReportService"
 */
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class

CsReportTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportListService listService;
    @Autowired
    private CsRegisterService registerService;

    private UserJoin userJoin;
    private CsConfig csConfig;

    @BeforeEach
    public void insertUser(){
        userJoin = new UserJoin();
        userJoin.setUserNm("홍다경");
        userJoin.setUserEmail("test2@naver.com");
        userJoin.setUserPw("aA!1234567");
        userJoin.setCellPhone("010-1234-5678");
        userJoin.setUserNickNm("밤빵");
        userJoin.setBirth("10/20");

        csConfig = new CsConfig();
        csConfig.of(csConfig).getUser().setUserEmail("test2@naver.com");
        csConfig.setDivision("board");
        csConfig.setCode(58670212L);
        csConfig.setDetail("빵꾸똥꾸라고 욕했어요.");
        csConfig.setStatus(ReportStatus.CLEAR.toString());
        csConfig.setProcess("욕설로 5회 신고 확인되어 탈퇴처리되었습니다.");

        registerService.register(csConfig);
    }




    /**
     * 회원 조회를 통해 가져올 항목을 결정합니다.
     * 회원명, 이메일, 전화번호 , 가입일시 등이 신고한 회원에 대한 정보를 볼 수 있습니다.
     * OR
     * 회원 목록 전체를 가져올 수 있습니다.
     */
    @Test
    @DisplayName("가입회원이면 회원 목록 가져오는데 성공하는지 체크")
    void report_User_List(){

        


    }
    /**
     * 신고 세부 유형에따라 신고대상 조회
     * 파일명:'CsRegisterService'의 reportTarget()
     * 회원신고일경우 - 회원정보조회/ 게시글신고일 경우 - 게시글 조회
     */
    @Test
    @DisplayName("신고대상 회원일경우 신고된 회원정보 조회되는지 체크")
    void report_Target_Member(){

    }

    @Test
    @DisplayName("신고대상 게시글일경우 신고된 게시글정보 조회되는지 체크")
    void report_Target_Boardlist(){

    }

    /**
     * 파일명 : 'ReportRegisterService'의 report() 함수 호출해서
     * 필수 항목 (신고 사유 )에 대해 체크합니다.
     */
    @Test
    @DisplayName("필수 항목 - 신고사유 값 null이면 -ReportFailException 예외 발생")
    void report_Reason_Null_Then_Fail(){

    }

    // 미필요시 삭제 가능합니다. service에서 바로 ReportStatus.READY으로 넣으면 되기에 생략 가능합니다.
    @Test
    @DisplayName("신고가 접수되면 기본 상태인 ReportStatus.READY로 입력되는지 체크")
    void report_Status_READY(){

    }

    @Test
    @DisplayName("신고한 게시글이 DB에 성공적으로 입력시 true 반환(최종목적)")
    void reportSuccess(){

    }


}
