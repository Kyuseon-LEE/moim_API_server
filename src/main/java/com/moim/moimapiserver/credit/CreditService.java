package com.moim.moimapiserver.credit;

import com.moim.moimapiserver.member.MemberDto;
import com.sun.source.doctree.SerialDataTree;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class CreditService {

    private final int SUCCESS_UPDATE_MEMBER_GRADE = 0;
    private final int FAIL_UPDATE_MEMBER_GRADE = 1;

    private final ICreditMapper iCreditMapper;

    public CreditService(final ICreditMapper iCreditMapper) {
        this.iCreditMapper = iCreditMapper;
    }

    public int insertToPayment(CreditDto creditDto) {
        log.info("[creditService] insert to payment credit record]");
        int result = iCreditMapper.insertToPayment(creditDto);

        return result;
    }

//    public List<MemberDto> downGradeMember () {
//
//        List<MemberDto> expiredMember = iCreditMapper.getExpiredMembers();
//
//        return expiredMember;
//    }


}
