package com.io.rol.member.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

// 회원가입 DTO
@Getter @Setter
@NoArgsConstructor
public class MemberDto {

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String phone; // 휴대폰 번호

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email; // 이메일

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 8자 이상이어야 합니다. 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password; // 비밀번호

    @Pattern(regexp = "^[가-힣]{2,6}$", message = "올바른 이름을 입력해주세요.")
    private String name; // 이름

    @Pattern(regexp = "^(?=.*[A-Za-z0-9가-힣])[A-Za-z0-9가-힣]{2,16}$", message = "닉네임은 특수문자를 포함하지 않은 2~16자 이어야 합니다.")
    private String nickname; // 닉네임

    @NotBlank(message = "주소를 입력해주세요.")
    private String zipcode; // 우편번호

    @NotBlank(message = "주소를 입력해주세요.")
    private String address; // 주소

    private String detailAddress; // 상세주소

    @Builder
    public MemberDto(String phone, String email, String password, String name, String nickname, String zipcode, String address, String detailAddress) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
