package com.moim.moimapiserver.member;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class MemberService {

    public static int ALREADY_EXIST_ID      = -1;
    public static int DATABASE_ERROR        = -2;
    public static int AVAILABLE_ID          = -3;
    public static int SIGNUP_SUCCESS        = 0;
    public static int SIGNUP_FAIL           = 1;
    public static int LOGIN_SUCCESS         = 1;
    public static int LOGIN_FAIL            = 2;

    private final IMemberMapper iMemberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberService(final IMemberMapper iMemberMapper, PasswordEncoder passwordEncoder) {
        this.iMemberMapper = iMemberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, Object> signupConfirm(MemberDto memberDto) {
        log.info("[MemberService] signupConfirm]");
        Map<String, Object> resultMap = new HashMap<>();

        String encodePassword = passwordEncoder.encode(memberDto.getM_pw());
        memberDto.setM_pw(encodePassword);

        int result = iMemberMapper.insertNewMember(memberDto);
        if(result > 0) {
            log.info("[MemberService] signupConfirm successful");
            resultMap.put("result", SIGNUP_SUCCESS);
            return resultMap;
        } else {
            log.info("[MemberService] signupConfirm failed");
            resultMap.put("result", SIGNUP_FAIL);
            return resultMap;
        }

    }
}
