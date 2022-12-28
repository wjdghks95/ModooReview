package wjdghks95.project.rol.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import wjdghks95.project.rol.domain.dto.MemberDto;

@Component
public class AuthCheckValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MemberDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberDto memberDto = (MemberDto) target;

        if (!memberDto.isPhoneCheck()) {
            errors.rejectValue("phoneCheck", "invalid.phoneCheck", new Object[]{memberDto.isPhoneCheck()}, "휴대폰 인증을 완료해주세요.");
        }
        if (!memberDto.isPasswordCheck()) {
            errors.rejectValue("passwordCheck", "invalid.passwordCheck", new Object[]{memberDto.isPasswordCheck()}, "비밀번호가 일치하지 않습니다.");
        }
    }
}
