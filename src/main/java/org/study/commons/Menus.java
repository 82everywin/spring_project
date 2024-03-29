package org.study.commons;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Menus {

    public List<MenuForm> _gets(String code) {
        return Menus.gets(code);
    }

    public static List<MenuForm> gets(String code) {
        List<MenuForm> menus = new ArrayList<>();

        /** admin */
        if (code.equals("board")) { // board 하위 메뉴
            menus.add(new MenuForm("board", "게시판 목록", "/admin/board"));
            menus.add(new MenuForm("register", "게시판 등록 & 수정", "/admin/board/register"));
        }


        /** front */
        if (code.equals("freetalk")) {
            menus.add(new MenuForm("freetalk", "자유게시판@", "/community/list"));
        }
        return menus;
    }

    public static String getSubMenuCode(HttpServletRequest request){
        String uri = request.getRequestURI();


        return uri.substring(uri.lastIndexOf('/') + 1);
    }
}
