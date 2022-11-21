package wjdghks95.project.rol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wjdghks95.project.rol.domain.dto.ReviewDto;
import wjdghks95.project.rol.domain.entity.Tag;
import wjdghks95.project.rol.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    @Transactional
    @Override
    public List<Tag> saveTag(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        tagNames.forEach(tagName -> {
            Tag tag = tagRepository.findByName(tagName).orElseGet(() ->
                    Tag.builder()
                            .name(tagName)
                            .build()
            );

            if (!tagRepository.existsByName(tagName)) {
                tagRepository.save(tag);
            }
            tags.add(tag);
        });
        return tags;
    }
}
