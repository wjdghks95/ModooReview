package com.io.rol.validator;

import com.io.rol.domain.dto.BoardDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class MultiPartFilesValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BoardDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardDto reviewDto = (BoardDto) target;
        List<MultipartFile> multipartFiles = reviewDto.getMultipartFiles();

        if (multipartFiles.get(0).isEmpty()) {
            errors.rejectValue("multipartFiles", "validate.invalid.multipartFiles", new Object[]{reviewDto.getMultipartFiles()}, "사진을 한 개 이상 등록해주세요.");
        }
    }
}
