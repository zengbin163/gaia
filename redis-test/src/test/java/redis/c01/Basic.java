package redis.c01;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;

/**
 * Created by zengbin on 2018/4/3.
 */
public class Basic {

    private static String host = Protocol.DEFAULT_HOST;
    private static int port = Protocol.DEFAULT_PORT; //default
    private static int connTimeout = Protocol.DEFAULT_TIMEOUT;
    private static int soTimeout = Protocol.DEFAULT_TIMEOUT;
    private static boolean ssl = false;

    @Test
    public void simple() throws InterruptedException{
        //第一步，创建一个jedis。
        //Jedis jedis = new Jedis(host);//TODO 很多重载
        Jedis jedis = new Jedis(host, port, connTimeout, soTimeout, ssl);//TODO 很多重载

        //return: Status code reply
        String code = jedis.set("k-test01", "v-test01");

        String val = jedis.get("k-test01");

        System.out.println("code:" + code);
        System.out.println("val: " + val);

        Thread.sleep(1000L*3);
        jedis.close();
    }

    @Test
    public void simple2(){
        //第一步，创建一个jedis。
        Jedis jedis = new Jedis();//TODO 很多重载
        Pool<Jedis> jedisPool = new JedisPool();//TODO 很多重载
        //设置数据源
        jedis.setDataSource(jedisPool);


    }
}
