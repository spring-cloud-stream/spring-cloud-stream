# 实现说明
## 仿照源码创建接收通道
```text
类及方法调用流程:
BindableProxyFactory#afterPropertiesSet 
                       |
                       V
SubscribableChannelBindingTargetFactory#createInput(String)
                       |
                       V
CompositeMessageChannelConfigurer#configureInputChannel(MessageChannel, String)
                       |
                       V
 AbstractBindableProxyFactory#createAndBindInputs(BindingService)
```


1.org.springframework.cloud.stream.messaging.Sink
- 根据`Sink`接口中的`input()`方法返回值`SubscribableChannel`，基于`SubscribableChannel`实现订阅通道
- 找到Stream相关的实现为`DirectWithAttributesChannel`

2.org.springframework.cloud.stream.messaging.DirectWithAttributesChannel
- 查找`DirectWithAttributesChannel`的使用类为`SubscribableChannelBindingTargetFactory`

3.org.springframework.cloud.stream.binding.SubscribableChannelBindingTargetFactory 
- 仿照`SubscribableChannelBindingTargetFactory#createInput(String)`创建接收通道
- `DirectWithAttributesChannel`类为设置通道参数类，需要修改`setAttribute("type", topicName)`
- 自己创建不需要注册到Spring应用上下文中,因此不需要添加
```
if (context != null && !context.containsBean(name)) {
    context.registerBean(name, DirectWithAttributesChannel.class, () -> subscribableChannel);
}
```
- 查找`SubscribableChannelBindingTargetFactory#createInput(String)`调用类为`BindableProxyFactory`

4.org.springframework.cloud.stream.binding.BindableProxyFactory
- `BindableProxyFactory#afterPropertiesSet()`初始化时，调用`SubscribableChannelBindingTargetFactory#createInput(String)`
- 将通道存储在`AbstractBindableProxyFactory`类 ``LinkedHashMap` inputHolders中
- 查找inputHolders的调用方法为`createAndBindInputs(BindingService)`
- `bindingService.bindConsumer(boundTargetHolder.getBoundTarget(), inputTargetName)`为绑定消费者方法
- `BindingService`是一个Spring Bean，通过注入获取，即可绑定消费者

## 核心代码示例
```java
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

```
