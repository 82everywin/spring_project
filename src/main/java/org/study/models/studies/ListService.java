package org.study.models.studies;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.study.commons.constants.Status;
import org.study.controllers.study.StudySearch;
import org.study.entities.Category;
import org.study.entities.FileInfo;
import org.study.entities.QStudies;
import org.study.entities.Studies;
import org.study.models.file.FileListService;
import org.study.repositories.CategoryRepository;
import org.study.repositories.StudiesRepository;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class ListService {
    private final CategoryRepository categoryRepository;

    private final FileListService fileListService;
    private final StudiesRepository studiesRepository;

    public Page<Studies> gets(StudySearch search) {
        QStudies studies = QStudies.studies;
        BooleanBuilder andBuilder = new BooleanBuilder();
        int page = search.getPage();
        page = page < 1 ? 1 : page;
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        /** 검색어 처리 S */
        String status = search.getStatus();
        if (status == null || status.isBlank()) {
            andBuilder.and(studies.status.eq(Status.APPROVE)); // 승인된 목록만 조회
        } else if (!status.equals("ALL")) {
            // 승인, 미승인 목록 검색 처리
            andBuilder.and(studies.status.eq(Status.valueOf(status)));
        }

        String sido = search.getSido();
        String sigugun = search.getSigugun();
        String cateCd = search.getCateCd();
        String skey = search.getSkey();

        if (cateCd != null && !cateCd.isBlank()) { // 분류 검색
            andBuilder.and(studies.cateCd.eq(cateCd));
        }

        if (sido != null && !sido.isBlank()) {
            andBuilder.and(studies.sido.eq(sido));
        }

        if (sigugun != null && !sigugun.isBlank()) {
            andBuilder.and(studies.sigugun.eq(sigugun));
        }

        if (skey != null && !skey.isBlank()) {
            skey = skey.trim();
            andBuilder.and(studies.simpleIntro.contains(skey));
        }
        /** 검색어 처리 E */


        Sort sort = Sort.by(desc("createdAt"));

        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Studies> data = studiesRepository.findAll(andBuilder, pageable);
        data.getContent().stream().forEach(study -> {
            String gid = study.getGid();
            /** 업로드한 파일 목록 S */
            List<FileInfo> mainImages = fileListService.getAll(gid, "main");
            List<FileInfo> editorFiles = fileListService.getAll(gid, "editor");
            study.setEditorFiles(editorFiles);
            study.setMainImages(mainImages);
            /** 업로드한 파일 목록 E */

            /** 분류명 조회 S */
            String cateCd2 = study.getCateCd();
            Category category = categoryRepository.findById(cateCd2).orElse(null);
            study.setCategory(category);
            /** 분류명 조회 E */
        });

        return data;
    }
}
