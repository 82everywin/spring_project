package org.study.models.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.removeAttribute("requiredUserEmail");
        session.removeAttribute("requiredUserPw");
        session.removeAttribute("loginFail");
        session.removeAttribute("userEmail");

        /** 회원 정보 편의상 세션에 유지 */
        UserInfo userInfo = (UserInfo)authentication.getPrincipal();
        session.setAttribute("userInfo", userInfo);


        String url = request.getContextPath();
        response.sendRedirect(url);
    }
}
