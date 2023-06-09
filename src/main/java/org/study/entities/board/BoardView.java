package org.study.entities.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes=@Index(name="idx_id_uid", columnList = "idBoardData, uid"))
@IdClass(BoardViewId.class)
public class BoardView {

    @Id
    private Long idBoardData; // 게시글 번호

    @Id
    @Column(length=50)
    private String uid; // 사용자별 UID
}

