package com.lfc.wxadminweb.common.shiro;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;


public class MySessionDAO extends AbstractSessionDAO {

	private JedisShiroSessionRepository shiroSessionRepository;

	@Override
	public void update(Session session)  {
		getShiroSessionRepository().saveSession(session);
	}

	@Override
	public void delete(Session session) {
		if (session == null) {
			return;
		}
		Serializable id = session.getId();
		if (id != null) {
			getShiroSessionRepository().deleteSession(id);
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return getShiroSessionRepository().getAllSessions();
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		getShiroSessionRepository().saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return getShiroSessionRepository().getSession(sessionId);
	}

	public JedisShiroSessionRepository getShiroSessionRepository() {
		return shiroSessionRepository;
	}

	public void setShiroSessionRepository(
			JedisShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}

}
