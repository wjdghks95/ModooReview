package com.io.rol.member.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

// 아이디 찾기 DTO
@Getter @Setter
@NoArgsConstructor
public class FindIdDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String phone;
}
