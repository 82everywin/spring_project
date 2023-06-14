package org.study.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.study.entities.CommunityBoard;

public interface CommunityPostRepository extends JpaRepository<CommunityBoard, Long>, QuerydslPredicateExecutor<CommunityBoard> {

}
