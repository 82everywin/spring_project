package org.study.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.study.commons.UserUtils;
import org.study.controllers.user.user.UserJoin;
import org.study.entities.User;
import org.study.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserEditService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final UserUtils userUtils;

 //   private final UserEditValidator editValidator;

    public void userEdit(UserJoin join, Errors errors) {

        String updateNick = join.getUserNickNm();
        String updatePw = join.getUserPw();
        String updatePhone = join.getCellPhone();

        UserInfo user = userUtils.getUser();

        User nowUser = repository.findByUserEmail(user.getUserEmail());




        String hash = passwordEncoder.encode(updatePw);

        nowUser.setUserNickNm(updateNick);
        nowUser.setUserPw(hash);
        nowUser.setCellPhone(updatePhone);

        repository.saveAndFlush(nowUser);
    }


}
