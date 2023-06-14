package org.study.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.study.commons.constants.ReportStatus;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes={
        @Index(name="idx_user_createdAt", columnList = "createdAt DESC")
})
@EntityListeners(AuditingEntityListener.class)
public class Report extends BaseEntity {

    @Column(length = 20)
    private String division; // 유입 구분

    @Id
    @GeneratedValue
    @Column(length = 8)
    private Long code;

    @Column(nullable=false, length=45)
    private String userNm;

    @Column(nullable=false)
    private String email; // 이메일은 비밀번호 찾기시 초기화 메일 전송으로 사용되므로 필수

    @Column(length=11)
    private String cellPhone;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime reportDt; // 신고 일자, BaseEntity - createdBy 가져와서 써야하는지?

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ReportStatus status = ReportStatus.READY;

    // 신고 사유 관리
    private String reason;  // 신고 사유

    private boolean useStatus;  // 사용 여부

}