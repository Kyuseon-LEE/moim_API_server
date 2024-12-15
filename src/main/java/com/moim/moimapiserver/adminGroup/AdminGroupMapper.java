package com.moim.moimapiserver.adminGroup;

import com.moim.moimapiserver.group.GroupDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminGroupMapper {
    int selectGroupCount();

    List<GroupDto> selectAllGroup(int offset, int size);

    int deleteGroupByGNo(int g_no);
}
