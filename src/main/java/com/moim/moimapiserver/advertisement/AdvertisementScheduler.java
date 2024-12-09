package com.moim.moimapiserver.advertisement;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class AdvertisementScheduler {

    private final AdvertisementService advertisementService;

    public AdvertisementScheduler(final AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void updateAdvertisementStatuses() {
        log.info("Advertisement statuses update");
        advertisementService.updateAllAdvertisementStatuses();
    }
}