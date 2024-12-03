package com.moim.moimapiserver.credit;
import com.moim.moimapiserver.member.MemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ExpiredPaymentReader implements ItemReader<CreditDto> {

    private ICreditMapper iCreditMapper;
    private List<CreditDto> members;
    private int currentIndex = 0;

    public ExpiredPaymentReader(ICreditMapper iCreditMapper) {
        this.iCreditMapper = iCreditMapper;
    }

    @Override
    public CreditDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // 한 번만 데이터를 가져옴
        if (members == null) {
            members = iCreditMapper.getExpiredMembers();
            log.info("만료된 회원 목록 로드 완료: {}", members);
        }

        // 현재 인덱스를 기준으로 항목 반환
        if (currentIndex < members.size()) {
            return members.get(currentIndex++);
        }
        return null;
    }

}
