package com.tangcheng.cors.client.third.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tangcheng on 8/9/2017.
 */
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }



}
