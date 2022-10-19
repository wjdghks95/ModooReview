package wjdghks95.project.rol.config;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

@Configuration
public class InitData {

    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        Member member = Member.builder()
                .phone("")
                .email("admin@admin.com")
                .password(passwordEncoder.encode("asdf1234!"))
                .name("admin")
                .nickname("관리자")
                .zipcode("")
                .address("")
                .detailAddress("")
                .role("ADMIN")
                .profileImage(null)
                .build();

        memberRepository.save(member);
    }
}
