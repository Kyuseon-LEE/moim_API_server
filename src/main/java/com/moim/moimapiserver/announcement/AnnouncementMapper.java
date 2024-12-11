package com.moim.moimapiserver.announcement;

import com.moim.moimapiserver.dto.AnnouncementDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AnnouncementMapper {
    int selectAnnouncementCount();

    List<AnnouncementDto> selectAllAnnouncement(int offset, int size);

    int deleteAnnouncementByAnNo(int an_no);

    int insertNewAnnouncement(AnnouncementDto announcement);

    int updateAnnouncementByAnNo(AnnouncementDto announcement);

    List<AnnouncementDto> selectAnnouncementByOffsetAndSort(Map<String, Object> params);

    int selectAnnouncementCountBySort(String sort);

    AnnouncementDto selectAnnouncementByAnNo(AnnouncementDto announcementDto);
}
