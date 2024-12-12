package com.moim.moimapiserver.customer;

import com.moim.moimapiserver.dto.FaqCategoryDto;
import com.moim.moimapiserver.dto.FaqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
}
