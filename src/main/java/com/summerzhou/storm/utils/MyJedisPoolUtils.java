package com.summerzhou.storm.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

public class MyJedisPoolUtils {
    private static ShardedJedisPool jedisPool;
    private static Jedis jedis;
    static {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(5); //池子中最大空闲数为5
        config.setMaxTotal(-1);//池子中最大连接数不限制
        config.setJmxEnabled(true);//设置开启jvm功能
        config.setMaxWaitMillis(3000l);//设置连接池没有连接后客户端等待的最长时间
        //设置List，存入Jedis服务信息，存入四个
        List<JedisShardInfo> jedisList = new ArrayList<>();
        jedisList.add(new JedisShardInfo("192.168.25.111",6379));
        jedisList.add(new JedisShardInfo("192.168.25.111",6379));
        jedisList.add(new JedisShardInfo("192.168.25.111",6379));
        jedisList.add(new JedisShardInfo("192.168.25.111",6379));
        //创建分布式连接池
        jedisPool = new ShardedJedisPool(config,jedisList);
    }


    public static ShardedJedis getJedis(){
        return jedisPool.getResource();
    }

    public static void close(ShardedJedis jedis){
        jedisPool.returnResource(jedis);
    }

    public static void main(String[] args) {
        ShardedJedis jedis = null;
        try {
            jedis = MyJedisPoolUtils.getJedis();
            jedis.set("a", "a");
            jedis.set("b", "b");
            jedis.set("v", "v");
            jedis.set("c", "c");
            Client client1 = jedis.getShard("a").getClient();
            Client client2 = jedis.getShard("b").getClient();
            Client client3 = jedis.getShard("v").getClient();
            Client client4 = jedis.getShard("c").getClient();

            System.out.println("host:"+client1.getHost()+"port:"+client1.getPort());
            System.out.println("host:"+client2.getHost()+"port:"+client2.getPort());
            System.out.println("host:"+client3.getHost()+"port:"+client3.getPort());
            System.out.println("host:"+client4.getHost()+"port:"+client4.getPort());

        }finally {
            if(jedis != null){
                MyJedisPoolUtils.close(jedis);
            }
        }
    }
}
