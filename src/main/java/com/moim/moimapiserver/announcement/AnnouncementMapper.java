package com.moim.moimapiserver.announcement;

import com.moim.moimapiserver.dto.AnnouncementDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnnouncementMapper {
    int selectAnnouncementCount();

    List<AnnouncementDto> selectAllAnnouncement(int offset, int size);

    int deleteAnnouncementByAnNo(int an_no);
}
