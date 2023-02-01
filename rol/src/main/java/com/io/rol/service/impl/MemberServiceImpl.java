package com.io.rol.service.impl;

import com.io.rol.domain.dto.MemberDto;
import com.io.rol.domain.entity.Follow;
import com.io.rol.domain.entity.Member;
import com.io.rol.respository.FollowRepository;
import com.io.rol.respository.MemberRepository;
import com.io.rol.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.io.rol.domain.Role.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    /**
     * 회원가입
     */
    @Transactional
    @Override
    public Long join(MemberDto memberDto) {
        Member member = Member.builder()
                .phone(memberDto.getPhone())
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .name(memberDto.getName())
                .nickname(memberDto.getNickname())
                .zipcode(memberDto.getZipcode())
                .address(memberDto.getAddress())
                .detailAddress(memberDto.getDetailAddress())
                .role(USER)
                .build();

        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    /**
     * 회원 조회
     */
    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("NoSuchElementException"));
    }

    /**
     * 팔로우 여부
     * 로그인한 member가 현재 page member를 팔로우하지 않은 경우 true
     */
    @Override
    public boolean isFollow(Long followerId, Long followingId) {
        return followRepository.findFollowByFollowerIdAndFollowingId(followerId, followingId).isEmpty();
    }

    /**
     * 팔로우
     */
    @Transactional
    @Override
    public void follow(Member follower, Member following) {
        Optional<Follow> followOptional = followRepository.findFollowByFollowerIdAndFollowingId(follower.getId(), following.getId());

        followOptional.ifPresentOrElse(
                // 팔로우 되어 있는 경우 삭제
                follow -> {
                    followRepository.delete(follow);
                },
                // 팔로우 하지 않은 경우 추가
                () -> {
                    Follow follow = new Follow();

                    follow.setFollower(follower);
                    follow.setFollowing(following);
                    followRepository.save(follow);
                }
        );
    }

    @Override
    @Transactional
    public void nicknameModify(Member member, String nickname) {
        member.setNickname(nickname);
    }
}
