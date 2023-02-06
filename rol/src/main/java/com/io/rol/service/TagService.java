package com.io.rol.service;

import com.io.rol.domain.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> saveTags(List<String> tagNames);
}
