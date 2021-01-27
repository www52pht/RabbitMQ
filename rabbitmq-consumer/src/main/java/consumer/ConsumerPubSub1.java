package consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Administrator
 */
public class ConsumerPubSub1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、设置参数
        // IP 默认值localhost（127.0.0.1）
        factory.setHost("47.110.248.149");
        //端口 默认值：5672
        factory.setPort(5672);
        //虚拟机 默认值：/
        factory.setVirtualHost("/pht");
        //用户名 默认值：guest
        factory.setUsername("www");
        //密码 默认值：guest
        factory.setPassword("123456");
        // 3、创建连接 Connection
        Connection connection = factory.newConnection();
        // 4、创建Channel
        Channel channel = connection.createChannel();

        String queueName1 = "test_fanout1";


        // 6、接收消息
        /**
         * basicConsume(String queue, boolean autoAck, Consumer callback)
         * 参数：
         *    queue：对列名称
         *    autoAck：是否自动确认
         *    callback：回调对象
         */
        Consumer consumer = new DefaultConsumer(channel) {
            /**
             * 这是一个回调方法，当收到消息后会自动调用这个方法
             * @param consumerTag：标识
             * @param envelope：获得一些信息，交换机，路由key。。。
             * @param properties：配置的信息
             * @param body：数据
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                System.out.println("consumerTag=" + consumerTag);
//                System.out.println("Exchange=" + envelope.getExchange());
//                System.out.println("RoutingKey=" + envelope.getRoutingKey());
//                System.out.println("properties=" + properties);
                System.out.println("body=" + new String(body));
                System.out.println("将信息打印到控制台！");
            }
        };
        channel.basicConsume(queueName1, true, consumer);

//        // 7、消费者不要关闭资源，因为需要一直监听是否有生产者的信息
//        channel.close();
//        connection.close();
    }
}
