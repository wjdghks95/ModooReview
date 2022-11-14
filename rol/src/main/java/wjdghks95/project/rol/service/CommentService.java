package wjdghks95.project.rol.service;

import wjdghks95.project.rol.domain.dto.CommentDto;
import wjdghks95.project.rol.domain.entity.Comment;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.domain.entity.Review;

import java.util.List;

public interface CommentService {

    Comment add(CommentDto commentDto, Member member, Review review);

    List<Comment> findComments(Long reviewId);

    void deleteComment(Comment comment);
}
