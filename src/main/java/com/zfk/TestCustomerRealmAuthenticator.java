package com.zfk;

import com.zfk.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;


public class TestCustomerRealmAuthenticator {

    public static void main(String[] args) {
        // 创建安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 注入Realm
        securityManager.setRealm(new CustomerRealm());
        // 全局工具类，配置管理器
        SecurityUtils.setSecurityManager(securityManager);
        // 全局工具类，获取subject
        Subject subject = SecurityUtils.getSubject();
        // 创建token
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
        // 执行认证
        try {
            subject.login(token);
            System.out.println("认证状态：" + subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码不正确");
        }
    }

}
