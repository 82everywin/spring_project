package org.study.models.Community;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.study.commons.UserUtils;
import org.study.entities.board.BoardView;
import org.study.entities.board.Post;
import org.study.repositories.board.BoardViewRepository;
import org.study.repositories.board.PostRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UpdateHitService {
    private final BoardViewRepository boardViewRepository;
    private final PostRepository postRepository;
    private final HttpServletRequest request;
    private final UserUtils userUtils;

    public void update(Long id) {
        try {
            BoardView boardView = new BoardView();
            boardView.setIdBoardData(id);
            boardView.setUid(""+getUid());
            boardViewRepository.saveAndFlush(boardView);

        } catch (Exception e) {}

        long cnt = boardViewRepository.getHit(id);
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setHit((int)cnt);
            postRepository.flush();
        }

    }

    private int getUid() {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        return userUtils.isLogin() ? userUtils.getUser().getUserNo().intValue() : Objects.hash(ip, ua);
    }
}
