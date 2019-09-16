package com.shimh.aurora.portal.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
@Controller
@RequestMapping("/")
public class SysIndexController {

    @GetMapping
    public String index() {
        return "index";
    }

}
