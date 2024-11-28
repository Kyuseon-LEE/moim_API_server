package com.moim.moimapiserver.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberMapper {
    int insertNewMember(MemberDto memberDto);

    MemberDto selectMemberForLogin(String m_id);

    MemberDto getMemberInfo(String m_id);

    String updateNickname(MemberDto memberDto);

    int updateMemberInfo(MemberDto memberDto);

    int insertCategories(MemberDto memberDto);
}
