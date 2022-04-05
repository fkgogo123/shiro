package com.zfk;

import com.zfk.realm.CustomerMd5Realm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

public class TestCustomerMd5RealmAuthenticator {
    public static void main(String[] args) {
        // 创建 安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 将 Realm对象 注入 安全管理器
        CustomerMd5Realm realm = new CustomerMd5Realm();
        // =================================================================
        // 默认的 凭证匹配为equals方法，可以通过set方法指定 凭证匹配方法 (密码验证规则)
        // 设置 Relam 使用 Md5 哈希匹配方法
        // 若AuthenticationInfo中返回了盐数据，则shiro会自动对输入密码进行盐值处理
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 声明：使用的算法
        credentialsMatcher.setHashAlgorithmName("md5");
        // 声明：散列次数
        // credentialsMatcher.setHashIterations(1024);
        realm.setCredentialsMatcher(credentialsMatcher);
        // =================================================================
        securityManager.setRealm(realm);
        // 将 安全管理器 注入 全局工具类
        SecurityUtils.setSecurityManager(securityManager);
        // 从工具类获取用户
        Subject subject = SecurityUtils.getSubject();
        // 获取token
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
        // 登录认证
        try {
            subject.login(token);
            System.out.println("登录成功");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        }

        if (subject.isAuthenticated()) {
            // 1. 基于角色权限控制
            // ①是否具有指定角色
            System.out.println(subject.hasRole("admin"));
            // ②是否具有所有的角色
            System.out.println(subject.hasAllRoles(Arrays.asList("admin", "user")));
            // ③是否具有其中一个角色
            boolean[] booleans = subject.hasRoles(Arrays.asList("admin", "user"));
            for (boolean b: booleans) System.out.println(b);

            System.out.println("====================================");

            // 基于权限字符串的访问， 资源标识符：操作：资源类型
            System.out.println(subject.isPermitted("user:*:*"));
            System.out.println(subject.isPermitted("user:*:01"));
            System.out.println(subject.isPermitted("product:create:01"));

            // 分别具有哪些权限
            boolean[] permitted = subject.isPermitted("user:*:01", "order:*:10");
            for (boolean b : permitted) System.out.println(b);

            // 是否同时具有哪些权限
            System.out.println(subject.isPermittedAll("user:*:01", "product:create:10"));

        }
    }
}
