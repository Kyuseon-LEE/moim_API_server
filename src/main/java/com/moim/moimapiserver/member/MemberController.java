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
        Map<String, Object> resultMap = memberService.getMemberInfo(memberDto);

        return resultMap;
    }

    @PostMapping("/updateMemberInfo")
    @ResponseBody
    public int updateMemberInfo(@RequestBody MemberDto memberDto) {
        log.info("updateMemberInfo()");
        log.info("Received MemberDto: {}", memberDto);
        int result = memberService.updateMemberInfo(memberDto);

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

    @PostMapping("/findMemberId")
    @ResponseBody
    public Object findMemberId(@RequestBody MemberDto memberDto) {
        log.info("[memberController]findMemberId()");
        Map<String, Object> resultMap =  memberService.findMemberId(memberDto);

        return resultMap;
    }

    @PostMapping("/findMemberPw")
    @ResponseBody
    public Object findMemberPw(@RequestBody MemberDto memberDto) {
        log.info("[memberController]findMemberPw()");
        int result =  memberService.findMemberPw(memberDto);

        return result;
    }

    @PostMapping("/findPasswordConfirm")
    @ResponseBody
    public Object findPasswordConfirm(@RequestBody MemberDto memberDto) {
        log.info("[memberController]findPasswordConfirm()");
        int result = memberService.findPasswordConfirm(memberDto);
        return result;
    }

    @PostMapping("/passwordConfirm")
    @ResponseBody
    public boolean passwordConfirm(@RequestBody MemberDto memberDto) {
        log.info("[memberController]passwordConfirm()");

        boolean result = memberService.passwordConfirm(memberDto);

        return result;
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public int changePassword(@RequestBody MemberDto memberDto) {
        log.info("[memberController]changePassword()");

        int result = memberService.changePassword(memberDto);

        return result;
    }

    @GetMapping("/checkNickname")
    public int checkNickname(@RequestParam String m_nickname) {
        log.info("[memberController]checkNickname()");
        int result = memberService.checkNickname(m_nickname);
        return result;

    }

    @PostMapping("/newProfileImage")
    @ResponseBody
    public Object newProfileImage(@RequestBody MemberDto memberDto) {
        log.info("[memberController]newProfileImage()");

        int result = memberService.newProfileImage(memberDto);
        return result;
    }





}

