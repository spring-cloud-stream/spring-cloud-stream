package com.github.zk.spring.cloud.stream.producer.service;

/**
 * 数据发送接口
 *
 * @author zk
 * @date 2020/10/26 14:20
 */
public interface ISendService {
    /**
     * 发送至固定主题
     * @param msg 消息
     */
    void sendMsg(String msg);

    /**
     * 发送至指定主题
     * @param topic 主题名称
     * @param msg 消息
     */
    void sendTopicMsg(String topic, String msg);
}
