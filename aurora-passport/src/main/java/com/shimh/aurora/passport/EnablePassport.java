package com.shimh.aurora.passport;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({PassportConfiguration.class})
public @interface EnablePassport {
}
