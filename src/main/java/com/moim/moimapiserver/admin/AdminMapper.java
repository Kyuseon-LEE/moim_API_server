package com.moim.moimapiserver.admin;

import com.moim.moimapiserver.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<AdminPartDto> selectAdminPart();

    List<AdminPositionDto> selectAdminPosition();

    int insertNewAdmin(AdminDto adminDto);

    AdminDto selectAdminByAId(String a_id);

    int adminDuplicateCheck(String a_id);

    List<AdminDto> selectAdmins(int offset, int size);

    int selectAdminCount();

    List<AdminDto> selectAdminsByApproval(int offset, int size);

    int updateAdminApprovalByANo(AdminDto adminDto);

    int deleteAdminByANo(int a_no);

    int updateAdminPartByANo(AdminDto adminDto);

    int updateAdminPositionByANo(AdminDto adminDto);

    int updateAdminAuthorityByANo(AdminDto adminDto);

    int updateAdminActiveByANo(AdminDto adminDto);

    List<AdminPartDto> selectAdminPartByOffset(int offset, int size);

    int selectAdminPartCount();

    int selectAdminCountByApproval();

    int deleteAdminPartItem(AdminPartDto adminPartDto);

    int insertNewAdminPart(AdminPartDto adminPartDto);

    int updateAdminPart(AdminPartDto adminPartDto);

    int selectAdminPositionCount();

    List<AdminPositionDto> selectAdminPositionByOffset(int offset, int size);

    int insertNewAdminPosition(AdminPositionDto adminPositionDto);

    int updateAdminPosition(AdminPositionDto adminPositionDto);

    int deleteAdminPosition(AdminPositionDto adminPositionDto);

    AdminDto selectAdminByAid(AdminDto adminDto);

    int updateAdminByAid(AdminDto adminDto);

    int selectUserCount();

    List<MemberDto> selectUsers(int offset, int size);

    List<MemberDto> selectMemberByMname(String searchKeyword);

    int deleteUserByMno(int m_no);

    int updateUserByMno(MemberDto memberDto);

    List<UserJoinGroupDto> selectUserGroupByMno(int m_no);

    List<UserPostDto> selectUserGroupPostByMno(int m_no);

    int deleteGroupMemberByGnoAndMno(Integer m_no, Integer g_no);

    int deleteGroupPostByPnoAndMno(Integer m_no, Integer p_no);

    List<AdminDto> selectAllAdmin();

    List<MemberDto> selectAllUser();
}
