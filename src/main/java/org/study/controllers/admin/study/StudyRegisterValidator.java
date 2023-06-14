package org.study.controllers.admin.study;

import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.study.repositories.StudyRepository;

@RequiredArgsConstructor
public class StudyRegisterValidator implements Validator {

    private final StudyRepository studyRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        // 1. 필수 항목 체크
        Study study = (Study) target;
        String studyNm = study.getStudyNm();
        String category = study.getCategory();
        Long maxMember = study.getMaxMember();
        Long numOfWeek = study.getNumOfWeek();
        //String regionType=study.getRegionType();
        String simpleIntro = study.getSimpleIntro();
        String introduction = study.getIntroduction();

        //1. 가입된 회원이 개설하는 것인지 체크
        // or 맵핑된 User에서 회원 갖고올거면 안해도 됨

        // 2. 신청 최대 인원수
        

    }

    /**
     * 스터디 개설을 신청할 때 필요한 유효성 검사
     *
     * 필수 항목 채크
     * 1. 가입된 회원이 개설을 신청하는 것인지 체크
     * 2. 신청 최대인원수 체크
     * 3. 스터디 주당 횟수 체크 ( 관리자가 어떻게 할 것인지에 따라 다름)
     * 4. 온라인/오프라인에 따른 지역 선택 체크
     *      1) 온라인
     *      2) 오프라인 선택시 시/도, 군/구 설정 ->
     */

}
