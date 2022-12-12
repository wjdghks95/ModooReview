package com.io.rol.service.impl;

import com.io.rol.domain.dto.BoardDto;
import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.Image;
import com.io.rol.domain.entity.Member;
import com.io.rol.domain.entity.Tag;
import com.io.rol.respository.BoardRepository;
import com.io.rol.respository.CategoryRepository;
import com.io.rol.service.BoardService;
import com.io.rol.service.BoardTagService;
import com.io.rol.service.ImageService;
import com.io.rol.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final ImageService imageService;
    private final TagService tagService;
    private final BoardTagService boardTagService;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    @Override
    public Long write(BoardDto boardDto, Member writer) throws IOException {
        List<Image> images = imageService.saveImages(boardDto.getFile());
        List<Tag> tags = tagService.saveTags(boardDto.getTagNames());

        Board board = Board.builder()
                .title(boardDto.getTitle())
                .description(boardDto.getDescription())
                .rating(boardDto.getRating())
                .category(categoryRepository.findByName(boardDto.getCategory()))
                .build();
        board.setThumbnail(images, boardDto.getThumbnailIdx());

        board.setMember(writer);  // 연관관계 매핑
        Board savedBoard = boardRepository.save(board);

        images.stream().forEach(image -> image.setBoard(savedBoard)); // 연관관계 매핑
        boardTagService.saveBoardTags(tags, savedBoard); // ManyToMany 양방향 연관관계 매핑

        return savedBoard.getId();
    }
}
