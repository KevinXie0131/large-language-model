package hello.controller;

import hello.model.Greeting;
import hello.service.RedisService;
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

    @Autowired
    RedisService redisService;

    @RequestMapping("/")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/test5")
    public String test5() {
        String result = redisService.test() ;
        return "Hello!" + result;
    }

    @RequestMapping("/test6")
    public String testRedis() {
        String result = redisService.testRedis() ;
        return "Hello!" + result;
    }
}
