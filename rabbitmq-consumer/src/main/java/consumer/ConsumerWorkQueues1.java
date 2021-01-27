package consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Administrator
 */
public class ConsumerWorkQueues1 {
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
        // 5、创建队列Queue
        /**
         * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
         * 参数：
         * queue:对列名称
         * durable：是否持久化，当mq重启之后，还在
         * exclusive
         *是否独占，只能一个消费者监听这个对列
         *当Connection关闭时，是否删除对列（一般设置false）
         * autoDelete：是否自动删除，当没有Consumer的时，自动删除
         * arguments：参数（配置怎么删除的，现在设置为null）
         */
        //如果没有一个名字叫“hello_world”的对列，则会自动创建，如果有则就不会创建
        channel.queueDeclare("work_queues", true, false, false, null);

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
            }
        };
        channel.basicConsume("work_queues", true, consumer);

//        // 7、消费者不要关闭资源，因为需要一直监听是否有生产者的信息
//        channel.close();
//        connection.close();
    }
}
