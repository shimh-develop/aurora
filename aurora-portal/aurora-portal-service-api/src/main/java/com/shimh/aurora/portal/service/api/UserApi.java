package com.shimh.aurora.portal.service.api;

import com.shimh.aurora.portal.service.api.vo.UserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@RequestMapping("/user")
public interface UserApi {
    @RequestMapping(value = "/getUserByUserName", method = {RequestMethod.GET})
    UserVo getUserByUserName(@RequestParam("userName") String userName);

    @GetMapping("/500")
    public String test500();

    @GetMapping("/age")
    public String age(@RequestParam("age") int age);

    @GetMapping("/404")
    public String test404();
}
