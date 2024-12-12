package com.moim.moimapiserver.customer;

import com.moim.moimapiserver.config.AdminStatusConfig;
import com.moim.moimapiserver.dto.AnnouncementDto;
import com.moim.moimapiserver.dto.FaqCategoryDto;
import com.moim.moimapiserver.dto.FaqDto;
import com.moim.moimapiserver.dto.ResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Log4j2
@Service
public class CustomerService {
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    private <T> ResponseWrapper<T> responseWrapper(String status, String message, T data) {
        return ResponseWrapper.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    public ResponseWrapper<List<FaqCategoryDto>> getFaqCategoryList() {
        log.info("getFaqCategoryList()");
        try {
            List<FaqCategoryDto> faqCategoryDtos = customerMapper.selectFaqCategory();
            if (!faqCategoryDtos.isEmpty()) {
                log.info("FAQ CATEGORY LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", faqCategoryDtos);
            } else {
                log.info("FAQ CATEGORY LIST GET FAIL");

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

    public ResponseWrapper<Integer> confirmFaqCreate(FaqDto faqDto) {
        log.info("getFaqCategoryList()");
        try {
            int result = customerMapper.insertNewCategory(faqDto);
            if (result > 0) {
                log.info("FAQ INSERT SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "INSERT SUCCESS", result);
            } else {
                log.info("FAQ INSERT FAIL");

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



    public ResponseWrapper<List<FaqDto>> getFaqList(int offset, int size, int sort) {
        log.info("getFaqList()");
        try {
            List<FaqDto> faqDtos = customerMapper.selectFaq(offset, size, sort);
            if (!faqDtos.isEmpty()) {
                log.info("FAQ LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", faqDtos);
            } else {
                log.info("FAQ LIST GET FAIL");

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

    public ResponseWrapper<Integer> getFaqListCount(int offset, int size, int sort) {
        log.info("getFaqListCount()");
        try {
            int result = customerMapper.selectFaqCount(offset, size, sort);
            if (result > 0) {
                log.info("FAQ LIST COUNT GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", result);
            } else {
                log.info("FAQ LIST COUNT GET FAIL");

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

    public ResponseWrapper<Integer> confirmFaqDelete(int faq_no) {
        log.info("confirmFaqDelete()");
        try {
            int result = customerMapper.deleteFaqByFaqNo(faq_no);
            if (result > 0) {
                log.info("FAQ DELETE SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "DATA GET SUCCESS", result);
            } else {
                log.info("FAQ DELETE FAIL");

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

    public ResponseWrapper<Integer> confirmFaqUpdate(FaqDto faqDto) {
        log.info("confirmFaqUpdate()");
        try {
            int result = customerMapper.updateFaqByFaqNo(faqDto);
            if (result > 0) {
                log.info("FAQ UPDATE SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", result);
            } else {
                log.info("FAQ UPDATE FAIL");

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

    public ResponseWrapper<Integer> confirmFaqCategoryCreate(FaqCategoryDto faqCategoryDto) {
        log.info("confirmFaqCategoryCreate()");
        try {
            int result = customerMapper.insertNewFaqCategory(faqCategoryDto);
            if (result > 0) {
                log.info("FAQ CATEGORY INSERT SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", result);
            } else {
                log.info("FAQ CATEGORY INSERT FAIL");

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

    public ResponseWrapper<Integer> confirmFaqCategoryDelete(int faq_category_no) {
        log.info("confirmFaqCategoryDelete()");
        try {
            int result = customerMapper.deleteFaqCategoryByCategoryNo(faq_category_no);
            if (result > 0) {
                log.info("FAQ CATEGORY DELETE SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "UPDATE SUCCESS", result);
            } else {
                log.info("FAQ CATEGORY DELETE FAIL");

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

    public ResponseWrapper<List<FaqDto>> getSearchFaq(String searchKeyword) {
        log.info("getSearchFaq()");
        log.info(searchKeyword);
        try {
            List<FaqDto> faqDtos = customerMapper.selectFaqByKeyword(searchKeyword);
            if (!faqDtos.isEmpty()) {
                log.info("FAQ SEARCH LIST GET SUCCESS");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "SEARCH SUCCESS [ MATCH SUCCESS ]", faqDtos);
            } else {
                log.info("FAQ SEARCH LIST GET FAIL");

                return responseWrapper(AdminStatusConfig.SUCCESS_STATUS, "SEARCH FAIL [ MISS MATCH ]", null);
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
