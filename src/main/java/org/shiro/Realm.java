package org.shiro;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.Arrays;

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
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken tk) {
		UsernamePasswordToken token = (UsernamePasswordToken) tk;
		System.out.println("Creating authentication info for token: " + token);
		System.out.println("Submitted principal: " + token.getPrincipal());
		char[] pwd = {'1', '2', '3'};
		char[] credentials = (char[])token.getCredentials();

		try {
			System.out.println("Submitted credentials: " + Arrays.toString(credentials));
			System.out.println("PWD: " + Arrays.toString(pwd));
			System.out.println("Equals?: " + Arrays.equals(pwd, credentials));
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}
		if ("admin".equals(token.getPrincipal()) && Arrays.equals(pwd, credentials)) {
			SimpleAccount account = new SimpleAccount(token.getPrincipal(), token.getCredentials(), "MY_REALM");
			System.out.println("Submitted token info is valid. Returning a valid account...: " + account);
			return account;
		}
		System.out.println("Submitted Token is not valid. Returning null");
		return null;
	}
}