package com.io.rol.member.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

// 비밀번호 찾기 DTO
@Getter @Setter
@NoArgsConstructor
public class FindPwdDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String phone;
}
