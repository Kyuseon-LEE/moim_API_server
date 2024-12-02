package com.moim.moimapiserver.Auth;

import com.moim.moimapiserver.dto.RefreshTokenDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    int insertNewRefreshToken(RefreshTokenDto refreshTokenDto);

    RefreshTokenDto selectRefreshToken(RefreshTokenDto refreshTokenDto);

    int updateRefreshTokenByAid(RefreshTokenDto refreshTokenDto);

    int deleteRefreshTokenByAid(RefreshTokenDto refreshTokenDto);


    RefreshTokenDto selectRefreshTokenByAid(String a_id);
}
