package hello.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new String(counter.incrementAndGet() + name);
    }


    public static final String EXCHANGE_DIRECT = "exchange.direct.order";
    public static final String ROUTING_KEY = "order";

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RequestMapping("/mq")
    public void test6() {
        // 准备消息
        Map<String,Object> msg = new HashMap<>();
        msg.put("name", "Jack");
        msg.put("age", 21);
        rabbitTemplate.convertAndSend("object.queue", msg);
    }


    @RequestMapping("/mq1")
    public void test7() throws InterruptedException {
        while(true){
            rabbitTemplate.convertAndSend(EXCHANGE_DIRECT, ROUTING_KEY, "Hello " + counter.incrementAndGet());
            Thread.sleep(100);
        }
    }


    public static final String EXCHANGE_NORMAL = "exchange.normal.video";
    public static final String EXCHANGE_DEAD_LETTER = "exchange.dead.letter.video";
    public static final String ROUTING_KEY_NORMAL = "routing.key.normal.video";
    public static final String ROUTING_KEY_DEAD_LETTER = "routing.key.dead.letter.video";
    public static final String QUEUE_NORMAL = "queue.normal.video";
    public static final String QUEUE_DEAD_LETTER = "queue.dead.letter.video";

    @RequestMapping("/mq2")
    public void test8() throws InterruptedException {
        rabbitTemplate.convertAndSend(
                        EXCHANGE_NORMAL,
                        ROUTING_KEY_NORMAL,
                        "测试死信情况1：消息被拒绝");
    }

    @RequestMapping("/mq3")
    public void test9() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend(
                    EXCHANGE_NORMAL,
                    ROUTING_KEY_NORMAL,
                    "测试死信情况2：消息数量超过队列的最大容量" + i);
        }
    }


    public static final String EXCHANGE_DELAY = "exchange.delay.video";
    public static final String ROUTING_KEY_DELAY = "routing.key.delay.video";
    public static final String QUEUE_DELAY = "queue.delay.video";

    @RequestMapping("/mq4")
    public void test10() throws InterruptedException {
        rabbitTemplate.convertAndSend(
                EXCHANGE_DELAY,
                ROUTING_KEY_DELAY,
                "测试基于插件的延迟消息 [" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "]",
                messageProcessor -> {
                    // 设置延迟时间：以毫秒为单位
                    messageProcessor.getMessageProperties().setHeader("x-delay", "10000");

                    return messageProcessor;
                });
    }

    // 1、创建消息后置处理器对象
    MessagePostProcessor messagePostProcessor = (Message message) -> {
        // 设定 TTL 时间，以毫秒为单位
        message.getMessageProperties().setHeader("x-delay", "5000");
        return message;
    };

    @RequestMapping("/mq5")
    public void test11() throws InterruptedException {
        rabbitTemplate.convertAndSend(
                EXCHANGE_DELAY,
                ROUTING_KEY_DELAY,
                "测试基于插件的延迟消息 [" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "]", messagePostProcessor);
    }

}
