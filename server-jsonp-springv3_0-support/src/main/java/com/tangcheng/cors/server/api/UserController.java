package com.tangcheng.cors.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * cors-demo
 *
 * @author : tang.cheng
 * @version : 2017-08-09  14:01
 */
@Controller
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/user/{id}/ResponseBody", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getUser(@PathVariable Long id) {
        return restTemplate.getForObject("http://localhost:8081/user/{id}", Map.class, id);
    }

    @RequestMapping(value = "/user/{id}/ResponseEntity", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> bookInfo3(@PathVariable Long id) {
        Map result = restTemplate.getForObject("http://localhost:8081/user/{id}", Map.class, id);
        return ResponseEntity.ok(result);
    }

}
