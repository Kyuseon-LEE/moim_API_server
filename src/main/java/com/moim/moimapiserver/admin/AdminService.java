package com.moim.moimapiserver.admin;

import com.moim.moimapiserver.config.AdminStatusConfig;
import com.moim.moimapiserver.dto.*;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class AdminService {

    private final AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    // 응답 메서드
    private <T> ResponseWrapper<T> responseWrapper(String status, String message, T data) {
        return ResponseWrapper.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }
    public ResponseWrapper<Object> checkAdminIdDuplicate(String a_id) {
        log.info("checkAdminIdDuplicate()");

        try {
            int result = adminMapper.adminDuplicateCheck(a_id);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "EXIST ID", null);

            } else {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "AVAILABLE ID", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> registerAdminAccount(AdminDto adminDto) {
        log.info("registerAdminAccount()");

        try {
            int result = adminMapper.insertNewAdmin(adminDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "REGISTER SUCCESS", null);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "REGISTER FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }

    }

    public ResponseWrapper<AdminDto> getAdminDetailsByAid(String a_id) {
        log.info("getAdminDetailsById()");

        try {
            AdminDto adminDto = adminMapper.selectAdminByAId(a_id);
            if (adminDto != null)

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", adminDto);
            else

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA NOT FOUNT", null);


        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<AdminPartDto>> getAdminPartData() {
        log.info("getAdminPartData()");
        try {
            List<AdminPartDto> adminPartDtos = adminMapper.selectAdminPart();
            if (adminPartDtos.isEmpty()) {
                log.info("ADMIN PART DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("ADMIN PART DATA FETCH SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", adminPartDtos);
            }
        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }


    public ResponseWrapper<List<AdminPositionDto>> getAdminPositionData() {
        log.info("getAdminPositionData()");

        try {
            List<AdminPositionDto> adminPositionDtos = adminMapper.selectAdminPosition();
            if (adminPositionDtos.isEmpty()) {
                log.info("ADMIN POSITION DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("ADMIN POSITION DATA FETCH SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", adminPositionDtos);
            }
        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }

    }

    public ResponseWrapper<List<AdminDto>> getAdminList(int page, int size) {
        log.info("getAdminList()");
        int offset = page * size;
        try {
            List<AdminDto> adminDtos = adminMapper.selectAdmins(offset, size);
            if (adminDtos.isEmpty()) {
                log.info("ADMIN LIST IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("ADMIN LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", adminDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<MemberDto>> getUserList(int page, int size) {
        log.info("getUserList()");
        int offset = page * size;
        try {
            List<MemberDto> memberDtos = adminMapper.selectUsers(offset, size);
            if (memberDtos.isEmpty()) {
                log.info("USER LIST IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("USER LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", memberDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Integer> getAdminCount() {
        log.info("getAdminCount()");

        try {
            int count = adminMapper.selectAdminCount();
            if (count < 0) {
                log.info("ADMIN LIST COUNT IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("ADMIN LIST COUNT GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", count);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<AdminDto>> getAdminApprovalList(int page, int size) {
        log.info("getAdminApprovalList()");
        int offset = page * size;
        try {
            List<AdminDto> adminDtos = adminMapper.selectAdminsByApproval(offset, size);
            if (adminDtos.isEmpty()) {
                log.info("ADMIN APPROVAL LIST IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("ADMIN APPROVAL LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", adminDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> approveAdmin(AdminDto adminDto) {
        log.info("approveAdmin()");
        try {
            int result = adminMapper.updateAdminApprovalByANo(adminDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", null);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "UPDATE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> confirmAdminDelete(int a_no) {
        log.info("confirmAdminDelete()");
        try {
            int result = adminMapper.deleteAdminByANo(a_no);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DELETE SUCCESS", null);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DELETE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> confirmAdminPartUpdate(AdminDto adminDto) {
        log.info("confirmAdminPartUpdate()");
        try {
            int result = adminMapper.updateAdminPartByANo(adminDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", null);

            } else {
                return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> confirmAdminPositionUpdate(AdminDto adminDto) {
        log.info("confirmAdminPositionUpdate()");
        try {
            int result = adminMapper.updateAdminPositionByANo(adminDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", null);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "UPDATE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> confirmAdminAuthorityUpdate(AdminDto adminDto) {
        log.info("confirmAdminAuthorityUpdate()");
        try {
            int result = adminMapper.updateAdminAuthorityByANo(adminDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", null);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "UPDATE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> confirmAdminActiveUpdate(AdminDto adminDto) {
        log.info("adminActiveUpdateConfirm()");
        try {
            int result = adminMapper.updateAdminActiveByANo(adminDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", null);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "UPDATE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> getAdminPartByOffset(int page, int size) {
        log.info("getAdminPartByOffset()");
        int offset = page * size;
        try {
            List<AdminPartDto> adminPartDtos = adminMapper.selectAdminPartByOffset(offset, size);
            if (adminPartDtos != null) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "GET DATA SUCCESS", adminPartDtos);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "GET DATA FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> getAdminPartCount() {
        log.info("getAdminPartCount()");
        try {
            int count = adminMapper.selectAdminPartCount();
            if (count > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "GET DATA SUCCESS", count);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "GET DATA FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }

    }

    public ResponseWrapper<Object> getAdminCountByApproval() {
        log.info("getAdminCountByApproval()");
        try {
            int count = adminMapper.selectAdminCountByApproval();
            if (count > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "GET DATA SUCCESS", count);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "GET DATA FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }

    }


    public ResponseWrapper<Object> confirmAdminPartDelete(AdminPartDto adminPartDto) {
        log.info("adminPartItemDeleteConfirm()");
        try {
            int result = adminMapper.deleteAdminPartItem(adminPartDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DELETE SUCCESS", result);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DELETE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> confirmNewAdminPart(AdminPartDto adminPartDto) {
        log.info("confirmNewAdminPart()");
        try {
            int result = adminMapper.insertNewAdminPart(adminPartDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "INSERT SUCCESS", result);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "INSERT FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> confirmUpdateAdminPartItem(AdminPartDto adminPartDto) {
        log.info("confirmUpdateAdminPartItem()");

        try {
            int result = adminMapper.updateAdminPart(adminPartDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "GET DATA SUCCESS", result);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "GET DATA FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> getAdminPositionCount() {
        try {
            int count = adminMapper.selectAdminPositionCount();
            if (count > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "GET DATA SUCCESS", count);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "GET DATA FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> getAdminPositionByOffset(int page, int size) {
        log.info("getAdminPositionByOffset()");
        int offset = page * size;
        try {
            List<AdminPositionDto> adminPositionDtos = adminMapper.selectAdminPositionByOffset(offset, size);
            if (adminPositionDtos != null) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "GET DATA SUCCESS", adminPositionDtos);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "GET DATA FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }


    public ResponseWrapper<Object> confirmNewAdminPosition(AdminPositionDto adminPositionDto) {
        log.info("confirmNewAdminPosition()");
        try {
            int result = adminMapper.insertNewAdminPosition(adminPositionDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "INSERT SUCCESS", result);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "INSERT FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> confirmAdminPositionItemUpdate(AdminPositionDto adminPositionDto) {
        log.info("confirmAdminPositionItemUpdate()");

        try {
            int result = adminMapper.updateAdminPosition(adminPositionDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", result);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "UPDATE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> confirmAdminPositionItemDelete(AdminPositionDto adminPositionDto) {
        log.info("confirmAdminPositionItemDelete()");
        try {
            int result = adminMapper.deleteAdminPosition(adminPositionDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DELETE SUCCESS", result);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DELETE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> getLoginedAdminData(AdminDto adminDto) {
        log.info("getLoginedAdminData()");
        try {
            AdminDto selectedAdminData = adminMapper.selectAdminByAid(adminDto);
            if (selectedAdminData != null) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "GET DATA SUCCESS", selectedAdminData);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "GET DATA FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> confirmAdminInfoUpdate(AdminDto adminDto) {
        log.info("confirmAdminInfoUpdate()");

        try {
            int result = adminMapper.updateAdminByAid(adminDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", result);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "UPDATE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Integer> getUserCount() {
        log.info("getUserCount()");

        try {
            int count = adminMapper.selectUserCount();
            if (count < 0) {
                log.info("USER LIST COUNT IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("USER LIST COUNT GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", count);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<MemberDto>> searchUsers(String searchKeyword) {
        log.info("searchUsers()");
        try {
            List<MemberDto> memberDtos = adminMapper.selectMemberByMname(searchKeyword);
            if (memberDtos != null) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "GET DATA SUCCESS", memberDtos);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "GET DATA FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }

    }

    public ResponseWrapper<List<AdminDto>> searchAdmins(String searchKeyword) {
        log.info("searchAdmins()");
        try {
            List<AdminDto> adminDtos = adminMapper.selectAdminByAname(searchKeyword);
            if (adminDtos != null) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "GET DATA SUCCESS", adminDtos);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "GET DATA FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }

    }

    public ResponseWrapper<Integer> confirmUserDelete(int m_no) {
        log.info("confirmUserDelete()");
        try {
            int result = adminMapper.deleteUserByMno(m_no);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DELETE SUCCESS", result);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DELETE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Integer> confirmUserUpdate(MemberDto memberDto) {
        try {
            int result = adminMapper.updateUserByMno(memberDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", result);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "UPDATE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<UserJoinGroupDto>> getUserDetailGroupInfo(int m_no) {
        log.info("getUserDetailGroupInfo()");
        try {
            List<UserJoinGroupDto> userJoinGroupDtos = adminMapper.selectUserGroupByMno(m_no);
            if (userJoinGroupDtos != null) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", userJoinGroupDtos);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATE GET FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }

    }

    public ResponseWrapper<List<UserPostDto>> getUserDetailPostInfo(int m_no) {
        log.info("getUserDetailPostInfo()");
        try {
            List<UserPostDto> userPostDtos = adminMapper.selectUserGroupPostByMno(m_no);
            if (userPostDtos != null) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", userPostDtos);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA GET FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Integer> confirmDeleteGroupMember(Integer m_no, Integer g_no) {
        log.info("confirmDeleteGroupMember()");
        try {
            int result = adminMapper.deleteGroupMemberByGnoAndMno(m_no, g_no);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DELETE SUCCESS", result);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DELETE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Integer> confirmDeleteGroupPost(Integer m_no, Integer p_no) {
        log.info("confirmDeleteGroupPost()");
        try {
            int result = adminMapper.deleteGroupPostByPnoAndMno(m_no, p_no);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DELETE SUCCESS", result);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DELETE FAIL", null);

            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<AdminDto>> getAllAdminList() {
        log.info("getAllAdminList");
        try {
            List<AdminDto> adminDtos = adminMapper.selectAllAdmin();
            if (adminDtos.isEmpty()) {
                log.info("ADMIN ALL LIST IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("ADMIN ALL LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", adminDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<MemberDto>> getAllUserList() {
        log.info("getAllUserList");
        try {
            List<MemberDto> memberDtos = adminMapper.selectAllUser();
            if (memberDtos.isEmpty()) {
                log.info("USER ALL LIST IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("USER ALL LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", memberDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<UserGenderStatisticsDto>> getGenderData() {
        log.info("getGenderData");
        try {
            List<UserGenderStatisticsDto> userGenderStatisticsDtos = adminMapper.selectGenderCount();
            if (userGenderStatisticsDtos.isEmpty()) {
                log.info("USER GENDER DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("USER GENDER DATA GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", userGenderStatisticsDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }

    }

    public ResponseWrapper<List<UserAgeStatisticsDto>> getAgeData() {
        log.info("getAgeData");
        try {
            List<UserAgeStatisticsDto> userGenderStatisticsDtos = adminMapper.selectAgeCount();
            if (userGenderStatisticsDtos.isEmpty()) {
                log.info("USER GENDER DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("USER GENDER DATA GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", userGenderStatisticsDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }

    }

    public ResponseWrapper<List<AgePremiumDataDto>> getAgePremiumData() {
        log.info("getAgePremiumData");
        try {
            List<AgePremiumDataDto> AgePremiumDataDtos = adminMapper.selectAgePremiumData();
            if (AgePremiumDataDtos.isEmpty()) {
                log.info("USER AGE PREMIUM DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("USER AGE PREMIUM DATA GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", AgePremiumDataDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<GenderPremiumDataDto>> getGenderPremiumData() {
        log.info("getGenderPremiumData");
        try {
            List<GenderPremiumDataDto> GenderPremiumDataDtos = adminMapper.selectGenderPremiumData();
            log.info("GenderPremiumDataDtos: {}", GenderPremiumDataDtos);
            if (GenderPremiumDataDtos.isEmpty()) {
                log.info("USER GENDER PREMIUM DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("USER GENDER PREMIUM GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", GenderPremiumDataDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<UserPremiumDataDto>> getUserPremiumData() {
        log.info("getUserPremiumData");
        try {
            List<UserPremiumDataDto> UserPremiumDataDtos = adminMapper.selectUserPremiumData();
            if (UserPremiumDataDtos.isEmpty()) {
                log.info("USER PREMIUM DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("USER PREMIUM GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", UserPremiumDataDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<RecentStatisticsDto>> getUserRecentData() {
        log.info("getUserRecentData");
        try {
            List<RecentStatisticsDto> RecentUserStatisticsDtos = adminMapper.selectUserRecentData();
            if (RecentUserStatisticsDtos.isEmpty()) {
                log.info("USER RECENT DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("USER RECENT GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", RecentUserStatisticsDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<GroupCategoryStatisticsDto>> getGroupCategoryCountData() {
        log.info("getGroupCategoryCountData");
        try {
            List<GroupCategoryStatisticsDto> GroupCategoryStatisticsDtos = adminMapper.selectGroupCategoryCount();
            if (GroupCategoryStatisticsDtos.isEmpty()) {
                log.info("GROUP CATEGORY COUNT DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("GROUP CATEGORY COUNT GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", GroupCategoryStatisticsDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<RecentStatisticsDto>> getGroupRecentData() {
        log.info("getGroupRecentData");
        try {
            List<RecentStatisticsDto> RecentStatisticsDtos = adminMapper.selectGroupRecentDate();
            if (RecentStatisticsDtos.isEmpty()) {
                log.info("GROUP RECENT DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("GROUP RECENT GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", RecentStatisticsDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<TotalAmountDto>> getTotalAmountData() {
        log.info("getTotalAmountData()");
        try {
            List<TotalAmountDto> totalAmountDtos = adminMapper.selectTotalAmountData();
            if (totalAmountDtos.isEmpty()) {
                log.info("TOTAL AMOUNT DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("TOTAL AMOUNT DATA GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", totalAmountDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<YearAndQuarterAmountDto>> getYearAndQuarterAmountData() {
        log.info("getYearAndQuarterAmountData()");
        try {
            List<YearAndQuarterAmountDto> yearAndQuarterAmountDtos = adminMapper.selectYearlyQuarterlySales();
            if (yearAndQuarterAmountDtos.isEmpty()) {
                log.info("YEAR AND QUARTER AMOUNT DATA IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("YEAR AND QUARTER AMOUNT DATA GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", yearAndQuarterAmountDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }
}
