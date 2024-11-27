package com.moim.moimapiserver.group;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create_group")
    @ResponseBody
    public Object createGroup(@RequestBody GroupDto groupDto) {
        log.info("Request received in createGroup: {}", groupDto);

        Map<String, Object> resultMap = groupService.createGroup(groupDto);
        log.info("Result from service: {}", resultMap);

        // 방어적 응답 반환
        if (resultMap == null || resultMap.isEmpty()) {
            return Map.of("status", "error", "message", "No response from service");
        }
        
        return resultMap;
    }
    
    @GetMapping("/user-groups/{m_id}")
    public ResponseEntity<List<GroupDto>> getUserGroups(@PathVariable("m_id") String m_id) {
        log.info("Fetching groups for user with m_id: {}", m_id);
        try {
            List<GroupDto> groups = groupService.getUserGroups(m_id);
            if (groups == null || groups.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            log.error("Error fetching groups for user with m_id: {}", m_id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Collections.emptyList());
        }
    }
    
    @GetMapping("/{g_no}")
    public ResponseEntity<GroupDto> getGroupByGNo(@PathVariable("g_no") int gNo) {
        log.info("Fetching group with g_no: {}", gNo);
        try {
            GroupDto group = groupService.getGroupByGNo(gNo);
            if (group != null) {
                return ResponseEntity.ok(group);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            log.error("Error fetching group with g_no: {}", gNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{g_no}/is-member/{m_no}")
    public ResponseEntity<Boolean> isGroupMember(
            @PathVariable("g_no") int gNo, 
            @PathVariable("m_no") int mNo) {
        log.info("isGroupMember 호출: g_no={}, m_no={}", gNo, mNo);
        try {
            boolean isMember = groupService.isGroupMember(gNo, mNo);
            log.info("isGroupMember 결과: {}", isMember);
            return ResponseEntity.ok(isMember);
        } catch (Exception e) {
            log.error("isGroupMember 처리 중 오류: g_no={}, m_no={}", gNo, mNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody PostDto postDto) {
        try {
            groupService.createPost(postDto);
            return ResponseEntity.ok("게시글 작성 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 작성 실패");
        }
    }
    
    @GetMapping("/{g_no}/posts")
    public ResponseEntity<List<PostDto>> getPostsByGroup(@PathVariable("g_no") int g_no) {
        List<PostDto> posts = groupService.getPostsByGroup(g_no);
        return ResponseEntity.ok(posts);
    }
    
    @PostMapping("/{gNo}/join")
    public ResponseEntity<?> joinGroup(@PathVariable("gNo") int gNo, @RequestBody Map<String, Object> requestData) {
        try {
            log.info("Join Request Received: gNo={}, requestData={}", gNo, requestData);

            String mNoStr = (String) requestData.get("m_no");
            String message = (String) requestData.get("message");

            // 문자열을 정수로 변환
            int mNo = Integer.parseInt(mNoStr);

            log.info("Processing Join Request: gNo={}, mNo={}, message={}", gNo, mNo, message);

            // 그룹의 G_CONFIRM 상태 확인
            int gConfirm = groupService.getGroupConfirmStatus(gNo);
            int gMRole = (gConfirm == 1) ? 1 : 0;

            groupService.addGroupMember(gNo, mNo, gMRole);

            return ResponseEntity.ok().body(Map.of("success", true, "message", "가입 신청이 완료되었습니다."));
        } catch (NumberFormatException e) {
            log.error("Invalid Number Format: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("success", false, "message", "잘못된 숫자 형식입니다."));
        } catch (Exception e) {
            log.error("Error in joinGroup:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("success", false, "message", "가입 신청 중 오류가 발생했습니다."));
        }
    }
    
    @GetMapping("/{g_no}/member/{m_no}/role")
    public ResponseEntity<Map<String, Object>> getGroupMemberRole(
            @PathVariable("g_no") int gNo,
            @PathVariable("m_no") int mNo) {
        try {
            Integer role = groupService.getGroupMemberRole(gNo, mNo);
            if (role != null) {
                return ResponseEntity.ok(Map.of("success", true, "g_m_role", role));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(Map.of("success", false, "message", "Role not found"));
            }
        } catch (Exception e) {
            log.error("Error fetching group member role:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("success", false, "message", "Error fetching role"));
        }
    }
    
    @GetMapping("/{g_no}/members")
    public ResponseEntity<List<GroupMemberDto>> getGroupMembers(@PathVariable("g_no") int gNo) {
        try {
            List<GroupMemberDto> members = groupService.getGroupMembers(gNo);
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            log.error("Error fetching group members:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
