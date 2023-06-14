package org.study.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.study.entities.StudyCategory;

import java.util.List;

public interface StudyCategoryRepository  extends JpaRepository<StudyCategory,String>, QuerydslPredicateExecutor {

    String findByScId(String scId);



}
