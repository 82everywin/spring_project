package org.study.repositories.cs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.study.entities.QReport;
import org.study.entities.Report;
import org.study.entities.User;

public interface ReportRepository extends JpaRepository<Report, Long>, QuerydslPredicateExecutor {

    //Report findByEmail(String email);

    /** 에러방지로 미구현된 메서드 주석처리 해놨어요~~ - 태인 */
//    default Report findReport(String email) {
//        QReport report = QReport.report;
//        Report rEmail = (Report) findOne(report.user.userEmail.eq(email)).orElse(null);
//        return rEmail;
//    }

//     Report findByUser_userId(String userEmail);

    /**
     * 신고 등록 여부 체크
     *
     * @param code
     * @return
     */
    default boolean exists(Long code) {
        QReport report = QReport.report;
        return exists(report.code.eq(code));
    }

}
