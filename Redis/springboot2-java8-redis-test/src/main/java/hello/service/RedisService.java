package hello.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Value("${spring.redis.host}")
    String value;

    @Autowired
    private RedisTemplate redisTemplate;

/*
    @Autowired
    private RedisOperations<String, String> operations;

    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;
*/

    public String test() {
        System.out.println(value);

        redisTemplate.opsForValue().set("spring boot","test spring");
        Object res = redisTemplate.opsForValue().get("spring boot");
        return  res.toString();
    }

    public String testRedis() {
        System.out.println(value);
        //设置值到redis
        redisTemplate.opsForValue().set("name","lucy");
        //从redis获取值
        String name = (String)redisTemplate.opsForValue().get("name");
        return name;
    }

}
