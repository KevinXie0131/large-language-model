package hello.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

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
        rabbitTemplate.convertAndSend(EXCHANGE_DIRECT, ROUTING_KEY, "Hello Rabbit!SpringBoot!");
    }

    @RequestMapping("/mq1")
    public void test7() throws InterruptedException {
        while(true){
            rabbitTemplate.convertAndSend(EXCHANGE_DIRECT, ROUTING_KEY, "Hello " + counter.incrementAndGet());
            Thread.sleep(100);
        }

    }
}
