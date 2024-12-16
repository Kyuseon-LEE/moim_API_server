package com.moim.moimapiserver.adminGroup;

import com.moim.moimapiserver.dto.ResponseWrapper;
import com.moim.moimapiserver.group.GroupDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/fetchAllGroupList")
    public ResponseEntity<ResponseWrapper<List<GroupDto>>> fetchAllGroupList() {
        log.info("fetchAllGroupList()");
        ResponseWrapper<List<GroupDto>> result = adminGroupService.getAllGroupList();

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

    @PostMapping("/deleteGroup")
    public ResponseEntity<ResponseWrapper<Integer>> deleteGroup(@RequestBody GroupDto groupDto) {
        log.info("deleteGroup()");

        ResponseWrapper<Integer> result = adminGroupService.confirmGroupDelete(groupDto.getG_no());
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/searchMoim")
    public ResponseEntity<ResponseWrapper<List<GroupDto>>> searchMoim(@RequestBody Map<String, String> data) {
        log.info("searchMoim()");
        ResponseWrapper<List<GroupDto>> result = adminGroupService.getSearchGroup(data.get("searchKeyword"));

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };

    }

    @PostMapping("/updateGroup")
    public ResponseEntity<ResponseWrapper<Integer>> updateGroup(@RequestBody GroupDto groupDto) {
        log.info("updateGroup()");

        ResponseWrapper<Integer> result = adminGroupService.confirmGroupUpdate(groupDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

}
