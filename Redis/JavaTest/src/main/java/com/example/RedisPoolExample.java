package com.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisPoolExample {
    public static void main(String[] args) {
      /*  Jedis jedis = new Jedis("192.168.10.100", 6379);
        jedis.auth("123456");
        jedis.select(0);*/
        JedisPool pool;
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(8);
        config.setMaxIdle(8);
        config.setMinIdle(0);
        config.setMaxWaitMillis(200);
        pool = new JedisPool(config,"192.168.10.100",6379,1000,"123456");

        Jedis jedis = pool.getResource();

        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
        String value = jedis.ping();
        System.out.println(value);

      /*  String r = jedis.set("newName1","new Jack");
        System.out.println(r);
        String r1 = jedis.get("newName1");
        System.out.println(r1);

        String r11 = jedis.setex("newNameTo", 5, "new Jack1");
        System.out.println(r11);
        String r12 = jedis.get("newNameTo");
        System.out.println(r12);

        long r2 = jedis.hset("hNewName1","hkey11","hval11");
        System.out.println(r2);
        String r33 = jedis.hget("hNewName1","hkey11");
        System.out.println(r33);

        Map map = new HashMap<>();
        map.put("hkey22","hval22");
        map.put("hkey22a","hval22a");
        String r4 = jedis.hmset("hNewName1", map);
        System.out.println(r4);
        List<String> r5 = jedis.hmget("hNewName1", "hkey22", "hkey22a");
        System.out.println(r5);

        System.out.println(jedis.hgetAll("hNewName1"));*/

        jedis.close();
    }
}
