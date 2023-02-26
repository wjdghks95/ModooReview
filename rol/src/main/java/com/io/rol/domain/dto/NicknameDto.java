package com.io.rol.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

// 닉네임 변경 DTO
@Getter @Setter
@NoArgsConstructor
public class NicknameDto {
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z0-9가-힣])[A-Za-z0-9가-힣]{2,16}$")
    private String nickname;
}
