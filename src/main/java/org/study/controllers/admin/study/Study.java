package org.study.controllers.admin.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.study.commons.constants.RegionType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Study {

    private Long studyCode; //스터디코드

    private String studyNm; //스터디명

    private String category; //카테고리
    private LocalDateTime requestDt;//개설신청일시
    private boolean approveStatus; //승인상태
    private LocalDateTime regStatusDt;//상태처리일시
    private Long maxMember; //신청최대인원수
    private Long numOfWeek; //스터디 주당횟수
    private RegionType regionType = RegionType.ONLINE; //지역(온라인/ 오프라인 -추후 프로그램내에서 시/도/군/구 값도 추가되도록 진행예정 )

    private String simpleIntro; //한줄 소개글

    private String Introduction; //소개글
}
