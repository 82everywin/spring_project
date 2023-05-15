package org.study.models.studies;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.study.commons.UserUtils;
import org.study.commons.constants.RegionType;
import org.study.commons.constants.Status;
import org.study.controllers.study.StudyForm;
import org.study.entities.Studies;
import org.study.repositories.StudiesRepository;


/**
 * 스터디 등록, 수정
 *
 */
@Service
@RequiredArgsConstructor
public class StudySaveService {

    private final StudiesRepository studiesRepository;
    private final UserUtils userUtils;

    public void save(StudyForm studyForm) {
        Studies studies = null;
        Long studyNo = studyForm.getStudyNo();
        if (studyNo != null) {
            studies = studiesRepository.findById(studyNo).orElse(null);
        }

        if (studies == null) {
            studies = new Studies();
            // 회원 정보는 최초 등록시에만 저장하고 변경이 되지 않도록 처리
            studies.setUser(userUtils.getEntity());
        }

        studies.setGid(studyForm.getGid());
        studies.setStatus(Status.valueOf(studyForm.getStatus()));
        studies.setCateCd(studyForm.getCateCd());
        studies.setMaxMember(studyForm.getMaxMember());
        studies.setNumOfWeek(studyForm.getNumOfWeek());
        studies.setRegionType(RegionType.valueOf(studyForm.getRegionType()));
        studies.setSimpleIntro(studyForm.getSimpleIntro());
        studies.setIntroduction(studyForm.getIntroduction());

        studyForm.setStudyNo(studies.getStudyNo());
    }
}
