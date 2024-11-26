package com.moim.moimapiserver.group;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    

    



}
