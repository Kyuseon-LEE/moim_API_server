package com.moim.moimapiserver.member;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public MemberService(final MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

}
