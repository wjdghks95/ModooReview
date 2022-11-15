package wjdghks95.project.rol.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wjdghks95.project.rol.domain.dto.MemberDto;
import wjdghks95.project.rol.domain.entity.Follow;
import wjdghks95.project.rol.domain.entity.LikeEntity;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.repository.FollowRepository;
import wjdghks95.project.rol.repository.MemberRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final FollowRepository followRepository;

    @Transactional
    public Long join(MemberDto memberDto) {

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .phone(memberDto.getPhone())
                .name(memberDto.getName())
                .nickname(memberDto.getNickname())
                .zipcode(memberDto.getZipcode())
                .address(memberDto.getAddress())
                .detailAddress(memberDto.getDetailAddress())
                .profileImage(null)
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void withdrawal(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        List<LikeEntity> likeList = member.getLikeList();
        likeList.forEach(likeEntity -> {
            likeEntity.getReview().discountLike(likeEntity);
            likeEntity.getReview().updateLikeCount();
        });
        memberRepository.delete(member);
    }

    // 로그인한 member가 현재 page member를 팔로우하지 않은 경우 false
    @Override
    public boolean isFollow(Member followingMember, Member followerMember) {
        return followRepository.findFollow(followingMember, followerMember).isEmpty();
    }

    @Transactional
    @Override
    public void follow(Member followingMember, Member followerMember) {
        Optional<Follow> byFollow = followRepository.findFollow(followingMember, followerMember);

        byFollow.ifPresentOrElse(
                // 팔로우 되어 있는 경우 삭제
                follow -> {
                    followRepository.delete(follow);
                },
                // 팔로우 하지 않은 경우 추가
                () -> {
                    Follow follow = Follow.builder()
                            .following(followingMember)
                            .follower(followerMember)
                            .build();

                    follow.setFollowing(followingMember);
                    follow.setFollower(followerMember);
                    followRepository.save(follow);
                }
        );
    }

    @Override
    @Transactional
    public void updateProfileImg(Member member, MultipartFile multipartFile) throws IOException {
        InputStream is = multipartFile.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is); // to byte array
        String img = Base64.getEncoder().encodeToString(bytes);

        member.setProfileImage(img);
        memberRepository.save(member);
    }
}
