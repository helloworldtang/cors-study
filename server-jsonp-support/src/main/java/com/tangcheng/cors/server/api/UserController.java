package com.tangcheng.cors.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * cors-demo
 *
 * @author : tang.cheng
 * @version : 2017-08-09  10:19
 */
@RestController
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("user/{id}")
    public Map<String, Object> getUser(@PathVariable Long id) {
        return restTemplate.getForObject("http://localhost:8081/user/{id}", Map.class, id);
    }


    @RequestMapping("jsonp/scene1/user/{id}")
    public void getUser(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/javascript; charset=UTF-8");
        String callbackFunName = request.getParameter("callback");//得到js函数名称
        try {
            Map result = restTemplate.getForObject("http://localhost:8081/user/{id}", Map.class, id);
            ObjectMapper mapper = new ObjectMapper();
            String valueAsString = mapper.writeValueAsString(new JSONPObject(callbackFunName, result));
            response.getWriter().write(valueAsString); //返回jsonp数据
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


    @RequestMapping("jsonp/scene2/user/{id}")
    public JSONPObject getUser(@PathVariable Long id, HttpServletRequest request) {
        String callbackFunName = request.getParameter("callback");//得到js函数名称
        Map result = restTemplate.getForObject("http://localhost:8081/user/{id}", Map.class, id);
        return new JSONPObject(callbackFunName, result);
    }


}
