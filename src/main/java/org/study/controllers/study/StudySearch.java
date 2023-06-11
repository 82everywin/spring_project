package org.study.controllers.study;

import lombok.Data;

@Data
public class StudySearch {
    private int page = 1;

    private int limit = 20;

    private String skey;
    private String cateCd;

    private String sido;
    private String sigugun;

    private String status;
}
