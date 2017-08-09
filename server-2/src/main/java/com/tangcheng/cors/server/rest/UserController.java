package com.tangcheng.cors.server.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangcheng on 8/9/2017.
 */
@RestController
public class UserController {

    @RequestMapping("user/{id}")
    public Map<String, Object> getUser(@PathVariable Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        Map<String, String> data = new HashMap<>();
        data.put("name", "name" + id);
        data.put("id", id.toString());
        map.put("data", data);
        return map;
    }

}
