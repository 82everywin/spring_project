package org.study.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.study.commons.constants.Gender;
import org.study.commons.constants.UserRole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * 회원 엔티티
 *
 */
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="member"
        ,indexes={ // 이메일 및 사용자 역할은 조회가 많이 될 수 있으므로 인덱스 부여
        // 관리자 페이지에서 최신 회원순으로 조회를 많이 할 수 있으므로 인덱스 부여
        @Index(name="idx_user_email", columnList = "userEmail"),
        @Index(name="idx_user_role", columnList = "role"),
        @Index(name="idx_user_createdAt", columnList = "createdAt DESC")
})
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column(unique=true, nullable=false, length=45)
    private String userEmail; //아이디(이메일 )

    @Column(nullable = false,length=35)
    private String userNm; // 회원명

    @Column(nullable = false, length=40)
    private String userNickNm; // 닉네임

    @Column(nullable=false, length=65)
    private String userPw;

    @Column(length=11)
    private String cellPhone;

    @Enumerated(EnumType.STRING)
    @Column(length=30)
    private Gender gender = Gender.MAN; // 성별, 기본값은 Man

    @Enumerated(EnumType.STRING)
    @Column(length=30, nullable = false)
    private UserRole role = UserRole.USER; // 사용자 역할, 기본값은 USER(일반사용자)
    
    /**
     * 추가해야하는 부분 ( !!중요!! UserInfo랑 UserInfoService 수정 !!중요!!)
     * ++ 이용제한은 UserSaveService에도 추가해야함
     * 탈퇴일자, 이용제한 ( 만약 라디오로 할거면 Enum으로, 캘린더로 할거면 이건 강사님한테 물어보던가 구글링 )
    */

    @OneToMany(mappedBy="user")
    private List<Study> studies = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<CommunityPost> communityPostLists = new ArrayList<>();
}
