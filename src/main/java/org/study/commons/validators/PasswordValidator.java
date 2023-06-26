package org.study.commons.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface PasswordValidator {

    // 비밀번호 유효성 검사
    default boolean checkPassword(String userPw ) {

        // ?= 모든 문자 . = 줄바꿈제외 모든 문자 한개
        // 소문자 대문자 = [a-zA-Z]
        // 모든 숫자 = [//d]
        // 특수문자 한개 =  [//W]
        String passPattern1 = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";

        return userPw.matches(passPattern1);
    }

    /**
     * 예외 상황
     * 1가지
     * 1) 8자 이상x 2) 영문 x 3) 숫자 4) 특수문자
     *
     * 2가지 (8자 이상 기본 충족 조건)
     * 1) 영문+숫자 2) 영문+특수문자 3) 숫자 + 특수문자
     */

    default boolean special_character(String userPw){
        // 특수문자 확인
        String passPattern2 =".*[!?@#$^*+=-].*";

        return userPw.matches(passPattern2);
    }

    // 연속 해서 사용하는 문자의 기준이 몇개인지 >> 3번 반복
    default boolean repeat_character(String userPw){
        // 반복된 문자 확인
        String passPattern3 = "(\\w)\\1{2,}";

        //
        return userPw.matches("^(?!.*" + passPattern3 + ").*$");
    }

}


