package com.io.rol.board.controller;

import com.io.rol.board.domain.dto.BoardDto;
import com.io.rol.domain.dto.PageDto;
import com.io.rol.board.domain.entity.Board;
import com.io.rol.tag.domain.entity.BoardTag;
import com.io.rol.comment.domain.entity.Comment;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.security.context.MemberContext;
import com.io.rol.board.service.BoardService;
import com.io.rol.tag.service.BoardTagService;
import com.io.rol.comment.service.CommentService;
import com.io.rol.member.service.MemberService;
import com.io.rol.validator.FileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final FileValidator fileValidator;
    private final CommentService commentService;
    private final BoardTagService boardTagService;

    @InitBinder("boardDto")
    public void boardValidation(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.addValidators(fileValidator); // 이미지 파일 업로드 여부 검사
    }

    // 게시글 등록 화면
    @GetMapping("/board/new")
    public String boardForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "contents/boardForm";
    }

    // 게시글 등록
    @PostMapping("/board/new")
    public String newBoard(@Validated @ModelAttribute BoardDto boardDto, BindingResult bindingResult,
                           @AuthenticationPrincipal MemberContext memberContext) throws IOException {

        if (bindingResult.hasErrors()) {
            return "contents/boardForm";
        }

        Member writer = memberService.getMember(memberContext.getMember().getId());
        Long id = boardService.write(boardDto, writer);
        return "redirect:/contents/board/" + id;
    }

    // 게시글
    @GetMapping("/board/{id}")
    public String board(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberContext memberContext) {
        Board board = boardService.getBoard(id);
        boardService.incrementViews(board); // 조회수 증가
        model.addAttribute("board", board);

        List<Comment> comments = commentService.getCommentList(id);
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        if (memberContext != null) {
            Long memberContextId = memberContext.getMember().getId();
            Member loginMember = memberService.getMember(memberContextId);
            boolean isFollow = memberService.isFollow(loginMember.getId(), board.getMember().getId());
            boolean isLike = boardService.isLike(loginMember.getId(), board.getId());

            model.addAttribute("loginMember", loginMember);
            model.addAttribute("isFollow", isFollow);
            model.addAttribute("isLike", isLike);
        }

        return "/contents/board";
    }

    // 게시글 수정 화면
    @GetMapping("/board/{id}/edit")
    public String boardEditForm(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberContext memberContext) {
        Board board = boardService.getBoard(id);
        if (memberContext.getMember().getId() != board.getMember().getId() || memberContext.getMember() == null) {
            return "redirect:/";
        }

        BoardDto boardDto = new BoardDto();
        boardDto.setTitle(board.getTitle());
        boardDto.setCategory(board.getCategory().getName());
        boardDto.setDescription(board.getDescription());

        model.addAttribute("board", board);
        model.addAttribute("boardDto", boardDto);

        return "/contents/boardEditForm";
    }

    // 게시글 수정
    @PostMapping("/board/{id}/edit")
    public String edit(@PathVariable Long id, @Validated @ModelAttribute BoardDto boardDto,
                       BindingResult bindingResult, Model model) throws IOException {
        Board board = boardService.getBoard(id);
        model.addAttribute("board", board);

        if (bindingResult.hasErrors()) {
            return "/contents/boardEditForm";
        }

        boardService.edit(board, boardDto);

        return "redirect:/contents/board/" + id;
    }

    // 게시글 삭제
    @DeleteMapping("/board/{id}/edit")
    @ResponseBody
    public void deleteReview(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext) {
        Board board = boardService.getBoard(id);
        if (memberContext.getMember().getId() != board.getMember().getId() || memberContext.getMember() == null) {
            throw new IllegalStateException();
        }
        boardService.remove(board);
    }

    // 게시글 목록
    @GetMapping
    public String contents(@PageableDefault(size = 12) Pageable pageable, Model model,
                           @RequestParam(required = false) String category,
                           @RequestParam(required = false) String keyword) {
        Page<Board> boardList = boardService.getBoardList(pageable, category, keyword);

        model.addAttribute("boardList", boardList);
        model.addAttribute("page", new PageDto(boardList.getTotalElements(), pageable));

        return "/contents/contents";
    }

    // 페이징 게시글 목록
    @GetMapping("/loadContents")
    public String loadContents(@PageableDefault(size = 12) Pageable pageable, Model model,
                               @RequestParam(required = false) String category,
                               @RequestParam(required = false) String keyword) {
        Page<Board> boardList = boardService.getBoardList(pageable, category, keyword);

        model.addAttribute("boardList", boardList);
        model.addAttribute("page", new PageDto(boardList.getTotalElements(), pageable));

        return "/contents/contents :: #contents";
    }

    // 해시태그 목록
    @GetMapping("/hashTag")
    public String hashTag(@RequestParam String tagName, @PageableDefault(size = 12) Pageable pageable,
                          Model model) {
        Page<BoardTag> boardTagList = boardTagService.getBoardTagList(pageable, tagName);
        List<Board> boardList = boardTagList.stream().map(boardTag -> boardTag.getBoard()).collect(Collectors.toList());

        model.addAttribute("totalElement", boardTagList.getTotalElements());
        model.addAttribute("totalPage", boardTagList.getTotalPages());
        model.addAttribute("boardList", boardList);
        model.addAttribute("tagName", tagName);
        return "/contents/hashTag";
    }

    // 해시태그 목록 스크롤 페이징 조회
    @GetMapping("/hashTag/loadBoardList")
    public String loadHashTagBoardList(@PageableDefault(size = 12) Pageable pageable,
                                       @RequestParam String tagName, Model model) {
        Page<BoardTag> boardTagList = boardTagService.getBoardTagList(pageable, tagName);
        List<Board> boardList = boardTagList.stream().map(boardTag -> boardTag.getBoard()).collect(Collectors.toList());

        model.addAttribute("boardList", boardList);
        model.addAttribute("tagName", tagName);
        return "/contents/hashTag :: .content__item";
    }
}
