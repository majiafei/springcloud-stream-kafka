package com.example.springcloudstreamkafka.controller;

import com.example.springcloudstreamkafka.producer.MessageProducerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: majiafei
 * @Date: 2018/12/16 18:34
 * @Description:
 */
@RestController
public class KafkaProducerController {

    @Autowired
    private MessageProducerBean messageProducerBean;

    @GetMapping("/send")
    public boolean sendToGerry(@RequestParam("message") String message) {
        messageProducerBean.sendToGerry(message);

        return true;
    }

    @PostMapping("/send")
    public boolean sendToGerry1(@RequestParam("message") String message) {
        messageProducerBean.sendToGerry(message);

        return true;
    }
}
