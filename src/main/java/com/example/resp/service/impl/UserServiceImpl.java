package com.example.resp.service.impl;

import com.example.resp.repostitory.UserDao;
import com.example.resp.entity.User;
import com.example.resp.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // 数据源
    @Resource
    private UserDao userDao;

    // 实现注册事务
    @Override
    public User registerService(User user) {
        //
        if(userDao.findByAccount(user.getAccount()) != null) {
            return null;
        }
        else {
            //返回一个创建好的用户对象，id为数据库自增的id， 发送到数据库链接类里，并且保存进数据库中
            return userDao.save(user);
        }
    }

    //实现登录事务
    @Override
    public User loginService(String account, String password) {
        //通过获取到的account和password查找user
        return userDao.findByAccountAndPassword(account, password);
    }
}
