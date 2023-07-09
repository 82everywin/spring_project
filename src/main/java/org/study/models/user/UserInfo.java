package org.study.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 스프링 시큐리티 UserDetails 재정의
 *
 */
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserInfo implements UserDetails {

    private Long userNo;
    private String userEmail;
    private String userPw;
    private String userNm;
    private String userNickNm;
    private String cellPhone;
    private String gender;
    private String nowPw; // 마이페이지 비밀번호 체크용
    private String userPwCk; // 비밀번호 확인

    // 탈퇴일자에 필요한 맴버 변수
    // private LocalDateTime exitDate;

    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
