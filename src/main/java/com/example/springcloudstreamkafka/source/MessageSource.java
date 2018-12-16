package com.example.springcloudstreamkafka.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Auther: majiafei
 * @Date: 2018/12/16 18:33
 * @Description:
 */
public interface MessageSource {
    String NAME = "gerry";

    @Output(NAME)
    MessageChannel output();
}
