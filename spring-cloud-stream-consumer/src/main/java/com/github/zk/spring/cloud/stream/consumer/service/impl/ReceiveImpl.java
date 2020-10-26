package com.github.zk.spring.cloud.stream.consumer.service.impl;

import com.github.zk.spring.cloud.stream.consumer.service.IReceive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.binding.CompositeMessageChannelConfigurer;
import org.springframework.cloud.stream.messaging.DirectWithAttributesChannel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态主题接收 {@link IReceive}接口 实现
 *
 * @author zk
 * @date 2020/10/26 14:49
 */
@Service
public class ReceiveImpl implements IReceive {
    @Autowired
    private CompositeMessageChannelConfigurer channelConfigurer;
    @Autowired
    private BindingService bindingService;

    private List<String> topics = new ArrayList<>();

    @Override
    public synchronized String receive(String topic) {
        if (!topics.contains(topic)) {
            DirectWithAttributesChannel subscribableChannel = new DirectWithAttributesChannel();
            subscribableChannel.setComponentName(topic);
            subscribableChannel.setAttribute("type", topic);
            this.channelConfigurer.configureInputChannel(subscribableChannel, topic);
            subscribableChannel.subscribe(message -> System.out.println(new String((byte[]) message.getPayload())));
            bindingService.bindConsumer(subscribableChannel, topic);
            topics.add(topic);
            return "success";
        }
        return "【" + topic + "】主题已存在";
    }

    @Override
    public synchronized String interruptTopicReceive(String topic) {
        if (topics.contains(topic)) {
            bindingService.unbindConsumers(topic);
            topics.remove(topic);
            return "success";
        }
        return "不存在【" + topic + "】主题";
    }
}
