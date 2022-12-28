package wjdghks95.project.rol.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import wjdghks95.project.rol.domain.dto.ReviewDto;

import java.util.List;

@Component
public class FileValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return ReviewDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ReviewDto reviewDto = (ReviewDto) target;

        List<MultipartFile> multipartFiles = reviewDto.getMultipartFiles();

        if (multipartFiles.get(0).isEmpty()) {
            errors.rejectValue("multipartFiles", "invalid.multipartFiles", new Object[]{reviewDto.getMultipartFiles()}, "사진을 한개 이상 등록해주세요.");
        }
    }

}
