package com.github.zk.spring.cloud.stream.consumer.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * 数据接收
 *
 * @author zk
 * @date 2020/10/26 14:45
 */
@EnableBinding(Sink.class)
public class ReceiveService {

    @StreamListener(Sink.INPUT)
    public void receive(Object payload) {
        System.out.println("接收到的消息为：" + payload);
    }
}
