package test.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by 张少昆 on 2017/9/13.
 */
public class TestGuavaCache<K, V> {
	private Cache<K, V> cache = CacheBuilder.newBuilder().maximumSize(2).expireAfterWrite(10, TimeUnit.MINUTES).build();

	public Object getCache(K keyValue, final String ThreadName){
		Object value = null;
		try{
			System.out.println("ThreadName getCache==============" + ThreadName);
			//从缓存获取数据
			value = cache.get(keyValue, () -> {
				System.out.println("ThreadName 执行业务数据并返回处理结果的数据（访问数据库等）==============" + ThreadName);
				return (V) "dataValue";
			});
		} catch(ExecutionException e){
			e.printStackTrace();
		}
		return value;
	}


	public static void main(String[] args){
		final TestGuavaCache<String, String> TestGuavaCache = new TestGuavaCache<>();


		Thread t1 = new Thread(() -> {
			System.out.println("T1======start========");
			Object value = TestGuavaCache.getCache("key", "T1");
			System.out.println("T1 value==============" + value);
			System.out.println("T1======end========");
		});

		Thread t2 = new Thread(() -> {
			System.out.println("T2======start========");
			Object value = TestGuavaCache.getCache("key", "T2");
			System.out.println("T2 value==============" + value);
			System.out.println("T2======end========");
		});

		Thread t3 = new Thread(() -> {
			System.out.println("T3======start========");
			Object value = TestGuavaCache.getCache("key", "T3");
			System.out.println("T3 value==============" + value);
			System.out.println("T3======end========");
		});

		t1.start();
		t2.start();
		t3.start();

	}


}