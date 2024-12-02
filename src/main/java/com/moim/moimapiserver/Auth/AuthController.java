package com.moim.moimapiserver.Auth;

import com.moim.moimapiserver.dto.RefreshTokenDto;
import com.moim.moimapiserver.dto.ResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/saveRefreshToken")
    public ResponseEntity<ResponseWrapper<Object>> saveRefreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        log.info("saveRefreshToken()");
        log.info("refreshTokenRequest: {}", refreshTokenDto);

        ResponseWrapper<Object> response = authService.confirmSaveRefreshToken(refreshTokenDto);

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    @PostMapping("/refreshTokenCheck")
    public ResponseEntity<ResponseWrapper<Object>> refreshTokenCheck(@RequestBody RefreshTokenDto refreshTokenDto) {
        log.info("refreshTokenCheck()");
        log.info("refreshTokenRequest: {}", refreshTokenDto);

        ResponseWrapper<Object> response = authService.confirmRefreshTokenCheck(refreshTokenDto);

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    @PostMapping("/saveNewRefreshToken")
    public ResponseEntity<ResponseWrapper<Object>> saveNewRefreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        log.info("saveNewRefreshToken()");
        log.info("refreshTokenRequest: {}", refreshTokenDto);

        ResponseWrapper<Object> response = authService.confirmSaveNewRefreshToken(refreshTokenDto);

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    @PostMapping("/deleteRefreshToken")
    public ResponseEntity<ResponseWrapper<Object>> deleteRefreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        log.info("deleteRefreshToken()");
        log.info("refreshTokenRequest: {}", refreshTokenDto);

        ResponseWrapper<Object> response = authService.confirmRefreshTokenDelete(refreshTokenDto);

        return switch (response.getStatus()) {
            case "SUCCESS", "FAIL" -> ResponseEntity.ok(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }



}
