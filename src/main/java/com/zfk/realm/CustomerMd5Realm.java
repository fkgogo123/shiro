package com.zfk.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomerMd5Realm extends AuthorizingRealm {

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 一个主体可能有多个身份，组成一个身份集合。但只能有一个主身份。（唯一标识主体的字段）。
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("身份信息：" + primaryPrincipal);

        // 根据主身份信息（此处是用户名）获取当前用户的角色信息，以及权限信息。 比如zhangsan有admin，user两个身份。
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 将数据库中查出来的权限信息赋值给权限对象。（这里是造的假数据）
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addRole("user");

        simpleAuthorizationInfo.addStringPermission("user:*:01"); // 对用户资源01号，具有所有权限
        simpleAuthorizationInfo.addStringPermission("product:create"); // 对商品具有创建权限
        return simpleAuthorizationInfo;
    }

    // 认证
    // 获得数据库账户认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        if ("zhangsan".equals(principal)) {
            // 参数1：数据库返回的用户名
            // 参数2：数据库中md5+salt的结果
            // 参数3：数据库返回的随机盐
            // 参数4：realm的名字
            // 需要从数据库中查到的数据。（这里造的假数据）
            return new SimpleAuthenticationInfo("zhangsan",
                    "3e1ade50ccffdd413e6ea7f770e31c17",
                    ByteSource.Util.bytes("x0*7sdf"),
                    this.getName());
        }
        return null;
    }
}
