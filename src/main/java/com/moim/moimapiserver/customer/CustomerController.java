package com.moim.moimapiserver.customer;

import com.moim.moimapiserver.dto.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/createFaqCategory")
    public ResponseEntity<ResponseWrapper<Integer>> createFaqCategory(@RequestBody FaqCategoryDto faqCategoryDto) {
        log.info("createFaqCategory()");

        ResponseWrapper<Integer> result = customerService.confirmFaqCategoryCreate(faqCategoryDto);
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/deleteFaqCategory")
    public ResponseEntity<ResponseWrapper<Integer>> deleteFaqCategory(@RequestBody FaqCategoryDto faqCategoryDto) {
        log.info("deleteFaqCategory()");

        ResponseWrapper<Integer> result = customerService.confirmFaqCategoryDelete(faqCategoryDto.getFaq_category_no());
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/searchFaq")
    public ResponseEntity<ResponseWrapper<List<FaqDto>>> searchFaq(@RequestBody Map<String, String> searchKeywordMap) {
        log.info("searchFaq()");

        String searchKeyword = searchKeywordMap.get("searchKeyword");

        ResponseWrapper<List<FaqDto>> result = customerService.getSearchFaq(searchKeyword);
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/fetchFaqDetail")
    public ResponseEntity<ResponseWrapper<FaqDto>> fetchFaqDetail(@RequestBody FaqDto faqDto) {
        log.info("fetchFaqDetail()");
        log.info("faqDto: {}", faqDto);
        ResponseWrapper<FaqDto> result = customerService.getFaqDetail(faqDto.getFaq_no());
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/createInquiries")
    public ResponseEntity<ResponseWrapper<Integer>> createInquiries(@RequestBody CustomerInquiriesDto customerInquiriesDto) {
        log.info("createInquiries()");
        log.info("customerInquiriesDto: {}", customerInquiriesDto);
        ResponseWrapper<Integer> result = customerService.confirmInquiriesCreate(customerInquiriesDto);
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }

    @PostMapping("/fetchUserInquiriesList")
    public ResponseEntity<ResponseWrapper<List<CustomerInquiriesDto>>> fetchUserInquiriesList(@RequestBody Map<String, Object> data) {
        log.info("fetchUserInquiriesList()");

        log.info("data: {}", data);

        ResponseWrapper<List<CustomerInquiriesDto>> result = customerService.getUserInquiriesList(data);
        return switch (result.getStatus()) {
            case "SUCCESS" -> ResponseEntity.ok(result);

            case "FAIL" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        };
    }
}
