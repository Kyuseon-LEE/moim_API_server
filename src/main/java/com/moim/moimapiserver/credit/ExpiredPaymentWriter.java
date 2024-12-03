package com.moim.moimapiserver.credit;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ExpiredPaymentWriter implements ItemWriter<CreditDto> {

    private final ICreditMapper iCreditMapper;

    public ExpiredPaymentWriter(final ICreditMapper iCreditMapper) {
        this.iCreditMapper = iCreditMapper;
    }

    // Chunk 처리용
    @Override
    public void write(Chunk<? extends CreditDto> chunk) throws Exception {
        log.info("Processing chunk. Size: {}", chunk.size());
        writeInternal(chunk);
    }

    // List 처리용 추가 메서드
    public void write(List<CreditDto> items) throws Exception {
        log.info("Processing list. Size: {}", items.size());
        writeInternal(items);
    }

    // 공통 로직
    private void writeInternal(Iterable<? extends CreditDto> items) {
        for (CreditDto creditDto : items) {
            log.info("Updating member status to expired for member ID: {}", creditDto.getM_no());
            iCreditMapper.updateMemberStatusToExpired(creditDto.getM_no());
        }
    }
}
