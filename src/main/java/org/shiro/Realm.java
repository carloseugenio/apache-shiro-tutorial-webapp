package org.shiro;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.AuthenticationToken;

public class Realm extends AuthorizingRealm {

	public Realm() {
		setName("MY_REALM");
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("Creating AuthorizationInfo for principals: " + principals);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission("create");
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		System.out.println("Creating authentication info for token: " + token);
		SimpleAccount sai = new SimpleAccount("USER_CARLOS", "123", "MY_REALM");
		return sai;
	}
}