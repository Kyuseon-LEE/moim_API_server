package com.moim.moimapiserver.group;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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


}
