package com.example;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.10.100", 6379);
        jedis.auth("123456");
        jedis.select(0);

        Set<String> keys = jedis.keys("*");
        System.out.println(keys);

        String r = jedis.set("newName","new Jack");
        System.out.println(r);
        String r1 = jedis.get("newName");
        System.out.println(r1);

        long r2 = jedis.hset("hNewName","hkey11","hval11");
        System.out.println(r2);
        String r33 = jedis.hget("hNewName","hkey11");
        System.out.println(r33);

        Map map = new HashMap<>();
        map.put("hkey22","hval22");
        map.put("hkey22a","hval22a");
        String r4 = jedis.hmset("hNewName1", map);
        System.out.println(r4);
        List<String> r5 = jedis.hmget("hNewName1", "hkey22", "hkey22a");
        System.out.println(r5);

        System.out.println(jedis.hgetAll("hNewName1"));

        jedis.close();
    }
}
