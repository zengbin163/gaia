package test.zk.c01;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.I0Itec.zkclient.ZkLock;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Op;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO 经验证，主要有两种创建zkclient的方式，一种是通过zkconnection，一种是直接使用zkservers。
 * TODO 此外，还可以设置会 连接超时时间、话超时时间、操作超时时间、zk序列化器等。
 * <p>
 * TODO 推荐直接使用zkservers+连接超时。
 * <p>
 * 注意，
 * <p>
 * Created by 张少昆 on 2018/4/13.
 */
public class ZkClientTest {

    ZkClient zkClient = null;

    @Before
    public void init(){
        String zkServers = "192.9.99.150:2181,192.9.99.151:2181,192.9.99.152:2181,192.9.99.153:2181";
        //推荐使用非常大的超时值，默认就是30000。
        int sessionTimeout = 30000;
        int connectionTimeout = 30000;
        ZkConnection zkConnection = new ZkConnection(zkServers, sessionTimeout);
//        ZooKeeper zookeeper = zkConnection.getZookeeper();//TODO 本质上都是用这个来操作的。

//        zkClient = new ZkClient(zkConnection);
//        zkClient = new ZkClient(zkServers, sessionTimeout, connectionTimeout); //TODO 注意两个超时的顺序
        zkClient = new ZkClient(zkServers, connectionTimeout);//看源码，默认已经connect了

        //TODO 嗯？需要检查下sessionId
        long sessionId = zkConnection.getZookeeper().getSessionId();
        byte[] sessionPasswd = zkConnection.getZookeeper().getSessionPasswd();
        ZooKeeper.States state = zkConnection.getZookeeper().getState();
    }

    @After
    public void destroy(){
        zkClient.close();
    }

    @Test
    public void exists(){
        //TODO 注意，创建好后已经自动connect了。
        boolean exists = zkClient.exists("/");
        System.out.println(exists);
    }

    @Test
    public void create(){
        if(zkClient.exists("/user/larry")){
            return;
        }
        //TODO 一次只能创建一个znode！！！
        String s1 = zkClient.create("/user", "me,larry", CreateMode.PERSISTENT);
        String s2 = zkClient.create("/user/larry", new Date(), CreateMode.PERSISTENT);
        System.out.println(s1);
        System.out.println(s2);
    }

    @Test
    public void readData(){
        //TODO 嗯？提示是字符串！看来需要自己反序列化
        String z1 = "/user";
        String z2 = "/user/larry";

        Object o1 = zkClient.readData(z1);
        Object o2 = zkClient.readData(z2);

        System.out.println("o1:" + o1);
        System.out.println("o2:" + o2);

        try{
            Object o3 = zkClient.readData("/user/zeal");//TODO 没有的话，抛出异常
            System.out.println("o3:" + o3);
        } catch(Exception e){
            e.printStackTrace();
        }
        Object o4 = zkClient.readData("/user/zeal", true);//TODO 没有的话，返回null
        System.out.println("o4:" + o4);

        Stat stat = new Stat();//TODO 带版本等条件的查询？ NO! 是用于接收具体信息的！
        stat.setVersion(2);//完全没用
        System.out.println(stat);
        Object o5 = zkClient.readData(z2, stat);
        System.out.println(stat);//能看到变化！！！

        System.out.println("o5:" + o5);
    }

