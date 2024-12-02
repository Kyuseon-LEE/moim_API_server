package com.moim.moimapiserver.advertisement;

import com.moim.moimapiserver.dto.AdStatusDto;
import com.moim.moimapiserver.dto.AdvertisementDto;
import com.moim.moimapiserver.dto.ResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/ad")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping("/createAd")
    public ResponseEntity<ResponseWrapper<Object>> createAd(@RequestBody AdvertisementDto advertisementDto) {
        log.info("createAd()");
        log.info("advertisementDto: {}", advertisementDto);
        ResponseWrapper<Object> response = advertisementService.confirmAdCreate(advertisementDto);

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    @GetMapping("/fetchAdList")
    public ResponseEntity<ResponseWrapper<List<AdvertisementDto>>> fetchAdList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        log.info("fetchAdList()");

        ResponseWrapper<List<AdvertisementDto>> advertisementDtos = advertisementService.getAdList(page, size);
        return switch (advertisementDtos.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(advertisementDtos);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(advertisementDtos);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(advertisementDtos);

        };
    }

    @GetMapping("/fetchAdCount")
    public ResponseEntity<ResponseWrapper<Integer>> fetchAdCount() {
        log.info("fetchAdCount()");

        ResponseWrapper<Integer> adCount = advertisementService.getAdCount();

        return switch (adCount.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(adCount);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(adCount);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(adCount);

        };
    }

    @GetMapping("/fetchAdStatus")
    public ResponseEntity<ResponseWrapper<List<AdStatusDto>>> fetchAdStatus() {
        log.info("fetchAdStatus()");

        ResponseWrapper<List<AdStatusDto>> adStatusDtos = advertisementService.getAdStatus();
        return switch (adStatusDtos.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(adStatusDtos);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(adStatusDtos);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(adStatusDtos);

        };
    }

    @GetMapping("/fetchCurrentAd")
    public ResponseEntity<ResponseWrapper<AdvertisementDto>> fetchCurrentAd() {
        log.info("fetchCurrentAd()");

        ResponseWrapper<AdvertisementDto> advertisementDto = advertisementService.getCurrentAd();
        return switch (advertisementDto.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(advertisementDto);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(advertisementDto);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(advertisementDto);

        };
    }
}
