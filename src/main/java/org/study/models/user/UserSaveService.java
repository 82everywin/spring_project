package org.study.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.study.controllers.user.UserJoin;
import org.study.entities.User;
import org.study.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserSaveService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public void save(UserJoin userJoin) {
        User user;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserInfo) { // 회원 정보 수정
            UserInfo userInfo = (UserInfo)auth.getPrincipal();
            user = repository.findById(userInfo.getUserNo()).orElse(UserJoin.of(userJoin));
            user.setUserNm(userJoin.getUserNm());
            user.setUserEmail(userJoin.getUserEmail());
            user.setUserPw(userJoin.getUserPw());
            user.setUserNickNm(userJoin.getUserNickNm());
            user.setCellPhone(userJoin.getCellphone());
        } else { // 회원 정보 추가
            user = UserJoin.of(userJoin);
        }

        if (userJoin.getUserPw() != null) { // 비밀번호 수정이 있는 경우 변경 처리
            String hash = passwordEncoder.encode(userJoin.getUserPw());
            user.setUserPw(hash);
        }

        repository.saveAndFlush(user);
    }
}
