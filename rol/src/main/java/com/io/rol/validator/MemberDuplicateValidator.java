package com.io.rol.validator;

import com.io.rol.domain.dto.MemberDto;
import com.io.rol.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MemberDuplicateValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberDto memberDto = (MemberDto) target;
        if(memberRepository.existsByEmail(memberDto.getEmail())){
            errors.rejectValue("email", "validate.invalid.email", new Object[]{memberDto.getEmail()}, "이미 사용중인 이메일 입니다.");
        }

        if(memberRepository.existsByNickname(memberDto.getNickname())){
            errors.rejectValue("nickname", "validate.invalid.nickname", new Object[]{memberDto.getNickname()}, "이미 사용중인 닉네임 입니다.");
        }
    }
}
