package com.io.rol.service.impl;

import com.io.rol.domain.entity.Tag;
import com.io.rol.respository.TagRepository;
import com.io.rol.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    /**
     * 태그 저장
     */
    @Transactional
    @Override
    public List<Tag> saveTags(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();

        // 태그가 중복되지 않은 경우에만 태그를 생성하고 저장
        tagNames.forEach(tagName -> {
            Tag tag = tagRepository.findByName(tagName).orElseGet(() ->
                    Tag.builder().name(tagName).build());

            if (!tagRepository.existsByName(tagName)) {
                tagRepository.save(tag);
            }
            tags.add(tag);
        });
        return tags;
    }
}
