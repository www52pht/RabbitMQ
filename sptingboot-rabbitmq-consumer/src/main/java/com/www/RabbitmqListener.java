package com.www;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author www
 * @creat 2021/1/26
 */
@Component
public class RabbitmqListener {
    @RabbitListener(queues = {"boot_queue"})
    public void ListenerQueue(Message message) {
//        System.out.println(message);
        System.out.println(new String(message.getBody()));
    }
}
