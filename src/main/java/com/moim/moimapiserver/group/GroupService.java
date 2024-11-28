package com.moim.moimapiserver.group;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GroupService {

    @Autowired
    private GroupMapper groupMapper;

    public Map<String, Object> createGroup(GroupDto groupDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("Received group data: {}", groupDto);

            // 1. m_id를 기반으로 m_no 조회
            String mId = groupDto.getM_id();
            int mNo = groupMapper.findMNoByMId(mId);
            log.info("m_id {} corresponds to m_no {}", mId, mNo);

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
    
    public List<GroupDto> getUserGroups(String mId) {
        try {
            List<GroupDto> groups = groupMapper.findGroupsByUserId(mId);
            if (groups == null) {
                return Collections.emptyList(); // null 대신 빈 리스트 반환
            }
            return groups;
        } catch (Exception e) {
            log.error("Error fetching groups for m_id: {}", mId, e);
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

}

