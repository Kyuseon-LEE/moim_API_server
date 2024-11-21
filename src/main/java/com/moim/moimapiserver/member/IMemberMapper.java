package com.moim.moimapiserver.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberMapper {
    int insertNewMember(MemberDto memberDto);
}
