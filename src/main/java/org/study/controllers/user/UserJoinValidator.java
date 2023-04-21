package org.study.controllers.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.study.admin.user.BadRequestException;
import org.study.commons.validators.CellPhoneValidator;
import org.study.commons.validators.PasswordValidator;
import org.study.repositories.UserRepository;

/**
 * 회원 가입 추가 유효성 검사
 *
 */
@Component
@RequiredArgsConstructor
public class UserJoinValidator implements Validator, CellPhoneValidator, PasswordValidator {

    @NonNull
    private UserRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserJoin.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserJoin userJoin = (UserJoin)target;

        String userEmail = userJoin.getUserEmail();
        String userPw = userJoin.getUserPw();
        String userPwCk = userJoin.getUserPwCk();
        String cellPhone = userJoin.getCellphone();
        String birth = userJoin.getBirth();

        /** 1. 이메일 중복 여부 S */
        if (userEmail != null && !userEmail.isBlank() && repository.isUserExists(userEmail)) {
            errors.rejectValue("userEmail", "user.validation.exists");
        }
        /** 1. 이메일 중복 여부 E */


        /** 2. 비밀번호 유효성 검사 S*/
        if(userPw != null && !userPw.isBlank()){
            // 일단 비밀번호 입력을 안했을 경우 NotBlank에서 에러 메세지
            if(!checkPassword(userPw)){
                // 8자리 이상, 특수문자가 들어가지 않는 경우 에러 메세지
                errors.rejectValue("userPw", "user.validation.checkPassword");
            }else if(!special_character(userPw)){
                // 정해준 특수문자(!?@#$^*+=-)를 기입하지 않는 경우 에러 메세지
                errors.rejectValue("userPw","user.validation.special_character");
            }else if(!repeat_character(userPw)){
                // 3번 이상 문자를 연속으로 입력한 경우 에러 메세지
                errors.rejectValue("userPw","user.validation.repeat_character");
            }
        }

        /** 2. 비밀번호 유효성 검사 E*/

        /** 3. 비밀번호 확인 체크 S */
        if (userPwCk != null && !userPwCk.isBlank() && !userPw.equals(userPwCk)) {

            errors.rejectValue("userPw", "user.validation.passwordIncorrect");
        }
        /** 3. 비밀번호 확인 체크 E */

        /** 4. 휴대전화번호 검증 S */
        if (cellPhone != null && !cellPhone.isBlank()) {
            cellPhone = cellPhone.replaceAll("\\D", "");
            userJoin.setCellphone(cellPhone);
            if (!checkCellPhoneNumber(cellPhone)) {
                errors.rejectValue("cellPhone", "Mobile.wrong");
            }
        }
        /** 4. 휴대전화번호 검증 E */
    }
}
