package com.github.zk.spring.cloud.stream.producer.controller;

import com.github.zk.spring.cloud.stream.producer.service.ISendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端请求处理器类
 *
 * @author zk
 * @date 2020/10/26 14:30
 */
@RestController
public class ProducterController {
    @Autowired
    private ISendService iSendService;

    @RequestMapping("/send/{msg}")
    public void sendMsg(@PathVariable("msg") String msg) {
        for (int i = 0; i < 10; i++) {
            //id为指定分区号，有yml配置partitionKeyExpression: payload.id 决定哪个字段为分区号
            String json = "{\"id\":\"0\",\"name\":\"zk\",\"msg\":\"" + msg + "\"}";
            iSendService.sendMsg(json + i);
        }
    }

    @RequestMapping("/sendToTopic")
    public void sendToTopic(@RequestParam("topic") String topic, @RequestParam("msg") String msg) {
        iSendService.sendTopicMsg(topic, msg);
    }
}

