package producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发送消息
 *
 * @author Administrator
 */
public class ProducerHelloWord {
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
        channel.queueDeclare("pig", true, false, false, null);
        // 6、发送消息
        /**
         * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
         * 参数：
         *    exchange:交换机名称 简单模式下交换机会使用默认的 “”
         *    routingKey：路由名称(跟对列的名称一致)
         *    props：配置信息
         *    body；发送消息数据
         */

        String bodyStr = "你是真的猪！是的呢，真的吗";
        channel.basicPublish("", "pig", null, bodyStr.getBytes());

        // 7、释放资源
        channel.close();
        connection.close();
    }
}
