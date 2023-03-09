package com.io.rol.board.service;

import com.io.rol.Image.domain.entity.Image;
import com.io.rol.Image.service.ImageService;
import com.io.rol.board.domain.dto.BoardDto;
import com.io.rol.board.domain.entity.Board;
import com.io.rol.board.repository.BoardRepository;
import com.io.rol.category.domain.entity.Category;
import com.io.rol.category.repository.CategoryRepository;
import com.io.rol.domain.entity.Like;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.respository.LikeRepository;
import com.io.rol.respository.query.BoardQueryRepository;
import com.io.rol.tag.domain.entity.Tag;
import com.io.rol.tag.service.BoardTagService;
import com.io.rol.tag.service.TagService;
import com.querydsl.core.types.OrderSpecifier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final BoardQueryRepository boardQueryRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;

    // 리뷰 작성
    @Transactional
    @Override
    public Long write(BoardDto boardDto, Member writer) throws IOException {
        List<Image> images = imageService.saveImages(boardDto.getFile()); // 이미지 저장
        List<Tag> tags = tagService.saveTags(boardDto.getTagNames()); // 태그 저장

        Board board = Board.builder()
                .title(boardDto.getTitle())
                .description(boardDto.getDescription())
                .rating(boardDto.getRating())
                .category(categoryRepository.findByName(boardDto.getCategory()))
                .build();

        board.setThumbnail(images, boardDto.getThumbnailIdx()); // 썸네일 번호 등록
        board.setMember(writer);  // 연관관계 매핑
        Board savedBoard = boardRepository.save(board);

        images.stream().forEach(image -> image.setBoard(savedBoard)); // 연관관계 매핑
        boardTagService.saveBoardTags(tags, savedBoard); // ManyToMany 양방향 연관관계 매핑

        return savedBoard.getId();
    }

    // 게시글 단건 조회
    @Override
    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("NoSuchElementException"));
    }


    /*
        좋아요 여부
        로그인한 member가 현재 게시글을 좋아요 하지 않은 경우 true
     */
    @Override
    public boolean isLike(Long memberId, Long boardId) {
        return likeRepository.findByMemberIdAndBoardId(memberId, boardId).isEmpty();
    }

    // 좋아요
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

    /*
       게시글 목록 페이징 조회
         - 카테고리 또는 키워드가 있는 경우 해당 목록 페이징 조회
     */
    @Override
    public Page<Board> getList(Pageable pageable, String category, String keyword) {
        return boardQueryRepository.findAllPagingByKeyword(pageable, category, keyword);
    }

    // 정렬된 게시글 목록 조회
    @Override
    public List<Board> getListBySort(OrderSpecifier<?> orderSpecifier) {
        return boardQueryRepository.findAllByOrder(orderSpecifier);
    }

    // 조회수 증가
    @Override
    @Transactional
    public void incrementViews(Board board) {
        board.incrementViews();
    }

    // 게시글 수정
    @Override
    @Transactional
    public void edit(Board board, BoardDto boardDto) throws IOException {
        // 이미지 저장
        imageService.deleteImages(board.getImages()); // 저장소에 저장된 이미지 삭제
        board.getImages().clear(); // 배열 안 이미지 제거

        List<Image> images = imageService.saveImages(boardDto.getFile());
        board.setThumbnail(images, boardDto.getThumbnailIdx());
        images.forEach(image -> image.setBoard(board));

        // 카테고리 수정
        Category category = categoryRepository.findByName(boardDto.getCategory());
        board.setCategory(category);

        // 제목, 평점, 설명, 수정일 수정
        board.updateBoard(boardDto.getTitle(), boardDto.getRating(), boardDto.getDescription());

        // 태그 저장
        List<Tag> tagList = tagService.saveTags(boardDto.getTagNames());
        board.getBoardTagList().clear(); // 배열 안 태그 목록 제거
        boardTagService.saveBoardTags(tagList, board);
    }

    // 게시글 삭제
    @Override
    @Transactional
    public void remove(Board board) {
        boardRepository.delete(board);
    }
}
