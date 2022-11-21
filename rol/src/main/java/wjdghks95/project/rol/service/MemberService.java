package wjdghks95.project.rol.service;

import org.springframework.web.multipart.MultipartFile;
import wjdghks95.project.rol.domain.dto.MemberDto;
import wjdghks95.project.rol.domain.entity.Member;

import java.io.IOException;

public interface MemberService {

    Long join(MemberDto memberDto);
    void withdrawal(Long id);

    void follow(Member followingMember, Member followerMember);

    boolean isFollow(Member followingMember, Member followerMember);
    void updateProfileImg(Member member, MultipartFile multipartFile) throws IOException;
}
