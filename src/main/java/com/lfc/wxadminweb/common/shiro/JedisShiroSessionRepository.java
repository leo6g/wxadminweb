package com.lfc.wxadminweb.common.shiro;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lfc.wxadminweb.common.utils.JedisUtil;
import com.lfc.wxadminweb.common.utils.SerializeUtil;




public class JedisShiroSessionRepository {

	private static final Logger logger = LoggerFactory
			.getLogger(JedisShiroSessionRepository.class);
	private static final String REDIS_SHIRO_SESSION = "SHIRO-SESSION:";
	private static final int EXPIRE_TIME = 600;
	public void saveSession(Session session) {
		if (session == null || session.getId() == null)
			throw new NullPointerException("session is empty");
		try {
			String sessionKey = buildRedisSessionKey(session.getId());
			byte[] key = SerializeUtil.serialize(sessionKey);
			byte[] value = SerializeUtil.serialize(session);
			Long sessionTimeOut = session.getTimeout() / 1000 + EXPIRE_TIME;
			logger.debug("SESSION_REDIS save, key = " + sessionKey + "    "+sessionTimeOut);
			JedisUtil.getBinaryCluster().setex(key, sessionTimeOut.intValue(), value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("save session error");
		}
	}

	public void deleteSession(Serializable id) {
		if (id == null) {
			throw new NullPointerException("session id is empty");
		}
		try {
			System.out.println("SESSION_REDIS del, key = " + id );
			JedisUtil.getBinaryCluster().del(SerializeUtil.serialize(buildRedisSessionKey(id)));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("delete session error");
		}
	}

	public Session getSession(Serializable id) {
		if (id == null)
			throw new NullPointerException("session id is empty");
		Session session = null;
		try {
			String sessionKey = buildRedisSessionKey(id);
			logger.debug("SESSION_REDIS get, key = " + sessionKey);
			byte[] value = JedisUtil.getBinaryCluster().get(SerializeUtil.serialize(sessionKey));
			session = SerializeUtil.deserialize(value, Session.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get session error");
		}
		return session;
	}

	public Collection<Session> getAllSessions() {
		System.out.println("get all sessions");
		return null;
	}

	private String buildRedisSessionKey(Serializable sessionId) {
		return REDIS_SHIRO_SESSION + sessionId;
	}
}