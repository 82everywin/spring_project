package org.study.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.study.entities.CommunityBoard;

public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, String>, QuerydslPredicateExecutor<CommunityBoard> {

}
