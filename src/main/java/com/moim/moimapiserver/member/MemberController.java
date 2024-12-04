package com.moim.moimapiserver.member;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup_confirm")
    @ResponseBody
    public Object signupConfirm(@RequestBody MemberDto memberDto) {
        log.info("signupConfirm()");

        Map<String, Object> resultMap = memberService.signupConfirm(memberDto);

        return resultMap;
    }


    @PostMapping("/getMemberInfo")
    @ResponseBody
    public Object getMemberInfo(@RequestBody MemberDto memberDto) {
        log.info("getMemberInfo()");

        String m_id = memberDto.getM_id();
        log.info("Authenticated m_id: " + m_id);

        memberDto.setM_id(m_id);

        Map<String, Object> resultMap = memberService.getMemberInfo(m_id);

        return resultMap;
    }

    @PostMapping("/updateMemberInfo")
    @ResponseBody
    public Object updateMemberInfo(@RequestBody MemberDto memberDto) {
        log.info("updateMemberInfo()");
        int result = memberService.updateMemberInfo(memberDto);
        log.info("memberDto", memberDto);
        return result;
    }

    @PostMapping("/insertCategories")
    @ResponseBody
    public Object insertCategories(@RequestBody MemberDto memberDto) {
        log.info("insertCategories()");
        int result = memberService.insertCategories(memberDto);

        return result;
    }

    @PostMapping("/socialSignup")
    @ResponseBody
    public Object socialSignup(@RequestBody MemberDto memberDto) {
        log.info("socialSignup()");

        Map<String, Object> resultMap = memberService.socialSignup(memberDto);

        return resultMap;
    }

    @PostMapping("/existMember")
    @ResponseBody
    public int existMember(@RequestBody Map<String, String> request) {
        log.info("[memberController]existMember()");
        String m_social_id = request.get("m_social_id");
        log.info("받은 m_social_id: {}", m_social_id);

        int result = memberService.existMember(m_social_id);
        return result;
    }

}

