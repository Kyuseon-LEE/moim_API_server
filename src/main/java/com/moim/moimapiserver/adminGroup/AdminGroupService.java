package com.moim.moimapiserver.adminGroup;

import com.moim.moimapiserver.config.AdminStatusConfig;
import com.moim.moimapiserver.dto.ResponseWrapper;
import com.moim.moimapiserver.group.GroupDto;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class AdminGroupService {
    private final AdminGroupMapper adminGroupMapper;

    public AdminGroupService(final AdminGroupMapper adminGroupMapper) {
        this.adminGroupMapper = adminGroupMapper;
    }

    private <T> ResponseWrapper<T> responseWrapper(String status, String message, T data) {
        return ResponseWrapper.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }


    public ResponseWrapper<Integer> getGroupListCount() {
        log.info("getGroupListCount()");
        try {
            int result = adminGroupMapper.selectGroupCount();
            if (result > 0) {
                log.info("GROUP LIST COUNT GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", result);
            } else {
                log.info("GROUP LIST COUNT GET FAIL");

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

    public ResponseWrapper<List<GroupDto>> getGroupList(int offset, int size) {
        log.info("getGroupListCount()");
        try {
            List<GroupDto> groupDtos = adminGroupMapper.selectAllGroupByOffset(offset, size);
            log.info("groupDtos: {}", groupDtos);
            if (!groupDtos.isEmpty()) {
                log.info("GROUP LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", groupDtos);
            } else {
                log.info("GROUP LIST GET FAIL");

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


    public ResponseWrapper<Integer> confirmGroupDelete(int g_no) {
        log.info("confirmGroupDelete()");
        try {
            int result = adminGroupMapper.deleteGroupByGNo(g_no);
            if (result > 0) {
                log.info("GROUP DELETE SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DELETE SUCCESS", result);
            } else {
                log.info("GROUP DELETE FAIL");

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

    public ResponseWrapper<List<GroupDto>> getSearchGroup(String searchKeyword) {
        log.info("getSearchGroup()");
        try {
            List<GroupDto> groupDtos = adminGroupMapper.selectGroupBySearchKeyword(searchKeyword);
            if (!groupDtos.isEmpty()) {
                log.info("SEARCH GROUP LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", groupDtos);
            } else {
                log.info("SEARCH GROUP LIST GET FAIL");

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

    public ResponseWrapper<Integer> confirmGroupUpdate(GroupDto groupDto) {
        log.info("confirmGroupUpdate()");
        try {
            int result = adminGroupMapper.updateGroupByGno(groupDto);
            if (result > 0) {
                log.info("GROUP UPDATE SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DELETE SUCCESS", result);
            } else {
                log.info("GROUP UPDATE FAIL");

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

    public ResponseWrapper<List<GroupDto>> getAllGroupList() {
        log.info("getAllGroupList()");
        try {
            List<GroupDto> groupDtos = adminGroupMapper.selectAllGroup();
            if (!groupDtos.isEmpty()) {
                log.info("ALL GROUP LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", groupDtos);
            } else {
                log.info("ALL GROUP LIST GET FAIL");

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
}
