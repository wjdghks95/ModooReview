package com.io.rol.service;

import com.io.rol.domain.dto.FindIdDto;
import com.io.rol.domain.dto.MemberDto;
import com.io.rol.domain.entity.Image;
import com.io.rol.domain.entity.Member;

public interface MemberService {
    Long join(MemberDto memberDto);
    Member findMember(Long id);
    boolean isFollow(Long followerId, Long followingId);
    void follow(Member follower, Member following);

    void nicknameModify(Member member, String nickname);

    void profileImgModify(Member member, Image image);

    void withdrawal(Member member);
    Member findId(FindIdDto findIdDto);
}
