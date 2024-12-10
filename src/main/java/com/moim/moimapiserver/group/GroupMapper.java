package com.moim.moimapiserver.group;

import java.util.List;
import java.util.Map;

import com.moim.moimapiserver.member.MemberDto;
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

	int getUserRoleInGroup(@Param("gNo") int gNo, @Param("mNo") int mNo);

	void deleteCommentsByPost(@Param("pNo") int pNo);

	void deletePost(@Param("gNo") int gNo, @Param("pNo") int pNo);

	int getPostOwner(@Param("pNo") int pNo);

	Integer checkPostExists(@Param("pNo") int pNo);

	int updatePost(@Param("pNo") int pNo, @Param("pText") String pText, @Param("pImg") String pImg);

    int deleteComment(@Param("coNo") int coNo);

    int getCommentOwner(@Param("coNo") int coNo);

	List<ChatDto> getMessagesByGroup(int g_no);

	void createMessage(ChatDto chatDto);

	int updateGroup(@Param("gNo") int gNo, @Param("updatedGroupData") GroupDto updatedGroupData);

	List<PostDto> findPostsByGroupAndMember(@Param("gNo") int gNo, @Param("mNo")int mNo);

	int deleteGroupMember(@Param("gNo") int gNo, @Param("mNo") int mNo);

	int updateMemberRole(@Param("gNo") int gNo, @Param("mNo") int mNo, @Param("gMRole") int gMRole);

	List<GroupDto> findAllGroups();

	List<GroupDto> getMyGroup(MemberDto memberDto);

	int updateGroupStatus(GroupDto groupDto);
}
	