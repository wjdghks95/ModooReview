package com.io.rol.service.impl;

import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.BoardTag;
import com.io.rol.domain.entity.Tag;
import com.io.rol.respository.BoardTagRepository;
import com.io.rol.service.BoardTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardTagServiceImpl implements BoardTagService {

    private final BoardTagRepository boardTagRepository;

    /**
     *  ManyToMany 양방향 매핑을 위한 BoardTag 엔티티 저장
     */
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
}
