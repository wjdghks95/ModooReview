package wjdghks95.project.rol.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
@NoArgsConstructor
public class MemberDto {

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;
    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 8자 이상이어야 합니다. 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$", message = "닉네임은 특수문자를 포함하지 않은 2~16자 이어야 합니다.")
    private String nickname;

    @NotBlank(message = "주소를 입력해주세요.")
    private String zipcode;

    @NotBlank
    private String address;

    private String detailAddress;

    private boolean phoneCheck;
    private boolean passwordCheck;

    @Builder
    public MemberDto(String phone, String email, String password, String name, String nickname, String zipcode, String address, String detailAddress, String phoneCheck, String passwordCheck) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.phoneCheck = Boolean.parseBoolean(phoneCheck);
        this.passwordCheck = Boolean.parseBoolean(passwordCheck);
    }
}
