package com.moim.moimapiserver.member;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



}

