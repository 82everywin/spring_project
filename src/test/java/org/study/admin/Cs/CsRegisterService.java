package org.study.admin.Cs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.study.commons.constants.ReportStatus;
import org.study.entities.Report;
import org.study.repositories.ReportRepository;

@Service
public class CsRegisterService {

    @Autowired
    private ReportRepository repository;

    public void config(CsConfig config) {
        config(config, null);
    }

    public void config(CsConfig config, Errors errors) {
        if(errors != null && errors.hasErrors()) {
            return;
        }

        /**
         * 엔티티가 이미 등록되어 있으면 기존 엔티티 가져오고(수정)
         * 없다면 새로운 엔티티로 변환 BoardConfig.of(config);(생성)
         */
        Long code = config.getCode();
        Report report = null;
        if(code != null && repository.exists(code)) {
            report = repository.findById(code).orElseGet(() -> CsConfig.of(config));
            report.setDivision(config.getDivision());
            report.setCode(code);
            report.setDetail(config.getDetail());
            report.setStatus(ReportStatus.valueOf(config.getStatus()));
            report.setProcess(config.getProcess());
            /** 기본 설정이 맞는지? */
        }

        if(report == null) {
            report = CsConfig.of(config);
        }

        repository.saveAndFlush(report);
    }

    /**
    *신고 세부 유형에따라 신고대상 조회
    *회원신고일경우 - 회원정보조회/ 게시글신고일 경우 - 게시글 조회
     */
    public void reportTarget() {

    }
}


