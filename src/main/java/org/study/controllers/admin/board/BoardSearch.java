package org.study.controllers.admin.board;

import lombok.Data;

/**
 *  게시판 설정 검색
 */
@Data
public class BoardSearch {
    private int page = 1; // 기본값 1
    private int limit = 20; // 1페이지당 20개씩
    private String sopt; // 검색 조건
    private String skey; // 검색 키워드
}