package com.mp.PLine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public BaseResponse<BaseResponseStatus> BaseException(BaseException e) {
        BaseResponseStatus baseResponse = null;
        try {
            baseResponse = BaseResponseStatus.getBaseStatusByCode(e.getStatus().getCode());
        } catch (Exception error) {
            log.error(error.getMessage());
        } finally {
            log.error(e.getMessage());
        }
        return new BaseResponse<>(baseResponse);
    }
}
