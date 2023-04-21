package org.study.admin.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.study.commons.validators.PasswordValidator;
import org.study.controllers.user.UserJoinValidator;
import org.study.models.user.UserJoinService;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * UserJoinValidator 파일을 통해서 각 항목에 대한 유효성 검사를 진행할 예정입니다.
 *
 *
 */
@SpringBootTest
public class UserJoinValidatorTest {


    @Autowired
    private UserJoinValidator validator;

    @Autowired
    private UserJoinService service;

    /** 유효성 검사 S */

    // 필수 항목 체크
    @Test
    @DisplayName("필수항목 체크 -  BadRequestException 발생")
    void join_User_Null_BadRequest() {
        //assertThrows(org.study.admin.user.BadRequestException.class, () -> {
        //validator.validate();
        //});
    }

    @Test
    @DisplayName("자동생성- 회원번호 자동생성되는지 체크")
    void join_Auto_Increment_UserNo(){

    }

    @Test
    @DisplayName("필수항목 유효성 검사-이메일 형식체크")
    void join_Valid_Essential_Of_Email(){

    }

    @Test
    @DisplayName("필수항목 유효성 검사-비밀번호 암호화 강도")
    void join_Valid_Essential_Of_PassWord(){

    }

    @Test
    @DisplayName("필수항목 유효성 검사-비밀번호 확인 중복체크")
    void join_Valid_Essential_Of_PassWord_Check(){

    }

    @Test
    @DisplayName("생성된 해시과 실제 비밀번호 일치하는지 체크")
    void joinUser_Password_Equals_Hash(){

    }

    @Test
    @DisplayName("Email로 DB-회원 목록 조회 체크")
    void check_UserList_for_Email(){

    }



    @Test
    @DisplayName("필수항목 유효성 검사-생년월일_2023년 기준, 1900년이하 존재 x")
    void join_Valid_Essential_Of_Birth(){

    }

    @Test
    @DisplayName("필수항목 유효성 검사-휴대전화번호_양식에 맞는지 체크")
    void join_Valid_Essential_Of_CellPhone(){

    }

    /** 유효성 검사 E */
}
