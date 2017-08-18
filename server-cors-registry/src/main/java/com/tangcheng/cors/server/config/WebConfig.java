package com.tangcheng.cors.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by tangcheng on 8/9/2017.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        /**
         * Enable cross-origin request handling for the specified path pattern.
         * <p>Exact path mapping URIs (such as {@code "/admin"}) are supported as
         * well as Ant-style path patterns (such as {@code "/admin/**"}).
         * <p>By default, all origins, all headers, credentials and {@code GET},
         * {@code HEAD}, and {@code POST} methods are allowed, and the max age
         * is set to 30 minutes.
         * @param pathPattern the path pattern to enable CORS handling for
         * @return CorsRegistration the corresponding registration object,
         * allowing for further fine-tuning
         */
        registry.addMapping("/**")
                /**
                 * Set the origins to allow, e.g. {@code "http://domain1.com"}.
                 * <p>The special value {@code "*"} allows all domains.
                 * <p>By default, all origins are allowed.
                 */
                .allowedOrigins("http://localhost")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
