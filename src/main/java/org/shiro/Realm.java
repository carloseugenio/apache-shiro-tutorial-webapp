package org.shiro;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

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
			SimplePrincipalCollection spc = new SimplePrincipalCollection();
			Map<String, Object> mapCredentials = new HashMap<String, Object>();
			mapCredentials.put("givenName", token.getPrincipal());
			spc.add(mapCredentials, "MY_REALM");
			SimpleAccount account = new SimpleAccount(spc, token.getCredentials());
			System.out.println("Submitted token info is valid. Returning a valid account...: " + account);
			return account;
		}
		System.out.println("Submitted Token is not valid. Returning null");
		return null;
	}
}