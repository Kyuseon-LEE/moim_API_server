package com.moim.moimapiserver.admin;

import com.moim.moimapiserver.dto.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 어드민 회원가입
    @PostMapping("/registerAdmin")
    public Object registerAdmin(@RequestBody AdminDto adminDto) {
    log.info("registerAdmin()");
    log.info("adminDto: {}", adminDto);
        ResponseWrapper<Object> response = adminService.registerAdminAccount(adminDto);

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    // 어드민 아이디 검증(중복체크)
    @PostMapping("/verifyAdminId")
    public Object verifyAdminId(@RequestBody AdminDto adminDto) {
        log.info("verifyAdminId()");

        ResponseWrapper<Object> response = adminService.checkAdminIdDuplicate(adminDto.getA_id());

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    // 어드민 정보요청 BY A_ID
    @PostMapping("/fetchAdminInfoByAid")
    public Object fetchAdminInfoByAid(@RequestBody AdminDto adminDto) {
        log.info("ADMIN ID: {}", adminDto.getA_id());
        ResponseWrapper<AdminDto> response = adminService.getAdminDetailsByAid(adminDto.getA_id());

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    // 어드민 부서 정보 요청
    @GetMapping("/fetchAdminPartData")
    public Object fetchAdminPartData() {
        log.info("fetchAdminPartData()");
        ResponseWrapper<List<AdminPartDto>> response = adminService.getAdminPartData();

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    // 어드민 직책 정보 요청
    @GetMapping("/fetchAdminPosition")
    public Object fetchAdminPosition() {
        log.info("fetchAdminPosition()");
        ResponseWrapper<List<AdminPositionDto>> response = adminService.getAdminPositionData();

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    // 어드민 리스트 요청
    @GetMapping("/fetchAdminList")
    public Object fetchAdminList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        log.info("getAdminList()");

        ResponseWrapper<List<AdminDto>> adminDtos = adminService.getAdminList(page, size);
        return switch (adminDtos.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(adminDtos);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(adminDtos);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(adminDtos);

        };
    }

    // 유저 리스트 요청
    @GetMapping("/fetchUserList")
    public Object fetchUserList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        log.info("fetchUserList()");

        ResponseWrapper<List<MemberDto>> memberDtos = adminService.getUserList(page, size);
        return switch (memberDtos.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(memberDtos);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(memberDtos);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(memberDtos);

        };
    }

    // 어드민 미승인 유저 리스트 요청
    @GetMapping("/fetchAdminApprovalList")
    public Object fetchAdminApprovalList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        log.info("fetchAdminApprovalList()");

        ResponseWrapper<List<AdminDto>> adminDtos = adminService.getAdminApprovalList(page, size);
        return switch (adminDtos.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(adminDtos);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(adminDtos);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(adminDtos);

        };
    }

    // 어드민 총 인원 요청
    @GetMapping("/fetchAdminCount")
    public Object fetchAdminCount() {
        log.info("getAdminListCount()");

        ResponseWrapper<Integer> adminCount = adminService.getAdminCount();

        return switch (adminCount.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(adminCount);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(adminCount);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(adminCount);

        };
    }

    // 어드민 가입 승인 요청
    @PostMapping("/updateAdminApproval")
    public Object updateAdminApproval(@RequestBody AdminDto adminDto) {
        log.info("updateAdminApproval()");

        ResponseWrapper<Object> result = adminService.approveAdmin(adminDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    // 어드민 탈퇴 요청
    @PostMapping("/deleteAdmin")
    public Object deleteAdmin(@RequestBody AdminDto adminDto) {
        log.info("deleteAdmin()");

        ResponseWrapper<Object> result = adminService.confirmAdminDelete(adminDto.getA_no());

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    // 어드민 부서 변경요청
    @PostMapping("/updateAdminPart")
    public Object updateAdminPart(@RequestBody AdminDto adminDto) {
        log.info("updateAdminPart()");

        ResponseWrapper<Object> result = adminService.confirmAdminPartUpdate(adminDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }
    
    // 어드민 직책 변경 요청
    @PostMapping("/updateAdminPosition")
    public Object updateAdminPosition(@RequestBody AdminDto adminDto) {
        log.info("adminPositionUpdate()");
        ResponseWrapper<Object> result = adminService.confirmAdminPositionUpdate(adminDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    // 어드민 권한 변경 요청
    @PostMapping("/updateAdminAuthority")
    public Object updateAdminAuthority(@RequestBody AdminDto adminDto) {
        log.info("adminAuthorityUpdate()");

        ResponseWrapper<Object> result = adminService.confirmAdminAuthorityUpdate(adminDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }
    
    // 어드민 계정 활성화 여부 변경 요청
    @PostMapping("/updateAdminActive")
    public Object updateAdminActive(@RequestBody AdminDto adminDto) {
        log.info("adminActiveUpdate()");

        ResponseWrapper<Object> result = adminService.confirmAdminActiveUpdate(adminDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }
    
    // 어드민 부서 리스트 요청
    @GetMapping("/fetchAdminPartByOffset")
    public Object fetchAdminPartByOffset(@RequestParam int page , @RequestParam int size) {
        log.info("getAdminPartByOffset()");
        ResponseWrapper<Object> result = adminService.getAdminPartByOffset(page, size);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }
    
    // 어드민 부서 카운트 요청
    @GetMapping("/fetchAdminPartCount")
    public Object fetchAdminPartCount() {
        log.info("getAdminPartCount()");
        ResponseWrapper<Object> result = adminService.getAdminPartCount();

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    // 어드민 미승인 유저 카운트 요청
    @GetMapping("/fetchAdminCountByApproval")
    public Object fetchAdminCountByApproval() {
        log.info("fetchAdminCountByApproval()");
        ResponseWrapper<Object> result = adminService.getAdminCountByApproval();
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };

    }

    // 어드민 부서아이템 삭제 요청
    @PostMapping("/deleteAdminPartItem")
    public Object deleteAdminPartItem(@RequestBody AdminPartDto adminPartDto) {
        log.info("deleteAdminPartItem()");
        ResponseWrapper<Object> result = adminService.confirmAdminPartDelete(adminPartDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    // 어드민 부서아이템 추가 요청
    @PostMapping("/addNewAdminPart")
    public Object addNewAdminPart(@RequestBody AdminPartDto adminPartDto) {
        log.info("addNewAdminPart()");
        ResponseWrapper<Object> result = adminService.confirmNewAdminPart(adminPartDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    // 어드민 부서 아이템 수정 요청
    @PostMapping("/updateAdminPartItem")
    public Object updateAdminPartItem(@RequestBody AdminPartDto adminPartDto) {
        log.info("updateAdminPartItem()");

        ResponseWrapper<Object> result = adminService.confirmUpdateAdminPartItem(adminPartDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    // 어드민 직책 카운트 요청
    @GetMapping("/fetchAdminPositionCount")
    public Object fetchAdminPositionCount() {
        log.info("fetchAdminPositionCount()");

        ResponseWrapper<Object> result = adminService.getAdminPositionCount();

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    // 어드민 직책 리스트 요청
    @GetMapping("/fetchAdminPositionByOffset")
    public Object fetchAdminPositionByOffset(@RequestParam int page , @RequestParam int size) {
        log.info("fetchAdminPositionByOffset()");

        ResponseWrapper<Object> result = adminService.getAdminPositionByOffset(page, size);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    // 어드민 직책 아이템 추가 요청
    @PostMapping("/addNewAdminPosition")
    public Object addNewAdminPosition(@RequestBody AdminPositionDto adminPositionDto) {
        log.info("addNewAdminPart()");
        ResponseWrapper<Object> result = adminService.confirmNewAdminPosition(adminPositionDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    // 어드민 직책 아이템 수정 요청
    @PostMapping("/updateAdminPositionItem")
    public Object updateAdminPositionItem(@RequestBody AdminPositionDto adminPositionDto) {
        log.info("updateAdminPositionItem()");
        ResponseWrapper<Object> result = adminService.confirmAdminPositionItemUpdate(adminPositionDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    // 어드민 직책 아이템 삭제 요청
    @PostMapping("/deleteAdminPositionItem")
    public Object deleteAdminPositionItem(@RequestBody AdminPositionDto adminPositionDto) {
        log.info("deleteAdminPositionItem()");
        ResponseWrapper<Object> result = adminService.confirmAdminPositionItemDelete(adminPositionDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }
    @PostMapping("/fetchLoginedAdminData")
    public ResponseEntity<ResponseWrapper<Object>> fetchLoginedAdminData(@RequestBody AdminDto adminDto) {
        log.info("deleteRefreshToken()");

        ResponseWrapper<Object> response = adminService.getLoginedAdminData(adminDto);

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    @PostMapping("/updateAdminInfo")
    public Object updateAdminInfo(@RequestBody AdminDto adminDto) {
        log.info("updateAdminInfo()");
        log.info("adminDto: {}", adminDto);
        ResponseWrapper<Object> response = adminService.confirmAdminInfoUpdate(adminDto);

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    // 어드민 총 인원 요청
    @GetMapping("/fetchUserCount")
    public Object fetchUserCount() {
        log.info("fetchUserCount()");

        ResponseWrapper<Integer> adminCount = adminService.getUserCount();

        return switch (adminCount.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(adminCount);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(adminCount);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(adminCount);

        };
    }

    @PostMapping("/searchUsers")
    public ResponseEntity<ResponseWrapper<List<MemberDto>>> searchUsers(@RequestBody Map<String, String> searchMap) {
        log.info("searchUsers()");

        ResponseWrapper<List<MemberDto>> result = adminService.searchUsers(searchMap.get("searchKeyword"));
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<ResponseWrapper<Integer>> deleteUser(@RequestBody MemberDto memberDto) {
        log.info("deleteUser()");

        ResponseWrapper<Integer> result = adminService.confirmUserDelete(memberDto.getM_no());

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/updateUser")
    public ResponseEntity<ResponseWrapper<Integer>> updateUser(@RequestBody MemberDto memberDto) {
        log.info("updateUser()");
        ResponseWrapper<Integer> result = adminService.confirmUserUpdate(memberDto);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/fetchUserDetailGroupInfo")
    public ResponseEntity<ResponseWrapper<List<UserJoinGroupDto>>> fetchUserDetailGroupInfo(@RequestBody MemberDto memberDto) {
        log.info("fetchUserDetailGroupInfo()");
        log.info("meberDto: {}", memberDto);
        ResponseWrapper<List<UserJoinGroupDto>> result = adminService.getUserDetailGroupInfo(memberDto.getM_no());

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/fetchUserDetailPostInfo")
    public ResponseEntity<ResponseWrapper<List<UserPostDto>>> fetchUserDetailPostInfo(@RequestBody MemberDto memberDto) {
        log.info("fetchUserDetailPostInfo()");
        ResponseWrapper<List<UserPostDto>> result = adminService.getUserDetailPostInfo(memberDto.getM_no());

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/deleteGroupMember")
    public ResponseEntity<ResponseWrapper<Integer>> deleteGroupMember (@RequestBody Map<String, Integer> data) {
        log.info("deleteGroupMember()");

        ResponseWrapper<Integer> result = adminService.confirmDeleteGroupMember(data.get("m_no"), data.get("g_no"));
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/deleteGroupPost")
    public ResponseEntity<ResponseWrapper<Integer>> deleteGroupPost (@RequestBody Map<String, Integer> data) {
        log.info("deleteGroupPost()");

        ResponseWrapper<Integer> result = adminService.confirmDeleteGroupPost(data.get("m_no"), data.get("p_no"));
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };

    }

    @GetMapping("/fetchAllAdminList")
    public ResponseEntity<ResponseWrapper<List<AdminDto>>> fetchAllAdminList() {
        log.info("fetchAllAdminList()");

        ResponseWrapper<List<AdminDto>> adminDtos = adminService.getAllAdminList();
        return switch (adminDtos.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(adminDtos);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(adminDtos);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(adminDtos);

        };
    }
    @GetMapping("/fetchAllUserList")
    public ResponseEntity<ResponseWrapper<List<MemberDto>>> fetchAllUserList() {
        log.info("fetchAllUserList()");

        ResponseWrapper<List<MemberDto>> result = adminService.getAllUserList();
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }
}
