package wjdghks95.project.rol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wjdghks95.project.rol.domain.dto.CommentDto;
import wjdghks95.project.rol.domain.entity.Comment;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.domain.entity.Review;
import wjdghks95.project.rol.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public Comment add(CommentDto commentDto, Member member, Review review) {
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .build();

        comment.setMember(member);
        comment.setReview(review);

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findComments(Long reviewId) {
        return commentRepository.findComments(reviewId);
    }

    @Transactional
    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }


}
