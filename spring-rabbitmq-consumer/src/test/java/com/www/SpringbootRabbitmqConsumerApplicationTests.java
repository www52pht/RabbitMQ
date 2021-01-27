package com.www;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration("classpath:spring-rabbitmq-consumer.xml")
class SpringbootRabbitmqConsumerApplicationTests {



    @Test
    void contextLoads() {
        boolean flag = true;
        while (true) {

        }
    }

}
