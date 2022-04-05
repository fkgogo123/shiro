package com.zfk;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;


public class TestAuthenticator {
    public static void main(String[] args) {

        int[] a = new int[5];

        // 1.创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 2.给安全管理器设置 realm
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));
        // 3.SecurityUtils给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        // 4.关键对象
        Subject subject = SecurityUtils.getSubject();
        // 5.创建令牌 (对用户表单提价的信息进行token创建)
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");

        // 用户认证，（与数据库中的信息进行匹配，认证）
        try {
            System.out.println("认证状态：" + subject.isAuthenticated());
            subject.login(token);
            System.out.println("认证状态：" + subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名不存在~ ");
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误！");
        }

    }
}

// - DisabledAccountException（帐号被禁用）
// - LockedAccountException（帐号被锁定）
// - ExcessiveAttemptsException（登录失败次数过多）
// - ExpiredCredentialsException（凭证过期）等
