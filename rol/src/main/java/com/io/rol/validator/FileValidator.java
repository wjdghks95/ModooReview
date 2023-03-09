package com.io.rol.validator;

import com.io.rol.board.domain.dto.BoardDto;
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

        for (MultipartFile MultipartFile : file) {
            // 파일이 존재하지 않는 경우
            if (MultipartFile.isEmpty()) {
                errors.rejectValue("file", "validate.invalid.file", new Object[]{boardDto.getFile()}, "사진을 한 개 이상 등록해주세요.");
            }
        }
    }
}
