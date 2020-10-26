package com.github.zk.spring.cloud.stream.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 消费者启动类
 *
 * @author zk
 * @date 2020/10/26 14:41
 */
@SpringBootApplication
public class SpringCloudStreamConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamConsumerApplication.class, args);
    }
}
