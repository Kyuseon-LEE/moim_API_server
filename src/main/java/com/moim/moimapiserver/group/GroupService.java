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

    public GroupDto getGroupByNo(int gNo) {
        try {
            GroupDto group = groupMapper.findGroupByNo(gNo); // GroupMapper 호출
            if (group == null) {
                log.warn("No group found for g_no: {}", gNo);
                return null;
            }
            return group;
        } catch (Exception e) {
            log.error("Error fetching group for g_no: {}", gNo, e);
            throw new RuntimeException("Error fetching group data");
        }
    }



}

