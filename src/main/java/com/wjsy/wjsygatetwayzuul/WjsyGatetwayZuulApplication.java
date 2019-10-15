package com.wjsy.wjsygatetwayzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
//@EnableDiscoveryClient
@EnableFeignClients
public class WjsyGatetwayZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(WjsyGatetwayZuulApplication.class, args);
    }

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

    @Bean
    public TokenCheckFilter tokenCheckFilter() {
        return new TokenCheckFilter();
    }
}
