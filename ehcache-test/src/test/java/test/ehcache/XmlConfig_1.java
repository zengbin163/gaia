package test.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

/**
 * Created by 张少昆 on 2017/12/28.
 */
public class XmlConfig_1 {
    private CacheManager cacheManager = null;

    @Before
    public void init(){
        URL myUrl = getClass().getClassLoader().getResource("ehcache-config.xml");
        System.out.println(myUrl);
        Configuration xmlConfig = new XmlConfiguration(myUrl);
        cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
        System.out.println(cacheManager.getStatus()); //UNINITIALIZED
        cacheManager.init();
        System.out.println(cacheManager.getStatus()); //AVAILABLE
    }
    @After
    public void close(){
        cacheManager.close();
        System.out.println(cacheManager.getStatus()); //UNINITIALIZED
    }

    @Test
    public void r1(){
        Cache<String, String> foo = cacheManager.getCache("foo", String.class, String.class);
        foo.put("a","aaa");
        foo.put("b","bbb");
        foo.put("c","ccc");
        foo.put("d","ddd");

        String replace = foo.replace("d", "eee");
        System.out.println(replace);
    }
}
