package com.io.rol.controller;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.board.service.BoardService;
import com.io.rol.member.service.MemberService;
import com.querydsl.core.types.OrderSpecifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

import static com.io.rol.domain.entity.QBoard.*;

// 홈 컨트롤러
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/")
    public String home(Model model) {

        OrderSpecifier<LocalDateTime> createdDate = board.createdDate.desc();
        OrderSpecifier<Integer> likeCount = board.likeCount.desc();
        OrderSpecifier<Integer> views = board.views.desc();

        List<Board> likeCountSortedBoardList = boardService.getListBySort(likeCount);
        List<Board> createdDateSortedBoardList = boardService.getListBySort(createdDate);
        List<Board> viewsSortedBoardList = boardService.getListBySort(views);

        model.addAttribute("likeCountSortedBoardList", likeCountSortedBoardList);
        model.addAttribute("createdDateSortedBoardList", createdDateSortedBoardList);
        model.addAttribute("viewsSortedBoardList", viewsSortedBoardList);

        // 관리자 등록
        Member admin = memberService.findMember(1L);
        List<Board> adminBoardList = admin.getBoardList();
        model.addAttribute("adminBoardList", adminBoardList);

        return "index";
    }
}
