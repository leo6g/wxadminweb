package com.lfc.wxadminweb.common.utils;


import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Protocol;

public class CacheUtil {

	private static final String IS_OK = Protocol.Keyword.OK.name();
	
	/**
	 * 放入缓存,永久生效
	 * 
	 * @param cacheKey
	 *            缓存key值
	 * @param value
	 *            缓存的value值
	 * @throws Exception
	 * @ReturnType true=成功
	 */
	public static boolean put2Cache(String cacheKey, String value) {
		String result = null;
		try {
			result = JedisUtil.getCluster().set(cacheKey, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return IS_OK.equals(result);
	}

	/**
	 * 放入缓存,并设置生效时间
	 * 
	 * @param cacheKey
	 *            缓存的key值
	 * @param value
	 *            缓存的value值
	 * @param seconds
	 *            失效时间(单位秒)
	 * @ReturnType true=成功
	 */
	public static boolean put2Cache(String cacheKey, String value, int seconds){
		String result = null;
		try {
			result = JedisUtil.getCluster().setex(cacheKey, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return IS_OK.equals(result);
	}

	/**
	 * 从缓存中获取信息
	 * 
	 * @param cacheKey
	 *            缓存的key值
	 * @return 根据cacheKey取到的值
	 * @throws Exception
	 * @ReturnType String
	 */
	public static String getFromCache(String cacheKey) {
		String result = null;
		try {
			result = JedisUtil.getCluster().get(cacheKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将Map放入缓存
	 * 
	 * @param cacheKey
	 *            缓存的key值
	 * @param map
	 *            待放入的值
	 * @return "OK" 成功
	 * @throws Exception
	 */
	public static String putMap(String cacheKey, Map<String, String> map) {
		String result = null;
		try {
			result = JedisUtil.getCluster().hmset(cacheKey, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 从缓存中读取map
	 * 
	 * @param cacheKey
	 *            缓存的key值
	 * @return 如果存在key值，则返回Map对象，如果不存在，则返回null
	 * @throws Exception
	 */
	public static Map<String, String> getMap(String cacheKey) {
		Map<String,String> resultMap = new HashMap<String,String>();
		try {
			resultMap = JedisUtil.getCluster().hgetAll(cacheKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 从缓存中删除指定key
	 * 
	 * @param cacheKey
	 *            缓存的key值
	 * @throws Exception
	 */
	public static void delKey(String... cacheKey) {
		try {
			JedisUtil.getCluster().del(cacheKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 从缓存中删除指定key
	 * 
	 * @param cacheKey
	 *            缓存的key值的filed
	 * @throws Exception
	 */
	public static void hdel(String cacheKey,String... field) {
		try {
			JedisUtil.getCluster().hdel(cacheKey, field);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取值
	 * @param args
	 * @throws Exception
	 */
	public static String hget(String key,String filed){
		String result = null;
		try {
			result = JedisUtil.getCluster().hget(key,filed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 存值
	 * @param args
	 * @throws Exception
	 */
	public static void hset(String key,String filed,String value){
		try {
			JedisUtil.getCluster().hset(key,filed,value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断是否有指定的key
	 * @param key
	 * @throws Exception
	 */
	public static boolean hasKey(String key){
		boolean isHas = false;
		try {
			isHas = JedisUtil.getCluster().exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isHas;
	}
	/**
	 * 指定的key的value加1
	 * @param key
	 * @throws Exception
	 */
	public static long incr(String key){
		long newVal = 0;
		try {
			newVal = JedisUtil.getCluster().incr(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newVal;
	}
	public static void main(String[] args) throws Exception {
//		put2Cache("lixiang", "aaa", 60);
//		System.out.println(getFromCache("lixiang"));
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("lixiang", "aaaaaaa");
//		map.put("sdfa", "bbbbaaa");
//		map.put("sdfadfafa", "1234135");
//		
//		putMap("lee", map);
//		
//		System.out.println(JedisUtil.getCluster().hget("lee", "lixiang"));
//		hset("a", "b","s");
//		System.out.println(hget("a", "b"));
		
		put2Cache("a", "5");
		System.out.println(incr("a"));
		
		
	}
}
