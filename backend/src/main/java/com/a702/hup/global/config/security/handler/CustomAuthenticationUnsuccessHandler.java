package com.a702.hup.global.config.security.handler;

import com.a702.hup.global.error.ErrorCode;
import com.a702.hup.global.error.exception.BusinessException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 이경태
 * @date 2024-04-26
 * @description 인증 실패 시의 에러를 처리하는 클래스
 **/
@Slf4j
@Configuration
@Component
public class CustomAuthenticationUnsuccessHandler implements AuthenticationFailureHandler {

    /**
     * @author 이경태
     * @date 2024-04-26
     * @description 인증 시 발생하는 에러에 대해 처리하는 함수
     **/
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        // todo : throw가 아닌 response 변경으로 교체 필요
        log.debug("[+] CustomAuthenticationUnsuccessHandler :: onAuthenticationFailure :: start");
        if(exception instanceof AuthenticationServiceException) {
            throw new BusinessException(ErrorCode.API_ERROR_AUTHENTICATION_FAIL);
        } else {
            throw new BusinessException(ErrorCode.API_ERROR_INTERNAL_SERVER_ERROR);
        }
    }
}
