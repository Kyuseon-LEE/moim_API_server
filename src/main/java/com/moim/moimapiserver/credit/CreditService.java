package com.moim.moimapiserver.credit;

import com.moim.moimapiserver.member.MemberDto;
import com.sun.source.doctree.SerialDataTree;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class CreditService {

    private final int SUCCESS_UPDATE_MEMBER_GRADE = 0;
    private final int FAIL_UPDATE_MEMBER_GRADE = 1;
    private final int UPDATE_MEMBERSHIP_CANCEL = 2;
    private final int FAIL_MEMBERSHIP_CANCEL = 3;
    private final ICreditMapper iCreditMapper;

    public CreditService(final ICreditMapper iCreditMapper) {
        this.iCreditMapper = iCreditMapper;
    }

    public int insertToPayment(CreditDto creditDto) {
        log.info("[creditService] insert to payment credit record]");
        int result = iCreditMapper.insertToPayment(creditDto);

        return result;
    }


    public Map<String, Object> getPayment(MemberDto memberDto) {
        log.info("[creditService] getPayment()");
        Map<String, Object> resultMap = new HashMap<>();

        CreditDto creditDtos = iCreditMapper.getPayment(memberDto);

        if(creditDtos != null) {
            log.info("[creditService] getPayment is successful");
            resultMap.put("creditDtos", creditDtos);
            return resultMap;
        } else {
            log.info("[creditService] getPayment is failed");
            resultMap.put("creditDtos", null);
            return resultMap;
        }
    }

    @Transactional
    public int cancelMembership(MemberDto memberDto) {
        log.info("[creditService] cancelMembership()");

        int memberResult = iCreditMapper.cancelMembership(memberDto);
        int paymentResult = iCreditMapper.updatePaymentStatus(memberDto);

        // 두 작업 모두 성공했는지 확인
        if (memberResult > 0 && paymentResult > 0) {
            return UPDATE_MEMBERSHIP_CANCEL;  // 둘 다 성공한 경우
        } else {
            return FAIL_MEMBERSHIP_CANCEL;   // 하나라도 실패한 경우
        }
    }
}
