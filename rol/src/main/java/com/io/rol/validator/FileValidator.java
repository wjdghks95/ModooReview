package com.io.rol.validator;

import com.io.rol.domain.dto.BoardDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class FileValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BoardDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardDto boardDto = (BoardDto) target;
        List<MultipartFile> file = boardDto.getFile();

        for (MultipartFile f : file) {
            if (f.isEmpty()) {
                errors.rejectValue("file", "validate.invalid.file", new Object[]{boardDto.getFile()}, "사진을 한 개 이상 등록해주세요.");
            }
        }
    }
}
