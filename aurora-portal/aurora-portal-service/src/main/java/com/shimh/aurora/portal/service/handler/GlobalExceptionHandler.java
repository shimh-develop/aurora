package com.shimh.aurora.portal.service.handler;

import com.shimh.aurora.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Slf4j
//@RestControllerAdvice
public class GlobalExceptionHandler {

//    @Autowired(required = false)
//    private MessageSource messageSource;

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public R handleException(HttpServletRequest request, Exception ex,  HttpServletResponse response) {
//        R r = R.error();
//        String requestURL = request.getRequestURL().toString();
//        r.m().put("message", ex.getMessage()).put("path", requestURL);
//        log.error("服务错误 path {} execution ", requestURL,  ex);
//        try {
//            if (ex instanceof ResponseStatusException) {
//                this.resolveResponseStatusException((ResponseStatusException)ex, request, response);
//                return r;
//            }
//            ResponseStatus status = (ResponseStatus)AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
//            if (status != null) {
//                this.resolveResponseStatus(status, request, response, ex);
//                return r;
//            }
//            this.applyStatusAndReason(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), response);
//        }catch (Exception e) {
//            log.error("GlobalExceptionHandler 错误 ", e);
//        }
//        return r;
//    }
//
//    protected void resolveResponseStatus(ResponseStatus responseStatus, HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
//        int statusCode = responseStatus.code().value();
//        String reason = responseStatus.reason();
//        this.applyStatusAndReason(statusCode, reason, response);
//    }
//
//    protected void resolveResponseStatusException(ResponseStatusException ex, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        int statusCode = ex.getStatus().value();
//        String reason = ex.getReason();
//        this.applyStatusAndReason(statusCode, reason, response);
//    }
//
//    protected void applyStatusAndReason(int statusCode, @Nullable String reason, HttpServletResponse response) throws IOException {
//        if (!StringUtils.hasLength(reason)) {
//            response.sendError(statusCode);
//        } else {
//            String resolvedReason = this.messageSource != null ? this.messageSource.getMessage(reason, (Object[])null, reason, LocaleContextHolder.getLocale()) : reason;
//            response.sendError(statusCode, resolvedReason);
//        }
//
//    }
}
