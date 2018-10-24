package test.ehcache;

import org.ehcache.PersistentCacheManager;
import org.ehcache.clustered.client.config.builders.ClusteringServiceConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;

import java.net.URI;

/**
 * TODO: 需要搭建terracotta服务器！
 * http://www.ehcache.org/documentation/3.4/clustered-cache.html#starting-server
 * <p>
 * Created by zengbin on 2017/12/28.
 */
public class JavaClusterConfig_1 {
    public void r1(){
        CacheManagerBuilder<PersistentCacheManager> clusteredCacheManagerBuilder =
                CacheManagerBuilder
                        .newCacheManagerBuilder()
                        .with(ClusteringServiceConfigurationBuilder
                                      // TODO 需要搭建terracotta服务器！
                                      .cluster(URI.create("terracotta://localhost/my-application"))
                                      .autoCreate());
        PersistentCacheManager cacheManager = clusteredCacheManagerBuilder.build(true);

        cacheManager.close();
    }
}
