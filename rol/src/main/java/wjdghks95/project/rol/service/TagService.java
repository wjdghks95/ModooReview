package wjdghks95.project.rol.service;

import wjdghks95.project.rol.domain.entity.Tag;

import java.util.List;

public interface TagService {

    public List<Tag> saveTag(List<String> tagNames);
}
