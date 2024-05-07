package com.example.resp.service.impl;

import com.example.resp.entity.UserData;
import com.example.resp.repostitory.UserDataDao;
import com.example.resp.repostitory.projuection.UserDatanameProjection;
import com.example.resp.service.UserDataService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDataServiceImpl implements UserDataService {
    //数据源
    @Resource
    private UserDataDao userDataDao;

    //实现的上传用户数据的事务服务
    @Override
    public UserData setUserDataService(UserData userData) {
        //如果同一个userid下拥有相同名字的数据源，则上传失败

        //将数据保存至数据库
        return userDataDao.save(userData);
    }


    //实现的查找全部用户数据的事务服务
    @Override
    public List<UserData> getUserDataService(Long userid) {
        //传入userid，查找数据
        return userDataDao.findAllByUserid(userid);
    }

    //删除数据事务服务

    @Override
    @Transactional
    public void deleteUserDataService(Long userid, Long dataid) {
        userDataDao.deleteByUseridAndDataid(userid, dataid);
    }

    //重命名事务服务
    @Override
    public UserData renameUserDataService(Long dataid) {
        return userDataDao.findByDataid(dataid);
    }



}
