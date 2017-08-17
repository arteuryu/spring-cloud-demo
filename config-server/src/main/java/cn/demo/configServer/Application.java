package cn.demo.configServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class Application {
    public static void main(String[] args) {
        String s = "dd";
        String s1 = s;
        s = "ee";
        System.out.print(s1);
        SpringApplication.run(Application.class, args);
    }
}
