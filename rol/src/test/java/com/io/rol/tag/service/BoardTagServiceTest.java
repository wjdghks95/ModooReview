package com.io.rol.tag.service;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.board.repository.BoardRepository;
import com.io.rol.tag.domain.entity.BoardTag;
import com.io.rol.tag.domain.entity.Tag;
import com.io.rol.tag.repository.BoardTagRepository;
import com.io.rol.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BoardTagServiceTest {

    @Autowired BoardRepository boardRepository;
    @Autowired TagService tagService;
    @Autowired TagRepository tagRepository;
    @Autowired BoardTagService boardTagService;
    @Autowired BoardTagRepository boardTagRepository;

    private static final String TAG1 = "태그1";
    private static final String TAG2 = "태그2";
    private static final String TAG3 = "태그3";

    @BeforeEach
    void init() {
        List<String> tagNames = getTagNames();
        tagService.saveTags(tagNames);
        
        Board board = Board.builder()
                .title("제목")
                .description("설명")
                .build();
        boardRepository.save(board);
    }

    private List<String> getTagNames() {
        List<String> tagNames = new ArrayList<>();
        tagNames.add(TAG1);
        tagNames.add(TAG2);
        tagNames.add(TAG3);
        return tagNames;
    }
    
    @Test
    @DisplayName("BoardTag 저장_성공")
    @Transactional
    void save_boardTag() {
        // given
        List<Tag> tags = tagRepository.findAll();
        Board board = boardRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("IllegalArgumentException"));

        // when
        boardTagService.saveBoardTags(tags, board);

        // then
        List<BoardTag> boardTags = boardTagRepository.findAll();
        assertEquals(board.getId(), boardTags.get(0).getId());
        assertEquals(boardTags.size(), 3);
        assertArrayEquals(boardTags.stream().map(boardTag -> boardTag.getTag().getName()).toArray(),
                tags.stream().map(tag -> tag.getName()).toArray());
    }
}