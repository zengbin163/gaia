package solr.c01;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by zengbin on 2018/4/2.
 */
public class CreateIndexTest {

    @Test
    public void connect(){ //TODO 看看solr都有哪些类型？目前就4种
        String solrServerUrl = "";
        String zkHost = "";
        //嗯？用于负载均衡？
        String[] solrServerUrls = null;

        // SolrServer是抽象类
        CloudSolrServer s1 = new CloudSolrServer(zkHost);//连接到zookeeper
        ConcurrentUpdateSolrServer s2 = new ConcurrentUpdateSolrServer(solrServerUrl, 50, 3);
        HttpSolrServer s3 = new HttpSolrServer(solrServerUrl);
        try{
            LBHttpSolrServer s4 = new LBHttpSolrServer(solrServerUrls); //Load balanced
        } catch(MalformedURLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void single(){ //
        String url = "http://localhost:8983/solr/"; // 必须带着尾 /  ？
        HttpSolrServer solrServer = new HttpSolrServer(url);

//        solrServer.addBean(obj);//看源码，应该是转成类似json格式的数据
        Collection<SolrInputDocument> list = new ArrayList<>();
        SolrInputDocument solrInputDocument = new SolrInputDocument();
//        solrInputDocument.addChildDocument();
//        solrInputDocument.addField(k,v);
        try{
            solrServer.add(list);
        } catch(SolrServerException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
