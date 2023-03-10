package com.io.rol.member.service;

import com.io.rol.Image.domain.entity.Image;
import com.io.rol.domain.dto.FindIdDto;
import com.io.rol.domain.dto.FindPwdDto;
import com.io.rol.member.domain.dto.MemberDto;
import com.io.rol.member.domain.entity.Member;

public interface MemberService {
    Long join(MemberDto memberDto); // 회원가입

    Member getMember(Long id); // 단일 회원 조회

    boolean isFollow(Long followerId, Long followingId); // 팔로우 여부

    void follow(Member follower, Member following); // 팔로우

    // 닉네임 변경
    void nicknameModify(Member member, String nickname);

    // 프로필 이미지 변경
    void profileImgModify(Member member, Image image);

    // 회원 탈퇴
    void withdrawal(Member member);

    // 아이디 찾기
    Member findId(FindIdDto findIdDto);

    // 비밀번호 찾기
    Member findPassword(FindPwdDto findPwdDto);

    // 비밀번호 변경
    void passwordModify(Member member, String tempPwd);
}
