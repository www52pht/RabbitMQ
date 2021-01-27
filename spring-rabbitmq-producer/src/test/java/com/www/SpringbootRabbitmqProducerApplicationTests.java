package com.www;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
class SpringbootRabbitmqProducerApplicationTests {
    //注入 RabbitTemplate
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testHelloWorld() {
        //发送消息
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("spring_queue",
                    "hello world spring>>>>"+i);
        }

    }

    /**
     * 发送 fanout
     */
   @Test
    public void testFanout() {
        //发送消息
       rabbitTemplate.convertAndSend("spring_fanout_exchange",
               "",
               "hello world fanout>>>>");
    }
   /**
     * 发送 topics类型
     */
   @Test
    public void testTopics() {
        //发送消息
       rabbitTemplate.convertAndSend("spring_topic_exchange",
               "pht.haha",
               "hello world testTopics>>>>");
    }

}
