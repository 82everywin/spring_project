package org.study.models.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.study.commons.constants.board.AfterWriteTarget;
import org.study.commons.constants.board.SkinType;
import org.study.commons.constants.board.ViewType;
import org.study.controllers.admin.board.BoardConfig;
import org.study.controllers.admin.category.CategoryForm;
import org.study.entities.board.Board;
import org.study.repositories.board.BoardRepository;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class BoardConfigService {
    @Autowired
    private BoardRepository repository;

    @Autowired
    private BoardConfigValidator validator;

    public void config(BoardConfig config) {
        config(config, null);
    }

    public void config(BoardConfig config, Errors errors) {
        if (errors != null && errors.hasErrors()) {
            return;
        }

        validator.check(config, errors);

        /**
         * mode가 update면
         *
         * 엔티티가 이미 등록되어 있으면 기존 엔티티 가져오고(수정)
         * 없다면 새로운 엔티티로 변환 BoardConfig.of(config);(생성)
         *
         */

        Board entity = null;
        String bId = config.getBId(); // 게시판 아이디
        String mode = config.getMode();
        if(mode != null && mode.equals("update")) { // mode 값이 수정이면 수정
            entity = repository.findById(bId).orElseGet(() -> Board.builder().bId(bId).build());
        } else { // 모드가 수정이 아니면 새로 추가
            entity = new Board();
            entity.setBId(bId);
        }

        if(bId != null && repository.exists(bId)) { // 이미 등록된 게시판 ID가 있다면
            entity = repository.findById(bId).orElseGet(() -> BoardConfig.of(config));
            entity.setBId(bId);
            entity.setBoardNm(config.getBoardNm());
            entity.setRowsPerPage(config.getRowsPerPage());
            entity.setSkin(SkinType.DEFAULT);
            /** 기본 값으로 설정이 맞는지?? */
        }

        if(entity == null) { // 게시판 ID가 없다면 boardConfig -> Board 엔티티로 변환
            entity = BoardConfig.of(config);
        }

        entity.setBoardNm(config.getBoardNm());
        entity.setUse(config.isUse());
        entity.setRowsPerPage(config.getRowsPerPage());
        entity.setUseViewList(config.isUseViewList());
        entity.setCategory(config.getCategory());
        entity.setViewType(ViewType.valueOf(config.getViewType()));
        entity.setUseEditor(config.isUseEditor());
        /** 파일, 이미지는 추후 등록 */
        entity.setAfterWriteTarget(AfterWriteTarget.valueOf(config.getAfterWriteTarget()));
        entity.setUseComment(config.isUseComment());
        entity.setSkin(SkinType.valueOf(config.getSkin()));
        entity.setReview(config.isReview());

        /** category: '\n' 줄바꿈울 기준으로 인식 */
        String getCate = config.getCategory();
        String[] categoryArr = getCate.split("\n");

        for (String category : categoryArr) {
            entity.setCategory(category);
        }

        repository.saveAndFlush(entity);
    }
}