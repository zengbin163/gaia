package redis.c01;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ClusterTest {
    JedisCluster jedisCluster;

    @Before
    public void init() {
        Set<HostAndPort> hosts = new HashSet<>();
//        添加任意多个redis实例的地址
//        不需要将集群中所有的地址都穷举
        hosts.add(new HostAndPort("127.0.0.1", 7000));
        hosts.add(new HostAndPort("127.0.0.1", 7001));
        hosts.add(new HostAndPort("127.0.0.1", 7002));
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMaxTotal(32);
        poolConfig.setMaxIdle(2);
        jedisCluster = new JedisCluster(hosts, poolConfig);
    }

    @Test
    public void r1() {

        jedisCluster.set("test001", "test001");//TODO 很想知道，jedis如何在node之间跳转？看源码，有个xxConnectionHandler，啧啧
        System.out.println(jedisCluster.get("test001"));
        try {
            jedisCluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
