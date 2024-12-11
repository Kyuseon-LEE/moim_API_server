package com.moim.moimapiserver.group;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moim.moimapiserver.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GroupService {
	
    
    final public static int SUCCESS_UPDATE_STATUS = 0;
    final public static int FAIL_UPDATE_STATUS = 1;

    @Autowired
    private GroupMapper groupMapper;

    public Map<String, Object> createGroup(GroupDto groupDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("Received group data: {}", groupDto);

            // 1. m_id 또는 m_social_id 기반으로 m_no 조회
            String userId = groupDto.getM_id();
            Integer mNo = groupMapper.findMNoByUserId(userId);
            if (mNo == null) {
                throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
            }
            log.info("User ID {} corresponds to m_no {}", userId, mNo);

            // 2. g_master_id 설정
            groupDto.setG_master_id(mNo);

            // 3. 그룹 데이터 삽입
            int groupInsertResult = groupMapper.insertNewGroup(groupDto);

            if (groupInsertResult > 0) {
                // 4. 새로 생성된 그룹의 G_NO 가져오기
                int gNo = groupMapper.findLastInsertedGroupNo();
                log.info("Newly created group G_NO: {}", gNo);

                // 5. TBL_GROUP_MEMBER에 모임장 정보 삽입
                int memberInsertResult = groupMapper.insertGroupMember(gNo, mNo, 3); // G_M_ROLE = 3 (모임장)
                log.info("Group member insert result: {}", memberInsertResult);

                if (memberInsertResult > 0) {
                    response.put("result", "success");
                    response.put("g_no", gNo);
                } else {
                    response.put("result", "fail");
                    response.put("message", "Failed to add group leader to TBL_GROUP_MEMBER.");
                }
            } else {
                response.put("result", "fail");
                response.put("message", "Failed to insert group into TBL_GROUP.");
            }
        } catch (Exception e) {
            log.error("Error during group creation", e);
            response.put("result", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    
    public List<GroupDto> getUserGroups(String userId) {
        try {
            List<GroupDto> groups = groupMapper.findGroupsByUserId(userId);
            if (groups == null) {
                return Collections.emptyList(); // null 대신 빈 리스트 반환
            }
            return groups;
        } catch (Exception e) {
            log.error("Error fetching groups for userId: {}", userId, e);
            return Collections.emptyList(); // 예외 발생 시 빈 리스트 반환
        }
    }


    public GroupDto getGroupByGNo(int gNo) {
        return groupMapper.findGroupByGNo(gNo);
    }

    public boolean isGroupMember(int gNo, int mNo) {
        log.info("isGroupMember Service 호출: gNo={}, mNo={}", gNo, mNo);
        int count = groupMapper.isGroupMember(gNo, mNo);
        log.info("isGroupMember 쿼리 결과: {}", count);
        return count > 0;
    }

	public void createPost(PostDto postDto) {
		groupMapper.insertPost(postDto);
		
	}

	 public List<PostDto> getPostsByGroup(int gNo) {
		 return groupMapper.findPostsByGroup(gNo);
	 }

	 public int getGroupConfirmStatus(int gNo) {
        try {
            log.info("Fetching G_CONFIRM for gNo={}", gNo);
            return groupMapper.findGroupConfirmStatus(gNo);
        } catch (Exception e) {
            log.error("Error Fetching G_CONFIRM for gNo={}", gNo, e);
            throw new RuntimeException("G_CONFIRM 상태를 가져오는 중 오류가 발생했습니다.", e);
        }
    }

    public void addGroupMember(int gNo, int mNo, int gMRole) {
        try {
            log.info("Inserting Group Member: gNo={}, mNo={}, gMRole={}", gNo, mNo, gMRole);
            groupMapper.insertGroupMember(gNo, mNo, gMRole);
        } catch (Exception e) {
            log.error("Error Adding Group Member: gNo={}, mNo={}, gMRole={}", gNo, mNo, gMRole, e);
            throw new RuntimeException("TBL_GROUP_MEMBER에 회원 추가 중 오류가 발생했습니다.", e);
        }
    }

    public Integer getGroupMemberRole(int gNo, int mNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("gNo", gNo);
        params.put("mNo", mNo);
        return groupMapper.findGroupMemberRole(params);
    }

    public List<GroupMemberDto> getGroupMembers(int gNo) {
        return groupMapper.findMembersByGroupNo(gNo);
    }

    public void addComment(CommentDto commentDto) {
    	groupMapper.insertComment(commentDto);
    }

	public List<CommentDto> getCommentsByPost(int pNo) {
		return groupMapper.findCommentsByPost(pNo);
	}

	@Transactional
	public boolean deletePostWithPermissionCheck(int gNo, int pNo, int mNo) {
	    System.out.println("deletePostWithPermissionCheck 호출");
	    System.out.println("gNo: " + gNo + ", pNo: " + pNo + ", mNo: " + mNo);

	    // 역할 확인
	    int userRole = groupMapper.getUserRoleInGroup(gNo, mNo);
	    System.out.println("userRole: " + userRole);

	    // 모임장인 경우
	    if (userRole == 3) {
	        groupMapper.deleteCommentsByPost(pNo); // 댓글 삭제
	        groupMapper.deletePost(gNo, pNo);     // 게시글 삭제
	        return true;
	    }

	    // 작성자 확인
	    Integer postOwner = groupMapper.getPostOwner(pNo);
	    if (postOwner == null) {
	        throw new IllegalArgumentException("해당 게시글의 작성자를 찾을 수 없습니다. pNo: " + pNo);
	    }

	    System.out.println("postOwner: " + postOwner);

	    // 작성자가 본인인 경우
	    if (postOwner == mNo) {
	        groupMapper.deleteCommentsByPost(pNo); // 댓글 삭제
	        groupMapper.deletePost(gNo, pNo);     // 게시글 삭제
	        return true;
	    }

	    // 삭제 권한 없음
	    return false;
	}



	public int getPostOwner(int pNo) {
	    return groupMapper.getPostOwner(pNo);
	}

	 @Transactional
	    public boolean editPost(int pNo, String pText, String pImg) {
	        // 게시글 존재 여부 확인
	        Integer postExists = groupMapper.checkPostExists(pNo);
	        if (postExists == null || postExists == 0) {
	            throw new IllegalArgumentException("수정하려는 게시글이 존재하지 않습니다.");
	        }

	        // 게시글 업데이트
	        int rowsUpdated = groupMapper.updatePost(pNo, pText, pImg);
	        return rowsUpdated > 0;
	    }

	@Transactional
    public boolean deleteComment(int coNo) {
        int rowsAffected = groupMapper.deleteComment(coNo);
        return rowsAffected > 0;
    }

    public int getCommentOwner(int coNo) {
        return groupMapper.getCommentOwner(coNo);
    }

    public List<ChatDto> getMessagesByGroup(int g_no) {
        return groupMapper.getMessagesByGroup(g_no);
    }

    public void createMessage(int g_no, ChatDto chatDto) {
        chatDto.setG_no(g_no);
        System.out.println("Spring - DB 저장 전 데이터: " + chatDto);
        groupMapper.createMessage(chatDto);
    }

    public boolean updateGroup(int gNo, GroupDto updatedGroupData) {
        int rowsAffected = groupMapper.updateGroup(gNo, updatedGroupData);
        return rowsAffected > 0; // 성공적으로 업데이트된 경우 true 반환
    }

    public List<PostDto> getPostsByGroupAndMember(int gNo, int mNo) {
        return groupMapper.findPostsByGroupAndMember(gNo, mNo);
    }

    @Transactional
    public boolean kickMember(int gNo, int mNo) {
        try {
            log.info("Attempting to kick member: gNo={}, mNo={}", gNo, mNo);
            
            // 그룹 멤버 삭제
            int rowsAffected = groupMapper.deleteGroupMember(gNo, mNo);
            log.info("Rows affected during kick: {}", rowsAffected);
            
            return rowsAffected > 0;
        } catch (Exception e) {
            log.error("Error during member kick: gNo={}, mNo={}", gNo, mNo, e);
            throw new RuntimeException("멤버 강퇴 중 오류 발생.", e);
        }
    }

    @Transactional
    public boolean updateMemberRole(int gNo, int mNo, int gMRole) {
        try {
            int rowsUpdated = groupMapper.updateMemberRole(gNo, mNo, gMRole);
            return rowsUpdated > 0; // 성공적으로 변경된 경우 true 반환
        } catch (Exception e) {
            log.error("등급 변경 중 오류 발생:", e);
            throw new RuntimeException("등급 변경 실패");
        }
    }

    public List<GroupDto> getAllGroups() {
        return groupMapper.findAllGroups();
    }

    public Map<String, Object> getMyGroup(MemberDto memberDto) {
        log.info("[groupService] getMyGroup");

        Map<String, Object> resultMap = new HashMap<>();

        List<GroupDto> groupDtos = groupMapper.getMyGroup(memberDto);
        if (groupDtos != null && !groupDtos.isEmpty()) {
            resultMap.put("groupDtos", groupDtos);
        } else {
            resultMap.put("groupDtos", Collections.emptyList());
        }

        return resultMap;
    }

    public int updateGroupStatus(GroupDto groupDto) {
        log.info("[groupService] updateGroupStatus");

        int result = groupMapper.updateGroupStatus(groupDto);
        if(result > 0) {
            return SUCCESS_UPDATE_STATUS;
        } else {
            return FAIL_UPDATE_STATUS;
        }
    }

    public Map<String, Object> getPremiumGroup() {

        Map<String, Object> resultMap = new HashMap<>();

        List<GroupDto> groupDtos = groupMapper.getPremiumGroup();
        if(groupDtos != null) {
            resultMap.put("premiumGroup", groupDtos);
            return resultMap;
        } else {
            return null;
        }
    }
}

