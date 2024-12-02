package com.moim.moimapiserver.credit;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICreditMapper {

    int insertToPayment(CreditDto creditDto);

    void upGradeMember(CreditDto creditDto);
}
