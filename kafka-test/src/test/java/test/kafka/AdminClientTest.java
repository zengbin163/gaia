package test.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Kafka内部创建topic的类！
 * 还可以进行各种管理操作！创建、更改、删除 - topic、partition、record、acl！我擦，牛逼啊！
 * <p>
 * Created by 张少昆 on 2018/5/4.
 */
public class AdminClientTest {

    @Test
    public void r1(){
        Map<String, Object> conf = new HashMap<>();
        conf.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        conf.put(AdminClientConfig.CLIENT_ID_CONFIG, 1);
        conf.put(AdminClientConfig.RETRIES_CONFIG, 0);

        AdminClient adminClient = AdminClient.create(conf);
        ListTopicsResult listTopicsResult = adminClient.listTopics();

        NewTopic newTopic = new NewTopic("new-test", 3, (short) 3);
        CreateTopicsOptions options = new CreateTopicsOptions();

        boolean b = options.shouldValidateOnly();//只校验，不创建？
        System.out.println(b);

        adminClient.createTopics(Arrays.asList(newTopic), options);
    }
}