    //TODO 删除，要么不带版本(true/false)，要么带正确版本，否则会报异常 KeeperException$BadVersionException
    //TODO 会不会删除子节点？不能！KeeperException$NotEmptyException
    @Test
    public void delete(){
        String node = "/user/fordelete";

        boolean delete1 = zkClient.delete(node);
        System.out.println(delete1);

        String path1 = zkClient.create(node, new Date(), CreateMode.PERSISTENT);
        String path2 = null;//TODO KeeperException$NodeExistsException
        try{
            path2 = zkClient.create(node, new Date(), CreateMode.PERSISTENT);
        } catch(RuntimeException e){
            e.printStackTrace();
        }
        System.out.println("path1:" + path1);
        System.out.println("path2:" + path2);

        //带版本的删除
        boolean delete = false;
        try{
            delete = zkClient.delete(node, 3);//TODO KeeperException$BadVersionException
        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("delete:" + delete);
        delete = zkClient.delete(node);
        System.out.println("delete:" + delete);
    }

    //TODO 不能删除非空节点！但可以使用deleteRecursive 来递归删除子节点！
    @Test
    public void deleteRecursive(){
        String node = "/user/del/a/b/c/d";
        zkClient.createPersistent(node, true);//创建父路径，且，如果已有路径，不会抛出  KeeperException$NodeExistsException ！

        try{
            boolean delete = zkClient.delete("/user/del");//TODO 目录非空！ KeeperException$NotEmptyException
            System.out.println("delete:" + delete);
        } catch(Exception e){
            e.printStackTrace();
        }

        boolean deleteRecursive = zkClient.deleteRecursive("/user/del");
        System.out.println("deleteRecursive:" + deleteRecursive);

    }

    //
    @Test
    public void writeData() throws InterruptedException{
        String node = "/user/data";

        zkClient.delete(node);

        String s1 = zkClient.create(node, new Date(), CreateMode.PERSISTENT);
        System.out.println("s1:" + s1);

        Object o1 = zkClient.readData(node);
        Thread.sleep(2000L);
        zkClient.writeData(node, new Date());//writeData
        Object o2 = zkClient.readData(node);
        Thread.sleep(2000L);
        zkClient.writeData(node, new Date(), 1);//TODO 需要正确的版本（即你希望当前是这个版本，然后在这个版本之上更新） 否则 KeeperException$BadVersionException

        Stat stat = new Stat();
        Object o3 = zkClient.readData(node, stat);

        System.out.println("o1:" + o1);
        System.out.println("o2:" + o2);
        System.out.println("o3:" + o3);
        System.out.println("stat:" + stat);
    }

    @Test
    public void children(){ //TODO 获取所有直接子节点的名字列表
        String root = "/";
        List<String> children = zkClient.getChildren(root);
        children.forEach(System.out::println);

        try{
            List<String> children2 = zkClient.getChildren("/sd;fjakdsgkdgkjdfakg"); //TODO 不存在，则 KeeperException$NoNodeException
            children2.forEach(System.out::println);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void countChildren(){ //TODO 获取所有直接子节点的数量，本质上是获取了名字列表的长度。
        String root = "/";
        int count = zkClient.countChildren(root); //返回的是？
        System.out.println(count);


        count = zkClient.countChildren("/sd;fjakdsgkdgkjdfakg"); //TODO 不存在的，不报错，只返回 0 。
        System.out.println(count);
    }

    @Test
    public void createPersistent(){ //可以创建父路径，即创建多层目录
        String node = "/user/a/b/c/d";

        System.out.println(zkClient.exists(node));
        try{
            zkClient.createPersistent(node);
        } catch(RuntimeException e){
            e.printStackTrace();
        }
        System.out.println(zkClient.exists(node));

        zkClient.createPersistent(node, true);//创建父路径，且，如果已有路径，不会抛出  KeeperException$NodeExistsException ！
        System.out.println(zkClient.exists(node));
    }

    @Test
    public void createEphemeral(){ //TODO 临时节点 不能有子节点！临时节点 断开连接就没了！！！
//        String node="/user/temp/a/b/c";
        String node = "/user/temp";

        System.out.println(zkClient.exists(node));

        zkClient.createEphemeral(node);//TODO 因为不能有子节点，所以不能同时创建多层目录！！！

        System.out.println(zkClient.exists(node));
    }

    //FIXME 涉及到ACL，待定。https://blog.csdn.net/wuhenzhangxing/article/details/52936040
    @Test
    public void multi(){ //TODO 在一个事务中进行多项操作。
        String node = "/user/a/b/c/d";

        Iterable<Op> ops = new ArrayList<>();
        Op check = Op.check(node, 1);
        byte[] bytes = new Date().toString().getBytes(Charset.forName("UTF-8"));
        Op setData = Op.setData(node, bytes, 1);
        List<ACL> acls = new ArrayList<>();
        ACL acl = new ACL();

        Op.create(node, bytes, acls, CreateMode.PERSISTENT);
        zkClient.multi(ops);
    }

    @Test
    public void getAcl(){ //FIXME 尚不明白
        String node = "/user/a/b/c/d";

        Map.Entry<List<ACL>, Stat> acl = zkClient.getAcl(node);
        System.out.println("key:" + acl.getKey());
        System.out.println("val:" + acl.getValue());
    }

    @Test
    public void watch(){//FIXME 不知道是干啥的
        String node = "/user/watch";
        zkClient.createEphemeral(node, true);

        List<String> list1 = zkClient.watchForChilds(node); //看起来和getChildren没啥区别啊？
        List<String> list2 = zkClient.getChildren(node);

        System.out.println(list1);
        System.out.println(list2);

        zkClient.watchForData(node); //看源码，内部调用了exists

    }

    // TODO getData,getChildren(),exists()这三个方法可以针对参数中的path设置watcher。
    // TODO 当path对应的Node 有相应变化时，server端会给对应的设置了watcher的client 发送一个一次性的触发通知事件。
    // TODO 客户端在收到这个触发通知事件后，可以根据自己的业务逻辑进行相应地处理。
    // TODO 注意原生zk api中，这个watcher的功能是[一次性]的，如果还想继续得到watcher通知，在处理完事件后，要重新register。
    // TODO 关于链接状态变化的监听，通常要在一开始就注册。
    @Test
    public void subscribe(){
        String node = "/user/watch";
        zkClient.deleteRecursive(node);
        zkClient.createPersistent(node, true);

        // TODO 自定义状态监听器，奇怪，无效，为啥？
        IZkStateListener stateListener = new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception{
                System.out.println("啊，状态改变了" + state + "，处理下");
            }

            @Override
            public void handleNewSession() throws Exception{
                System.out.println("啊，新的会话，处理下");
            }

            @Override
            public void handleSessionEstablishmentError(Throwable error) throws Exception{
                System.out.println("啊，会话创建错误，处理下");
            }
        };
        zkClient.subscribeStateChanges(stateListener);//看了源码，貌似也没有跟server交互。

        // TODO 自定义子节点监听器
        IZkChildListener childListener = (parentPath, currentChilds) -> System.out.println("路径[" + parentPath + "]下面的子节点变更。子节点为：" + currentChilds);
        zkClient.subscribeChildChanges(node, childListener);

        // TODO 自定义数据监听器
//        IZkDataListener dataListener = new ContentWatcher(zkClient, node); //嗯，居然有一个默认的实现？？有变化就输出一下
        IZkDataListener dataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception{
                System.out.println("路径[" + dataPath + "]的数据变更。数据：" + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception{
                System.out.println("路径[" + dataPath + "]的数据被删除。");
            }
        }; //嗯，居然有一个默认的实现？？有变化就输出一下
        zkClient.subscribeDataChanges(node, dataListener);

        // TODO 测试 数据监听器
        for(int i = 0; i < 5; i++){
            zkClient.writeData(node, new Date());
            try{
                Thread.sleep(1000L);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        // TODO 测试子节点监听器
        for(int i = 0; i < 5; i++){
            zkClient.createPersistent(node + "/" + i);
            try{
                Thread.sleep(1000L);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        // FIXME 测试状态监听器 - 失败鸟
        for(int i = -1; i < 6; i++){
//            Watcher.Event.KeeperState currentState = Watcher.Event.KeeperState.ConnectedReadOnly; //我去，设置链接状态用的啊，不能乱用吧。。

            if(i == 2){//2不存在
                continue;
            }
            zkClient.setCurrentState(Watcher.Event.KeeperState.fromInt(i));
            zkClient.writeData(node, i);
            try{
                Thread.sleep(1000L);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    @Test
    public void sync(){ //嗯？没有sync()啊
//        zkClient.
//        zookeeper.sync();
    }

    @Test
    public void eventLock(){
        //得到一个互斥锁？看源码，就是一个ReentrantLock。干嘛的？
        ZkLock eventLock = zkClient.getEventLock();

    }
}
