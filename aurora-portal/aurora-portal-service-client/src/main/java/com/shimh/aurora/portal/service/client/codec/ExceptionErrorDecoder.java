package com.shimh.aurora.portal.service.client.codec;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
public class ExceptionErrorDecoder implements ErrorDecoder {

    private ErrorDecoder errorDecoder = new feign.codec.ErrorDecoder.Default();
    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            // {"timestamp":"2019-09-11T10:25:08.088+0000","status":404,"error":"Not Found","message":"404 没有找到","path":"/user/404"}
//            String body = Util.toString(response.body().asReader());
//            R r = JSON.parseObject(body, R.class);
//            if(response.status() >= 400 && response.status() <= 499){
//                return new HystrixBadRequestException(r.getMsg());
//            }
//
//            return new AuroraException(r.getMsg(), r.getCode());

            if(response.status() >= 400 && response.status() <= 499){
                return new HystrixBadRequestException(errorMessage(methodKey, response));
            }

        } catch (IOException ex) {
        }

        return errorDecoder.decode(methodKey, response);
    }

    public static String errorMessage(String methodKey, Response response) throws IOException{
        String message = String.format("status %s reading %s", response.status(), methodKey);
        if (response.body() != null) {
            String body = Util.toString(response.body().asReader());
            message = message + "; content:\n" + body;
        }
        return message;
    }
}
