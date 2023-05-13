package org.study.models.cs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.study.commons.constants.QnaCategory;
import org.study.commons.constants.ReportStatus;
import org.study.controllers.admin.cs.QuestionConfig;
import org.study.entities.Question;
import org.study.repositories.cs.QuestionRepository;

@Service
public class QuestionRegisterService {

    @Autowired
    private QuestionRepository repository;

    @Autowired
    private QuestionValidator validator;


    public void qsRegister(QuestionConfig config) {
        qsRegister(config, null);
    }

    public void qsRegister(QuestionConfig config, Errors errors) {
        if(errors != null && errors.hasErrors()) {
            return;
        }

        validator.check(config, errors);


        /**
         * 엔티티가 이미 등록되어 있으면 기존 엔티티 가져오고(수정)
         * 없다면 새로운 엔티티로 변환 QuestionConfig.of(config);(생성)
         */
        Question question = null;
        Long code = config.getQsCode();
        if(code != null && repository.exists(code)) {
            question = repository.findById(code).orElseGet(() -> QuestionConfig.of(config));
            // 조회된 데이터 -> 영속상태 Question 엔티티 -> 수정
        } else {
            question = new Question(); // 비 영속상태 -> 추가
            question.setQsCode(code);
        }

        question.setQsCode(code);
        question.setSubject(config.getSubject());
        question.setContent(config.getContent());
        question.setCsProcess(ReportStatus.valueOf(config.getCsProcess()));
        question.setCategory(QnaCategory.valueOf(config.getCategory()));

        repository.saveAndFlush(question);
    }


}
