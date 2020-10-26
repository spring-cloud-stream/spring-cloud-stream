package com.github.zk.spring.cloud.stream.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.core.BeanFactoryMessageChannelDestinationResolver;
import org.springframework.messaging.support.MessageBuilder;

/**
 * 发送数据 {@link ISendService} 接口实现类
 *
 * @author zk
 * @date 2020/10/26 14:23
 */
@EnableBinding(Source.class)
public class SendServiceImpl implements ISendService {

    @Autowired
    private Source source;
    @Autowired
    private BeanFactoryMessageChannelDestinationResolver destinationResolver;
    @Override
    public void sendMsg(String msg) {
        source.output().send(MessageBuilder.withPayload(msg).build());
    }

    @Override
    public void sendTopicMsg(String topic, String msg) {
        destinationResolver.resolveDestination(topic).send(MessageBuilder.withPayload(msg).build());
    }
}
