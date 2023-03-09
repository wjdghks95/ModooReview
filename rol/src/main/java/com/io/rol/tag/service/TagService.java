package com.io.rol.tag.service;

import com.io.rol.tag.domain.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> saveTags(List<String> tagNames); // 태그 저장

}
