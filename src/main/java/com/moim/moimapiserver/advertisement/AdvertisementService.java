package com.moim.moimapiserver.advertisement;

import com.moim.moimapiserver.config.AdminStatusConfig;
import com.moim.moimapiserver.dto.AdStatusDto;
import com.moim.moimapiserver.dto.AdvertisementDto;
import com.moim.moimapiserver.dto.ResponseWrapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.exceptions.PersistenceException;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Log4j2
@Service
public class AdvertisementService {

    private final AdvertisementMapper advertisementMapper;
    private final TaskScheduler taskScheduler;
    private final ConcurrentHashMap<Integer, List<ScheduledFuture<?>>> scheduledTasksMap = new ConcurrentHashMap<>();

    public AdvertisementService(final AdvertisementMapper advertisementMapper, final TaskScheduler taskScheduler) {
        this.advertisementMapper = advertisementMapper;
        this.taskScheduler = taskScheduler;
    }

    private <T> ResponseWrapper<T> responseWrapper(String status, String message, T data) {
        return ResponseWrapper.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }


    // TaskScheduler
    @PostConstruct
    public void init() {
        List<AdvertisementDto> advertisements = advertisementMapper.findActiveAdvertisements();
        for (AdvertisementDto ad : advertisements) {
            scheduleAdvertisementTasks(ad);
        }
    }

    // TaskScheduler
    private void scheduleAdvertisementTasks(AdvertisementDto ad) {
        List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();

        if (ad.getAd_start_date() != null) {
            Runnable startTask = () -> {
                advertisementMapper.updateAdvertisementStartStatus(ad.getAd_no());
                System.out.println("광고 시작 상태 업데이트: 광고 ID " + ad.getAd_no());
            };
            ScheduledFuture<?> startFuture = taskScheduler.schedule(startTask, ad.getAd_start_date());
            scheduledFutures.add(startFuture);
        }

        if (ad.getAd_end_date() != null) {
            Runnable endTask = () -> {
                advertisementMapper.updateAdvertisementEndStatus(ad.getAd_no());
                System.out.println("광고 종료 상태 업데이트: 광고 ID " + ad.getAd_no());
            };
            ScheduledFuture<?> endFuture = taskScheduler.schedule(endTask, ad.getAd_end_date());
            scheduledFutures.add(endFuture);
        }

        scheduledTasksMap.put(ad.getAd_no(), scheduledFutures);
    }

    private void cancelScheduledTasks(int ad_no) {
        List<ScheduledFuture<?>> scheduledFutures = scheduledTasksMap.get(ad_no);
        if (scheduledFutures != null) {
            for (ScheduledFuture<?> future : scheduledFutures) {
                future.cancel(false);
            }
            scheduledTasksMap.remove(ad_no);
        }
    }


    public ResponseWrapper<Object> confirmAdCreate(AdvertisementDto advertisementDto) {
        log.info("confirmAdCreate()");
        try {
            int result = advertisementMapper.insertNewAd(advertisementDto);


            if (result > 0) {
                log.info("AD INSERT SUCCESS");

                // 새로운 스케줄 등록
                scheduleAdvertisementTasks(advertisementDto);

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
        log.info("getCurrentAd()");
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
                if (advertisementDto.getAd_status() == 2) {
                    // 광고 대기로 변경시 시작 스케줄 등록
                    scheduleAdvertisementTasks(advertisementDto);

                } else if (advertisementDto.getAd_status() == 3) {
                    // 광고 시작으로 변경시 종료 스케줄 등록
                    scheduleAdvertisementTasks(advertisementDto);

                } else if (advertisementDto.getAd_status() == 4) {
                    // 광고 종료로 변경시 스케줄 캔슬
                    cancelScheduledTasks(advertisementDto.getAd_no());

                }
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

                // 광고 삭제시 스케줄 삭제
                cancelScheduledTasks(advertisementDto.getAd_no());

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

    public ResponseWrapper<List<AdvertisementDto>> getAdTimeList() {
        log.info("getAdTimeList()");
        try {
            List<AdvertisementDto> advertisementDtos = advertisementMapper.selectAdTime();
            if (advertisementDtos == null) {
                log.info("AD TIME DTO IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("AD TIME DTO GET SUCCESS");

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

    public ResponseWrapper<List<AdvertisementDto>> getAdTimeListByFilteredAdNo(int ad_no) {
        log.info("getAdTimeListByFilteredAdNo()");
        try {
            List<AdvertisementDto> advertisementDtos = advertisementMapper.selectAdTimeByFilteredAdNo(ad_no);
            if (advertisementDtos == null) {
                log.info("AD TIME DTO IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("AD TIME DTO GET SUCCESS");

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


    public ResponseWrapper<List<AdvertisementDto>> getAllAdList() {
        log.info("getAllAdList()");
        try {
            List<AdvertisementDto> advertisementDtos = advertisementMapper.selectAllAd();
            if (advertisementDtos == null) {
                log.info("AD DTO IS EMPTY");

                return responseWrapper(AdminStatusConfig.FAIL_STATUS, "DATA IS EMPTY", null);
            } else {
                log.info("AD DTO GET SUCCESS");

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
}
