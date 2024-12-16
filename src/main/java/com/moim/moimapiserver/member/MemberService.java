package com.moim.moimapiserver.member;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class MemberService implements MemberServiceConfig{

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
        if (result > 0) {
            log.info("[MemberService] signupConfirm successful");
            resultMap.put("result", SIGNUP_SUCCESS);
            return resultMap;
        } else {
            log.info("[MemberService] signupConfirm failed");
            resultMap.put("result", SIGNUP_FAIL);
            return resultMap;
        }

    }


    public Map<String, Object> getMemberInfo(MemberDto memberDto) {
        log.info("[MemberService] getMemberInfo");

        Map<String, Object> resultMap = new HashMap<>();

        MemberDto memberDtos = iMemberMapper.getMemberInfo(memberDto);

        if (memberDtos != null) {
            log.info("[MemberService] getMemberInfo successful");
            resultMap.put("memberDtos", memberDtos);
            return resultMap;
        } else {
            log.info("[MemberService] getMemberInfo failed");
            resultMap.put("result", FAIL_MEMBER_INFO);
            return resultMap;
        }
    }

    public int updateMemberInfo(MemberDto memberDto) {

        int result = iMemberMapper.updateMemberInfo(memberDto);

        if (result > 0) {
            log.info("[MemberService] updateMemberInfo successful");
            return SUCCESS_MEMBER_UPDATE;
        } else {
            log.info("[MemberService] updateMemberInfo failed");
            return FAIL_MEMBER_UPDATE;
        }
    }

    public int insertCategories(MemberDto memberDto) {
        log.info("[memberService] insertCategories]");

        int result = iMemberMapper.insertCategories(memberDto);

        if(result > 0) {
            return SUCCESS_CATEGORY_INSERT;
        } else {
            return FAIL_CATEGORY_INSERT;
        }

    }


    public Map<String, Object> socialSignup(MemberDto memberDto) {
        log.info("[memberService] socialSignup");
        Map<String, Object> resultMap = new HashMap<>();
        int result = iMemberMapper.socialSignup(memberDto);

        if(result > 0) {
            resultMap.put("result", SUCCESS_SOCIAL_SIGNUP);
            return resultMap;
        } else {
            resultMap.put("result", FAIL_SOCIAL_SIGNUP);
            return resultMap;
        }
    }


    public int existMember(String m_social_id) {
        log.info("[memberService] existMember()");

        int result = iMemberMapper.existMember(m_social_id);
        log.info("result ---> {}", result);
        if(result > 0) {
            return EXIST_SOCIAL_MEMBER;
        } else {
            return NOT_EXIST_SOCIAL_MEMBER;
        }
    }

    public Map<String, Object> findMemberId(MemberDto memberDto) {
        log.info("[memberService] findMemberId");

        Map<String, Object> resultMap = new HashMap<>();
        MemberDto memberId = iMemberMapper.findMemberId(memberDto);

        if(memberId != null) {
            resultMap.put("memberId", memberId);
            return resultMap;
        } else {
            return null;
        }
    }

    public int findMemberPw(MemberDto memberDto) {

        int result = iMemberMapper.findMemberPw(memberDto);
        if(result > 0) {
            return CORRECT_USER_FOR_FIND_PASSWORD;
        } else {
            return FAIL_USER_FOR_FIND_PASSWORD;
        }
    }

    public int findPasswordConfirm(MemberDto memberDto) {
        log.info("[memberService] findPasswordConfirm");

        int result = iMemberMapper.findPasswordConfirm(memberDto);

        if(result > 0) {
            return SUCCESS_UPDATE_TEMPORARY_PASSWORD;
        } else {
            return FAIL_UPDATE_TEMPORARY_PASSWORD;
        }
    }

    public boolean passwordConfirm(MemberDto memberDto) {
        log.info("[memberService] passwordConfirm");
        String passwordInDB = iMemberMapper.getPasswordById(memberDto);
        if(passwordInDB == null) {
            log.info("[memberService] 사용자 정보가 존재하지 않습니다.");
            return false;
        } else {
            boolean isMatch = passwordEncoder.matches(memberDto.getM_pw(), passwordInDB);
            if(isMatch) {
                log.info("비밀번호가 일치합니다");
                return true;
            } else {
                log.info("비밀번호가 일치하지 않습니다.");
                return false;
            }
        }
    }
    public int changePassword(MemberDto memberDto) {
        log.info("[memberService] changePassword");
        String newPassword = passwordEncoder.encode(memberDto.getM_pw());
        memberDto.setM_pw(newPassword);
        int result = iMemberMapper.changePassword(memberDto);
        if(result > 0) {
            return SUCCESS_UPDATE_PASSWORD;
        } else {
            return FAIL_UPDATE_PASSWORD;
        }
    }
    public int checkNickname(String m_id) {
        log.info("[memberService] checkNickname");
        int result = iMemberMapper.checkNickname(m_id);
        if(result > 0) {
            return NOT_OKAY_MID;
        } else {
            return OKAY_MID;
        }
    }
    public int newProfileImage(MemberDto memberDto) {
        log.info("[memberService] newProfileImage");
        int result = iMemberMapper.newProfileImage(memberDto);
        if(result > 0) {
            return SUCCESS_UPDATE_PROFILE_IMAGE;
        } else {
            return FAIL_UPDATE_PROFILE_IMAGE;
        }
    }
}


