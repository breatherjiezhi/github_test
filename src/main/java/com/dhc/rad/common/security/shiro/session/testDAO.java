package com.dhc.rad.common.security.shiro.session;

import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

public class testDAO extends EnterpriseCacheSessionDAO implements SessionDAO {

	@Override
	public Collection<Session> getActiveSessions(boolean includeLeave) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Session> getActiveSessions(boolean includeLeave,
			Object principal, Session filterSession) {
		// TODO Auto-generated method stub
		return null;
	}

}
