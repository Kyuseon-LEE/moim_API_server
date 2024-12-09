package com.moim.moimapiserver.credit;

import com.moim.moimapiserver.member.MemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/credit")
public class CreditController {

    private final CreditService creditService;

    public CreditController(final CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping("/insertToPayment")
    @ResponseBody
    public int insertToPayment(@RequestBody CreditDto creditDto) {
        log.info("[creditController]insert to payment creditDto: {}", creditDto);
        int result = creditService.insertToPayment(creditDto);

        return result;
    }

    @PostMapping("/getPayment")
    @ResponseBody
    public Object getPayment(@RequestBody MemberDto memberDto) {
        log.info("[creditController]getPayment creditDto: {}", memberDto);

        Map<String, Object> resultMap = creditService.getPayment(memberDto);

        return resultMap;
    }


    @PostMapping("/cancelMembership")
    @ResponseBody
    public int cancelMembership(@RequestBody MemberDto memberDto) {
        log.info("[creditController]cancelMembership creditDto: {}", memberDto);

        int result = creditService.cancelMembership(memberDto);

        return result;
    }


}
