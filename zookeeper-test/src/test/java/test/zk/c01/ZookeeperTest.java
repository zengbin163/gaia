package test.zk.c01;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * https://blog.csdn.net/jin5203344/article/details/52900036
 * TODO 这里是直接使用zk自己的接口。不推荐。
 */
public class ZookeeperTest {
    ZooKeeper zooKeeper = null;

    @Before
    public void init() {
        String conStr = "192.9.99.150:2181,192.9.99.151:2181,192.9.99.152:2181,192.9.99.153:2181";

        int sessionTimeout = 3000;

        Watcher watcher = null;
        try {
            zooKeeper = new ZooKeeper(conStr, sessionTimeout, watcher);//zkClient本身是watcher，调用的是zkConnection.connect(watcher)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void r1(){

    }
}
