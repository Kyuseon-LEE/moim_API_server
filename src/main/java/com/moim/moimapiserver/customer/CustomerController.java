package com.moim.moimapiserver.customer;

import com.moim.moimapiserver.dto.AnnouncementDto;
import com.moim.moimapiserver.dto.FaqCategoryDto;
import com.moim.moimapiserver.dto.FaqDto;
import com.moim.moimapiserver.dto.ResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequestMapping("/customer")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/createFaq")
    public ResponseEntity<ResponseWrapper<Integer>> createFaq(@RequestBody FaqDto faqDto) {
        log.info("createFaq()");

        ResponseWrapper<Integer> result = customerService.confirmFaqCreate(faqDto);
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/modifyFaq")
    public ResponseEntity<ResponseWrapper<Integer>> modifyFaq(@RequestBody FaqDto faqDto) {
        log.info("modifyFaq()");

        ResponseWrapper<Integer> result = customerService.confirmFaqUpdate(faqDto);
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };

    }

    @PostMapping("/deleteFaq")
    public ResponseEntity<ResponseWrapper<Integer>> deleteFaq(@RequestBody FaqDto faqDto) {
        log.info("deleteFaq()");

        ResponseWrapper<Integer> result = customerService.confirmFaqDelete(faqDto.getFaq_no());
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @GetMapping("/fetchFaqCategoryList")
    public ResponseEntity<ResponseWrapper<List<FaqCategoryDto>>> fetchFaqCategoryList() {
        log.info("fetchFaqCategoryList()");

        ResponseWrapper<List<FaqCategoryDto>> result = customerService.getFaqCategoryList();
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @GetMapping("/fetchFaqList")
    public ResponseEntity<ResponseWrapper<List<FaqDto>>> fetchFaqList(@RequestParam("offset") int offset, @RequestParam("size") int size, @RequestParam("sort")int sort) {
        log.info("fetchFaqList()");
        ResponseWrapper<List<FaqDto>> result = customerService.getFaqList(offset, size, sort);
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @GetMapping("/fetchFaqCount")
    public ResponseEntity<ResponseWrapper<Integer>> fetchFaqCount(@RequestParam("offset") int offset, @RequestParam("size") int size, @RequestParam("sort")int sort) {
        log.info("fetchFaqCount()");
        ResponseWrapper<Integer> result = customerService.getFaqListCount(offset, size, sort);
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }
}
