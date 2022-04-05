package com.zfk.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义Realm实现。
 *
 */
public class CustomerRealm extends AuthorizingRealm {

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 认证
    // AuthenticationInfo 是数据库中的用户信息, 一般用SimpleAuthenticationInfo实现类。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String) token.getPrincipal();
        System.out.println(principal);
        // 根据身份信息使用 jdbc mybatis （userservice）查询相关数据库
        if ("zhangsan".equals(principal)) {
            // 参数1：数据库中的用户名 参数2：数据库中的凭证  参数3：提供当前Realm的名字
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("zhangsan", "123456", this.getName());
            return simpleAuthenticationInfo;
        }

        return null;
    }
}
