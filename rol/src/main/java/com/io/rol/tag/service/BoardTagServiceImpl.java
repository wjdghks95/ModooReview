package com.io.rol.tag.service;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.tag.domain.entity.BoardTag;
import com.io.rol.tag.domain.entity.Tag;
import com.io.rol.tag.repository.BoardTagQueryRepository;
import com.io.rol.tag.repository.BoardTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardTagServiceImpl implements BoardTagService {

    private final BoardTagRepository boardTagRepository;
    private final BoardTagQueryRepository boardTagQueryRepository;

    // BoardTag 저장
    @Transactional
    @Override
    public List<BoardTag> saveBoardTags(List<Tag> tags, Board board) {
        List<BoardTag> boardTagList = new ArrayList<>();
        tags.forEach(tag -> {
            BoardTag boardTag = boardTagRepository.save(
                    BoardTag.builder()
                            .board(board)
                            .tag(tag)
                            .build());
            boardTagList.add(boardTag);
        });
        return boardTagList;
    }

    // tagName에 해당하는 BoardTag 페이징 목록 조회
    @Override
    public Page<BoardTag> getBoardTagList(Pageable pageable, String tagName) {
        return boardTagQueryRepository.findBoardTagListByTagName(pageable, tagName);
    }
}
