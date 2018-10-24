package test.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap是一个线程安全的HashTable，并提供了一组和HashTable功能相同但是线程安全的方法。
 * ConcurrentHashMap可以做到读取数据不加锁，提高了并发能力。
 * 我们先不考虑内存元素回收或者在保存数据会出现内存溢出的情况，我们用ConcurrentHashMap模拟本地缓存，当在高并发环境一下，会出现一些什么问题？
 * <p>
 * Created by zengbin on 2017/9/13.
 */
public class TestConcurrentHashMapCache<K, V> {
	private final ConcurrentHashMap<K, V> cacheMap = new ConcurrentHashMap<>();

	/**
	 * 获取缓存
	 * 示例方法
	 *
	 * @param keyValue
	 * @param ThreadName
	 * @return
	 */
	public Object getCache(K keyValue, String ThreadName){
		System.out.println("ThreadName getCache==============" + ThreadName);
		Object value;
		// 先从缓存获取数据
		value = cacheMap.get(keyValue);
		// 如果缓存没有的话，再把数据放到缓存
		if(value == null){
			return putCache(keyValue, ThreadName);
		}
		return value;
	}

	/**
	 * @param keyValue
	 * @param ThreadName
	 * @return
	 */
	private Object putCache(K keyValue, String ThreadName){
		System.out.println("ThreadName 执行业务数据并返回处理结果的数据（访问数据库等）==============" + ThreadName);

		@SuppressWarnings( "unchecked" )
		V value = (V) "dataValue"; // 模拟获取到的数据

		// 把数据放到缓存
		cacheMap.put(keyValue, value);

		return value;
	}


	public static void main(String[] args){
		final TestConcurrentHashMapCache<String, String> cache = new TestConcurrentHashMapCache<>();

		Thread t1 = new Thread(() -> {
			System.out.println("T1======start========");
			Object value = cache.getCache("key", "T1");
			System.out.println("T1 value==============" + value);
			System.out.println("T1======end========");
		});

		Thread t2 = new Thread(() -> {
			System.out.println("T2======start========");
			Object value = cache.getCache("key", "T2");
			System.out.println("T2 value==============" + value);
			System.out.println("T2======end========");
		});

		Thread t3 = new Thread(() -> {
			System.out.println("T3======start========");
			Object value = cache.getCache("key", "T3");
			System.out.println("T3 value==============" + value);
			System.out.println("T3======end========");
		});

		t1.start();
		t2.start();
		t3.start();

	}

}
