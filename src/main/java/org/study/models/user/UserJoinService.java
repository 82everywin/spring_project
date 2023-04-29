package org.study.models.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.study.commons.constants.UserRole;
import org.study.controllers.user.user.UserJoin;
import org.study.entities.User;
import org.study.repositories.UserRepository;


@Service
@RequiredArgsConstructor
public class UserJoinService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;


    public void join(UserJoin join){


        String hash = passwordEncoder.encode(join.getUserPw());

        User user = User.builder()
                .userEmail(join.getUserEmail())
                .userPw(hash)
                .userNm(join.getUserNm())
                .userNickNm(join.getUserNickNm())
                .cellPhone(join.getCellphone())
                .gender(join.getGender())
                .role(UserRole.USER) //기본
                .birth(join.getBirth())
                .build();

        repository.save(user);


    }
}
