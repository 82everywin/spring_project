package org.study.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommunityController {
    /**
     * <커뮤니티> 클릭하면 나오는 페이지
     *
     * @return
     */
    @GetMapping("/user/community")
    public String community() {return "/front/community";}
}