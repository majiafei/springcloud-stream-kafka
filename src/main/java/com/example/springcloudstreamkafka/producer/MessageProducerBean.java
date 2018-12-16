package com.example.springcloudstreamkafka.producer;

import com.example.springcloudstreamkafka.source.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Auther: majiafei
 * @Date: 2018/12/16 18:28
 * @Description:
 */
@Component
@EnableBinding({Source.class})
public class MessageProducerBean {

   @Autowired
    @Qualifier(Source.OUTPUT)
    private MessageChannel messageChannel;

   @Autowired
   private Source source;

    /**
     *
     * @param message
     */
    public void sendToGerry(String message) {
        // 通过消息管道发送消息
         messageChannel.send(MessageBuilder.withPayload(message).build());
//        gerryMessageChannel.send(MessageBuilder.withPayload(message).build());
    }
}
