package com.moim.moimapiserver.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberMapper {
    int insertNewMember(MemberDto memberDto);

    MemberDto selectMemberForLogin(String m_id);

    MemberDto getMemberInfo(MemberDto memberDto);

    int updateMemberInfo(MemberDto memberDto);

    int insertCategories(MemberDto memberDto);

    int socialSignup(MemberDto memberDto);

    int existMember(String m_social_id);

    MemberDto findMemberId(MemberDto memberDto);

    int findMemberPw(MemberDto memberDto);

    int findPasswordConfirm(MemberDto memberDto);
}
