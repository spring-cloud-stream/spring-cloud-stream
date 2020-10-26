package com.github.zk.spring.cloud.stream.consumer.controller;

import com.github.zk.spring.cloud.stream.consumer.service.IReceive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消费者请求处理器
 *
 * @author zk
 * @date 2020/10/26 14:55
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private IReceive iReceive;

    @RequestMapping("/receive")
    public String receive(@RequestParam("topic") String topic) {
        return iReceive.receive(topic);
    }

    @RequestMapping("/interruptTopicReceive")
    public String interruptTopicReceive(@RequestParam("topic") String topic) {
        return iReceive.interruptTopicReceive(topic);
    }
}
