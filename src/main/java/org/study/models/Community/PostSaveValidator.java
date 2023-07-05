package org.study.models.Community;

import org.springframework.stereotype.Component;
import org.study.commons.validators.PostValidationException;
import org.study.commons.validators.RequiredCheckValidator;
import org.study.commons.validators.ServiceValidator;
import org.study.controllers.community.PostForm;

/**
 * 게시글 등록 Validator
 */
@Component
public class PostSaveValidator implements ServiceValidator<PostForm>, RequiredCheckValidator {
    @Override
    public void check(PostForm postForm) {
        /** PostConfig가 null 값일 경우 예외 발생 */
        nullCheck(postForm, new PostValidationException("BadRequest"));

        /** 필수항목 체크 */
        requiredCheck(postForm.getBId(), new PostValidationException("BadRequest"));
        requiredCheck(postForm.getGid(), new PostValidationException("BadRequest"));
        requiredCheck(postForm.getPoster(), new PostValidationException("NotBlank.postForm.poster"));
        requiredCheck(postForm.getSubject(), new PostValidationException("NotBlank.postForm.subject"));
        requiredCheck(postForm.getContent(), new PostValidationException("NotBlank.postForm.content"));
    }
}
