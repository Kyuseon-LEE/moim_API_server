package com.moim.moimapiserver.group;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupMapper {
    int insertNewGroup(GroupDto groupDto);
}
