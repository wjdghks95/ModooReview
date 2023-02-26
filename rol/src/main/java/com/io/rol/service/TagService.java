package com.io.rol.service;

import com.io.rol.domain.entity.Tag;

import java.util.List;

// 태그 서비스
public interface TagService {

    // 태그 저장
    List<Tag> saveTags(List<String> tagNames);
}
