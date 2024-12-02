package com.moim.moimapiserver.Auth;

import com.moim.moimapiserver.config.AdminStatusConfig;
import com.moim.moimapiserver.dto.RefreshTokenDto;
import com.moim.moimapiserver.dto.ResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Log4j2
@Service
public class AuthService {

    private final AuthMapper authMapper;

    public AuthService(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }
    // 응답 메서드
    private <T> ResponseWrapper<T> responseWrapper(String status, String message, T data) {
        return ResponseWrapper.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    public ResponseWrapper<Object> confirmSaveRefreshToken(RefreshTokenDto refreshTokenDto) {
        log.info("confirmSaveRefreshToken()");

        try {
            int result = authMapper.insertNewRefreshToken(refreshTokenDto);
            if (result > 0) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "INSERT SUCCESS", null);

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

    public ResponseWrapper<Object> confirmRefreshTokenCheck(RefreshTokenDto refreshTokenDto) {
        log.info("confirmRefreshTokenCheck()");
        try {
            RefreshTokenDto selectedRefreshToken = authMapper.selectRefreshToken(refreshTokenDto);
            if (!Objects.equals(selectedRefreshToken.getRefresh_token(), refreshTokenDto.getRefresh_token())) {
                log.info("리프래시 토큰 불일치");
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "REFRESH TOKEN MATCH FAIL", null);
            } else {
                log.info("리프래시 토큰 일치");
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "REFRESH TOKEN MATCH SUCCESS", null);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Object> confirmSaveNewRefreshToken(RefreshTokenDto refreshTokenDto) {
        log.info("confirmSaveNewRefreshToken()");
        try {
            int result = authMapper.updateRefreshTokenByAid(refreshTokenDto);
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

    public ResponseWrapper<Object> confirmRefreshTokenDelete(RefreshTokenDto refreshTokenDto) {
        log.info("confirmRefreshTokenDelete()");
        try {
            int result = authMapper.deleteRefreshTokenByAid(refreshTokenDto);
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


    public ResponseWrapper<Object> getRefreshTokenByAid(String a_id) {
        log.info("getRefreshTokenByAid()");
        try {
            RefreshTokenDto refreshTokenDto = authMapper.selectRefreshTokenByAid(a_id);
            if (refreshTokenDto != null) {
                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "TOKEN GET SUCCESS", refreshTokenDto);

            } else {
                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "TOKEN GET FAIL", null);

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
