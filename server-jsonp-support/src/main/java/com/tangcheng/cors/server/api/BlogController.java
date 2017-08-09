package com.tangcheng.cors.server.api;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * cors-demo
 *
 * @author : tang.cheng
 * @version : 2017-08-09  10:26
 */
@RestController
@CrossOrigin(origins = "http://localhost", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class BlogController {

    @RequestMapping("blog/{id}")
    public Map<String, Object> getBlog(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("time", new Date());
        map.put("data", data);
        return map;
    }

}
