package com.io.rol.service.impl;

import com.io.rol.domain.entity.Tag;
import com.io.rol.service.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TagServiceTest {

    @Autowired TagService tagService;

    private static String TAG1 = "태그1";
    private static String TAG2 = "태그2";
    private static String TAG3 = "태그3";
    private static String TAG4 = "태그4";

    /**
     * 태그 저장
     *    중복되지 않은 태그인 경우 저장
     *    중복된 태그인 경우 저장소에서 꺼냄
     */
    @Test
    @DisplayName("태그 저장_중복이 없는 태그 저장")
    void save_tag_not_duplication() {
        // given
        List<String> tagNames = getTagNames();

        // when
        List<Tag> tags = tagService.saveTags(tagNames);

        // then
        assertEquals(tags.size(), 3);
        assertArrayEquals(tags.stream().map(tag -> tag.getName()).toArray(), tagNames.toArray());
    }

    @Test
    @DisplayName("태그 저장_중복이 있는 태그 저장")
    void save_tag_duplicated() {
        // given
        List<String> tagNames = getTagNames();
        tagService.saveTags(tagNames);

        // when
        tagNames.add(TAG4);
        List<Tag> tags = tagService.saveTags(tagNames);

        // then
        assertEquals(tags.size(), 4);
        assertArrayEquals(tags.stream().map(tag -> tag.getName()).toArray(), tagNames.toArray());
    }

    private List<String> getTagNames() {
        List<String> tagNames = new ArrayList<>();
        tagNames.add(TAG1);
        tagNames.add(TAG2);
        tagNames.add(TAG3);
        return tagNames;
    }
}