package com.moim.moimapiserver.advertisement;

import com.moim.moimapiserver.dto.AdStatusDto;
import com.moim.moimapiserver.dto.AdvertisementDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdvertisementMapper {
    int insertNewAd(AdvertisementDto advertisement);

    List<AdvertisementDto> selectAds(int offset, int size);

    int selectAdCount();

    List<AdStatusDto> selectAdStatus();

    AdvertisementDto selectAdByAdStatus();

    int updateAdStatusByAdNo(AdvertisementDto advertisementDto);

    int updateAdByAdNo(AdvertisementDto advertisementDto);

    int deleteAdByAdNo(AdvertisementDto advertisementDto);
}

