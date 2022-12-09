package com.io.rol.service;

import com.io.rol.domain.dto.MemberDto;
import com.io.rol.domain.entity.Member;

public interface MemberService {
    Long join(MemberDto memberDto);
    Member findMember(Long id);
}
