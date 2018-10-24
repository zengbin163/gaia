package test.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.Test;

/**
 * http://www.ehcache.org/documentation/3.4/getting-started.html
 * <p>
 * ehcache有编码式配置和XML配置。
 * 编码式配置，一般使用builder来构建。 - 编码式配置自3.3版本起有效。
 * <p>
 * Created by zengbin on 2017/12/28.
 */
public class JavaConfig_1 {
    @Test
    public void r1(){
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                                            .withCache("preConfigured", //cache name
                                                    // k, v, max
                                                    CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)))
                                            .build(); //一旦调用了build()，则创建实例，但未初始化！
        cacheManager.init(); //初始化，也可以 CacheManagerBuilder.build(true)

        // 获取cache有两种方式，一种是在开始就定义好，这里直接*获取*即可；一种是使用cachemanager*创建*。二者都需要传入相应配置。
        Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class); //干啥用？

        Cache<Long, String> myCache = cacheManager.createCache("myCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)));

        myCache.put(1L, "da one!");
        myCache.put(1L, "da two!"); //如果已有，则更改
        myCache.putIfAbsent(1L, "hehe"); //如果不存在，则更改

        String value = myCache.get(1L); //获取
        System.out.println(value);
        System.out.println(myCache.get(2L)); //获取不存在的key ： null

//        myCache.put(null, null); //k,v都不能是null！

        cacheManager.removeCache("preConfigured"); //不止是移除cache，还会关闭它，释放资源！

        cacheManager.close(); //关掉所有的cache，并释放资源
    }

    // try-with-resources  推荐，这样就不担心忘记close了。 -- 当然仅适合自己玩的情况。
    @Test
    public void r2(){
        // 直接build(true) ，实例化 并 初始化CacheManager！
        try(CacheManager cacheManager =
                    CacheManagerBuilder
                            .newCacheManagerBuilder()
                            .withCache("pre",
                                    CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(3)))
                            .build(true)){
            Cache<Long, String> pre = cacheManager.getCache("pre", Long.class, String.class);

            // 因为设置了存储的max，所以只能存储3条。新的会取代旧的。
            for(int i = 0; i < 10; i++){
                pre.put((long) i, "no." + i);
            }
            pre.forEach(longStringEntry -> {
                System.out.println(longStringEntry.getKey() + "  -  " + longStringEntry.getValue());
            });
        }
    }
}
