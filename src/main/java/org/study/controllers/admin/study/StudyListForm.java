package org.study.controllers.admin.study;

import lombok.Data;

import java.util.List;

/**
 * 리스트 수정 양식 데이터
 *
 */
@Data
public class StudyListForm {
    private List<Integer> num;
    private List<Long> studyNo;
    private List<String> status;
}
