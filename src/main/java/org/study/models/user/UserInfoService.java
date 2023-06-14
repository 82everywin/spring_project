package org.study.models.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.study.entities.User;
import org.study.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUserEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        // 사용자 권한 
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString()));

        return UserInfo.builder()
                .userNo(user.getUserNo())
                .userEmail(user.getUserEmail())
                .userNm(user.getUserNm())
                .userNickNm(user.getUserNickNm())
                .userPw(user.getUserPw())
                .cellPhone(user.getCellPhone())
                .gender(user.getGender())
                .authorities(authorities) // 사용자 권한 설정
                // .exitDate(user.getExitDate()) // 탈퇴일자
                .build();
    }
}
