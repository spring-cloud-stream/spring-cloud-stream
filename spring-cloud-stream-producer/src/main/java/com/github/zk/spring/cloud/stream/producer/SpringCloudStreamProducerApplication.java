package com.github.zk.spring.cloud.stream.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 生产者启动类
 *
 * @author zk
 * @date 2020/10/26 13:48
 */
@SpringBootApplication
public class SpringCloudStreamProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamProducerApplication.class, args);
    }
}
