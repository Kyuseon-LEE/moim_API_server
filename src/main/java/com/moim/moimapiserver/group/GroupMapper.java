package com.moim.moimapiserver.group;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupMapper {
    int insertNewGroup(GroupDto groupDto);

	int findMNoByMId(String mId);

	int findLastInsertedGroupNo();

	int insertGroupMember(@Param("gNo") int gNo, @Param("mNo") int mNo, @Param("gMRole") int gMRole);

	List<GroupDto> findGroupsByUserId(String mId);

	GroupDto findGroupByGNo(int gNo);

}
