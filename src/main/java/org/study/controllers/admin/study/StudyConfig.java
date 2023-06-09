package org.study.controllers.admin.study;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.study.commons.constants.RegionType;
import org.study.commons.constants.Status;
import org.study.entities.User;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyConfig {
    private String mode; //update이면 수정모드

    @NotNull
    private Long studyCode; //스터디코드

    @NotBlank
    private String studyNm; //스터디명


    @NotBlank
    private String category; //카테고리

    private LocalDateTime requestDt;//개설신청일시


    private String approveStatus = Status.APPLY.toString(); //승인상태

    private LocalDateTime regStatusDt;//상태처리일시

    @NotNull
    @Range(min = 0, max = 1000)
    private Long maxMember; //신청최대인원수

    private Long remainSeat = 0L; //남은 자리수


    private boolean activeStatus; //모집상태(모집중 - true, 모집마감(자리없음) -false)
    @NotBlank
    private String numOfWeek; //스터디 주당횟수

    @NotNull
    private String regionType = RegionType.ONLINE.toString(); //지역(온라인/ 오프라인 -추후 프로그램내에서 시/도/군/구 값도 추가되도록 진행예정 )

    @NotBlank
    private String simpleIntro; //한줄 소개글

    @NotBlank
    @Lob
    private String introduction; //소개글

    private String addressDo;

    private String addressSiGunGu;

    private int page = 1; // 페이지 번호
    private int limit = 20; // 1페이지당 출력 갯수

    private User user;
}
