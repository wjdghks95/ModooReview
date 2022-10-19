package wjdghks95.project.rol.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.repository.MemberRepository;
import wjdghks95.project.rol.security.service.MemberContext;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberRepository memberRepository;

    @Value("classpath:${resource.path.default-image}")
    private Resource resourceFile;

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException();
        });

        String defaultProfileImage = null;
        try (InputStream inputStream = new FileInputStream(resourceFile.getFile())){
            byte[] bytes = IOUtils.toByteArray(inputStream); // to byte array
            defaultProfileImage = Base64.getEncoder().encodeToString(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("member", member);
        model.addAttribute("defaultProfileImage", defaultProfileImage);
        return "mypage/myPage_profile";
    }
}
