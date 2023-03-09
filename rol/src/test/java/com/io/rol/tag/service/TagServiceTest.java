package com.io.rol.tag.service;

import com.io.rol.tag.domain.entity.Tag;
import com.io.rol.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TagServiceTest {

    @Autowired TagService tagService;
    @Autowired TagRepository tagRepository;

    private static final String TAG1 = "태그1";
    private static final String TAG2 = "태그2";
    private static final String TAG3 = "태그3";
    private static final String TAG4 = "태그4";

    @BeforeEach
    void save_tag() {
        List<String> tagNames = getTagNames();
        tagService.saveTags(tagNames);
    }

    private List<String> getTagNames() {
        List<String> tagNames = new ArrayList<>();
        tagNames.add(TAG1);
        tagNames.add(TAG2);
        tagNames.add(TAG3);
        return tagNames;
    }

    /*
       태그 저장
          중복되지 않은 태그인 경우 저장
          중복된 태그인 경우 저장소에서 꺼냄
     */
    @Test
    @DisplayName("태그 저장_중복이 없는 태그 저장")
    void save_tag_not_duplication() {
        // given
        List<String> tagNames = new ArrayList<>();
        tagNames.add(TAG4);

        // when
        tagService.saveTags(tagNames);

        // then
        List<Tag> tags = tagRepository.findAll();
        assertEquals(tags.size(), 4);
    }

    @Test
    @DisplayName("태그 저장_중복이 있는 태그 저장")
    void save_tag_duplicated() {
        // given
        List<String> tagNames = new ArrayList<>();
        tagNames.add(TAG3);

        // when
        tagService.saveTags(tagNames);

        // then
        List<Tag> tags = tagRepository.findAll();
        assertEquals(tags.size(), 3);
    }
}