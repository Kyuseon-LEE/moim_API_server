package com.moim.moimapiserver.credit;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class CreditService {

    private final ICreditMapper iCreditMapper;

    public CreditService(final ICreditMapper iCreditMapper) {
        this.iCreditMapper = iCreditMapper;
    }

    public int insertToPayment(CreditDto creditDto) {
        log.info("[creditService] insert to payment credit record]");
        int result = iCreditMapper.insertToPayment(creditDto);

        return result;
    }

    public void upGradeMember(CreditDto creditDto) {
        log.info("[creditService] upgrade credit record");
        iCreditMapper.upGradeMember(creditDto);
    }
}
