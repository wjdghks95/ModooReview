package com.io.rol.board.service;

import com.io.rol.board.domain.dto.BoardDto;
import com.io.rol.board.domain.entity.Board;
import com.io.rol.board.exception.BoardException;
import com.io.rol.board.exception.BoardExceptionType;
import com.io.rol.board.repository.BoardRepository;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.member.domain.entity.Role;
import com.io.rol.member.exception.MemberException;
import com.io.rol.member.exception.MemberExceptionType;
import com.io.rol.member.repository.MemberRepository;
import com.io.rol.member.service.MemberService;
import com.io.rol.security.context.MemberContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceImplTest {
    @Autowired private EntityManager em;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private BoardService boardService;
    @Autowired private BoardRepository boardRepository;

    @Value("${file.dir}/")
    private String fileDirPath;

    private static final String USERNAME = "test@test.com";
    private static final int THUMBIDX = 0;
    private static final String TITLE = "제목";
    private static final String CATEGORY = "food";
    private static final int RATING = 5;
    private static final String DESCRIPTION = "테스트를 하기 위한 20자 이상의 내용입니다.";
    private static final String IMAGE1 = "sample1.png";
    private static final String IMAGE2= "sample2.png";

    private void clear() {
        em.flush();
        em.clear();
    }

    @BeforeEach
    private void init() throws Exception {
        Member member = Member.builder()
                .phone("01012345678")
                .email("test@test.com")
                .password(passwordEncoder.encode("asdf1234!"))
                .name("이름")
                .nickname("테스트")
                .zipcode("12345")
                .address("주소")
                .detailAddress("상세주소")
                .role(Role.USER)
                .build();
        memberRepository.save(member);

        Member findMember = memberRepository.findByEmail(USERNAME).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(findMember.getRole().value()));

        MemberContext memberContext = new MemberContext(findMember, roles);

        // 게시글 작성자 설정을 위해 SecurityContext 에 인증 객체를 생성하여 담아둠
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(memberContext, null, roles));
        SecurityContextHolder.setContext(securityContext);
        clear();
    }

    /*
       게시글 작성
          업도르한 이미지가 한 개라도 없으면 오류
          제목과 내용을 입력하지 않거나 카테고리를 선택하지 않으면 오류
     */
    @Test
    @DisplayName("게시글 작성_성공")
    void board_write_success() throws IOException {
        // given
        BoardDto boardDto = createBoardDto();
        MemberContext memberContext = (MemberContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member writer = memberRepository.findById(memberContext.getMember().getId()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        // when
        Long id = boardService.write(boardDto, writer);
        clear();

        // then
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException(BoardExceptionType.BOARD_NOT_FOUND));
        assertNotNull(board);
        assertEquals(boardDto.getFile().size(), board.getImages().size());
        board.getImages().stream().forEach(image -> {
            File file = new File(fileDirPath + image.getStoreFileName());
            assertTrue(file.exists());
            file.delete();
        });
        assertEquals(id, board.getId());
        assertEquals(boardDto.getTitle(), board.getTitle());
        assertEquals(boardDto.getCategory(), board.getCategory().getName());
        assertEquals(boardDto.getRating(), board.getRating());
        assertEquals(boardDto.getDescription(), board.getDescription());
        assertArrayEquals(boardDto.getTagNames().toArray(),
                board.getBoardTagList().stream().map(boardTag -> boardTag.getTag().getName()).toArray());
    }

    @Test
    @DisplayName("게시글 작성_실패_입력하지 않은 필드 존재")
    void board_write_failure_empty_field() throws IOException {
        // given
        BoardDto boardDto1 = createBoardDto();
        boardDto1.setFile(null);

        BoardDto boardDto2 = createBoardDto();
        boardDto2.setTitle(null);

        BoardDto boardDto3 = createBoardDto();
        boardDto3.setCategory(null);

        BoardDto boardDto4 = createBoardDto();
        boardDto4.setDescription(null);

        MemberContext memberContext = (MemberContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member writer = memberRepository.findById(memberContext.getMember().getId()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        // when, then
        assertThrows(Exception.class, () -> boardService.write(boardDto1, writer));
        assertThrows(Exception.class, () -> boardService.write(boardDto2, writer));
        assertThrows(Exception.class, () -> boardService.write(boardDto3, writer));
        assertThrows(Exception.class, () -> boardService.write(boardDto4, writer));
    }

    private BoardDto createBoardDto() throws IOException {
        return BoardDto
                .builder()
                .file(getMockUploadFiles())
                .thumbnailIdx(THUMBIDX)
                .title(TITLE)
                .category(CATEGORY)
                .rating(RATING)
                .description(DESCRIPTION)
                .tagNames(createTagNames())
                .build();
    }

    private List<String> createTagNames() {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("태그1");
        tagNames.add("태그2");
        tagNames.add("태그3");
        return tagNames;
    }

    private List<MultipartFile> getMockUploadFiles() throws IOException {
        List<MultipartFile> multipartFileList = new ArrayList<>();
        MultipartFile mockUploadFile1 = getMockUploadFile(IMAGE1);
        MultipartFile mockUploadFile2 = getMockUploadFile(IMAGE2);
        multipartFileList.add(mockUploadFile1);
        multipartFileList.add(mockUploadFile2);
        return multipartFileList;
    }

    private MockMultipartFile getMockUploadFile(String fileName) throws IOException {
        return new MockMultipartFile("file", "file.jpg", "image/jpg",
                new FileInputStream(fileDirPath + fileName));
    }
}