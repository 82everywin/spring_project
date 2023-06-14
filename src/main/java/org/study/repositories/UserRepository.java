package org.study.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.study.entities.User;
import org.study.entities.QUser;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    // 아이디로 회원 정보 조회
   User findByUserEmail(String userEmail);

    /**
     * 회원이 등록되어 있는지 아이디로 체크
     *
     * @param userEmail
     * @return {boolean}
     */
    default boolean isUserExists(String userEmail) {
        QUser user = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.userEmail.eq(userEmail));

        long cnt = this.count(builder);
        return cnt > 0;
    }


}
