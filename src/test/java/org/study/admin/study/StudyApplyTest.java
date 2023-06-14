package org.study.admin.study;

import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.study.commons.constants.Gender;
import org.study.commons.constants.RegionType;
import org.study.commons.constants.Status;
import org.study.commons.constants.UserRole;
import org.study.entities.Study;
import org.study.entities.StudyCategory;
import org.study.entities.User;
import org.study.models.study.StudyApplyService;
import org.study.repositories.StudyCategoryRepository;
import org.study.repositories.StudyRepository;
import org.study.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
public class StudyApplyTest {
    /**
    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyCategoryRepository scRepository;

    // private final StudyApplyService applyService;
    @Autowired
    private StudyApplyService applyService;
    @Test
    @DisplayName("스터디 신청 시 제대로 db에 들어가는 지 ")
    public void apply() {
        Study study = Study.builder()
                .studyNm("공부해요")
                .category("study")
                .maxMember(10L)
                .numOfWeek("주3회")
                .approveStatus(Status.APPLY)
                .regionType(RegionType.ONLINE)
                .simpleIntro("안녕하세용~")
                .Introduction("Lob은 largeObject의 줄임말이었다...하하")
                .build();
    }


    /**
     * @Test Case - StudyApplyService::apply
     *
     * 스터디 신청이 되면 관리자페이지의 승인을 거치게 되며
     * 승인이라는 상태 값을 통해서 신청 상태가 관리가 됩니다.
     *
     * 테스트는 등록과 승인이라는 측면에서 진행되며 기능이 구현됩니다.
     * 1. 등록에서의 최종 목표는 예외 발생 없이 등록이 되는 것
     * 2. 제대로 등록하기 위한 유효성 검사 항목이 다음과 같이 정리됩니다.
     */
    /**
    @BeforeEach
    public void UserInsert(){
        for(int i=1;i<10;i++) {
            User user = User.builder()
                    .userEmail("user0"+i+"@korea.org")
                    .userNm("사용자0"+i)
                    .userPw("12345678")
                    .userNickNm("사용자0"+i)
                    .gender(Gender.MAN)
                    .role(UserRole.USER)
                    .cellPhone("01000001111")
                    .build();

            userRepository.save(user);
        }
        User user1 = userRepository.findByUserEmail("userO1@korea.org");


        /**
         * 회원이 스터디 개설 신청한게 db에 들어갔는지
         */
    /**
        List<Study> studies = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Study study = Study.builder()
                    .studyNm("공부해요")
                    .category("study")
                    .maxMember(10L)
                    .numOfWeek(10L)
                    .approveStatus(Status.APPLY)
                    .regionType(RegionType.ONLINE)
                    .simpleIntro("안녕하세용~")
                    .Introduction("Lob은 largeObject의 줄임말이었다...하하")
                    .user()
                    .build();

            studyRepository.save(study);
            studies.add(study);
        }
        System.out.println(studies);

    }



    /** 단위 테스트 S */

    // 단위 테스트는 구현된 Service가 정상 동작하는지 단위별로 테스트 합니다.
    // junit5의 단언(assert)와 mockito를 주로 사용하게 됩니다.

    @Test
    @DisplayName("스터디 개설 신청이 완료되면 예외가 발생하지 않음(최종 목적)")
    public void applySuccess() {
    }

    @Test
    @DisplayName("스터디 신청 필수 항목 유효성 검사 - 카테고리, 한줄 소개글, 소개내용, 메인 사진")
    public void applyValidationTest(){
        // 카테고리는 나중에 검사



    }

    /**
     * 신청 데이터 항목을 커맨드 객체에 담아 두고 그 데이터를 가지고
     * apply() 함수를 호출해서 최종 처리 결과를 보고
     * H2 데이터베이스에 추가된 내용과 비교하여 각 항목의 일치여부를 검증한다.
     */
    @Test
    @DisplayName("스터디 신청 데이터와 DB 데이터의 일치 여부 체크")
    public void assertApplyToDB(){


    }


    /** 단위 테스트 E */

    /**
     * 스터디에 들어가있는 회원 조회 -> querydsl 사용
     */

}

