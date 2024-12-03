package com.moim.moimapiserver.credit;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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


}
