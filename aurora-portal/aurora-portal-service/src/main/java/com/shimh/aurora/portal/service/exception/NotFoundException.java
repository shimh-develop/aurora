package com.shimh.aurora.portal.service.exception;

import com.shimh.aurora.common.exception.AuroraException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends AuroraException {

    public NotFoundException(String message) {
        super(message);
    }
}
