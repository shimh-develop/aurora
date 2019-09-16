package com.shimh.aurora.portal.service.client.fallback;

import com.shimh.aurora.portal.service.api.UserApi;
import com.shimh.aurora.portal.service.api.vo.UserVo;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Slf4j
public class UserApiFallbackFactory implements FallbackFactory<UserApi> {

    @Override
    public UserApi create(Throwable throwable) {
        return new UserApi() {
            @Override
            public UserVo getUserByUserName(String userName) {
                log.error("用户服务 getUserByUserName 调用失败", throwable);
                return null;
            }


            @Override
            public String test500() {
                log.error("用户服务 test500 调用失败", throwable);
                return null;
            }

            @Override
            public String age(int age) {
                log.error("用户服务 age 调用失败", throwable);
                return null;
            }

            @Override
            public String test404() {
                log.error("用户服务 test404 调用失败", throwable);
                return null;
            }
        };
    }
}
