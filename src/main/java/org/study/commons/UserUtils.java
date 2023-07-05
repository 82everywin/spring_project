package org.study.commons;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.study.commons.constants.UserRole;
import org.study.entities.User;
import org.study.models.user.UserInfo;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final HttpSession session;

    /**
     * 로그인 여부 체크
     *
     * @return
     */
    public boolean isLogin() {

        return getUser() != null;
    }

    /**
     * 관리자 여부 체크
     *
     * @return
     */
    public boolean isAdmin() {
        User user = getEntity();

        if (user != null && user.getRole() == UserRole.ADMIN) {
            return true;
        }

        return false;
    }

    /**
     * 회원번호와 로그인한 회원번호가 일치 체크
     *
     * @param userEmail
     * @return
     */
    public boolean isMine(String userEmail) {

        return isLogin() && getUser().getUserEmail() == userEmail;
    }


    /**
     * 회원정보 조회
     *
     * @return
     */
    public UserInfo getUser() {
        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");

        return userInfo;
    }

    /**
     * 엔티티로 가져오기
     * 
     * @return
     */
    public User getEntity() {
        UserInfo userInfo = getUser();
        return userInfo != null ? new ModelMapper().map(userInfo, User.class) : null;
    }
}
