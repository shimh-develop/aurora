package com.shimh.aurora.portal.service.client;

import com.shimh.aurora.portal.service.api.UserApi;
import com.shimh.aurora.portal.service.client.codec.ExceptionErrorDecoder;
import com.shimh.aurora.portal.service.client.fallback.UserApiFallbackFactory;
import feign.Client;
import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.hystrix.FallbackFactory;
import feign.hystrix.HystrixFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Import(FeignClientsConfiguration.class)
public class PortalServiceConfiguration {

   @Bean
    public UserApi userApi(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        UserApi userApi = HystrixFeign.builder().client(client)
                .encoder(encoder)
                .decoder(decoder)
                .errorDecoder(errorDecoder())
                .contract(contract)
                .target(UserApi.class, "http://aurora-portal-service", fallbackFactory());
        return userApi;
    }

    @Bean
    public FallbackFactory fallbackFactory() {
        return new UserApiFallbackFactory();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ExceptionErrorDecoder();
    }



}
