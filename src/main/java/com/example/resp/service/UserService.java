package com.example.resp.service;

import com.example.resp.entity.User;

public interface UserService {

    /**
     * 注册事务
     * @param user 传入的用户对象，主键id要为空
     * */
    User registerService (User user);

    /**
     * 登录事务
     * @param account 账号
     * @param password 密码
     * @return User
     * */
    User loginService(String account, String password);
}
