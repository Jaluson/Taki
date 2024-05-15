package com.taki.config;

import com.taki.vo.currency.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Arrays;

@Slf4j
@RequestMapping
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    R<String> exception(Exception e) {
        log.error(e.getMessage());
        return R.fail("发现未知错误: " + e.getMessage());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    R<String> handlerMethodValidationException(HandlerMethodValidationException e) {
        Object[] detailMessageArguments = e.getDetailMessageArguments();
        return R.fail("发现校验错误: " + Arrays.toString(detailMessageArguments));
    }
}
