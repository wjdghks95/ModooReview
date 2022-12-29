package com.io.rol.service.impl;

import com.io.rol.domain.dto.BoardDto;
import com.io.rol.domain.entity.*;
import com.io.rol.respository.BoardRepository;
import com.io.rol.respository.CategoryRepository;
import com.io.rol.respository.LikeRepository;
import com.io.rol.service.BoardService;
import com.io.rol.service.BoardTagService;
import com.io.rol.service.ImageService;
import com.io.rol.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final ImageService imageService;
    private final TagService tagService;
    private final BoardTagService boardTagService;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;

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

    @Override
    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("NoSuchElementException"));
    }


    /**
     *  좋아요 여부
     *  로그인한 member가 현재 게시글을 좋아요 하지 않은 경우 true
     */
    @Override
    public boolean isLike(Long memberId, Long boardId) {
        return likeRepository.findByMemberIdAndBoardId(memberId, boardId).isEmpty();
    }

    /**
     * 좋아요
     */
    @Transactional
    @Override
    public void like(Member member, Board board) {
        Optional<Like> likeOptional = likeRepository.findByMemberIdAndBoardId(member.getId(), board.getId());
        likeOptional.ifPresentOrElse(
                // 좋아요가 있을 경우 삭제
                like -> {
                    board.removeLike(like);
                    board.updateLikeCount();
                    likeRepository.delete(like);
                },

                // 좋아요가 없을 경우 좋아요 추가
                () -> {
                    Like like = new Like();
                    like.setReview(board);
                    like.setMember(member);

                    board.updateLikeCount();

                    likeRepository.save(like);
                }
        );
    }

}
