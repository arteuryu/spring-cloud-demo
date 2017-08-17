package com.springcloud.consul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuerte969 on 17/7/2017.
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ConsulApp {
    @RequestMapping("/consulTest")
    public String consulTest(){
        System.out.println("constulTest");
        return "consul test";
    }

    public static void main(String[] args){
        SpringApplication.run(ConsulApp.class,args);
    }
}
