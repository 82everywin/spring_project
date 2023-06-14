package org.study.controllers.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.study.commons.constants.Gender;
import org.study.entities.User;
import java.util.Calendar;

/**
 * 회원 가입 커맨드 객체 생성 클래스
 *
 */
@Data
public class UserJoin {

    @NotBlank @Email
    private String userEmail; // 이메일

    @NotBlank
    private String userPw; // 비밀번호

    @NotBlank
    private String userPwCk; // 비밀번호 확인

    @NotBlank
    private String userNickNm; // 닉네임

    @NotBlank
    private String userNm; // 회원명

    @Enumerated(EnumType.STRING)
    private Gender gender ;

    private Calendar birth; //생년월일

    private String cellphone; //휴대전화번호

    @AssertTrue
    private boolean termsAgree; // 회원가입 약관 동의

    // User 엔티티로 변환
    public static User of(UserJoin userJoin) {

        return new ModelMapper().map(userJoin, User.class);
    }
}
