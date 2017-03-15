package com.lfc.wxadminweb.common.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.BinaryJedisCluster;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import com.lfc.wxadminweb.common.constant.Constants.SYSTEM_PROP_CONFIG;

public class JedisUtil {
	private static final  Logger LOGGER = LoggerFactory.getLogger(JedisUtil.class);

	private static Set<HostAndPort> jedisClusterNodes = new HashSet<>();
	private static JedisCluster jedisCluster = null;
	private static BinaryJedisCluster binaryCluster = null;
	
	
	private JedisUtil(){}
	/**
	 * JedisCluster
	 * @return
	 * @throws Exception
	 */
	public static JedisCluster getCluster() throws Exception {
		if (jedisCluster == null) {
			synchronized (JedisUtil.class) {
				GenericObjectPoolConfig config = new GenericObjectPoolConfig();
				config.setMaxTotal(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MAX_TOTAL));
				config.setMaxIdle(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MAX_IDLE));
				config.setMinIdle(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MIN_IDLE));
				
				jedisCluster = new JedisCluster(getHostNodes(), 2000, 60, config);
			}
		}
		return jedisCluster;
	}

	/**
	 * BinaryJedisCluster
	 * 
	 * @return
	 * @throws Exception
	 */
	public static BinaryJedisCluster getBinaryCluster() throws Exception{
		if (binaryCluster == null) {
			synchronized (JedisUtil.class) {
				GenericObjectPoolConfig config = new GenericObjectPoolConfig();
				config.setMaxTotal(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MAX_TOTAL));
				config.setMaxIdle(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MAX_IDLE));
				config.setMinIdle(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MIN_IDLE));
				
				binaryCluster = new BinaryJedisCluster(getHostNodes(), 2000, 60 ,config);
			}
		}
		return binaryCluster;
	}
	
	public static Set<HostAndPort> getHostNodes() throws Exception {
		LOGGER.info("getHostNodes","获取redisNodes");
		if (!jedisClusterNodes.isEmpty()) {
			return jedisClusterNodes;
		}
		String redisAddrCfg = PropertiesUtil.getString(SYSTEM_PROP_CONFIG.REDIS_ADDR_CFG);
		if (StringUtils.isEmpty(redisAddrCfg) || redisAddrCfg.split(SYSTEM_PROP_CONFIG.REDIS_CFG_SPLIT).length == 0) {
			throw new Exception("System.properties读取EDIS_ADDR_CFG配置错误");
		}
		String[] addrs = redisAddrCfg.split(SYSTEM_PROP_CONFIG.REDIS_CFG_SPLIT);
		for (String addr : addrs) {
			String regex="((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))):(\\d+)";
			if(!addr.matches(regex)){
				throw new Exception("System.properties读取EDIS_ADDR_CFG配置错误");
			}
			String[] ipAndPort = addr.split(":");
			
			jedisClusterNodes.add(new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1])));
		}
		
		return jedisClusterNodes;
	}
	
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 100000; i++) {
			new Thread(
				new Runnable() {
					@Override
					public void run() {
						try {
							for (int j = 0; j < 10; j++) {
							 long id = getCluster().incr("nihaoa");
							LOGGER.info("main",Thread.currentThread().getName() + " " + id);
							}
						} catch (Exception e) {
							LOGGER.error("main", e.getMessage(), e);
						}
					}
				}
			,Integer.toString(i)).start();
			
		}
}
}

