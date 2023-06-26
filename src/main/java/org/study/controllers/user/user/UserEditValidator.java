package org.study.controllers.user.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.study.commons.UserUtils;
import org.study.commons.validators.CellPhoneValidator;
import org.study.entities.User;
import org.study.models.user.UserInfo;
import org.study.repositories.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserEditValidator implements Validator, CellPhoneValidator {

    private final UserRepository repository;

    private final UserUtils userUtils;

    @Override
    public boolean supports(Class<?> clazz) {

        return UserJoin.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if(errors != null && errors.hasErrors()) {
            return;
        }

        UserJoin userJoin = (UserJoin) target;

        UserInfo userInfo = userUtils.getUser();
        User user = repository.findByUserEmail(userInfo.getUserEmail());

        String nickNm = userJoin.getUserNickNm();
        String nowPw = userJoin.getNowPw();
        String updatePw = userJoin.getUserPw();
        String updatePwCk = userJoin.getUserPwCk();
        String phone = userJoin.getCellPhone();

        if (nickNm == null || nickNm.isBlank()) {
           nickNm = user.getUserNickNm();
        }

        if (updatePw == null) {
            updatePw = user.getUserPw();
        }

        if (phone == null) {
            phone = user.getCellPhone();
        }


        if (nickNm != null && (nickNm.length() < 2 || nickNm.length() > 10)) {
            errors.rejectValue("userNickNm", "NickLengthError","닉네임은 2~10자로 입력해주세요.");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (nowPw != null && !encoder.matches(nowPw, user.getUserPw())) { // 해시로 변환 전 비번하고 현재 비밀번호 확인 할 때 같은지 체크
            errors.rejectValue("nowPw", "IncorrectNowPw", "입력한 비밀번호가 현재 비밀번호와 일치하지 않습니다.");
        }


        if (updatePw != null && updatePwCk!= null && !updatePw.equals(updatePwCk)) {
            errors.rejectValue("userPwCk", "PasswordCheck","새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 새 비번이 숫자, 영어 대소문자, 특문 1개 이상 포함, 8~16자리 확인
        String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{8,16}$";
        Matcher matcher = Pattern.compile(pwPattern).matcher(updatePw);
        if (updatePw != null && !matcher.matches() || matcher == null){
            errors.rejectValue("userPw", "WrongPassword", "비밀번호는 특수문자(~!@#%\\^&*()-)중 하나를 포함하고 영어 대소문자, 숫자 1개를 포함해야합니다.");
        }


        phone = phone.replaceAll("\\D", "");
        String pattern = "^01[016]\\d{3,4}\\d{4}$";


        if(phone != null && !phone.matches(pattern)) {
            errors.rejectValue("cellphone", "WrongPhoneNum", "휴대번호 양식이 잘못되었습니다.");
        }
    }
}
