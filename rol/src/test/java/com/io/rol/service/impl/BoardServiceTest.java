package com.io.rol.service.impl;

import com.io.rol.domain.dto.BoardDto;
import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.Member;
import com.io.rol.respository.BoardRepository;
import com.io.rol.respository.MemberRepository;
import com.io.rol.security.context.MemberContext;
import com.io.rol.service.BoardService;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired private EntityManager em;
    @Autowired private BoardService boardService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private BoardRepository boardRepository;

    @Value("${resource.path.fileStore}/")
    private String fileDirPath;

    private static String USERNAME = "user@test.com";
    private static int THUMBIDX = 0;
    private static String TITLE = "제목";
    private static String CATEGORY = "food";
    private static int RATING = 5;
    private String DESCRIPTION = "테스트를 하기 위한 20자 이상의 내용입니다.";
    private static String IMAGE1 = "sample1.jpg";
    private static String IMAGE2= "sample2.jpg";

    private void clear() {
        em.flush();
        em.clear();
    }

    @BeforeEach
    private void setAuthentication() throws Exception {
        Member member = memberRepository.findByEmail(USERNAME).orElseThrow(() -> new UsernameNotFoundException("UsernameNotFoundException"));

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole().value()));

        MemberContext memberContext = new MemberContext(member, roles);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(memberContext, null, roles));
        SecurityContextHolder.setContext(securityContext);
        clear();
    }

    /**
     * 게시글 작성
     *    업도르한 이미지가 한 개라도 없으면 오류
     *    제목과 내용을 입력하지 않거나 카테고리를 선택하지 않으면 오류
     */
    @Test
    @DisplayName("게시글 작성_성공")
    void board_write_success() throws IOException {
        // given
        BoardDto boardDto = createBoardDto();
        MemberContext memberContext = (MemberContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member writer = memberRepository.findById(memberContext.getMember().getId()).orElseThrow(() -> new UsernameNotFoundException("UsernameNotFoundException"));

        // when
        Long id = boardService.write(boardDto, writer);
        clear();

        // then
        Board board = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("NoSuchElementException"));
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
        Member writer = memberRepository.findById(memberContext.getMember().getId()).orElseThrow(() -> new UsernameNotFoundException("UsernameNotFoundException"));

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

    private List<String> createTagNames() {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("태그1");
        tagNames.add("태그2");
        tagNames.add("태그3");
        return tagNames;
    }
}