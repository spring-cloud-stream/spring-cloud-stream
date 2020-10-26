package com.github.zk.spring.cloud.stream.consumer.service;

/**
 * 接收接口
 *
 * @author zk
 * @date 2020/10/26 14:48
 */
public interface IReceive {
    /**
     * 动态主题接收
     * @param topic 主题名称
     * @return
     */
    String receive(String topic);

    /**
     * 中断接收主题消息
     * @param topic 主题名称
     * @return
     */
    String interruptTopicReceive(String topic);
}
