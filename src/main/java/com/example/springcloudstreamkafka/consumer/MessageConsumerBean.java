package com.example.springcloudstreamkafka.consumer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Auther: majiafei
 * @Date: 2018/12/16 18:29
 * @Description:
 */
@Component
@EnableBinding(value={Sink.class})
public class MessageConsumerBean {
    @Autowired
    @Qualifier(Sink.INPUT)
    private SubscribableChannel subscribableChannel;

    //1、 当subscribableChannel注入完成后完成回调
    @PostConstruct
    public void init() {
        subscribableChannel.subscribe(message->{
            System.out.println("回调");
            System.out.println(message.getPayload());
        });
    }
    // 2、@ServiceActivator
    @ServiceActivator(inputChannel=Sink.INPUT)
    public void message(String message) {
        System.out.println("@ServiceActivator:"+message);
    }
    //3、@StreamListener
    @StreamListener(Sink.INPUT)
    public void onMessage(String message) {
        System.out.println("@StreamListener:"+message);
    }
}
