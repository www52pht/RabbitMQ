package com.www;

import com.rabbitmq.client.Return;
import com.rabbitmq.client.ReturnCallback;
import com.www.config.rabbitmqConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SptingbootRabbitmqProducerApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend(rabbitmqConfig.EXCHANGE_NAME, "boot.haha", "hello-pig!");
    }


    /**
     * 确认模式：
     * 步骤：1、确认模式开启：ConnectionFactory中开启 publisher-confirm-type: correlated
     * 2、在rabbitmqTemplate定义 ConfirmCallback 回调函数
     */
    @Test
    public void testConfirm() {

        // 2、定义回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData:相关配置
             * @param b：exchange交换机 是否成功接收到信息。true是成功 false代表失败
             * @param s
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                System.out.println("TMD,是真的舒服！！");
                if (b) {
                    System.out.println("接收成功！");
                } else {
                    System.out.println("接收失败消息" + s);
                }
            }
        });

        rabbitTemplate.convertAndSend(rabbitmqConfig.EXCHANGE_NAME, "boot.ba", "这次bbaa不行吧");

    }


    /**
     * 回退模式：当消息发送给Exchange后，Exchange路由到Queue失败后 才会执行 ReturnCallback
     * 步骤：1、开启回退模式    publisher-returns: true
     * 2、设置ReturnCallback
     * 3、设置Exchange处理消息模式
     * 1、如果消息没有路由到Queue，则丢弃（默认）
     * 2、如果消息没有路由到Queue，返回给消息发送方ReturnCallback
     */
    @Test
    public void testReturn() {
        //设置交换机消息失败处理模式
        rabbitTemplate.setMandatory(true);

         //  2、设置ReturnCallback
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             *
             * @param message  ：消息对象
             * @param replyCode  ：错误码
             * @param replyText  ：错误信息
             * @param exchange   ：交换机
             * @param routingKey ：路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("return 执行 。。。。");
                System.out.println("message:"+message);
                System.out.println("replyCode:"+replyCode);
                System.out.println("replyText:"+replyText);
                System.out.println("exchange:"+exchange);
                System.out.println("routingKey:"+routingKey);
            }
        });
        rabbitTemplate.convertAndSend(rabbitmqConfig.EXCHANGE_NAME, "aaaa.ba", "这次bbaa不行吧");
    }

}
