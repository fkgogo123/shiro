package com.zfk;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.nio.charset.StandardCharsets;

public class TestMd5 {
    public static void main(String[] args) {
        // 创建一个 md5 算法
        // Md5Hash md5Hash = new Md5Hash();
        // 不推荐使用set方法进行赋值
        // md5Hash.setBytes("123".getBytes());
        // String s = md5Hash.toHex();
        // System.out.println(s);

        // md5: 使用构造方法的形式进行赋值
        Md5Hash md5Hash = new Md5Hash("123456");
        System.out.println(md5Hash.toHex());

        // md5+salt: 默认是把盐加在后边
        Md5Hash md5Hash1 = new Md5Hash("123456", "x0*7sdf");
        System.out.println(md5Hash1.toHex());

        // md5 + salt + hash 散列
        Md5Hash md5Hash2 = new Md5Hash("123456", "x0*7sdf", 1024); // 散列1024次
        System.out.println(md5Hash2.toHex());
    }
}
