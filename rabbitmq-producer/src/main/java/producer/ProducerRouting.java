package producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Administrator
 */
public class ProducerRouting {
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

        factory.setAutomaticRecoveryEnabled(true);
        // 3、创建连接 Connection
        Connection connection = factory.newConnection();
        // 4、创建Channel
        Channel channel = connection.createChannel();
        // 5、创建交换机
        /**
         *  exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
         * @param exchange:交换机的名字
         * @param type：交换机的四种类型：DIRECT("direct")：定向
         *                             FANOUT("fanout")：扇形（广播），发送消息到每一个与之绑定队列
         *                             TOPIC("topic")：通配符方式
         *                             HEADERS("headers")：参数匹配（用的少）
         * @param durable：是否持久
         * @param autoDelete：是否自动删除
         * @param internal：是否内部使用（一般设置false）
         * @param arguments：参数
         */
        String exchangeName = "test_direct";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false, false, null);
        // 6、创建队列
        /**
         *  queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
         */
        String queueName1 = "test_direct1_queues";
        String queueName2 = "test_direct2_queues";
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueDeclare(queueName2, true, false, false, null);
        // 7、绑定队列和交换机
        /**
         *  queueBind(String queue, String exchange, String routingKey)
         * @param queue:队列名称
         * @param exchange：交换机名称
         * @param routingKey：路由键，绑定规则（如果交换机的类型是fanout,routingKey设置为""）
         */
        //队列1绑定error
        channel.queueBind(queueName1, exchangeName, "error");
        //队列2绑定error，info，warning
        channel.queueBind(queueName2, exchangeName, "error");
        channel.queueBind(queueName2, exchangeName, "info");
        channel.queueBind(queueName2, exchangeName, "warning");
        // 8、发送消息
        String body = "日志信息：www调用了findAll()方法,日志的级别为warning....";
        channel.basicPublish(exchangeName,
                "error",
                null, body.getBytes());
        // 9、是否释放资源
        channel.close();
        connection.close();
    }
}
