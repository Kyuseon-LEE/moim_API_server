package com.moim.moimapiserver.advertisement;

import com.moim.moimapiserver.config.AdminStatusConfig;
import com.moim.moimapiserver.dto.AdStatusDto;
import com.moim.moimapiserver.dto.AdvertisementDto;
import com.moim.moimapiserver.dto.ResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class AdvertisementService {

    private final AdvertisementMapper advertisementMapper;

    public AdvertisementService(final AdvertisementMapper advertisementMapper) {
        this.advertisementMapper = advertisementMapper;
    }

    private <T> ResponseWrapper<T> responseWrapper(String status, String message, T data) {
        return ResponseWrapper.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }


    public ResponseWrapper<Object> confirmAdCreate(AdvertisementDto advertisementDto) {
        log.info("confirmAdCreate()");
        try {
            int result = advertisementMapper.insertNewAd(advertisementDto);
            if (result > 0) {
                log.info("AD INSERT SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "INSERT SUCCESS", null);
            } else {
                log.info("AD INSERT FAIL");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "INSERT FAIL", null);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<List<AdvertisementDto>> getAdList(int page, int size) {
        log.info("getAdList()");
        int offset = page * size;
        try {
            List<AdvertisementDto> advertisementDtos = advertisementMapper.selectAds(offset, size);
            if (advertisementDtos.isEmpty()) {
                log.info("AD LIST IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("AD LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", advertisementDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Integer> getAdCount() {
        log.info("getAdCount()");

        try {
            int count = advertisementMapper.selectAdCount();
            if (count < 0) {
                log.info("AD LIST COUNT IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("AD LIST COUNT GET SUCCESS");

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

    public ResponseWrapper<List<AdStatusDto>> getAdStatus() {
        log.info("getAdStatus()");
        try {
            List<AdStatusDto> adStatusDtos = advertisementMapper.selectAdStatus();
            if (adStatusDtos.isEmpty()) {
                log.info("AD STATUS LIST IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("AD STATUS LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", adStatusDtos);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<AdvertisementDto> getCurrentAd() {
        log.info("getAdStatus()");
        try {
            AdvertisementDto advertisementDto = advertisementMapper.selectAdByAdStatus();
            if (advertisementDto == null) {
                log.info("AD IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("AD GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", advertisementDto);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }


    public ResponseWrapper<Integer> updateStatusByAdNo(AdvertisementDto advertisementDto) {
        log.info("updateStatusByAdNo()");
        try {
            int result = advertisementMapper.updateAdStatusByAdNo(advertisementDto);
            if (result > 0) {
                log.info("AD UPDATE SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", null);
            } else {
                log.info("AD UPDATE FAIL");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE FAIL", null);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Integer> confirmAdUpdate(AdvertisementDto advertisementDto) {
        log.info("confirmAdUpdate()");
        try {
            int result = advertisementMapper.updateAdByAdNo(advertisementDto);
            if (result > 0) {
                log.info("AD INFO UPDATE SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", null);
            } else {
                log.info("AD INFO UPDATE FAIL");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE FAIL", null);
            }

        } catch (PersistenceException e) {
            log.error("MyBatis 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.MYBATIS_ERROR_STATUS, AdminStatusConfig.MYBATIS_ERROR_MSG, null);

        } catch (Exception e) {
            log.error("기타 오류 발생: {}", e.getMessage(), e);
            return responseWrapper(AdminStatusConfig.DB_ERROR_STATUS, AdminStatusConfig.DB_ERROR_MSG, null);

        }
    }

    public ResponseWrapper<Integer> confirmAdDelete(AdvertisementDto advertisementDto) {
        log.info("confirmAdDelete()");
        try {
            int result = advertisementMapper.deleteAdByAdNo(advertisementDto);
            if (result > 0) {
                log.info("AD INFO DELETE SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DELETE SUCCESS", null);
            } else {
                log.info("AD INFO DELETE FAIL");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DELETE FAIL", null);
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
