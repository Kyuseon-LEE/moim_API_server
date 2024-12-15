package com.moim.moimapiserver.customer;

import com.moim.moimapiserver.dto.CustomerInquiriesDto;
import com.moim.moimapiserver.dto.FaqCategoryDto;
import com.moim.moimapiserver.dto.FaqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustomerMapper {
    List<FaqCategoryDto> selectFaqCategory();

    int insertNewCategory(FaqDto faqDto);

    List<FaqDto> selectFaq(int offset, int size, int sort);

    int selectFaqCount(int offset, int size, int sort);

    int deleteFaqByFaqNo(int faq_no);

    int updateFaqByFaqNo(FaqDto faqDto);

    int insertNewFaqCategory(FaqCategoryDto faqCategoryDto);

    int deleteFaqCategoryByCategoryNo(int faq_category_no);

    List<FaqDto> selectFaqByKeyword(String searchKeyword);

    FaqDto selectFaqByFaqNo(int faq_no);

    int insertNewInquiries(CustomerInquiriesDto customerInquiriesDto);

    List<CustomerInquiriesDto> selectInquiriesListByMid(Map<String, Object> data);

    CustomerInquiriesDto selectInquiriesByCsiNo(int csi_no);

    int updateCsiStatusByCsiNo(int csi_no);
}
