package com.tangcheng.cors.client.third.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by tangcheng on 8/9/2017.
 */
@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("user/{id}")
    public Map<String, Object> getUser(@PathVariable Long id) {
        return restTemplate.getForObject("http://localhost:8081/user/{id}", Map.class, id);
    }

}
