package com.springcloud.zipkinServer.demo.Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

/**
 * Created by yuerte969 on 5/7/2017.
 */
@EnableZipkinStreamServer
@SpringBootApplication
public class ZipkinApplication {
    public static void main(String[] args){
        SpringApplication.run(ZipkinApplication.class,args);
    }
}
