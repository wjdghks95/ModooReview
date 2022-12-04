package com.io.rol.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberDto {

    private String phone;
    private String email;

    private String password;

    private String name;

    private String nickname;

    private String zipcode;

    private String address;

    private String detailAddress;

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
