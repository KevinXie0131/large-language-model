package hello.listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Configuration
public class MyMessageConfig {

    public static final String EXCHANGE_DIRECT = "exchange.direct.order";
    public static final String ROUTING_KEY = "order";
    public static final String QUEUE_NAME = "queue.order";

    //    写法一：监听 + 在 RabbitMQ 服务器上创建交换机、队列
/*    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_NAME, durable = "true"),
            exchange = @Exchange(value = EXCHANGE_DIRECT),
            key = {ROUTING_KEY}
        )
    )*/

    //    写法二：监听
    @RabbitListener(queues = {QUEUE_NAME})
    public void processMessage(String dataString, Message message, Channel channel) throws IOException, InterruptedException {
        System.out.println("消费端1：" + dataString);

        Thread.sleep((new Random()).nextInt(400));

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, false);
    }

    @RabbitListener(queues = {QUEUE_NAME})
    public void processMessage1(String dataString, Message message, Channel channel) throws IOException, InterruptedException {
        System.out.println("消费端2：" + dataString);

        Thread.sleep((new Random()).nextInt(400));

        // 1、获取当前消息的 deliveryTag 值备用
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, false);
    }

    public static final String QUEUE_NORMAL = "queue.normal.video";
    public static final String QUEUE_DEAD_LETTER = "queue.dead.letter.video";

    @RabbitListener(queues = {QUEUE_NORMAL})
    public void processMessageNormal(Message message, Channel channel) throws IOException, InterruptedException {
        // 监听正常队列，但是拒绝消息
        System.out.println("★[normal]消息接收到，但我拒绝。");
        //  channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);

        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);

        //  channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        //  Thread.sleep(100);
    }

    @RabbitListener(queues = {QUEUE_DEAD_LETTER})
    public void processMessageDead(String dataString, Message message, Channel channel) throws IOException {
        // 监听死信队列
        System.out.println("★[dead letter]dataString = " + dataString);
        System.out.println("★[dead letter]我是死信监听方法，我接收到了死信消息");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    public static final String QUEUE_DELAY = "queue.delay.video";

    @RabbitListener(queues = {QUEUE_DELAY})
    public void process(String dataString, Message message, Channel channel) throws IOException {
        System.out.println("[生产者]" + dataString);
        System.out.println("[消费者]" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    @RabbitListener(queues = "object.queue")
    public void listenObject(Map<String, Object> msg, Message message, Channel channel) throws InterruptedException, IOException {
        System.out.println("消费者 收到了 object.queue的消息：【" + msg +"】");
        System.out.println("消费者 收到了 object.queue的消息：【" + message.getMessageProperties().getMessageId() +"】");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
