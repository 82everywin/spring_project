package org.study.controllers.admin.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class CommunitySearch {

    private int page = 1;
    private int limit = 20;
    private String bId;
    private Long gid;
    private String poster;
    private String subject;
    private String content;
    private String sopt; // 선택옵션
    private String skey; // 키워드

}
