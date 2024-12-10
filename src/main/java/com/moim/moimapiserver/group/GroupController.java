package com.moim.moimapiserver.group;

import com.moim.moimapiserver.member.MemberDto;
import lombok.extern.log4j.Log4j2;

import org.springframework.dao.EmptyResultDataAccessException;
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
    
    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
        try {
            groupService.addComment(commentDto); // 서비스 호출
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글 작성 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 작성 실패: " + e.getMessage());
        }
    }
    
    @GetMapping("/post/{p_no}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable("p_no") int pNo) {
        List<CommentDto> comments = groupService.getCommentsByPost(pNo);
        return ResponseEntity.ok(comments);
    }
    
    @PostMapping("/{gNo}/posts/{pNo}/delete")
    public ResponseEntity<Map<String, Object>> deletePost(
            @PathVariable("gNo") int gNo,
            @PathVariable("pNo") int pNo,
            @RequestBody Map<String, Object> requestBody) { // JSON 요청 본문
        System.out.println("gNo: " + gNo);
        System.out.println("pNo: " + pNo);
        System.out.println("mNo: " + requestBody.get("m_no"));

        int mNo = Integer.parseInt(requestBody.get("m_no").toString());
        boolean isDeleted = groupService.deletePostWithPermissionCheck(gNo, pNo, mNo);
        if (isDeleted) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("success", false, "message", "삭제 권한이 없습니다."));
        }
    }

    @GetMapping("/post/{pNo}/owner")
    public ResponseEntity<Map<String, Object>> getPostOwner(@PathVariable("pNo") int pNo) {
        try {
            int owner = groupService.getPostOwner(pNo);
            return ResponseEntity.ok(Map.of("success", true, "data", Map.of("m_no", owner)));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("success", false, "message", "No owner found for the given post ID"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("success", false, "message", "An unexpected error occurred"));
        }
    }
    
    @PutMapping("/posts/{pNo}/edit")
    public ResponseEntity<Map<String, Object>> editPost(
    		@PathVariable("pNo") int pNo,
            @RequestBody Map<String, String> requestBody) {
        try {
            String pText = requestBody.get("p_text");
            String pImg = requestBody.get("p_img");

            if (pText == null || pText.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "게시글 내용은 필수입니다."));
            }

            boolean isUpdated = groupService.editPost(pNo, pText, pImg);

            if (isUpdated) {
                return ResponseEntity.ok(Map.of("success", true, "message", "게시글이 수정되었습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "게시글을 찾을 수 없습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "게시글 수정 중 오류가 발생했습니다.", "error", e.getMessage()));
        }
    }
    
    @DeleteMapping("/comment/{coNo}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable("coNo") int coNo) {
        try {
            boolean isDeleted = groupService.deleteComment(coNo);
            if (isDeleted) {
                return ResponseEntity.ok(Map.of("success", true, "message", "댓글이 삭제되었습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("success", false, "message", "댓글 삭제 실패"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "댓글 삭제 중 오류가 발생했습니다."));
        }
    }

    @GetMapping("/comment/{coNo}/owner")
    public ResponseEntity<Map<String, Object>> getCommentOwner(@PathVariable("coNo") int coNo) {
        try {
            int owner = groupService.getCommentOwner(coNo);
            return ResponseEntity.ok(Map.of("m_no", owner));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "댓글 작성자를 찾을 수 없습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "댓글 작성자 조회 중 오류가 발생했습니다."));
        }
    }
    
    @GetMapping("/{g_no}/messages")
    public ResponseEntity<?> getMessagesByGroup(@PathVariable("g_no") int g_no) {
        try {
            List<ChatDto> messages = groupService.getMessagesByGroup(g_no);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch messages");
        }
    }

    @PostMapping("/{g_no}/message")
    public ResponseEntity<?> createMessage(@PathVariable("g_no") int g_no, @RequestBody ChatDto chatDto) {
        try {
            chatDto.setG_no(g_no); // g_no 추가
            System.out.println("Spring - 메시지 전송 데이터: " + chatDto);
            groupService.createMessage(g_no, chatDto);
            return ResponseEntity.ok("Message sent successfully");
        } catch (Exception e) {
            e.printStackTrace(); // 전체 스택 트레이스 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message");
        }
    }
    
    @PutMapping("/{g_no}/update")
    public ResponseEntity<?> updateGroup(@PathVariable("g_no") int gNo, @RequestBody GroupDto updatedGroupData) {
        try {
            boolean isUpdated = groupService.updateGroup(gNo, updatedGroupData);
            if (isUpdated) {
                return ResponseEntity.ok("그룹 정보가 성공적으로 업데이트되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("그룹 정보 업데이트 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류로 인해 그룹 정보를 업데이트할 수 없습니다.");
        }
    }
    @GetMapping("/{g_no}/posts/{m_no}")
    public ResponseEntity<?> getPostsByGroupAndMember(
            @PathVariable("g_no") int gNo,
            @PathVariable("m_no") int mNo) {
        try {
            List<PostDto> posts = groupService.getPostsByGroupAndMember(gNo, mNo);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch posts");
        }
    }
    
    @DeleteMapping("/{g_no}/kick")
    public ResponseEntity<Map<String, Object>> kickMember(
            @PathVariable("g_no") int gNo,
            @RequestBody Map<String, Object> requestBody) {
        try {
            int mNo = (int) requestBody.get("m_no"); // JSON의 m_no 추출
            log.info("Kick request received: g_no={}, m_no={}", gNo, mNo);

            // 강퇴 처리
            boolean isKicked = groupService.kickMember(gNo, mNo);

            if (isKicked) {
                return ResponseEntity.ok(Map.of("success", true, "message", "멤버를 성공적으로 강퇴했습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "멤버 강퇴 실패. 멤버가 존재하지 않을 수 있습니다."));
            }
        } catch (Exception e) {
            log.error("Error during member kick: g_no={}, m_no={}", gNo, requestBody.get("m_no"), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "멤버 강퇴 중 서버 오류 발생."));
        }
    }
    
    @PutMapping("/{g_no}/member/{m_no}/role")
    public ResponseEntity<?> updateMemberRole(
        @PathVariable("g_no") int gNo,
        @PathVariable("m_no") int mNo,
        @RequestBody Map<String, Object> requestBody) {
        try {
            int newRole = (int) requestBody.get("g_m_role");

            // 역할 변경 서비스 호출
            boolean isUpdated = groupService.updateMemberRole(gNo, mNo, newRole);
            if (isUpdated) {
                return ResponseEntity.ok(Map.of("success", true, "message", "등급이 성공적으로 변경되었습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "등급 변경에 실패했습니다."));
            }
        } catch (Exception e) {
            log.error("등급 변경 오류:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "서버 오류가 발생했습니다."));
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        try {
            List<GroupDto> groups = groupService.getAllGroups();
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Collections.emptyList());
        }
    }



    @PostMapping("/getMyGroup")
    @ResponseBody
    public Object getMyGroup(@RequestBody MemberDto memberDto) {
        log.info("[GroupController] getMyGroup()");

        Map<String, Object> resultMap = groupService.getMyGroup(memberDto);

        return resultMap;
    }


    @PostMapping("/updateGroupStatus")
    @ResponseBody
    public int updateGroupStatus(@RequestBody GroupDto groupDto) {
        log.info("[GroupController] updateGroupStatus");
        int result = groupService.updateGroupStatus(groupDto);
        return result;
    }

    @GetMapping("/getPremiumGroup")
    public Object getPremiumGroup () {
        log.info("[GroupController] getPremiumGroup");

        Map<String, Object> resultMap = groupService.getPremiumGroup();

        return resultMap;
    }

        
}
