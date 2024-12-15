package com.moim.moimapiserver.adminGroup;

import com.moim.moimapiserver.dto.ResponseWrapper;
import com.moim.moimapiserver.group.GroupDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequestMapping("/admin_group")
@RestController
public class AdminGroupController {
    private final AdminGroupService adminGroupService;

    public AdminGroupController(final AdminGroupService adminGroupService) {
        this.adminGroupService = adminGroupService;
    }

    @GetMapping("/fetchGroupList")
    public ResponseEntity<ResponseWrapper<List<GroupDto>>> fetchGroupList(
            @RequestParam("offset")int offset,
            @RequestParam("size")int size) {
        log.info("fetchGroupList()");
        ResponseWrapper<List<GroupDto>> result = adminGroupService.getGroupList(offset, size);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };

    }

    @GetMapping("/fetchGroupListCount")
    public ResponseEntity<ResponseWrapper<Integer>> fetchGroupListCount() {
        log.info("fetchGroupListCount()");

        ResponseWrapper<Integer> result = adminGroupService.getGroupListCount();
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

}
