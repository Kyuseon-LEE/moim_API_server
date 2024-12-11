package com.moim.moimapiserver.announcement;

import com.moim.moimapiserver.config.AdminStatusConfig;
import com.moim.moimapiserver.dto.AnnouncementDto;
import com.moim.moimapiserver.dto.ResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class AnnouncementService {

    private final AnnouncementMapper announcementMapper;

    public AnnouncementService(AnnouncementMapper announcementMapper) {
        this.announcementMapper = announcementMapper;
    }

    private <T> ResponseWrapper<T> responseWrapper(String status, String message, T data) {
        return ResponseWrapper.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }


    public ResponseWrapper<Integer> getAnnouncementCount() {
        log.info("getAnnouncementCount()");
            try {
                int result = announcementMapper.selectAnnouncementCount();
                log.info("result :" + result);
                if (result >= 0) {
                    log.info("ANNOUNCEMENT COUNT GET SUCCESS");

                    return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", result);
                } else {
                    log.info("ANNOUNCEMENT COUNT GET FAIL");

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

    public ResponseWrapper<List<AnnouncementDto>> getAnnouncementList(int page, int size) {
        log.info("getAnnouncementList()");
        int offset = page * size;
        try {
            List<AnnouncementDto> announcementDtos = announcementMapper.selectAllAnnouncement(offset, size);
            if (!announcementDtos.isEmpty()) {
                log.info("ANNOUNCEMENT LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", announcementDtos);
            } else {
                log.info("ANNOUNCEMENT LIST GET FAIL");

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

    public ResponseWrapper<Integer> confirmAnnouncementDelete(int an_no) {
        log.info("confirmAnnouncementDelete()");

        try {
            int result = announcementMapper.deleteAnnouncementByAnNo(an_no);
            if (result > 0) {
                log.info("ANNOUNCEMENT DELETE SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DELETE SUCCESS", null);
            } else {
                log.info("ANNOUNCEMENT DELETE FAIL");

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

    public ResponseWrapper<Integer> confirmAnnouncementCreate(AnnouncementDto announcement) {
        log.info("confirmAnnouncementCreate()");

        try {
            int result = announcementMapper.insertNewAnnouncement(announcement);
            if (result > 0) {
                log.info("ANNOUNCEMENT INSERT SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "INSERT SUCCESS", null);
            } else {
                log.info("ANNOUNCEMENT INSERT FAIL");

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

    public ResponseWrapper<Integer> confirmAnnouncementUpdate(AnnouncementDto announcement) {
        log.info("confirmAnnouncementUpdate()");

        try {
            int result = announcementMapper.updateAnnouncementByAnNo(announcement);
            if (result > 0) {
                log.info("ANNOUNCEMENT UPDATE SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", null);
            } else {
                log.info("ANNOUNCEMENT UPDATE FAIL");

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

    public ResponseWrapper<List<AnnouncementDto>> getAnnouncementListForUser(int offset, int size, String sort) {
        log.info("getAnnouncementListForUser()");
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("size", size);
        params.put("sort", sort);
        try {
            List<AnnouncementDto> announcementDtos = announcementMapper.selectAnnouncementByOffsetAndSort(params);
            if (!announcementDtos.isEmpty()) {
                log.info("ANNOUNCEMENT FOR USER LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", announcementDtos);
            } else {
                log.info("ANNOUNCEMENT FOR USER LIST GET FAIL");

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

    public ResponseWrapper<Integer> getAnnouncementCountBySort(String sort) {
        log.info("getAnnouncementCountBySort()");
        try {
            int result = announcementMapper.selectAnnouncementCountBySort(sort);
            if (result >= 0) {
                log.info("ANNOUNCEMENT COUNT BY SORT GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", result);
            } else {
                log.info("ANNOUNCEMENT COUNT BY SORT GET FAIL");

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

    public ResponseWrapper<AnnouncementDto> getAnnouncementDetail(AnnouncementDto announcementDto) {
        log.info("getAnnouncementDetail()");
        try {
            AnnouncementDto selectedAnnouncementDto = announcementMapper.selectAnnouncementByAnNo(announcementDto);
            if (announcementDto != null) {
                log.info("ANNOUNCEMENT GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", selectedAnnouncementDto);
            } else {
                log.info("ANNOUNCEMENT GET FAIL");

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
