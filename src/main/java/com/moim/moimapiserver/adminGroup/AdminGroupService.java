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
            List<GroupDto> groupDtos = adminGroupMapper.selectAllGroup(offset, size);
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
}
