package com.moim.moimapiserver.credit;

import com.moim.moimapiserver.member.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICreditMapper {

    int insertToPayment(CreditDto creditDto);

    List<CreditDto> getExpiredMembers();

    void updateMemberStatusToExpired(int m_no);
}
