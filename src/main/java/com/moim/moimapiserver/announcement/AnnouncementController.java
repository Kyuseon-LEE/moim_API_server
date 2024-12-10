package com.moim.moimapiserver.announcement;

import com.moim.moimapiserver.dto.AnnouncementDto;
import com.moim.moimapiserver.dto.ResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequestMapping("/announce")
@RestController
public class AnnouncementController {
   private final AnnouncementService announcementService;

   public AnnouncementController(AnnouncementService announcementService) {
       this.announcementService = announcementService;
   }

    @GetMapping("/fetchAnnouncementCount")
    public ResponseEntity<ResponseWrapper<Integer>> fetchAnnouncementCount() {
        log.info("fetchAnnouncementCount()");

        ResponseWrapper<Integer> result = announcementService.getAnnouncementCount();

        log.info("result : {}", result);
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @GetMapping("/fetchAnnouncementList")
    public ResponseEntity<ResponseWrapper<List<AnnouncementDto>>> fetchAnnouncementList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
       log.info("fetchAnnouncementList()");

        ResponseWrapper<List<AnnouncementDto>> result = announcementService.getAnnouncementList(page, size);

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/deleteAnnouncement")
    public ResponseEntity<ResponseWrapper<Integer>> deleteAnnouncement(@RequestBody AnnouncementDto announcement) {
       log.info("deleteAnnouncement()");

       ResponseWrapper<Integer> result = announcementService.confirmAnnouncementDelete(announcement.getAn_no());

        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/createAnnouncement")
    public ResponseEntity<ResponseWrapper<Integer>> createAnnouncement(@RequestBody AnnouncementDto announcement) {
       log.info("createAnnouncement()");

        ResponseWrapper<Integer> result = announcementService.confirmAnnouncementCreate(announcement);
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/updateAnnouncement")
    public ResponseEntity<ResponseWrapper<Integer>> updateAnnouncement(@RequestBody AnnouncementDto announcement) {
       log.info("updateAnnouncement()");

        ResponseWrapper<Integer> result = announcementService.confirmAnnouncementUpdate(announcement);
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };

    }
}
