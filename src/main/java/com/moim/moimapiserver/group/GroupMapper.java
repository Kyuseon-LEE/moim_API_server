package com.moim.moimapiserver.group;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupMapper {
    int insertNewGroup(GroupDto groupDto);

	int findMNoByMId(String mId);

	int findLastInsertedGroupNo();

	int insertGroupMember(@Param("gNo") int gNo, @Param("mNo") int mNo, @Param("gMRole") int gMRole);

	List<GroupDto> findGroupsByUserId(String mId);

	GroupDto findGroupByGNo(int gNo);

	int isGroupMember(@Param("gNo") int gNo, @Param("mNo") int mNo);

	void insertPost(PostDto postDto);

	List<PostDto> findPostsByGroup(int gNo);

	int findGroupConfirmStatus(int gNo);

	Integer findGroupMemberRole(Map<String, Object> params);

	List<GroupMemberDto> findMembersByGroupNo(int gNo);

	void insertComment(CommentDto commentDto);

	List<CommentDto> findCommentsByPost(int pNo);

}
	