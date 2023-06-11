package org.study.models.studies.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.study.commons.constants.Status;
import org.study.controllers.admin.study.StudyListForm;
import org.study.entities.Studies;
import org.study.repositories.StudiesRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * 스터디 목록 수정
 *
 */
@Service
@RequiredArgsConstructor
public class StudyListUpdateService {
    private final StudiesRepository studiesRepository;
    public void update(StudyListForm listForm) {
        List<Integer> nums = listForm.getNum();
        List<Long> studyNos = listForm.getStudyNo();
        List<String> statuses = listForm.getStatus();
        if (nums == null || nums.isEmpty()) {
            throw new StudyAdminException("수정할 스터디를 선택하세요.");
        }

        List<Studies> items = new ArrayList<>();
        for (int num : nums) {
            Long studyNo = studyNos.get(num);
            Status status = Status.valueOf(statuses.get(num));
            Studies item = studiesRepository.findById(studyNo).orElse(null);
            if (item == null) {
                continue;
            }

            item.setStatus(status);
            items.add(item);
        }

        studiesRepository.saveAllAndFlush(items);
    }
}
